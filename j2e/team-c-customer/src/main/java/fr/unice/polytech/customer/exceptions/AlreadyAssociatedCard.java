package fr.unice.polytech.customer.exceptions;

public class AlreadyAssociatedCard extends Exception {

    private String id;

    public AlreadyAssociatedCard(String id) {
        this.id = id;
    }


    public AlreadyAssociatedCard() {
    }

}
