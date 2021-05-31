package fr.unice.polytech.utils;


import fr.unice.polytech.exceptions.ExternalPartnerException;
import fr.unice.polytech.entities.Customer;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankAPI {

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    private String url;

    public BankAPI(String host, String port) {
        this.url = "http://" + host + ":" + port;
    }

    public BankAPI() { this("172.16.238.10", "9090"); }
    //172.16.238.10
    public boolean performPayment(Customer customer, double value) throws ExternalPartnerException {
        // Building payment request
        JSONObject request = new JSONObject().put("CreditCard", customer.getCreditCard()).put("Amount", value);

        // Sending a Payment request to the mailbox
        Integer id;
        try {
            String str = WebClient.create(url).path("/mailbox")
                    .accept(MediaType.APPLICATION_JSON_TYPE).header("Content-Type", MediaType.APPLICATION_JSON)
                    .post(request.toString(), String.class);
            id = Integer.parseInt(str);
        } catch (Exception e) {
            throw new ExternalPartnerException(url+"/mailbox", e);
        }

        // Retrieving the payment status
        JSONObject payment;
        try {
            String response = WebClient.create(url).path("/payments/" + id).get(String.class);
            payment = new JSONObject(response);
        } catch (Exception e) {
            throw new ExternalPartnerException(url + "payments/" + id, e);
        }
        // Assessing the payment status
        log.log(Level.INFO, payment.toString());
        return (payment.getInt("Status") == 0);
    }

}
