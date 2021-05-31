package cli.commands;

import stubs.cart.*;

import java.util.List;

public class AddCard extends CartManagement {

	private CardType cardType;

	@Override
	public void load(List<String> args) {
		super.load(args);
		cardType = CardType.valueOf(args.get(2).toUpperCase());
	}

	@Override
	public String identifier() { return "add-card"; }

	@Override
	public void execute() throws Exception {
		CardItem cardItem = new CardItem();
		cardItem.setQuantity(quantity);
		cardItem.setType(ItemType.CARD);
		cardItem.setCardType(cardType);
		shell.system.carts.addItemToCustomerCart(customerName, cardItem);
	}

	@Override
	public String describe() {
		return "Add card to cart (add-card CUSTOMER_EMAIL QUANTITY CARD_TYPE)";
	}
}
