package fr.unice.polytech.employee.exceptions;

import java.io.Serializable;

public class ZoneNameNotFoundException extends Exception implements Serializable {
    
    public ZoneNameNotFoundException(String name){
        super("There is no Zone with the name " + name);
    }
}
