package fr.unice.polytech.cart.components;


import fr.unice.polytech.card.interfaces.CardFinder;
import fr.unice.polytech.cart.exceptions.EmptyCartException;
import fr.unice.polytech.cart.interceptors.AnalyticsPassCounter;
import fr.unice.polytech.cart.interceptors.ItemVerifier;
import fr.unice.polytech.cart.interfaces.CartModifier;
import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.cashier.interfaces.Payment;
import fr.unice.polytech.entities.*;
import fr.unice.polytech.exceptions.UnknownCardException;
import fr.unice.polytech.utils.ItemType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class CartBean implements CartModifier {

    private static final Logger log = Logger.getLogger(Logger.class.getName());


    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;

    @EJB
    public Payment cashier;

    @EJB
    public CardFinder cardFinder;

    @Override
    @Interceptors({ItemVerifier.class})
    public boolean addItem(Customer customer, OrderItem item) {
        log.log(Level.INFO, "Order item added : " + item);
        customer = manager.merge(customer);
        customer.setCart(updateCart(customer, item));
        log.log(Level.INFO, "Customer : " + customer);
        return true;

    }




    @Override
    @Interceptors({ItemVerifier.class})
    public boolean removeItem(Customer customer, OrderItem item) {
        item.setQuantity(-Math.abs(item.getQuantity()));
        log.log(Level.INFO, "Item  " + item.toString() + " removed!");

        return addItem(customer, item);
    }



    protected Set<OrderItem> updateCart(Customer c, OrderItem item) {
        Set<OrderItem> items = contents(c);
        Optional<OrderItem> existing = items.stream().filter(e -> e.equals(item)).findFirst();
        if (existing.isPresent()) {
            existing.get().setQuantity(Math.max(0, item.getQuantity() + existing.get().getQuantity()));
            if (existing.get().getQuantity() == 0) {
                items.remove(existing.get());
            }
        } else {
            if (item.getQuantity() > 0) {
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public Set<OrderItem> contents(Customer customer) {
        customer = manager.merge(customer);
        return customer.getCart();
    }

    @Override
    public Set<OrderItem> payByCb(Customer c) throws PaymentException, EmptyCartException, InvalidCardIdException, UnknownCardException, UnknownCardException {
        if (contents(c).isEmpty())
            throw new EmptyCartException(c.getName());
        Set<OrderItem> items = contents(c);
        log.log(Level.INFO, items.toString());
        if(cashier.payByCb(c, items)){
            return items;
        }

        throw new PaymentException();
    }

    @Override
    @Interceptors({AnalyticsPassCounter.class})
    public Set<String> cartValidated(Customer c, Set<OrderItem> cart){

        log.log(Level.INFO, "Analytics before updated !!!!!!!!!!!!!!!!");
        Set<String> cardIds = updateData(cart, c);
        c.clearCart();
        return cardIds;
    }

    public Set<String> updateData(Set<OrderItem> items, Customer c){
        Set<String> cardIds = new HashSet<>();
        log.log(Level.INFO, items.toString());
        for(OrderItem orderItem : items)
            if (orderItem.getType().equals(ItemType.CARD)) {
                Card card = new Card((CardItem) orderItem);
                cardFinder.putCard(card);
                manager.flush();
                manager.refresh(card);
                c.setCard(card);
                manager.merge(c);
                log.log(Level.INFO, "Card " + card + " added");
                cardIds.add(card.getId());
            } else if (orderItem.getType().equals(ItemType.PASS)) {
                log.log(Level.INFO, "Pass item : " + orderItem);
                Pass pass = new Pass((PassItem) orderItem);
                log.log(Level.INFO, "Pass : " + pass);
                Optional<Card> linkedCard = cardFinder.getCardById(((PassItem) orderItem).getCardLinkId());
                log.log(Level.INFO, "LINKED CARD : " + linkedCard);
                cardFinder.putPass(pass);
                if(linkedCard.isPresent()){
                    linkedCard.get().setPass(pass);
                    cardFinder.putCard(linkedCard.get());
                }
            }
        return cardIds;
    }

    public void setPayment(Payment p){
        this.cashier = p;
    }
}
