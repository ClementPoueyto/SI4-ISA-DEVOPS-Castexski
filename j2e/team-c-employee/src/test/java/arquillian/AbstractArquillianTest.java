package arquillian;

import fr.unice.polytech.employee.components.ScreenBean;
import fr.unice.polytech.employee.components.StationBean;
import fr.unice.polytech.employee.interfaces.*;
import fr.unice.polytech.entities.Screen;
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
				// Entities
				.addPackage(ScreenBean.class.getPackage())
				.addPackage(Zone.class.getPackage())
				.addPackage(Screen.class.getPackage())
				.addPackage(ScreenFinder.class.getPackage())
				.addPackage(ScreenAdder.class.getPackage())
				.addPackage(DisplayMessage.class.getPackage())
				.addPackage(GateAdder.class.getPackage())

				.addPackage(StationBean.class.getPackage())
				.addPackage(ZoneFinder.class.getPackage())
				.addPackage(ZoneAdder.class.getPackage())
				// Components Interfaces

				// Interceptors
				.addPackage(Logger.class.getPackage())
				// Components implementations
				
				.addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");

	}

}
