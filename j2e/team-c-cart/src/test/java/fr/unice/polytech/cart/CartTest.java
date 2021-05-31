package fr.unice.polytech.cart;

import arquillian.AbstractArquillianTest;


import fr.unice.polytech.cart.interfaces.CartModifier;
import fr.unice.polytech.cashier.interfaces.Payment;
import fr.unice.polytech.customer.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.entities.*;
import fr.unice.polytech.utils.CardType;
import fr.unice.polytech.utils.PassType;
import fr.unice.polytech.utils.PersonType;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class CartTest extends AbstractArquillianTest {

    @EJB
    CartModifier cart;

    @EJB
    Payment cashier;


    @Inject
    private UserTransaction utx;

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;

    private Customer pascal;

    @Before
    public void setUpContext() throws AlreadyExistingCustomerException {

        pascal = new Customer("pascal@gmail.com","Pascal");
        pascal.setCreditCard("123456");
        manager.persist(pascal);
    }

    @After
    public void cleaningUp() throws Exception {
        utx.begin();
        System.out.println(pascal);
        pascal = manager.merge(pascal);
        manager.remove(pascal);
        pascal = null;
        utx.commit();
    }

    // Verifier prix commande avec un pass et une carte

    @Test
    public void checkOrderPrice(){
        PassItem pass = new PassItem(
                4,
                "",
                PassType.CLASSIC,
                new DateSerializable(2021, 5, 3, 9, 0),
                new DateSerializable(2021, 5, 13, 18, 0),
                PersonType.CHILD,
                "beginner"
        );
        cart.addItem(pascal, pass);
        assertEquals(1, cart.contents(pascal).size());
        // 4(qty)*11(days)*75(+7days)*0.75(child)*15
        assertEquals(371.25, cashier.getOrderPrice(cart.contents(pascal)));

    }
    //Ajouter un pass

    @Test
    public void addPass(){
        PassItem pass = new PassItem();
        cart.addItem(pascal, pass);
        assertEquals(1, cart.contents(pascal).size());
        assertTrue(cart.contents(pascal).contains(pass));
        assertEquals(1, cart.contents(pascal).iterator().next().getQuantity());

    }
    //Ajouter une carte

    @Test
    public void addCard(){
        CardItem card = new CardItem();
        card.setQuantity(3);
        cart.addItem(pascal, card);
        // TO TEST - cart.addItem(pascal, new PassItem());

        System.out.println(cart.contents(pascal));
        System.out.println(pascal);


        assertEquals(1, cart.contents(pascal).size());
        assertEquals(3, cart.contents(pascal).iterator().next().getQuantity());

        CardItem card2 = new CardItem();
        card.setCardType(CardType.SUPERCARTEX);
        card.setQuantity(2);
        cart.addItem(pascal, card2);
        assertEquals(2, cart.contents(pascal).size());

        // TODO : REPARER
        //assertEquals(2, cart.contents(pascal).iterator().next().getQuantity());
    }

    //Supprimer un pass ou une carte
    @Test
    public void removeItem(){
        PassItem pass = new PassItem();
        pass.setQuantity(10);
        cart.addItem(pascal, pass);
        assertEquals(1, cart.contents(pascal).size());

        PassItem passToRemove = new PassItem();

        passToRemove.setQuantity(7);
        cart.removeItem(pascal, passToRemove);
        assertEquals(1, cart.contents(pascal).size());
        assertEquals(3, cart.contents(pascal).iterator().next().getQuantity());

        passToRemove.setQuantity(2);
        cart.removeItem(pascal, passToRemove);
        assertEquals(1, cart.contents(pascal).size());
        assertEquals(1, cart.contents(pascal).iterator().next().getQuantity());

        passToRemove.setQuantity(1);
        cart.removeItem(pascal, passToRemove);
        assertEquals(0, cart.contents(pascal).size());

    }


    @Test
    public void emptyCartByDefault() {
        assertEquals(0, cart.contents(pascal).size());

    }


}
