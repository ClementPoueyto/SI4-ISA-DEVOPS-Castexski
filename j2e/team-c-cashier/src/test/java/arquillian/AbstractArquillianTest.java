package arquillian;

import fr.unice.polytech.cashier.components.CashierBean;
import fr.unice.polytech.cashier.interfaces.Payment;
import fr.unice.polytech.customer.components.CustomerBean;
import fr.unice.polytech.entities.Customer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.util.logging.Logger;

public abstract class AbstractArquillianTest {


	@Deployment
	public static WebArchive createDeployment() {
		// Building a Web ARchive (WAR) containing the following elements:
		return ShrinkWrap.create(WebArchive.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addPackage(Customer.class.getPackage())
				.addPackage(CashierBean.class.getPackage())
				.addPackage(Logger.class.getPackage())
				.addPackage(Payment.class.getPackage());

	}

}
