package fr.unice.polytech.employee.interfaces;

import fr.unice.polytech.entities.SkiLift;

import javax.ejb.Local;
import java.util.Optional;

@Local
public interface SkiliftFinder {

    Optional<SkiLift> getSkiLiftByName(String name);

}
