package fr.unice.polytech.alert;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

@Startup
@Stateless
public class TrafficAlert {

    @PostConstruct
    public void init() {

    }

    @Schedule(hour = "*/24", minute = "*", second = "*")
    public void dailyMail() {

    }

}
