package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;
import stubs.analytics.AnalyticsPass;

import java.util.List;

public class GetPassAnalytics extends Command<CastexSkiAPI> {

	private int day;
	private int month;
	private int year;

	@Override
	public void load(List<String> args) {
		this.day = Integer.parseInt(args.get(0));
		this.month = Integer.parseInt(args.get(1));
		this.year = Integer.parseInt(args.get(2));
	}

	@Override
	public String identifier() { return "pass-analytics"; }

	@Override
	public void execute() throws Exception {

		List<AnalyticsPass> passes = shell.system.analyticsWebService.getPassAnalyticsByDay(day,month, year);
		for(AnalyticsPass pass: passes){
			System.out.println(pass.getPassType().toString()+" : "+pass.getCounter());

		}
		if(passes.size()==0){
			System.out.println("pas de statistiques de forfait");

		}
	}

	@Override
	public String describe() {
		return "Get day pass analytics (pass-analytics day month year)";
	}
}
