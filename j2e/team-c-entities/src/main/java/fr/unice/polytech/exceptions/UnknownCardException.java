package fr.unice.polytech.exceptions;


import java.io.Serializable;

public class UnknownCardException extends Exception implements Serializable {

    public String id;

    public UnknownCardException(){}

    public UnknownCardException(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UnknownCardException with id: " + id + '\'' ;
    }
}
