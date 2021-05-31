package arquillian;

import fr.unice.polytech.analytics.components.AnalyticsBean;
import fr.unice.polytech.analytics.interfaces.AnalyticsFinder;
import fr.unice.polytech.analytics.interfaces.AnalyticsUpdate;
import fr.unice.polytech.entities.AnalyticsPass;
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
				.addPackage(AnalyticsPass.class.getPackage())
				.addPackage(AnalyticsUpdate.class.getPackage())
				.addPackage(AnalyticsFinder.class.getPackage())
				.addPackage(AnalyticsBean.class.getPackage())

				// Cart components
				// Interceptors
				.addPackage(Logger.class.getPackage())
				// Components implementations

				.addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");

	}

}
