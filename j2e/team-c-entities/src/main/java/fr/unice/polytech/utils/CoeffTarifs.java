package fr.unice.polytech.utils;

public enum CoeffTarifs {
    DAY(1),
    HALF_DAY(0.6),
    THREE_DAYS_AND_MORE(0.9),
    ONE_WEEK_AND_MORE(0.75);

    private double coeff;

    CoeffTarifs(double coeff){
        this.coeff = coeff;
    }

    public double getCoeff() {
        return coeff;
    }

    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }
}
