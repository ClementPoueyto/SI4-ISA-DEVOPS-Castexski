package fr.unice.polytech.utils;
public enum PassType {
    CLASSIC(15.00, 1),
    WEEK(70.00, 7); // price by day

    private double duration;
    private double price;

    PassType(double price, double days){
        this.price = price;
        this.duration = days;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
