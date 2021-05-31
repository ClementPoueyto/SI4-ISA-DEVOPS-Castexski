package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;
import stubs.cart.*;

import java.util.List;

public class Checkout extends Command<CastexSkiAPI> {

	private String customerName;

	@Override
	public void load(List<String> args) {
		customerName = args.get(0);
	}

	@Override
	public String identifier() { return "checkout"; }

	@Override
	public void execute() throws PaymentException_Exception, EmptyCartException_Exception, UnknownCustomerException_Exception, UnknownCardException_Exception, InvalidCardIdException_Exception {
		List<String> cardIds = shell.system.carts.validate(customerName);
		if(cardIds.size() > 0) {
			System.out.print("Cartes attribuée(s) : ");
			cardIds.forEach(id -> System.out.print(id + ", "));
			System.out.println();
		}
	}

	@Override
	public String describe() {
		return "Checkout an order (checkout CUSTOMER_EMAIL)";
	}
}
