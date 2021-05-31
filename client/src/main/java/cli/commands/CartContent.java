package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;
import stubs.cart.*;

import java.util.List;

public class CartContent extends Command<CastexSkiAPI> {

    private String customerMailAddr;

    @Override
    public void load(List<String> args) {
        this.customerMailAddr = args.get(0);

    }

    @Override
    public String identifier() {
        return "cart";
    }

    @Override
    public void execute() throws UnknownCustomerException_Exception {
        List<OrderItem> items = shell.system.carts.getCustomerCartContents(this.customerMailAddr);

        if (items.isEmpty()) {
            System.out.println("  Empty cart");
        } else {
            for (OrderItem i : items) {
                System.out.println(" Name : " + ((i.getType().equals(ItemType.CARD)) ? i.getType().name() : i.getType().name()) + "  | Price " + i.getPrice() + "  | Quantity : " + i.getQuantity() + ((i.getType().equals(ItemType.CARD)) ? " | CardId: TO ADD" : ""));
            }
        }
    }

    @Override
    public String describe() {
        return "Display user cart (cart CUSTOMER_EMAIL)";
    }
}
