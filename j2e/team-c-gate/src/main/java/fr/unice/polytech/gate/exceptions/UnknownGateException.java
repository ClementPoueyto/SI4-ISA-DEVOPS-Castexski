package fr.unice.polytech.gate.exceptions;

import java.io.Serializable;

public class UnknownGateException extends Exception implements Serializable {

    public UnknownGateException(){
        super("Unknown Gate");
    }
}
