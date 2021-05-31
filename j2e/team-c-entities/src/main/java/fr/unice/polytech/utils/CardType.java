package fr.unice.polytech.utils;

public enum CardType {
    CARTEX(2.00),
    SUPERCARTEX(10.00);

    private double price;

    CardType(double price){
        this.price = price;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
