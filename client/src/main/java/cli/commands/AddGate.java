package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;
import stubs.employee.AlreadyExistingGateException_Exception;
import stubs.employee.UnknownSkiliftException_Exception;


import java.util.List;

public class AddGate extends Command<CastexSkiAPI> {

    private String gatePhysicalId;
    private String skiliftId;

    @Override
    public void load(List<String> args) {
        gatePhysicalId = args.get(0);
        skiliftId = args.get(1);
    }

    @Override
    public String identifier() { return "add-gate"; }

    @Override
    public void execute() throws UnknownSkiliftException_Exception, AlreadyExistingGateException_Exception {
        shell.system.employeeWebService.addGate(gatePhysicalId, skiliftId);
    }

    @Override
    public String describe() {
        return "Add a gate to the system";
    }
}
