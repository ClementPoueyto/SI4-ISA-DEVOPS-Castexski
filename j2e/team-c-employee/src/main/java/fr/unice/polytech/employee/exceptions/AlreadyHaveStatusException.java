package fr.unice.polytech.employee.exceptions;

import java.io.Serializable;

import fr.unice.polytech.utils.Status;

public class AlreadyHaveStatusException extends Exception implements Serializable{
    
    public AlreadyHaveStatusException(Status status){
        super("The Zone is already " + status.toString());
    }
}
