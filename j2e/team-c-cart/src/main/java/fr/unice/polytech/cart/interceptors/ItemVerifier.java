package fr.unice.polytech.cart.interceptors;


import fr.unice.polytech.entities.OrderItem;
import fr.unice.polytech.exceptions.UncheckedException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemVerifier {

	private static final Logger log = Logger.getLogger(ItemCounter.class.getName());

	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {

		OrderItem it = (OrderItem) ctx.getParameters()[1];

		if (it.getQuantity() <= 0) {
			throw new UncheckedException("Inconsistent quantity!", null);
		}
		log.log(Level.INFO, "Item checked !!!!!!!!!!!!!!!!");

		return ctx.proceed();  // do what you're supposed to do

	}

}
