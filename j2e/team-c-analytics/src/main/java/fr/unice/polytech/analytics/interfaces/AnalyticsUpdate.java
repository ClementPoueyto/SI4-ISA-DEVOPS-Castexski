package fr.unice.polytech.analytics.interfaces;

import fr.unice.polytech.entities.AnalyticsPass;
import fr.unice.polytech.entities.AnalyticsVisit;
import fr.unice.polytech.entities.OrderItem;

import javax.ejb.Local;
import java.util.Set;

@Local
public interface AnalyticsUpdate {

    void updateAnalytics(AnalyticsVisit analytics);

    void updateAnalytics(AnalyticsPass analytics);
    void updateAnalytics(Set<OrderItem> cart);

}
