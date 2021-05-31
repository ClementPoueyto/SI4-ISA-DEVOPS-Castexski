package fr.unice.polytech.gate.components;
import fr.unice.polytech.employee.interfaces.ZoneFinder;
import fr.unice.polytech.exceptions.ExternalPartnerException;
import fr.unice.polytech.gate.exceptions.AlreadyHaveStatusException;
import fr.unice.polytech.gate.exceptions.UnknownGateException;
import fr.unice.polytech.gate.exceptions.UnknownZoneException;
import fr.unice.polytech.gate.interceptors.AnalyticsVisitCounter;
import fr.unice.polytech.gate.interfaces.*;
import fr.unice.polytech.utils.*;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.unice.polytech.cart.exceptions.EmptyCartException;
import fr.unice.polytech.cart.interfaces.CartModifier;
import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.cashier.interfaces.Payment;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.CardItem;
import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.entities.Gate;
import fr.unice.polytech.entities.OrderItem;
import fr.unice.polytech.entities.Pass;
import fr.unice.polytech.entities.PassItem;
import fr.unice.polytech.entities.SkiLift;
import fr.unice.polytech.entities.Zone;
import fr.unice.polytech.exceptions.ExternalPartnerException;
import fr.unice.polytech.exceptions.UncheckedException;
import fr.unice.polytech.exceptions.UnknownCardException;
import fr.unice.polytech.gate.exceptions.AlreadyHaveStatusException;
import fr.unice.polytech.gate.exceptions.UnknownSkiliftException;
import fr.unice.polytech.gate.exceptions.UnknownZoneException;
import fr.unice.polytech.gate.interfaces.GateCheck;

import fr.unice.polytech.gate.interfaces.StateModifier;
import fr.unice.polytech.utils.CardType;
import fr.unice.polytech.utils.PassageResponse;
import fr.unice.polytech.utils.PersonType;
import fr.unice.polytech.utils.Status;
import fr.unice.polytech.utils.WeatherAPI;
import fr.unice.polytech.utils.WeatherStatus;

@Stateless
public class GateBean implements GateCheck, StateModifier {

    @EJB CartModifier cartModifier;

    @EJB
    ZoneFinder zoneFinder;

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    private WeatherAPI weatherAPI = new WeatherAPI();

    public void useWeatherReference(WeatherAPI weatherAPI) {
        this.weatherAPI = weatherAPI;
    }

    @Override
    @Interceptors({AnalyticsVisitCounter.class})
    public PassageResponse isPassageValid(Card card, Gate gate) throws UncheckedException, PaymentException, UnknownCardException, InvalidCardIdException, EmptyCartException, UnknownGateException {
        log.log(Level.INFO, "CARD : " + card);
        if(card==null){
            log.log(Level.INFO, "Card null");

            throw new UnknownCardException("null");
        }
        if(gate==null){
            log.log(Level.INFO, "Gate null");

            throw new UnknownGateException();
        }
        log.log(Level.INFO, "ID CARD : "+card.getId());

        if(card.getCardType() == CardType.SUPERCARTEX && (card.getPass() == null || !card.getPass().isValid())){
            Customer customer = card.getOwner();
            manager.flush();
            manager.refresh(customer);
            if(card.getEndDate().getDate().isBefore(LocalDateTime.now())) {
                CardItem cI = new CardItem(CardType.SUPERCARTEX);
                cartModifier.addItem(customer ,cI);
                manager.flush();
                manager.refresh(customer);
                cartModifier.cartValidated(customer,cartModifier.payByCb(customer));

            }
            manager.flush();
            manager.refresh(customer);
            PassItem passItem = new PassItem();
            passItem.setZones("all_zones_supercartex");
            passItem.setCardLinkId(customer.getCard().getId());
            log.log(Level.INFO, "SUPER CARTEX - Pass Item : " + passItem);
            cartModifier.addItem(customer, passItem);
            manager.flush();
            manager.refresh(customer);
            cartModifier.cartValidated(customer,cartModifier.payByCb(customer));
            manager.flush();
            manager.refresh(customer);
            card = customer.getCard();
        }

        Pass pass = card.getPass();
        log.log(Level.INFO, "PASS : " + pass.toString());

        LocalDateTime now = LocalDateTime.now();

        if(card.getCardType() != CardType.SUPERCARTEX) {
            if (pass.getStart().getDate().compareTo(now) > 0) {
                log.log(Level.INFO, "Pass starts later");
                return PassageResponse.PASS_STARTS_LATER;
            }
        }

        if (pass.getEnd().getDate().compareTo(now) < 0) {
            log.log(Level.INFO, "Pass is no longer valid");
            return PassageResponse.PASS_NO_LONGER_VALID;
        }
        log.log(Level.INFO, "DATE VALID ");

        if(gate.getSkiLift() == null) {
            log.log(Level.INFO, "Unknown gate !");
            return PassageResponse.UNKNOWN_IDS;
        }

        if(card.getCardType() != CardType.SUPERCARTEX) {
            Set<Zone> passZones = new HashSet<>();
            pass.getZones().forEach(zone -> {
                Optional<Zone> z = zoneFinder.getZoneByName(zone);
                z.ifPresent(passZones::add);
            });
            if (!passZones.contains(gate.getSkiLift().getZone())) {
                log.log(Level.INFO, "Invalid pass : zone not included !");
                return PassageResponse.ZONE_NOT_INCLUDED;
            }
        }

        if (!isZoneOpened(gate.getId())) {
            log.log(Level.INFO, "PASSAGE VALIDATED");
            return PassageResponse.ZONE_CLOSED;
        }

        log.log(Level.INFO, "ZONE VALID ");

        if (pass.getPersonType().equals(PersonType.CHILD)) {
            log.log(Level.INFO, "/!\\ Child pass !");
            return PassageResponse.PASS_OK_CHILD;
        }


        try {
            WeatherStatus rep = weatherAPI.getWeather();
            if(rep == WeatherStatus.Avalanche){ // Noooooooo
                return PassageResponse.ZONE_CLOSED;
            }
        } catch (ExternalPartnerException e) {
            log.log(Level.SEVERE, "/!\\ Could not fetch weather forecast server");
            e.printStackTrace();
        }

        log.log(Level.INFO, "PASSAGE VALIDATED");
        return PassageResponse.PASS_OK;

    }

    public boolean isZoneOpened(String idGate){
        return true;
    }



    // Maybe to move elsewhere


    @Override
    public void setSkiLiftState(String idLift, Status status) {
        //memory.getSkilifts().get(idLift).setStatus(status);
    }

    @Override
    public void setZoneState(Zone zone, Status status) throws UnknownZoneException, AlreadyHaveStatusException {
        manager.merge(zone);
        if(zone!=null){
            if(zone.getStatus().equals(status)) throw new AlreadyHaveStatusException(status);
            else zone.setStatus(status);
        } else {
            throw new UnknownZoneException();
        }
    }

    @PostConstruct
    private void initializeRestPartnership() {
        WeatherAPI weatherAPI = new WeatherAPI();
		/*Properties prop = new Properties();
		prop.load(this.getClass().getResourceAsStream("/bank.properties"));
		bank = new BankAPI(	prop.getProperty("bankHostName"),
							prop.getProperty("bankPortNumber"));*/
    }






    public void setCartModifier(CartModifier cM){
        this.cartModifier = cM;
    }


}