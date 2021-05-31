package cli.commands;

import api.CastexSkiAPI;
import cli.framework.Command;
import stubs.analytics.AnalyticsPass;
import stubs.analytics.AnalyticsVisit;
import stubs.cart.CardItem;
import stubs.cart.ItemType;

import java.util.List;

public class GetVisitAnalytics extends Command<CastexSkiAPI> {

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
	public String identifier() { return "visit-analytics"; }

	@Override
	public void execute() throws Exception {

		List<AnalyticsVisit> visits = shell.system.analyticsWebService.getVisitAnalyticsByDay(day,month, year);
		for(AnalyticsVisit visit: visits){
			System.out.println(visit.getSkiLiftName().toString()+" : "+visit.getCounter());
		}
		if(visits.size()==0){
			System.out.println("pas de statistiques de passage");

		}
	}

	@Override
	public String describe() {
		return "Get day visit analytics (visit-analytics day month year)";
	}
}
