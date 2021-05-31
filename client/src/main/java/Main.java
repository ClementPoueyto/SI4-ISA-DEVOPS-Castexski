import cli.commands.*;
import cli.framework.Shell;
import api.CastexSkiAPI;

/**
 * An Interactive shell that interacts with a Cookie on Demand instance
 * Use -Dexec.args="IP_ADDRESS PORT_NUMBER" to change host/port parameters
 */
public class Main extends Shell<CastexSkiAPI> {

	public Main(String host, String port) {

		this.system  = new CastexSkiAPI(host, port);
		this.invite  = "CastexSki";

		// Registering the command available for the user
		register(
				// Getting out of here
				Bye.class,
				Register.class,
				AddPass.class,
				AddCard.class,
				RemoveItem.class,
				Catalogue.class,
				Checkout.class,
				CartContent.class,
				Passage.class,
				AddZone.class,
				AddSkilift.class,
				AddGate.class,
				GetVisitAnalytics.class,
				DisplayMessage.class,
				AddScreen.class,
				GetVisitAnalytics.class,
				GetPassAnalytics.class

		);
	}

	public static void main(String[] args) {
		String host = ( args.length == 0 ? "localhost" : args[0] );
		String port = ( args.length < 2  ? "8080"      : args[1] );
		System.out.println("\n\nStarting CastexSki client");
		System.out.println("  - Remote server: " + host);
		System.out.println("  - Port number:   " + port);
		Main main = new Main(host, port);
		main.run();
		System.out.println("Exiting\n\n");
	}

}
