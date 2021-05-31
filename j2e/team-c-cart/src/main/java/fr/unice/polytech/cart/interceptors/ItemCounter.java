package fr.unice.polytech.cart.interceptors;

import fr.unice.polytech.entities.OrderItem;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemCounter implements Serializable {
/*
	private static final Logger log = Logger.getLogger(ItemCounter.class.getName());

	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		OrderItem it = (OrderItem) ctx.getParameters()[1];
		log.log(Level.INFO, "#Cart validated ");

		return null;
	}
*/
}
