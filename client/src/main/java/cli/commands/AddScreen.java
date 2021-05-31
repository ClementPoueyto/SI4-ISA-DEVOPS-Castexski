package cli.commands;

import java.util.List;

import api.CastexSkiAPI;
import cli.framework.Command;

public class AddScreen extends Command<CastexSkiAPI>{

    String zoneName; 

    @Override
    public String identifier() {
        return "add-screen";
    }

    @Override
    public void load(List<String> args) {
        this.zoneName = args.get(0);
    }

    @Override
    public void execute() throws Exception {
        if(shell == null){
            System.out.println("shell null");
        }
        shell.system.employeeWebService.addANewScreen(zoneName);
    
    }

    @Override
    public String describe() {
        return "Add a new screen to a Zone (add-screen ZONE_NAME)";
    }
    
}
