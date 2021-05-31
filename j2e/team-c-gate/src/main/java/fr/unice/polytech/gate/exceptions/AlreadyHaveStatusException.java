package fr.unice.polytech.gate.exceptions;

import fr.unice.polytech.utils.Status;

import java.io.Serializable;

public class AlreadyHaveStatusException extends Exception implements Serializable{
    
    public AlreadyHaveStatusException(Status status){
        super("The Zone is already " + status.toString());
    }
}
