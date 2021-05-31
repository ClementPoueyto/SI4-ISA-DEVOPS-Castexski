package fr.unice.polytech.customer.webservices;

import fr.unice.polytech.customer.exceptions.AlreadyAssociatedCard;
import fr.unice.polytech.customer.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/customer")
public interface CustomerWebService {

    @WebMethod
    void registerCustomer(@WebParam(name = "customer_email") String customerEmail,
                          @WebParam(name = "customer_name") String customerName, @WebParam(name = "credit_card") String creditCard) throws AlreadyExistingCustomerException;

    @WebMethod
    void associateCardToCustomer(@WebParam(name = "customer") Customer customer,
                                 @WebParam(name = "card") Card card) throws AlreadyAssociatedCard;
}
