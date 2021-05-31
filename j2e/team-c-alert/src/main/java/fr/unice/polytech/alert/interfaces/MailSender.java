package fr.unice.polytech.alert.interfaces;


import fr.unice.polytech.alert.utils.MailAPI;

import javax.ejb.Local;
import javax.ejb.Schedule;

@Local
public interface MailSender {

    void useMailerAPIReference(MailAPI mailAPI);

//    @Schedule(hour = "*/24", minute = "*", second = "*")
    boolean dailyMail();
}
