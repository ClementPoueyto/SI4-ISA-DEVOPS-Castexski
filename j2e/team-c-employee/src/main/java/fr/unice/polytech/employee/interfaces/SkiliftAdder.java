package fr.unice.polytech.employee.interfaces;

import fr.unice.polytech.employee.exceptions.AlreadyExistingSkiliftException;
import fr.unice.polytech.employee.exceptions.UnknownZoneException;

import javax.ejb.Local;

@Local
public interface SkiliftAdder {

    void addSkilift(String name, String zone) throws AlreadyExistingSkiliftException, UnknownZoneException;

}
