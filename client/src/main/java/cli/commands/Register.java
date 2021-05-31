package cli.commands;

import java.util.List;

import api.CastexSkiAPI;
import cli.framework.Command;

public class Register extends Command<CastexSkiAPI> {

    private String customerMailAddr;
    private String customerName;
    private String creditCard;

    @Override
    public String identifier() {
        return "register";
    }

    @Override
    public void load(List<String> args) {
        this.customerMailAddr = args.get(0);
        this.customerName = args.get(1);
        this.creditCard = args.get(2);
    }

    @Override
    public void execute() throws Exception {
        if(shell == null){
            System.out.println("shell null");
        }
        shell.system.cws.registerCustomer(customerMailAddr, customerName, creditCard);

    }

    @Override
    public String describe() {
        return "Register a customer in the CastexSki backend (register CUSTOMER_MAIL_ADDR CUSTOMER_NAME CREDIT_CARD_NUMBER)";
    }

}
