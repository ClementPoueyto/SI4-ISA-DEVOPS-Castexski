package fr.unice.polytech.analytics.business;

import arquillian.AbstractArquillianTest;
import fr.unice.polytech.analytics.interfaces.AnalyticsFinder;
import fr.unice.polytech.analytics.interfaces.AnalyticsUpdate;
import fr.unice.polytech.entities.AnalyticsVisit;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class AnalyticsVisitTest extends AbstractArquillianTest {



    @Inject
    private UserTransaction utx;

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;


    @EJB
    AnalyticsFinder finder;

    @EJB
     AnalyticsUpdate update;

    private AnalyticsVisit analytic;

    @Before
    public void setUpContext() {
        analytic = new AnalyticsVisit("test");

    }

    @After
    public void cleaningUp() throws Exception {
        utx.begin();
        analytic = manager.merge(analytic);
        manager.remove(analytic);
        analytic = null;
        utx.commit();
    }

    @Test
    public void createAnalytics(){
        analytic.setCounter(3);
        update.updateAnalytics(analytic);

        AnalyticsVisit toCheck = manager.find(AnalyticsVisit.class, analytic.getId());
        assertEquals(3,toCheck.getCounter());

        AnalyticsVisit an = new AnalyticsVisit("test2");
        an.setCounter(2);
        update.updateAnalytics(an);

        toCheck = manager.find(AnalyticsVisit.class, an.getId());
        assertEquals(2,toCheck.getCounter());

        manager.remove(an);
    }

    @Test
    public void updateAnalytics(){
        analytic.setCounter(3);
        update.updateAnalytics(analytic);
        AnalyticsVisit toCheck = manager.find(AnalyticsVisit.class, analytic.getId());
        assertEquals(3,toCheck.getCounter());

        AnalyticsVisit an = new AnalyticsVisit("test");
        an.setCounter(5);
        update.updateAnalytics(an);
        assertEquals(8,toCheck.getCounter());

        manager.remove(an);
    }

    @Test
    public void sameId(){
        AnalyticsVisit a1 = new AnalyticsVisit("oui");
        AnalyticsVisit a2 = new AnalyticsVisit("oui");

        assertEquals(a1.getId(),a2.getId());


        AnalyticsVisit a3 = new AnalyticsVisit("zone1");
        AnalyticsVisit a4 = new AnalyticsVisit("zone2");

        assertNotEquals(a3.getId(),a4.getId());
    }



    @Test
    public void getAnalyticsByDayTest(){
        LocalDateTime now = LocalDateTime.now();
        assertEquals(0, finder.getVisitAnalyticsByDate(now.getDayOfMonth(), now.getMonthValue(), now.getYear()).size());

        AnalyticsVisit an1 = new AnalyticsVisit("green");
        an1.setCounter(34);

        update.updateAnalytics(an1);
        assertEquals(1, finder.getVisitAnalyticsByDate(now.getDayOfMonth(), now.getMonthValue(), now.getYear()).size());

        LocalDateTime anotherday = LocalDateTime.of(2000,3,1,0,0,0);
        AnalyticsVisit an2 = new AnalyticsVisit("anothername",anotherday);
        update.updateAnalytics(an2);
        assertEquals(1, finder.getVisitAnalyticsByDate(now.getDayOfMonth(), now.getMonthValue(), now.getYear()).size());
        assertEquals(1, finder.getVisitAnalyticsByDate(1, 3, 2000).size());

        manager.remove(an1);
        manager.remove(an2);

    }

    @Test
    public void CantFindAnalyticsTest(){
        int day = 100;
        int month= 0;
        int year = 100;
        assertEquals(0,finder.getVisitAnalyticsByDate(day,month,year).size());

    }

}
