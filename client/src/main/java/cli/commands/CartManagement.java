package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;

import java.util.List;

public abstract class CartManagement extends Command<CastexSkiAPI> {

	public String customerName;
	public int quantity;

	@Override
	public void load(List<String> args) {
		customerName = args.get(0);
		quantity = Integer.parseInt(args.get(1));
	}

}
