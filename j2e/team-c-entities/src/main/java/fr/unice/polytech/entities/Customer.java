package fr.unice.polytech.entities;

import fr.unice.polytech.utils.ItemType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @Pattern(regexp = "\\d{6}+", message = "Invalid creditCardNumber")
    private String creditCard;

    @ElementCollection
    private Set<PassItem> cartPass = new HashSet<>();

    @ElementCollection
    private Set<CardItem> cartCard = new HashSet<>();

    @OneToOne(cascade = {CascadeType.ALL})
    private Card card;


    public Customer() {
    }

    public Customer(String email, String name) {
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", creditCard='" + creditCard + '\'' +
                ", cartPass=" + cartPass +
                ", cartCard=" + cartCard +
                ", card=" + card +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public Set<PassItem> getCartPass() {
        return cartPass;
    }

    public void setCartPass(Set<PassItem> cartPass) {
        this.cartPass = cartPass;
    }

    public Set<CardItem> getCartCard() {
        return cartCard;
    }

    public void setCartCard(Set<CardItem> cartCard) {
        this.cartCard = cartCard;
    }

    public void setCart(Set<OrderItem> ois) {
        this.cartPass = new HashSet<>();
        this.cartCard = new HashSet<>();
        for(OrderItem oi : ois) {
            if(oi.getType().equals(ItemType.PASS)) {
                cartPass.add((PassItem) oi);
            }
            else if(oi.getType().equals(ItemType.CARD)) {
                cartCard.add((CardItem) oi);
            }
        }
    }

    public Set<OrderItem> getCart() {
        Set<OrderItem> orderItems = new HashSet<>(this.getCartCard());
        orderItems.addAll(this.getCartPass());
        return orderItems;
    }

    public void clearCart() {
        this.cartPass.clear();
        this.cartCard.clear();
    }
}