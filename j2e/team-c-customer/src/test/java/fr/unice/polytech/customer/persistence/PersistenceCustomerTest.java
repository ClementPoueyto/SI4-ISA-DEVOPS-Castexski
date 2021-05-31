package fr.unice.polytech.customer.persistence;

import arquillian.AbstractArquillianTest;
import fr.unice.polytech.customer.exceptions.AlreadyAssociatedCard;
import fr.unice.polytech.customer.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.customer.interfaces.CustomerFinder;
import fr.unice.polytech.customer.interfaces.CustomerRegistry;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;


@RunWith(Arquillian.class)
public class PersistenceCustomerTest extends AbstractArquillianTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpContext()  {
    }

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager entityManager;

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void testCustomerStorage() throws Exception {
        Customer c = new Customer();			// create an empty customer
        c.setCreditCard("896983");		// setting up details
        c.setName("John Doe");
        c.setEmail("John Doe");

        entityManager.persist(c);
        String idUser = c.getId();
        // making the entity persistent
       					// an id was assigned by the persistence layer
        Customer stored = (Customer) entityManager.find(Customer.class, idUser);
        assertEquals(c, stored);				// The initial customer and the retrieved one are equals
    }


}
