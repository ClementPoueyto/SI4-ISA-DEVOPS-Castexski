package fr.unice.polytech.analytics.business;

import arquillian.AbstractArquillianTest;
import fr.unice.polytech.analytics.interfaces.AnalyticsFinder;
import fr.unice.polytech.analytics.interfaces.AnalyticsUpdate;
import fr.unice.polytech.entities.*;
import fr.unice.polytech.utils.PassType;
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
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class AnalyticsPassTest extends AbstractArquillianTest {



    @Inject
    private UserTransaction utx;

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;


    @EJB
    AnalyticsFinder finder;

    @EJB
     AnalyticsUpdate update;

    private AnalyticsPass analytic;

    @Before
    public void setUpContext() {
        analytic = new AnalyticsPass();

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

        AnalyticsPass toCheck = manager.find(AnalyticsPass.class, analytic.getId());
        assertEquals(3,toCheck.getCounter());

        PassItem it = new PassItem();
        it.setPassType(PassType.WEEK);

        AnalyticsPass an = new AnalyticsPass(it);
        an.setCounter(2);
        update.updateAnalytics(an);

        toCheck = manager.find(AnalyticsPass.class, an.getId());
        assertEquals(2,toCheck.getCounter());

        manager.remove(an);
    }

    @Test
    public void updateAnalytics(){
        analytic.setCounter(3);
        update.updateAnalytics(analytic);
        AnalyticsPass toCheck = manager.find(AnalyticsPass.class, analytic.getId());
        assertEquals(3,toCheck.getCounter());

        AnalyticsPass an = new AnalyticsPass();
        an.setCounter(5);
        update.updateAnalytics(an);
        assertEquals(8,toCheck.getCounter());

        manager.remove(an);
    }

    @Test
    public void sameId(){
        AnalyticsPass a1 = new AnalyticsPass();
        AnalyticsPass a2 = new AnalyticsPass();

        assertEquals(a1.getId(),a2.getId());

        PassItem item = new PassItem();
        item.setPassType(PassType.WEEK);
        //item.setPassZoneType(PassZone.BEGINNER);

        AnalyticsPass a3 = new AnalyticsPass(item);
        AnalyticsPass a4 = new AnalyticsPass(item);

        assertEquals(a3.getId(),a4.getId());
    }
    @Test
    @Transactional(TransactionMode.COMMIT)
    public void testAnalyticsStorageFromCart()  {
        Set<OrderItem> items = new HashSet<>();
        PassItem it = new PassItem();
        PassItem it3 = new PassItem();
        //it3.setPassZoneType(PassZone.BEGINNER);

        items.add(it);
        items.add(it3);

        update.updateAnalytics(items);

        AnalyticsPass an = new AnalyticsPass(it);
        AnalyticsPass an3 = new AnalyticsPass(it3);

        assertEquals(1, manager.find(AnalyticsPass.class, an.getId()).getCounter());
        assertEquals(1, manager.find(AnalyticsPass.class, an3.getId()).getCounter());

        update.updateAnalytics(items);
        update.updateAnalytics(items);

        assertEquals(3, manager.find(AnalyticsPass.class, an.getId()).getCounter());
        assertEquals(3, manager.find(AnalyticsPass.class, an3.getId()).getCounter());

        manager.remove(an);
        manager.remove(an3);
        manager.clear();

    }

    @Test
    public void getAnalyticsByDayTest(){
        LocalDateTime now = LocalDateTime.now();
        assertEquals(0, finder.getPassAnalyticsByDate(now.getDayOfMonth(), now.getMonthValue(), now.getYear()).size());

        AnalyticsPass an1 = new AnalyticsPass();
        an1.setCounter(34);

        update.updateAnalytics(an1);
        assertEquals(1, finder.getPassAnalyticsByDate(now.getDayOfMonth(), now.getMonthValue(), now.getYear()).size());

        LocalDateTime anotherday = LocalDateTime.of(2000,3,1,0,0,0);
        AnalyticsPass an2 = new AnalyticsPass(anotherday);
        update.updateAnalytics(an2);
        assertEquals(1, finder.getPassAnalyticsByDate(now.getDayOfMonth(), now.getMonthValue(), now.getYear()).size());
        assertEquals(1, finder.getPassAnalyticsByDate(1, 3, 2000).size());

        PassItem anotherPass = new PassItem();
        //anotherPass.setPassZoneType(PassZone.BEGINNER);
        AnalyticsPass an3 = new AnalyticsPass(anotherPass,anotherday);
        update.updateAnalytics(an3);

        manager.remove(an1);
        manager.remove(an2);
        manager.remove(an3);

    }

    @Test
    public void CantFindAnalyticsTest(){
        int day = 100;
        int month= 0;
        int year = 100;
        assertEquals(0,finder.getPassAnalyticsByDate(day,month,year).size());

    }

}
