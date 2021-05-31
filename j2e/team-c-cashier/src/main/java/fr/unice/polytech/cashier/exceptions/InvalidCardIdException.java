package fr.unice.polytech.cashier.exceptions;


import java.io.Serializable;

public class InvalidCardIdException extends Exception implements Serializable {
    private String cardId;
    private String name;

    public InvalidCardIdException(){}
    public InvalidCardIdException(String cardId, String name) {
       this.cardId = cardId;
       this.name = name;
    }

    public String getCardId() {
        return cardId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Card with id " + this.getCardId();
    }
}
