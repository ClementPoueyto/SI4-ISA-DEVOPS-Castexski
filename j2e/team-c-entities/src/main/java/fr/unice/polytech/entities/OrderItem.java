package fr.unice.polytech.entities;

import fr.unice.polytech.utils.ItemType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderItem implements Serializable {

    @Enumerated(EnumType.STRING)
    @NotNull
    protected ItemType type;

    @NotNull
    protected int quantity;

    @NotNull
    protected boolean available;

    @NotNull
    protected double price;

    public OrderItem() {
        this.quantity=1;
        this.available=true;
        this.type = ItemType.PASS;

    }

    public OrderItem(ItemType type) {
        this.type = type;
        this.quantity=1;
        this.available=true;
    }

    public OrderItem(int quantity) {
        this.quantity = quantity;
        this.available=true;
        this.type = ItemType.PASS;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return type == orderItem.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "type=" + type +
                ", quantity=" + quantity +
                ", available=" + available +
                ", price=" + price +
                '}';
    }
}

