package fr.unice.polytech.analytics.webservices;

import fr.unice.polytech.analytics.interfaces.AnalyticsFinder;
import fr.unice.polytech.entities.AnalyticsPass;
import fr.unice.polytech.entities.AnalyticsVisit;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;

@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/analytics")
@Stateless(name = "AnalyticsWS")
public class AnalyticsWebServiceImpl implements AnalyticsWebService {

    @EJB private AnalyticsFinder finder;


    @Override
    public List<AnalyticsVisit> getVisitAnalyticsByDay(int day, int month, int year) {
        return finder.getVisitAnalyticsByDate(day,month, year);
    }

    @Override
    public List<AnalyticsPass> getPassAnalyticsByDay(int day, int month, int year) {
        return finder.getPassAnalyticsByDate(day,month, year);

    }

    @Override
    public String getDailyEmail(int day, int month, int year) {
        return finder.getDailyAnalytics(day,month,year);
    }
}
