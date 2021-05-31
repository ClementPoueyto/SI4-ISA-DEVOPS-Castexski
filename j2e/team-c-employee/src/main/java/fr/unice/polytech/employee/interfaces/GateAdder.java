package fr.unice.polytech.employee.interfaces;

import fr.unice.polytech.employee.exceptions.AlreadyExistingGateException;
import fr.unice.polytech.employee.exceptions.UnknownSkiliftException;

import javax.ejb.Local;

@Local
public interface GateAdder {

    void addGate(String physicalId, String skiliftId) throws UnknownSkiliftException, AlreadyExistingGateException;

}
