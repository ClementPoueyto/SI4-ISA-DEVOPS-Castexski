package fr.unice.polytech.analytics.interfaces;

import fr.unice.polytech.entities.AnalyticsPass;
import fr.unice.polytech.entities.AnalyticsVisit;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AnalyticsFinder {

    List<AnalyticsPass> getPassAnalyticsByDate(int day, int month, int year);

    List<AnalyticsVisit> getVisitAnalyticsByDate(int day, int month, int year);

    String getDailyAnalytics(int day, int month, int year);
}
