package fr.unice.polytech.employee.exceptions;

public class ScreenNotFoundException extends Exception {
    public ScreenNotFoundException(String id){
        super("There is no Screen with the id " + id);
    }
}
