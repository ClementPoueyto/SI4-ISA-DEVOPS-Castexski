package arquillian;

import fr.unice.polytech.cart.components.CartBean;
import fr.unice.polytech.cart.interfaces.CartModifier;
import fr.unice.polytech.cashier.components.CashierBean;
import fr.unice.polytech.customer.components.CustomerBean;
import fr.unice.polytech.customer.interfaces.CustomerFinder;
import fr.unice.polytech.customer.interfaces.CustomerRegistry;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.entities.Pass;
import fr.unice.polytech.entities.Zone;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import javax.ejb.EJB;
import java.util.logging.Logger;

public abstract class AbstractArquillianTest {



	@Deployment
	public static WebArchive createDeployment() {
		// Building a Web ARchive (WAR) containing the following elements:
		return ShrinkWrap.create(WebArchive.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Utils
				// Entities
				.addPackage(Customer.class.getPackage())
				.addPackage(Pass.class.getPackage())
				.addPackage(Card.class.getPackage())
				.addPackage(Zone.class.getPackage())

				// Components Interfaces
				.addPackage(CartModifier.class.getPackage())
				// Cart components
				// Interceptors
				.addPackage(Logger.class.getPackage())
				// Components implementations
				.addPackage(CartBean.class.getPackage())

				.addPackage(CashierBean.class.getPackage())
				.addPackage(CustomerBean.class.getPackage())
				.addPackage(CustomerRegistry.class.getPackage())
				.addPackage(CustomerFinder.class.getPackage())

				.addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");

	}

}
