package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;
import stubs.employee.AlreadyExistingZoneException_Exception;

import java.util.List;

public class AddZone extends Command<CastexSkiAPI> {

    private String zoneName;

    @Override
    public void load(List<String> args) {
        zoneName = args.get(0);
    }

    @Override
    public String identifier() { return "add-zone"; }

    @Override
    public void execute() throws AlreadyExistingZoneException_Exception {
        shell.system.employeeWebService.addZone(zoneName, "CastexSki");
    }

    @Override
    public String describe() {
        return "Add a zone to the system";
    }
}
