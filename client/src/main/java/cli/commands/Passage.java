package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;
import stubs.gate.PassageResponse;

import java.util.List;

public class Passage extends Command<CastexSkiAPI> {

    String customerCardID;
    String gateID;
    @Override
    public String identifier() {
        return "passage";
    }


    @Override
    public void load(List<String> args) {
        this.customerCardID = args.get(0);
        this.gateID = args.get(1);
    }

    @Override
    public void execute() {
        if(shell == null){
            System.out.println("shell null");
        }
        try {
            PassageResponse passageResponse = shell.system.gateWebService.checkCard(this.customerCardID, this.gateID);
            switch (passageResponse) {
                case ZONE_CLOSED:
                    System.out.println("Invalid passage : Zone closed ");
                    break;
                case PASS_NO_LONGER_VALID:
                    System.out.println("Invalid pass : no longer valid");
                    break;
                case PASS_STARTS_LATER:
                    System.out.println("Invalid pass : starts later");
                    break;
                case UNKNOWN_IDS:
                    System.out.println("Bad arguments : unknown card / gate");
                    break;
                case ZONE_NOT_INCLUDED:
                    System.out.println("Invalid pass : zone not included");
                    break;
                case PASS_OK:
                    System.out.println("Passage validated");
                    break;
                case PASS_OK_CHILD:
                    System.out.println("Passage validated - children");
                    break;
            }
        }
        catch (Exception e){
            System.out.println("REFUSED NO PASS");
        }

    }

    @Override
    public String describe() {
        return "valid a customer passage at a gate in the CastexSki backend (passage CUSTOMER_CARD_ID)";
    }

}
