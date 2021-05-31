package fr.unice.polytech.cart.interfaces;

import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.cashier.interfaces.Payment;
import fr.unice.polytech.cart.exceptions.EmptyCartException;
import fr.unice.polytech.entities.*;
import fr.unice.polytech.exceptions.UnknownCardException;

import javax.ejb.Local;
import java.util.Set;

@Local
public interface CartModifier {
    boolean addItem(Customer customer, OrderItem card);

    boolean removeItem(Customer customer, OrderItem item);

//    boolean buyPassWithCard(Customer customer, Pass pass);

    Set<OrderItem> payByCb(Customer customer) throws PaymentException, EmptyCartException, InvalidCardIdException, UnknownCardException;

    Set<OrderItem> contents(Customer customer);

    void setPayment(Payment p);

    Set<String> cartValidated(Customer c, Set<OrderItem> cart);
}
