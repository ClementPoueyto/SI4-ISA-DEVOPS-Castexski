package fr.unice.polytech.gate.interfaces;

import fr.unice.polytech.entities.Zone;
import fr.unice.polytech.gate.exceptions.AlreadyHaveStatusException;
import fr.unice.polytech.gate.exceptions.UnknownZoneException;
import fr.unice.polytech.utils.Status;

import javax.ejb.Local;

@Local
public interface StateModifier {

    void setZoneState(Zone zone, Status status) throws UnknownZoneException, AlreadyHaveStatusException;

    void setSkiLiftState(String idLift, Status status);

}