package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;

import java.util.List;
import java.util.Set;

public class Catalogue extends Command<CastexSkiAPI> {


    @Override
    public String identifier() {
        return "catalogue";
    }

    @Override
    public void load(List<String> args) {

    }

    @Override
    public void execute() {
        /*if(shell == null){
            System.out.println("shell null");
        }
        List<Item> items = shell.system.carts.getCatalogueContent();

        if (items.isEmpty()) {
            System.out.println("  No items available");
        } else {
            for(Item c: items) {
                System.out.println(" Name : " + c.getName()+"  | Price "+ c.getPrice()+ "  | Quantity : "+c.getQuantity());
            }
        }*/
    }

    @Override
    public String describe() {
        return "Get catalogue in the CastexSki backend (catalogue )";
    }
    
}
