package fr.unice.polytech.gate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.*;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import fr.unice.polytech.entities.*;
import fr.unice.polytech.gate.components.GateBean;
import fr.unice.polytech.gate.exceptions.UnknownGateException;
import fr.unice.polytech.utils.*;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;

import arquillian.AbstractCastexSkiTest;
import fr.unice.polytech.card.interfaces.CardFinder;
import fr.unice.polytech.cart.exceptions.EmptyCartException;
import fr.unice.polytech.cart.interfaces.CartModifier;
import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.cashier.interfaces.Payment;
import fr.unice.polytech.customer.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.exceptions.ExternalPartnerException;
import fr.unice.polytech.exceptions.UncheckedException;
import fr.unice.polytech.exceptions.UnknownCardException;
import fr.unice.polytech.gate.exceptions.AlreadyHaveStatusException;
import fr.unice.polytech.gate.exceptions.UnknownZoneException;
import fr.unice.polytech.gate.exceptions.UnvalidatedPassException;
import fr.unice.polytech.gate.interfaces.GateCheck;
import fr.unice.polytech.gate.interfaces.StateModifier;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class GateTest extends AbstractCastexSkiTest {

    WeatherAPI mockedWeather;

    @EJB
    StateModifier stateModifier;

    @EJB
    CardFinder cardFinder;

    @EJB
    GateCheck gateCheck;


    @EJB
    Payment payment;

    @EJB
    CartModifier cartModifier;




    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;


    @Inject
    private UserTransaction utx;

    private Gate gate;

    private Zone zone1, zone2, zone3;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpContext() throws ExternalPartnerException {

        zone1 = new Zone("zone1", "castex", Status.OPENED);
        zone2 = new Zone("zone2", "castex", Status.OPENED);
        zone3 = new Zone("zone3", "castex", Status.OPENED);
        manager.persist(zone1);
        manager.persist(zone2);
        manager.persist(zone3);

        SkiLift skiLift = new SkiLift("Marmottes", zone3, Status.OPENED);

        manager.persist(skiLift);
        gate = new Gate("Gate0", skiLift);
        manager.persist(gate);

        mockedWeather = mock(WeatherAPI.class);
        gateCheck.useWeatherReference(mockedWeather);
        when(mockedWeather.getWeather()).thenReturn(WeatherStatus.Sun);

        BankAPI mocked = mock(BankAPI.class);
        payment.useBankReference(mocked);
        when(mocked.performPayment(any(Customer.class), anyDouble())).thenReturn(true);
        cartModifier.setPayment(payment);
        gateCheck.setCartModifier(cartModifier);
    }

    @After
    public void cleaningUp() throws Exception {
        utx.begin();
        Gate g =manager.find(Gate.class,gate.getId());
        Optional<Gate> toDispose;
        if(g!=null){
            toDispose = Optional.of(g);
        }
        else{
            toDispose = Optional.empty();
        }

        toDispose.ifPresent(gt -> { Gate c = manager.merge(gt); manager.remove(c); });
        Zone z1 = manager.find(Zone.class, zone1.getId());
        if(z1!=null){
            manager.remove(z1);
        }
        Zone z2 = manager.find(Zone.class, zone2.getId());
        if(z2!=null){
            manager.remove(z2);
        }
        Zone z3 = manager.find(Zone.class, zone3.getId());
        if(z3!=null){
            manager.remove(z3);
        }
        utx.commit();
    }
    @Test
    public void checkPassWithBadZone() throws UnvalidatedPassException, UnknownCardException, EmptyCartException, PaymentException, InvalidCardIdException, UnknownGateException {
        // Date OK, zone KO
        Set<String> passZones = new HashSet<>();
        passZones.add("zone1");
        passZones.add("zone2");
        Pass pass1 = new Pass(
                new DateSerializable(2021, 2, 26, 9, 0),
                new DateSerializable(2099, 2, 26, 18, 0),
                passZones
        );
        Card card1 = new Card();

        manager.persist(pass1);

        manager.persist(card1);
        cardFinder.linkPassToCard(card1,pass1);

        assertEquals(gateCheck.isPassageValid(card1, gate), PassageResponse.ZONE_NOT_INCLUDED);
    }

    @Test
    public void blockGateOnAvalanche() throws UnvalidatedPassException, UnknownCardException, ExternalPartnerException, UncheckedException, PaymentException, InvalidCardIdException, EmptyCartException, UnknownGateException {
        Set<String> passZones = new HashSet<>();
        passZones.add("zone3");
        Pass pass1 = new Pass(
                new DateSerializable(2021, 2, 26, 9, 0),
                new DateSerializable(2099, 2, 26, 18, 0),
                passZones
        );
        Card card1 = new Card();

        manager.persist(pass1);

        manager.persist(card1);
        cardFinder.linkPassToCard(card1,pass1);

        when(mockedWeather.getWeather()).thenReturn(WeatherStatus.Avalanche);

        assertEquals(gateCheck.isPassageValid(card1, gate), PassageResponse.ZONE_CLOSED);
    }

    @Test
    public void checkPassEnded() throws UnvalidatedPassException, UnknownCardException, UncheckedException, PaymentException, InvalidCardIdException, EmptyCartException, UnknownGateException {
        // Card #2 - Pass ended - Bad Zone
        Set<String> passZones = new HashSet<>();
        passZones.add("zone3");
        Pass pass = new Pass(
                new DateSerializable(2020, 2, 26, 9, 0),
                new DateSerializable(2020, 2, 26, 18, 0),
                passZones
        );
        manager.persist(pass);

        Card card2 = new Card();

        card2.setPass(pass);
        manager.persist(card2);
        cardFinder.linkPassToCard(card2,pass);

        assertEquals(gateCheck.isPassageValid(card2, gate), PassageResponse.PASS_NO_LONGER_VALID);
    }

    @Test
    public void checkValidPass() throws UnvalidatedPassException, UnknownCardException, UncheckedException, PaymentException, InvalidCardIdException, EmptyCartException, UnknownGateException {
        // Card #3 - Date valid - Zone valid
        Set<String> passZones = new HashSet<>();
        passZones.add("zone3");
        Pass pass = new Pass(
                new DateSerializable(2020, 2, 26, 9, 0),
                new DateSerializable(2099, 2, 26, 18, 0),
                passZones
        );
        Card card3 = new Card();
        manager.persist(pass);
        card3.setPass(pass);
        manager.persist(new Zone("haut_station", "", Status.OPENED));
        /*manager.persist(card3);
        cardFinder.linkPassToCard(card3,pass);*/

        assertEquals(gateCheck.isPassageValid(card3, gate), PassageResponse.PASS_OK);
    }
   @Test
    public void setZoneState() throws UnknownZoneException, AlreadyHaveStatusException {
        Zone zone = new Zone("castor", "greoliere", Status.OPENED);
        manager.persist(zone);
        stateModifier.setZoneState(zone, Status.CLOSED);
        assertEquals(Status.CLOSED, manager.find(Zone.class, zone.getId()).getStatus());
    }

    @Test(expected = AlreadyHaveStatusException.class)
    public void alreadyHaveStatus() throws UnknownZoneException, AlreadyHaveStatusException {
        Zone zone = new Zone("castor", "greoliere", Status.OPENED);
        manager.persist(zone);

        stateModifier.setZoneState(zone, Status.OPENED);
        thrown.expect(AlreadyHaveStatusException.class);
    }



    @Test(expected = UnknownZoneException.class)
    public void nonExistingZone() throws UnknownZoneException, AlreadyHaveStatusException {
        stateModifier.setZoneState(null, Status.OPENED);
        thrown.expect(UnknownZoneException.class);
    }

    @Test
    public void autoPaymentSuperCartexValid() throws AlreadyExistingCustomerException, UncheckedException, UnvalidatedPassException, PaymentException, UnknownCardException, InvalidCardIdException, EmptyCartException, UnknownGateException {
        
        
        Card card = new Card(CardType.SUPERCARTEX);
        manager.persist(card);
        Customer customer = new Customer("test@gmail.com", "test");
        customer.setCreditCard("123478");
        customer.setCard(card);
        manager.persist(customer);
        manager.flush();
        manager.refresh(card);
        assertEquals(customer.getEmail(), card.getOwner().getEmail());
        gateCheck.isPassageValid(customer.getCard(), gate);
        manager.refresh(card);
        assertNotNull(customer.getCard().getPass());

    }


    @Test
    public void autoPaymentSuperCartexNotValid() throws UncheckedException, UnvalidatedPassException, PaymentException, UnknownCardException, InvalidCardIdException, EmptyCartException, UnknownGateException {
        Card card = new Card(CardType.SUPERCARTEX);
        DateSerializable valid = new DateSerializable();
        valid.setDate(card.getEndDate().getDate());
        System.out.println(valid.getDate());
        card.getEndDate().setDate(card.getEndDate().getDate().minusYears(2));
        System.out.println(card.getEndDate().getDate());
        manager.persist(card);
        Customer customer = new Customer("test2@gmail.com", "test");
        customer.setCreditCard("123478");
        customer.setCard(card);
        manager.persist(customer);
        manager.flush();
        manager.refresh(card);
        assertEquals(customer.getEmail(), card.getOwner().getEmail());
        gateCheck.isPassageValid(customer.getCard(), gate);
        manager.refresh(card);
        manager.refresh(customer);
        assertNotEquals(card, customer.getCard());
        assertTrue(Duration.between(valid.getDate(),customer.getCard().getEndDate().getDate()).toHours()<1);
        assertNotNull(customer.getCard().getPass());

    }
}