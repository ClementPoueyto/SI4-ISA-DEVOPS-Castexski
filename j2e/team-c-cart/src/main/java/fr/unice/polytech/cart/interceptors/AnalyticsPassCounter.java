package fr.unice.polytech.cart.interceptors;

import fr.unice.polytech.analytics.interfaces.AnalyticsUpdate;
import fr.unice.polytech.entities.OrderItem;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyticsPassCounter implements Serializable {

	private static final Logger log = Logger.getLogger(AnalyticsPassCounter.class.getName());

	@EJB
	private AnalyticsUpdate analytics;

	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		Set<OrderItem> it = (Set<OrderItem>) ctx.getParameters()[1];
		log.log(Level.INFO, "Analytics pass updated");

		analytics.updateAnalytics(it);
		return ctx.proceed();

	}

}
