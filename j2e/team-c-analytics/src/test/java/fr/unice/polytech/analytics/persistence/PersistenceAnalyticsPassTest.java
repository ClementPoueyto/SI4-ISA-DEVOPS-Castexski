package fr.unice.polytech.analytics.persistence;

import arquillian.AbstractArquillianTest;
import fr.unice.polytech.entities.*;
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
public class PersistenceAnalyticsPassTest extends AbstractArquillianTest {


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
    public void testAnalyticsStorage() throws Exception {
        AnalyticsPass a = new AnalyticsPass();
        entityManager.persist(a);
        int idAnalytics = a.getId();
        AnalyticsPass stored = entityManager.find(AnalyticsPass.class, idAnalytics);
        assertEquals(a, stored);
        entityManager.remove(a);
    }


    @Test
    @Transactional(TransactionMode.COMMIT)
    public void testAnalyticsStorage2() throws Exception {
        AnalyticsPass a = new AnalyticsPass();
        entityManager.persist(a);
        int idAnalytics = a.getId();
        AnalyticsPass stored = entityManager.find(AnalyticsPass.class, idAnalytics);
        assertEquals(a, stored);

        AnalyticsPass a2 = new AnalyticsPass();
        a2.setPassType(PassType.WEEK);
        entityManager.persist(a2);
        int idAnalytics2 = a2.getId();
        AnalyticsPass stored2 = entityManager.find(AnalyticsPass.class, idAnalytics2);
        assertEquals(a2, stored2);

        entityManager.remove(a);
        entityManager.remove(a2);
    }



}
