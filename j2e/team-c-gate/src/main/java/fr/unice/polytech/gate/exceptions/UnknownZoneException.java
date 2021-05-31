package fr.unice.polytech.gate.exceptions;

import java.io.Serializable;

public class UnknownZoneException extends Exception implements Serializable {
    
    public UnknownZoneException(){
        super("Unknown Zone");
    }
}
