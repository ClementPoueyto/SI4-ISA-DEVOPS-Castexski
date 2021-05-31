package fr.unice.polytech.alert.components;

import fr.unice.polytech.alert.interfaces.MailSender;
import fr.unice.polytech.alert.utils.MailAPI;
import fr.unice.polytech.analytics.interfaces.AnalyticsFinder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import java.util.Timer;
import java.util.TimerTask;

@Startup
@Stateless
public class AlertBean implements MailSender {

    MailAPI mailAPI;

    @EJB
    public AnalyticsFinder analytics;

    @Override
    public void useMailerAPIReference(MailAPI mailAPI){
        this.mailAPI = mailAPI;
    }

    @PostConstruct
    public void init() {
        mailAPI = new MailAPI(analytics);
        TimerTask task = new TimerTask() {
            public void run() {
                dailyMail();
            }
        };
        Timer timer = new Timer("Timer");

//        long delay = 1000L;
//        timer.schedule(task, delay);
    }

//    @Schedule(hour = "*/24", minute = "*", second = "*")
    public boolean dailyMail() {
        System.out.println("Sending daily analytics mail...");
        return mailAPI.sendMailToMayor();
    }
}
