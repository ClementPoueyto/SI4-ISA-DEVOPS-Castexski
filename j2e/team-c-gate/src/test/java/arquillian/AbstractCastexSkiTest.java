package arquillian;

import fr.unice.polytech.analytics.components.AnalyticsBean;
import fr.unice.polytech.analytics.interfaces.AnalyticsUpdate;
import fr.unice.polytech.card.components.CardBean;
import fr.unice.polytech.card.interfaces.CardFinder;
import fr.unice.polytech.cart.components.CartBean;
import fr.unice.polytech.cart.interfaces.CartModifier;
import fr.unice.polytech.cashier.components.CashierBean;
import fr.unice.polytech.cashier.interfaces.Payment;
import fr.unice.polytech.employee.components.StationBean;
import fr.unice.polytech.employee.interfaces.GateFinder;
import fr.unice.polytech.employee.interfaces.SkiliftFinder;
import fr.unice.polytech.employee.interfaces.ZoneFinder;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.gate.components.GateBean;
import fr.unice.polytech.gate.interceptors.AnalyticsVisitCounter;
import fr.unice.polytech.gate.interfaces.GateCheck;
import fr.unice.polytech.gate.interfaces.StateModifier;
import fr.unice.polytech.gate.webservices.GateWebService;
import fr.unice.polytech.gate.webservices.GateWebServiceImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.util.logging.Logger;

public abstract class AbstractCastexSkiTest {



    @Deployment
    public static WebArchive createDeployment() {
        // Building a Web ARchive (WAR) containing the following elements:
        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Entities
                .addPackage(Card.class.getPackage())
                .addPackage(CardFinder.class.getPackage())
                .addPackage(CardBean.class.getPackage())
                .addPackage(CashierBean.class.getPackage())
                .addPackage(Payment.class.getPackage())
                // Components Interfaces
                .addPackage(GateCheck.class.getPackage())
                // Cart components
                // Interceptors
                .addPackage(Logger.class.getPackage())
                // Components implementations
                .addPackage(GateBean.class.getPackage())
                .addPackage(Customer.class.getPackage())
                .addPackage(CartBean.class.getPackage())
                .addPackage(CartModifier.class.getPackage())
                .addPackage(StateModifier.class.getPackage())
                .addPackage(SkiliftFinder.class.getPackage())
                .addPackage(ZoneFinder.class.getPackage())
                .addPackage(StationBean.class.getPackage())

                .addPackage(GateFinder.class.getPackage())
                .addPackage(AnalyticsVisitCounter.class.getPackage())
                .addPackage(AnalyticsBean.class.getPackage())
                .addPackage(AnalyticsUpdate.class.getPackage())

                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");

    }

}