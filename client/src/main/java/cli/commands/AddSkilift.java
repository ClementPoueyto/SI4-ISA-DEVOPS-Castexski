package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;
import stubs.employee.AlreadyExistingSkiliftException_Exception;
import stubs.employee.UnknownZoneException_Exception;

import java.util.List;

public class AddSkilift extends Command<CastexSkiAPI> {

    private String skiliftName;
    private String zoneId;

    @Override
    public void load(List<String> args) {
        skiliftName = args.get(0);
        zoneId = args.get(1);
    }

    @Override
    public String identifier() { return "add-skilift"; }

    @Override
    public void execute() throws UnknownZoneException_Exception, AlreadyExistingSkiliftException_Exception {
        shell.system.employeeWebService.addSkilift(skiliftName, zoneId);
    }

    @Override
    public String describe() {
        return "Add a skilift to the system";
    }
}
