package arquillian;

import fr.unice.polytech.card.components.CardBean;
import fr.unice.polytech.card.interfaces.CardFinder;

import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.entities.Pass;
import fr.unice.polytech.entities.Zone;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

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
				.addPackage(CardBean.class.getPackage())

				.addPackage(CardFinder.class.getPackage())
				// Cart components
				// Interceptors
				.addPackage(Logger.class.getPackage())
				// Components implementations

				.addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");

	}

}
