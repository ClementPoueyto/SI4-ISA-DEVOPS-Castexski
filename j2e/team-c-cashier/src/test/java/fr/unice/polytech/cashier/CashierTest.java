package fr.unice.polytech.cashier;

import arquillian.AbstractArquillianTest;
import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.cashier.interfaces.Payment;
import fr.unice.polytech.customer.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.customer.interfaces.CustomerFinder;
import fr.unice.polytech.customer.interfaces.CustomerRegistry;
import fr.unice.polytech.entities.*;
import fr.unice.polytech.exceptions.ExternalPartnerException;
import fr.unice.polytech.exceptions.UnknownCardException;
import fr.unice.polytech.utils.*;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Arquillian.class)
public class CashierTest extends AbstractArquillianTest {

    @EJB
    Payment cashier;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    Set<OrderItem> itemSet;

    private Customer pascal;

    @Before
    public void setUpContext() throws AlreadyExistingCustomerException, ExternalPartnerException {
        pascal = new Customer("pascal@gmail.com", "Pascal");
        pascal.setCreditCard("896915");
        PassItem pass = new PassItem();
        CardItem card = new CardItem();
        itemSet = new HashSet<>();
        itemSet.add(pass);
        itemSet.add(card);
        BankAPI mocked = mock(BankAPI.class);
        cashier.useBankReference(mocked);
        when(mocked.performPayment(eq(pascal), anyDouble())).thenReturn(true);

    }


    // Verifier le prix d'un set d'item

    @Test
    public void calculateOrderPrice(){
        Set<OrderItem> cart = new HashSet<>();

        //empty cart
        assertEquals(0.0, cashier.getOrderPrice(cart));

        // then add more cards
        CardItem card = new CardItem();
        cart.add(card);

        // cart with one pass and a card
        assertEquals(2.0, cashier.getOrderPrice(cart));

        PassItem pass2 = new PassItem(
                1,
                "",
                PassType.CLASSIC,
                new DateSerializable(2021, 5, 3, 9, 0),
                new DateSerializable(2021, 5, 4, 18, 0),
                PersonType.ADULT,
                "beginner"
        );
        cart.add(pass2);

        assertEquals(32.0, cashier.getOrderPrice(cart));

        PassItem pass3 = new PassItem(
                1,
                "",
                PassType.CLASSIC,
                new DateSerializable(2021, 5, 3, 9, 0),
                new DateSerializable(2021, 5, 7, 18, 0),
                PersonType.CHILD,
                "beginner,haut_station"
        );
        cart.add(pass3);
        // 15(price)*5(days)*0.9(3days+)*0.75(child)*2(zones)
        assertEquals(133.25, cashier.getOrderPrice(cart));
        PassItem pass4 = new PassItem(
                1,
                "",
                PassType.CLASSIC,
                new DateSerializable(2021, 5, 3, 9, 0),
                new DateSerializable(2021, 5, 3, 13, 0),
                PersonType.ADULT,
                "beginner"
        );
        cart.add(pass4);
        // 15(price)*0.6(half-day)*1(adult)*1(zone)
        assertEquals(142.25, cashier.getOrderPrice(cart));
    }


    // Passer une commande (via un mock de BankAPI)
    @Test()
    public void passOrder() throws UnknownCardException, PaymentException, InvalidCardIdException {
        assertTrue(cashier.payByCb(pascal, itemSet));
    }

    // Test qui passe pas pour le build
    @Ignore
    @Test
    public void testQuiPassePas(){
        assertTrue(false);
    }

    @Test(expected = PaymentException.class)
    public void passOrderFail() throws PaymentException, UnknownCardException, InvalidCardIdException {
        Customer clement = new Customer("clement@gmail.com", "Clement");
        cashier.payByCb(clement, itemSet);
        thrown.expect(PaymentException.class);
    }

}
