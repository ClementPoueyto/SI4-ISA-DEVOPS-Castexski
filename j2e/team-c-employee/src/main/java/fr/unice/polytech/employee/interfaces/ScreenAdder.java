package fr.unice.polytech.employee.interfaces;

import javax.ejb.Local;

import fr.unice.polytech.employee.exceptions.ZoneIdNotFoundException;
import fr.unice.polytech.employee.exceptions.ZoneNameNotFoundException;

@Local
public interface ScreenAdder {
    
    String addScreen(String zoneName) throws ZoneNameNotFoundException;
}
