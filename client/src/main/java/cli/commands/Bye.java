package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;

public class Bye extends Command<CastexSkiAPI> {

	@Override
	public String identifier() { return "bye"; }

	@Override
	public void execute() { }

	@Override
	public String describe() {
		return "Exit CastexSki";
	}

	@Override
	public boolean shouldContinue() { return false; }

}
