package fr.unice.polytech.analytics.components;

import fr.unice.polytech.analytics.interfaces.AnalyticsFinder;
import fr.unice.polytech.analytics.interfaces.AnalyticsUpdate;
import fr.unice.polytech.entities.*;
import fr.unice.polytech.utils.ItemType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class AnalyticsBean implements AnalyticsUpdate, AnalyticsFinder {

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;


    @Override
    public void updateAnalytics(AnalyticsVisit analytics) {
        AnalyticsVisit an = manager.find(AnalyticsVisit.class,analytics.getId());
        if(an!=null){
            an.setCounter(an.getCounter()+analytics.getCounter());
        }
        else{

            analytics.setCounter(analytics.getCounter());
            manager.persist(analytics);
            log.log(Level.INFO, "## Analytics : "+analytics.toString());

        }
    }

    @Override
    public void updateAnalytics(AnalyticsPass analytics) {
        AnalyticsPass an = manager.find(AnalyticsPass.class,analytics.getId());
        if(an!=null){
            an.setCounter(an.getCounter()+analytics.getCounter());
        }
        else{

            analytics.setCounter(analytics.getCounter());
            manager.persist(analytics);
            log.log(Level.INFO, "## Analytics : "+analytics.toString());

        }
    }

    @Override
    public void updateAnalytics(Set<OrderItem> cart) {
        for (OrderItem it : cart) {
            if(it.getType().equals(ItemType.PASS)&& it.getQuantity()>0) {
                AnalyticsPass analytics = new AnalyticsPass((PassItem) it);
                updateAnalytics(analytics);
            }
        }
    }


    @Override
    public List<AnalyticsPass> getPassAnalyticsByDate(int day, int month, int year) {
        return manager
                .createQuery("Select DISTINCT c from AnalyticsPass c where c.dayOfMonth="+day+
                        " and c.month="+month+" and c.year ="+year, AnalyticsPass.class)
                .getResultList();
    }



    @Override
    public List<AnalyticsVisit> getVisitAnalyticsByDate(int day, int month, int year) {
        return manager
                .createQuery("Select DISTINCT c from AnalyticsVisit c where c.dayOfMonth="+day+
                        " and c.month="+month+" and c.year ="+year, AnalyticsVisit.class)
                .getResultList();
    }

    @Override
    public String getDailyAnalytics(int day, int month, int year) {
        List<AnalyticsPass> analytics = getPassAnalyticsByDate(day,month,year);
        List<AnalyticsVisit> analyticsVisit = getVisitAnalyticsByDate(day,month,year);

        String mail = "Rapport vente forfait du "+day+"/"+month+"/"+year+" :";
        int counter=0;
        for(AnalyticsPass a : analytics){
            counter+=a.getCounter();
        }
        mail+="<br/>   "+counter+" forfaits vendus";
        mail+="<br/>Rapport frequentation : ";
        counter=0;
        for(AnalyticsVisit a : analyticsVisit){
            counter+=a.getCounter();
        }
        mail+="<br/>   "+counter+" passages dans la station";
        return mail;
    }

}
