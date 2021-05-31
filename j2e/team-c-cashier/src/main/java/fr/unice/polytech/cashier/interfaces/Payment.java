package fr.unice.polytech.cashier.interfaces;

import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.entities.OrderItem;
import fr.unice.polytech.exceptions.UnknownCardException;
import fr.unice.polytech.utils.BankAPI;

import javax.ejb.Local;
import java.util.Set;

@Local
public interface Payment {


    double getOrderPrice(Set<OrderItem> items);

    boolean payByCb(Customer customer, Set<OrderItem> items) throws PaymentException, InvalidCardIdException, UnknownCardException;

    void useBankReference(BankAPI bank);


}
