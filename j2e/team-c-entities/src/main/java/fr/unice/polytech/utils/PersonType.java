package fr.unice.polytech.utils;

public enum PersonType {
    ADULT(1),
    CHILD(0.75);

    private double coeff;

    PersonType(double coeff){
        this.coeff = coeff;
    }

    public double getCoeff() {
        return coeff;
    }

    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }

}
