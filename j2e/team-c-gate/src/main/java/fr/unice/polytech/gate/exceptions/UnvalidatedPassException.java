package fr.unice.polytech.gate.exceptions;

import java.io.Serializable;

public class UnvalidatedPassException extends Exception implements Serializable{

    public UnvalidatedPassException(String id){
        super("Unknown card " + id.toString());
    }
}
