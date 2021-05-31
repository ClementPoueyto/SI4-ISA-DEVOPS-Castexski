package fr.unice.polytech.utils;


import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.exceptions.ExternalPartnerException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

public class WeatherAPI {

    private String url;

    public WeatherAPI(String host, String port) {
        this.url = "http://" + host + ":" + port;
    }

    public WeatherAPI() { this("172.16.238.10", "9091"); }
    //172.16.238.10

    public WeatherStatus getWeather() throws ExternalPartnerException {
        // Retrieving the weather status
        JSONObject weather;
        try {
            String response = WebClient.create(url).path("/weather").get(String.class);
            weather = new JSONObject(response);
        } catch (Exception e) {
            throw new ExternalPartnerException(url + "weather", e);
        }
        // Assessing the payment status
        return WeatherStatus.values()[weather.getInt("Status")];
    }

}
