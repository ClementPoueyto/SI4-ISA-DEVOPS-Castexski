package fr.unice.polytech.customer.business;

import arquillian.AbstractArquillianTest;
import fr.unice.polytech.card.interfaces.CardFinder;
import fr.unice.polytech.customer.exceptions.AlreadyAssociatedCard;
import fr.unice.polytech.customer.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.customer.interfaces.CustomerFinder;
import fr.unice.polytech.customer.interfaces.CustomerRegistry;
import fr.unice.polytech.entities.Customer;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.Optional;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class CustomerTest extends AbstractArquillianTest {

    @EJB
    CustomerRegistry registry;
    @EJB
    CustomerFinder finder;

    @EJB
    CardFinder cardFinder;

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;


    @Inject
    private UserTransaction utx;

    private Customer customer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpContext()  {
        customer = new Customer("clement@gmail.com", "clement");
        customer.setCreditCard("896983");		// setting up details
    }



    @After
    public void cleaningUp() throws Exception {
        utx.begin();
        Optional<Customer> toDispose = finder.findByEmail(customer.getEmail());
        toDispose.ifPresent(cust -> { Customer c = manager.merge(cust); manager.remove(c); });
        customer=null;
        utx.commit();
    }

    @Test
    public void addClient() throws Exception {
        registry.registerCustomer(customer.getEmail(),customer.getName(), "896983");
        assertTrue(finder.findByEmail(customer.getEmail()).isPresent());
    }

    @Test(expected = AlreadyExistingCustomerException.class)
    public void addClientException() throws AlreadyExistingCustomerException {
        registry.registerCustomer(customer.getEmail(), "clement", "896983");
        registry.registerCustomer(customer.getEmail(),"Maeva", "896983");

        thrown.expect(AlreadyExistingCustomerException.class);
    }

    @Test
    public void findClientByEmail() throws AlreadyExistingCustomerException {
        registry.registerCustomer(customer.getEmail(),customer.getName(), "896983");
        assertTrue(finder.findByEmail("clement@gmail.com").isPresent());
        assertEquals("clement", finder.findByEmail("clement@gmail.com").get().getName());

        registry.registerCustomer("loic@gmail.com","loic", "896983");

        assertFalse(finder.findByEmail("albert@gmail.com").isPresent());
        Optional<Customer> toDispose = finder.findByEmail("loic@gmail.com");
        toDispose.ifPresent(cust -> { Customer c = manager.merge(cust); manager.remove(c); });
    }


    @Test
    public void findClientById() throws AlreadyExistingCustomerException {
        registry.registerCustomer("maeva@gmail.com","maeva", "896983");
        Customer maeva = finder.findByEmail("maeva@gmail.com").get();
        assertTrue(finder.findById(maeva.getId()).isPresent());
        assertEquals("maeva", finder.findById(maeva.getId()).get().getName());

        Optional<Customer> toDispose = finder.findByEmail("maeva@gmail.com");
        toDispose.ifPresent(cust -> { Customer c = manager.merge(cust); manager.remove(c); });
    }

    @Test
    public void associateCardTest() throws AlreadyAssociatedCard, AlreadyExistingCustomerException {
        registry.registerCustomer(customer.getEmail(),"loic", "896983");
        Customer c = finder.findByEmail(customer.getEmail()).get();

        Card card = new Card();
        cardFinder.putCard(card);

        registry.associateCardToCustomer(c, card);

        Customer custToTest = finder.findByEmail(c.getEmail()).get();
        assertEquals(custToTest.getCard(), card);

        Card cardToTest = cardFinder.getCardById(card.getId()).get();

        assertEquals(cardToTest.getOwner(), c);

    }


    @Test(expected = AlreadyAssociatedCard.class)
    public void associateCardException() throws AlreadyAssociatedCard, AlreadyExistingCustomerException {
        registry.registerCustomer("loic@gmail.com","loic", "896983");
        Customer customer = finder.findByEmail("loic@gmail.com").get();
        Card card = new Card();
        card.setOwner(customer);

        registry.associateCardToCustomer(customer, card);
        thrown.expect(AlreadyAssociatedCard.class);

        Optional<Customer> toDispose = finder.findByEmail("loic@gmail.com");
        toDispose.ifPresent(cust -> { Customer c = manager.merge(cust); manager.remove(c); });

    }

}