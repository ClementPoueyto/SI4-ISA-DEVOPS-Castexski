package fr.unice.polytech.employee.interfaces;

import javax.ejb.Local;

import fr.unice.polytech.employee.exceptions.ZoneIdNotFoundException;
import fr.unice.polytech.entities.Zone;

import java.util.Optional;

@Local
public interface ZoneFinder {

    Optional<Zone> getZoneByName(String name);

    Zone getZoneById(String id) throws ZoneIdNotFoundException;
}
