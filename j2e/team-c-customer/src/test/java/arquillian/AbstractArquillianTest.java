package arquillian;

import fr.unice.polytech.card.components.CardBean;
import fr.unice.polytech.card.interfaces.CardFinder;
import fr.unice.polytech.customer.components.CustomerBean;
import fr.unice.polytech.customer.interfaces.CustomerFinder;
import fr.unice.polytech.customer.interfaces.CustomerRegistry;
import fr.unice.polytech.entities.Customer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.util.logging.Logger;

public abstract class AbstractArquillianTest {


//	@EJB
//	protected DatabaseCustomer memory;

	@Deployment
	public static WebArchive createDeployment() {
		// Building a Web ARchive (WAR) containing the following elements:
		return ShrinkWrap.create(WebArchive.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Entities
				.addPackage(Customer.class.getPackage())
				// Components Interfaces
				// Interceptors
				.addPackage(Logger.class.getPackage())
				// Components implementations
				.addPackage(CustomerBean.class.getPackage())
				.addPackage(CardFinder.class.getPackage())
				.addPackage(CardBean.class.getPackage())

				.addPackage(CustomerFinder.class.getPackage())
				.addPackage(CustomerRegistry.class.getPackage())

				.addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");

	}

}
