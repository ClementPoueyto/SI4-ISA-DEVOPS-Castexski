package fr.unice.polytech.alert.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import fr.unice.polytech.analytics.components.AnalyticsBean;
import fr.unice.polytech.analytics.interfaces.AnalyticsFinder;
import org.joda.time.DateTime;
import org.json.JSONObject;



import javax.ejb.EJB;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MailAPI {


    public AnalyticsFinder analyticsFinder;

    public MailAPI(AnalyticsFinder analyticsFinder){
        this.analyticsFinder = analyticsFinder;
    }

    private static final Logger log = Logger.getLogger(Logger.class.getName());



    public boolean sendMailToMayor() {
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT, new Locale("FR","fr"));

        DateTime dt = new DateTime();
//        System.out.println(analytics);
        HttpResponse<String> response = null;
        try {
            String body  = "Hello dear employee, Here are your daily stats for your ski station: <br/>"+analyticsFinder.getDailyAnalytics(dt.getDayOfMonth(), dt.getMonthOfYear(), dt.getYear());
//            log.log(Level.WARNING, body);

            response = Unirest.post("https://api.pepipost.com/v5/mail/send")
                    .header("api_key", "3543003b5db6d85c8bc72f64652d5da9")
                    .header("content-type", "application/json")
                    .body("{\"from\":{\"email\":\"antoinefacq@pepisandbox.com\",\"name\":\"Castexski\"}," +
                            "\"subject\":\"[CASTEXSKI] Daily report of "+shortDateFormat.format(new Date())+"\"," +
                            "\"content\":[{\"type\":\"html\",\"value\":\""+body+"\"}],\"personalizations\":[" +
                            "{\"to\":[" +
                            "{\"email\":\"antoine.facq@etu.univ-cotedazur.fr\",\"name\":\"Antoine FACQ\"}," +
                            "{\"email\":\"clement.poueyto@etu.univ-cotedazur.fr\",\"name\":\"Clément POUEYTO\"}," +
                            "{\"email\":\"loic.bertolotto@etu.univ-cotedazur.fr\",\"name\":\"Loic BERTOLOTTO\"}," +
                            "{\"email\":\"alexandre.mazurier@etu.univ-cotedazur.fr\",\"name\":\"Alexandre MAZURIER\"}," +
                            "{\"email\":\"maeva.lecavelier@etu.univ-cotedazur.fr\",\"name\":\"Maeva LECAVELIER\"}" +
                            "]}]}")
                    .asString();

        } catch (UnirestException e) {
            e.printStackTrace();
        }
//        log.log(Level.WARNING, response.getBody());



        boolean status = (new JSONObject(response.getBody())).getString("status").equals("success");
        if(status){
            log.log(Level.INFO, "Mail was successfully sent!");
        }else{
            log.log(Level.INFO, "Mail failed to send, is API available?");

        }
        return status;

    }
}
