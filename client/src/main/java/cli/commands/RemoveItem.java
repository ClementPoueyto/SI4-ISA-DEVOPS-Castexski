package cli.commands;

import stubs.cart.*;

import java.util.List;

public class RemoveItem extends CartManagement {

	private String itemInput;

	@Override
	public void load(List<String> args) {
		super.load(args);
		itemInput = args.get(2).toUpperCase();
	}

	@Override
	public String identifier() { return "rm"; }

	@Override
	public void execute() throws Exception {
		/*Item item;
		try {
			PassType.valueOf(itemInput);
			item = new Pass();
			((Pass) item).setPassType(PassType.valueOf(this.itemInput));
		}catch (Exception e){
			CardType.valueOf(itemInput);
			item = new Card();
			((Card) item).setCardType(CardType.valueOf(this.itemInput));
		}

		item.setQuantity(quantity);
		shell.system.carts.removeItemToCustomerCart(customerName, item);*/
	}

	@Override
	public String describe() {
		return "Remove item (rm CUSTOMER_EMAIL QUANTITY ITEM_TYPE)";
	}
}
