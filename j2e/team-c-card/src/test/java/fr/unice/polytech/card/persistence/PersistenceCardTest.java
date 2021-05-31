package fr.unice.polytech.card.persistence;

import arquillian.AbstractArquillianTest;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.entities.Pass;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;


@RunWith(Arquillian.class)
public class PersistenceCardTest extends AbstractArquillianTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpContext()  {
    }

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager entityManager;

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void testCardStorage() throws Exception {
        Card c = new Card();
        entityManager.persist(c);
        String idCard = c.getId();
        Card stored = entityManager.find(Card.class, idCard);
        assertEquals(c, stored);
    }

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void testPassStorage() throws Exception {
        Pass c = new Pass();
        entityManager.persist(c);
        String idPass = c.getId();
        Pass stored = entityManager.find(Pass.class, idPass);
        assertEquals(c, stored);
    }

}
