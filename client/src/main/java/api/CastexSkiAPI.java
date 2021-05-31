package api;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import stubs.analytics.AnalyticsWebService;
import stubs.analytics.AnalyticsWebServiceImplService;
import stubs.cart.CartWebService;
import stubs.cart.CartWebServiceImplService;
import stubs.customer.CustomerWebService;
import stubs.customer.CustomerWebServiceImplService;
import stubs.employee.EmployeeWebService;
import stubs.employee.EmployeeWebServiceImplService;
import stubs.gate.GateWebService;
import stubs.gate.GateWebServiceImplService;

public class CastexSkiAPI {

	public CartWebService carts;
	public CustomerWebService cws;
	public GateWebService gateWebService;
	public AnalyticsWebService analyticsWebService;
	public EmployeeWebService employeeWebService;

	public CastexSkiAPI(String host, String port) {
		initCart(host, port);
		initCWS(host, port);
		initGateWS(host, port);
		initEmployeeWS(host, port);
		initAnalyticsWS(host, port);
	}

	private void initCart(String host, String port) {
		URL wsdlLocation = CastexSkiAPI.class.getResource("/CartWS.wsdl");
		CartWebServiceImplService factory = new CartWebServiceImplService(wsdlLocation);
		this.carts = factory.getCartWebServiceImplPort();
		String address = "http://" + host + ":" + port + "/webservices-module/webservices/CartWS";
		((BindingProvider) carts).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
	}

	private void initCWS(String host, String port) {
		URL wsdlLocation = CastexSkiAPI.class.getResource("/CustomerWS.wsdl");
		CustomerWebServiceImplService factory = new CustomerWebServiceImplService(wsdlLocation);
		this.cws = factory.getCustomerWebServiceImplPort();
		String address = "http://" + host + ":" + port + "/webservices-module/webservices/CustomerWS";
		((BindingProvider) cws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
	}

	private void initEmployeeWS(String host, String port) {
		URL wsdlLocation = CastexSkiAPI.class.getResource("/EmployeeWS.wsdl");
		EmployeeWebServiceImplService factory = new EmployeeWebServiceImplService(wsdlLocation);
		this.employeeWebService = factory.getEmployeeWebServiceImplPort();
		String address = "http://" + host + ":" + port + "/webservices-module/webservices/EmployeeWS";
		((BindingProvider) employeeWebService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
	}

	private void initGateWS(String host, String port) {
		URL wsdlLocation = CastexSkiAPI.class.getResource("/GateWS.wsdl");
		GateWebServiceImplService factory = new GateWebServiceImplService(wsdlLocation);
		this.gateWebService = factory.getGateWebServiceImplPort();
		String address = "http://" + host + ":" + port + "/webservices-module/webservices/GateWS";
		((BindingProvider) gateWebService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
	}

	private void initAnalyticsWS(String host, String port) {
		URL wsdlLocation = CastexSkiAPI.class.getResource("/AnalyticsWS.wsdl");
		AnalyticsWebServiceImplService factory = new AnalyticsWebServiceImplService(wsdlLocation);
		this.analyticsWebService = factory.getAnalyticsWebServiceImplPort();
		String address = "http://" + host + ":" + port + "/webservices-module/webservices/AnalyticsWS";
		((BindingProvider) analyticsWebService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
	}

}
