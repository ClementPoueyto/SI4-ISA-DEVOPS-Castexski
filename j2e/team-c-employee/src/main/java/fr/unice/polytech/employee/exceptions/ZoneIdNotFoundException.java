package fr.unice.polytech.employee.exceptions;

import java.io.Serializable;

public class ZoneIdNotFoundException extends Exception implements Serializable {
    
    public ZoneIdNotFoundException(String id){
        super("There is no Zone with the id " + id);
    }
}
