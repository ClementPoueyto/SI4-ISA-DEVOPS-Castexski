package fr.unice.polytech.customer.interfaces;

import fr.unice.polytech.customer.exceptions.AlreadyAssociatedCard;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.customer.exceptions.AlreadyExistingCustomerException;

import javax.ejb.Local;

@Local
public interface CustomerRegistry {


    void registerCustomer(String email, String name, String creditCard) throws AlreadyExistingCustomerException;

    void associateCardToCustomer(Customer customer, Card card) throws AlreadyAssociatedCard;

}
