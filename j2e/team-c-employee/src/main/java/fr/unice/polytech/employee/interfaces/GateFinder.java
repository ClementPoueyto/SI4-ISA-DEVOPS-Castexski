package fr.unice.polytech.employee.interfaces;

import fr.unice.polytech.entities.Gate;

import javax.ejb.Local;
import java.util.Optional;

@Local
public interface GateFinder {
    Optional<Gate> getGateByPhysicalId(String physicalId);
}
