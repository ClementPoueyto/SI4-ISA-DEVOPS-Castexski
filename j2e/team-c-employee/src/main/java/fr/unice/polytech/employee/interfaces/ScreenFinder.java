package fr.unice.polytech.employee.interfaces;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import fr.unice.polytech.employee.exceptions.ScreenNotFoundException;
import fr.unice.polytech.entities.Screen;

@Local
public interface ScreenFinder {

    Screen findById(String id) throws ScreenNotFoundException;

    List<Screen> findAll();
    
}
