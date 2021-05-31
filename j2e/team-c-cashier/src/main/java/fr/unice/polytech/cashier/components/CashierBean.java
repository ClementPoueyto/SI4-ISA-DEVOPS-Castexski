package fr.unice.polytech.cashier.components;


import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.cashier.interfaces.Payment;
import fr.unice.polytech.entities.*;
import fr.unice.polytech.utils.BankAPI;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class CashierBean implements Payment {

    private static final Logger log = Logger.getLogger(Logger.class.getName());


    private BankAPI bank;

    public void useBankReference(BankAPI bank) {
        this.bank = bank;
    }


    @Override
    public double getOrderPrice(Set<OrderItem> items) {
        float res = 0;
        for (OrderItem item : items) {
            res += item.getPrice() * item.getQuantity();
        }
        return res;
    }

    @Override
    public boolean payByCb(Customer customer, Set<OrderItem> items) throws PaymentException {
        log.log(Level.INFO, "Processing payment by CB...");
        log.log(Level.INFO, customer.toString());
        double price = getOrderPrice(items);

        boolean status;
        try {
            status = bank.performPayment(customer, price);
        } catch (fr.unice.polytech.exceptions.ExternalPartnerException e) {
            throw new PaymentException(customer.getName(), price);
        }

        if (!status) {
            throw new PaymentException(customer.getName(), price);
        }

        log.log(Level.INFO, "Payment process done!");
        log.log(Level.INFO, customer.getName()+" paid "+price+"€");
        log.log(Level.INFO, "#####################");

        return true;
    }

    @PostConstruct
    private void initializeRestPartnership() {
        bank = new BankAPI();
    }

}
