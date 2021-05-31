package cli.commands;

import java.util.Arrays;
import java.util.List;

import api.CastexSkiAPI;
import cli.framework.Command;

public class DisplayMessage extends Command<CastexSkiAPI> {

    private String message;
    private List<String> zonesNames;

    @Override
    public String identifier() {
        return "display";
    }

    @Override
    public void load(List<String> args) {
        this.message = args.get(0);
        this.zonesNames = args.subList(1, args.size());
        
    }

    @Override
    public void execute() throws Exception {
        if(shell == null){
            System.out.println("shell null");
        }
        shell.system.employeeWebService.diplayMessageOnZonesScreens(message, zonesNames);

        
    }

    @Override
    public String describe() {
        return "Print a message on all screens of the Zones put in parameters (diplsay MESSAGE ZONES_NAMES[])";
    }
    
}
