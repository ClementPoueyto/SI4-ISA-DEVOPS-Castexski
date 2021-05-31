package fr.unice.polytech.analytics.persistence;

import arquillian.AbstractArquillianTest;
import fr.unice.polytech.entities.AnalyticsPass;
import fr.unice.polytech.entities.AnalyticsVisit;
import fr.unice.polytech.utils.PassType;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;


@RunWith(Arquillian.class)
public class PersistenceAnalyticsVisitTest extends AbstractArquillianTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpContext()  {
    }

    @After
    public void cleaningUp() throws Exception {
    }


    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager entityManager;


    @Test
    @Transactional(TransactionMode.COMMIT)
    public void testAnalyticsVisitStorage() throws Exception {
        AnalyticsVisit a = new AnalyticsVisit("test");
        entityManager.persist(a);
        int idAnalytics = a.getId();
        AnalyticsVisit stored = entityManager.find(AnalyticsVisit.class, idAnalytics);
        assertEquals(a, stored);
        entityManager.remove(a);
    }


    @Test
    @Transactional(TransactionMode.COMMIT)
    public void testAnalyticsVisitStorage2() throws Exception {
        AnalyticsVisit a = new AnalyticsVisit("piou");
        entityManager.persist(a);
        int idAnalytics = a.getId();
        AnalyticsVisit stored = entityManager.find(AnalyticsVisit.class, idAnalytics);
        assertEquals(a, stored);

        AnalyticsVisit a2 = new AnalyticsVisit("pioupiou");

        entityManager.persist(a2);
        int idAnalytics2 = a2.getId();
        AnalyticsVisit stored2 = entityManager.find(AnalyticsVisit.class, idAnalytics2);
        assertEquals(a2, stored2);

        entityManager.remove(a);
        entityManager.remove(a2);
    }
}
