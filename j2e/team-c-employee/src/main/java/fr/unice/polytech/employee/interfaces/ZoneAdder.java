package fr.unice.polytech.employee.interfaces;

import javax.ejb.Local;

import fr.unice.polytech.employee.exceptions.AlreadyExistingZoneException;

@Local
public interface ZoneAdder {

    void addZone(String name, String station) throws AlreadyExistingZoneException;

}
