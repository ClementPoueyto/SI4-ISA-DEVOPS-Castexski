package fr.unice.polytech.gate.interceptors;

import fr.unice.polytech.analytics.interfaces.AnalyticsUpdate;
import fr.unice.polytech.entities.AnalyticsVisit;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Gate;
import fr.unice.polytech.utils.PassageResponse;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyticsVisitCounter implements Serializable {

	private static final Logger log = Logger.getLogger(AnalyticsVisitCounter.class.getName());

	@EJB
	private AnalyticsUpdate analyticsVisit;

	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		PassageResponse response = (PassageResponse) ctx.proceed();
		Card card = (Card) ctx.getParameters()[0];
		Gate gate = (Gate) ctx.getParameters()[1];
		if(response==PassageResponse.PASS_OK) {
			log.log(Level.INFO, "Analytics visit updated : "+gate.getSkiLift().getName());
			AnalyticsVisit av = new AnalyticsVisit(gate.getSkiLift().getName());
			analyticsVisit.updateAnalytics(av);
		}
		return response;
	}

}
