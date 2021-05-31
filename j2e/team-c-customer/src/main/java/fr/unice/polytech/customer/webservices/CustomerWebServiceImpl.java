package fr.unice.polytech.customer.webservices;

import fr.unice.polytech.customer.exceptions.AlreadyAssociatedCard;
import fr.unice.polytech.customer.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.customer.interfaces.CustomerRegistry;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/customer")
@Stateless(name = "CustomerWS")
public class CustomerWebServiceImpl implements CustomerWebService{

    @EJB private CustomerRegistry customerRegistry;

    @Override
    public void registerCustomer(String email,String customerName, String creditCard) throws AlreadyExistingCustomerException {
        customerRegistry.registerCustomer(email,customerName, creditCard);
    }

    @Override
    public void associateCardToCustomer(Customer customer, Card card) throws AlreadyAssociatedCard {
        customerRegistry.associateCardToCustomer(customer,card);
    }


}
