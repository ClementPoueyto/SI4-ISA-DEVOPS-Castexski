package fr.unice.polytech.exceptions;


public class UncheckedException extends RuntimeException {

    public UncheckedException(Exception e) {
        super(e);
    }

    public UncheckedException(String msg, Exception e) {
        super(msg, e);
    }

}
