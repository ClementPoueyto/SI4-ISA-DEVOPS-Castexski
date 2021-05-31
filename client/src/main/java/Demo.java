import stubs.analytics.AnalyticsWebService;
import stubs.analytics.AnalyticsWebServiceImplService;
import stubs.cart.*;
import stubs.customer.CustomerWebService;
import stubs.customer.CustomerWebServiceImplService;
import stubs.gate.GateWebService;
import stubs.gate.GateWebServiceImplService;

import javax.xml.ws.BindingProvider;
import java.net.URL;

public class Demo {



	public static void main(String[] args) throws Exception {
		System.out.println("#### Collecting arguments (host, port)");
		String host = ( args.length == 0 ? "localhost" : args[0] );
		String port = ( args.length < 2  ? "8080"      : args[1] );
		CustomerWebService cws = initCWS(host,port);
		CartWebService carts = initCartWS(host,port);
		GateWebService gateWebService = initGateWS(host,port);
		System.out.println("#### Running the demo");
		demo(carts, cws, gateWebService);
	}

	private static void demo(CartWebService cartWS, CustomerWebService custWS, GateWebService gateWS) throws Exception {
		/*final String GATEID = "125378";
		final String email = "joe@gmail.com";
		final String IDCard = "123";
		System.out.println("#### REGISTER JOE ");
		custWS.registerCustomer(email, "joe", "896983");

		System.out.println("#### CATALOGUE DESCRIPTION ");
		for(Item c: cartWS.getCatalogueContent()) {
			System.out.println(" Name : " + c.getName()+"  | Price "+ c.getPrice()+ "  | Quantity : "+c.getQuantity());
		}

		System.out.println("\n#### JOE ADD 1 PASS ( Classic ) TO HIS CART ");
		Pass pass = new Pass();
		pass.setPassType(PassType.CLASSIC);
		pass.setQuantity(1);
		cartWS.addPassToCustomerCart(email, pass);

		System.out.println("#### CART DESCRIPTION ");
		for(Item c: cartWS.getCustomerCartContents(email)) {
			System.out.println(" Name : " + c.getName()+"  | Price "+ c.getPrice()+ "  | Quantity : "+c.getQuantity());
		}

		System.out.println("#### JOE CHECKOUT ");
		cartWS.validate(email);

		System.out.println("#### JOE VALIDATE AT A GATE ");
		System.out.println("Passage : "+gateWS.checkCard(IDCard, GATEID));*/
	}




	private static CartWebService initCartWS(String host, String port) {
		URL wsdlLocation = Demo.class.getResource("/CartWS.wsdl");
		CartWebServiceImplService factory = new CartWebServiceImplService(wsdlLocation);
		CartWebService ws = factory.getCartWebServiceImplPort();
		String address = "http://"+host+":"+port+"/cart/webservices/CartWS";
		((BindingProvider) ws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
		return ws;
	}

	private static CustomerWebService initCWS(String host, String port) {
		URL wsdlLocation = Demo.class.getResource("/CustomerWS.wsdl");
		CustomerWebServiceImplService factory = new CustomerWebServiceImplService(wsdlLocation);
		CustomerWebService cws = factory.getCustomerWebServiceImplPort();
		String address = "http://" + host + ":" + port + "/customer/webservices/CustomerWS";
		((BindingProvider) cws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
		return cws;
	}

	private static AnalyticsWebService initAnalyticsWS(String host, String port) {
		URL wsdlLocation = Demo.class.getResource("/AnalyticsWS.wsdl");
		AnalyticsWebServiceImplService factory = new AnalyticsWebServiceImplService(wsdlLocation);
		AnalyticsWebService gateWebService = factory.getAnalyticsWebServiceImplPort();
		String address = "http://" + host + ":" + port + "/analytics/webservices/AnalyticsWS";
		((BindingProvider) gateWebService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
		return gateWebService;
	}

	private static GateWebService initGateWS(String host, String port) {
		URL wsdlLocation = Demo.class.getResource("/GateWS.wsdl");
		GateWebServiceImplService factory = new GateWebServiceImplService(wsdlLocation);
		GateWebService gateWebService = factory.getGateWebServiceImplPort();
		String address = "http://" + host + ":" + port + "/gate/webservices/GateWS";
		((BindingProvider) gateWebService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
		return gateWebService;
	}
}
