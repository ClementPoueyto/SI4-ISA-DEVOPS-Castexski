package fr.unice.polytech.cart.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartCounter implements Serializable {

	private static final Logger log = Logger.getLogger(CartCounter.class.getName());

	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		Object result = ctx.proceed();  // do what you're supposed to do
		log.log(Level.INFO, "#Cart validated ");
		return result;
	}

}
