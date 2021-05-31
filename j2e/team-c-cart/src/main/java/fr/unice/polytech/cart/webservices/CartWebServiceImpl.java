package fr.unice.polytech.cart.webservices;

import fr.unice.polytech.cart.exceptions.EmptyCartException;
import fr.unice.polytech.cart.interfaces.CartModifier;
import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.cart.exceptions.UnknownCustomerException;
import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.entities.OrderItem;
import fr.unice.polytech.exceptions.UnknownCardException;
import fr.unice.polytech.customer.interfaces.CustomerFinder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Optional;
import java.util.Set;

@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/cart")
@Stateless(name = "CartWS")
public class CartWebServiceImpl implements CartWebService{

    @EJB private CartModifier cart;
    @EJB private CustomerFinder customerFinder;


    @Override
    public void addItemToCustomerCart(String customerId, OrderItem it) throws UnknownCustomerException {
        cart.addItem(readCustomer(customerId), it);
    }

    @Override
    public void removeItemToCustomerCart(String idCustomer, OrderItem it) throws UnknownCustomerException {
        cart.removeItem(readCustomer(idCustomer), it);
    }

    @Override
    public Set<OrderItem> getCustomerCartContents(String idCustomer) throws UnknownCustomerException {
        return cart.contents(readCustomer(idCustomer));
    }

    @Override
    public Set<String> validate(String idCustomer)
           throws PaymentException, UnknownCustomerException, EmptyCartException, InvalidCardIdException, UnknownCardException
    {
        Customer c = readCustomer(idCustomer);
        Set<OrderItem> order = cart.payByCb(c);
        return cart.cartValidated(c,order);
    }

    private Customer readCustomer(String idCustomer)
            throws UnknownCustomerException {
        Optional<Customer> c = customerFinder.findByEmail(idCustomer);
        if(!c.isPresent())
            throw new UnknownCustomerException(idCustomer);
        return c.get();
    }
}
