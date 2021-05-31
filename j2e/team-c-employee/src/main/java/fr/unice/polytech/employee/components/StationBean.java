package fr.unice.polytech.employee.components;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fr.unice.polytech.employee.exceptions.*;
import fr.unice.polytech.employee.interfaces.*;
import fr.unice.polytech.entities.Gate;
import fr.unice.polytech.entities.SkiLift;
import fr.unice.polytech.entities.Zone;
import fr.unice.polytech.utils.Status;

@Stateless
public class StationBean implements ZoneAdder, ZoneFinder, SkiliftAdder, SkiliftFinder, GateAdder, GateFinder {

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;


    @Override
    public void addZone(String name, String station) throws AlreadyExistingZoneException {
        if(getZoneByName(name).isPresent()) {
            throw new AlreadyExistingZoneException();
        }
        Zone zone = new Zone(name, station, Status.OPENED);
        manager.persist(zone);
        log.log(Level.INFO, "Zone added : " + zone);
    }

    @Override
    public void addSkilift(String name, String zoneName) throws AlreadyExistingSkiliftException, UnknownZoneException {
        if(getSkiLiftByName(name).isPresent()) {
            throw new AlreadyExistingSkiliftException();
        }

        Optional<Zone> zone = getZoneByName(zoneName);
        if(zone.isPresent()) {
            SkiLift skiLift = new SkiLift(name, zone.get(), Status.OPENED);
            manager.persist(skiLift);
            log.log(Level.INFO, "Skilift added : " + skiLift);
        } else {
            throw new UnknownZoneException();
        }
    }


    @Override
    public void addGate(String physicalId, String skiliftId) throws UnknownSkiliftException, AlreadyExistingGateException {
        Optional<SkiLift> skiLift = getSkiLiftByName(skiliftId);
        if(getGateByPhysicalId(physicalId).isPresent()){
            throw new AlreadyExistingGateException();
        }
        if(skiLift.isPresent()) {
            for(Gate g : skiLift.get().getGates()){
                if(g.getPhysicalId().equals(physicalId)){
                    throw new AlreadyExistingGateException();
                }
            }
            Gate gate = new Gate(physicalId, skiLift.get());
            manager.persist(gate);
            log.log(Level.INFO, "Gate added : " + gate);
        } else {
            throw new UnknownSkiliftException();
        }
    }


    @Override
    public Optional<Zone> getZoneByName(String name) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Zone> criteria = builder.createQuery(Zone.class);
        Root<Zone> root =  criteria.from(Zone.class);
        criteria.select(root).where(builder.equal(root.get("name"), name));
        TypedQuery<Zone> query = manager.createQuery(criteria);
        Optional<Zone> zone;
        try {
            zone= Optional.of(query.getSingleResult());
        } catch (NoResultException nre){
            zone = Optional.empty();
        }
        if(zone.isPresent()) {
            log.log(Level.INFO, "Zone found with name " + zone.get().getName() + "-" + zone);
        }
        else{
            log.log(Level.INFO, "No zone found with name : " + name);
        }
        return zone;
    }


    @Override
    public Optional<Gate> getGateByPhysicalId(String physicalId) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Gate> criteria = builder.createQuery(Gate.class);
        Root<Gate> root =  criteria.from(Gate.class);
        criteria.select(root).where(builder.equal(root.get("physicalId"), physicalId));
        TypedQuery<Gate> query = manager.createQuery(criteria);
        Optional<Gate> gate;
        try {
            gate= Optional.of(query.getSingleResult());
        } catch (NoResultException nre){
            gate = Optional.empty();
        }
        if(gate.isPresent()) {
            log.log(Level.INFO, "Gate found : " + gate);
        }
        else{
            log.log(Level.INFO, "No gate found with physical ID : " + physicalId);
        }
        return gate;
    }

    @Override
    public Optional<SkiLift> getSkiLiftByName(String name) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<SkiLift> criteria = builder.createQuery(SkiLift.class);
        Root<SkiLift> root =  criteria.from(SkiLift.class);
        criteria.select(root).where(builder.equal(root.get("name"), name));
        TypedQuery<SkiLift> query = manager.createQuery(criteria);
        Optional<SkiLift> skilift;
        try {
            skilift= Optional.of(query.getSingleResult());
        } catch (NoResultException nre){
            skilift = Optional.empty();
        }
        if(skilift.isPresent()) {
            log.log(Level.INFO, "Skilift found with name " + skilift.get().getName() + "-" + skilift);
        }
        else{
            log.log(Level.INFO, "No skilift found with name : " + name);
        }
        return skilift;
    }

    @Override
    public Zone getZoneById(String id) throws ZoneIdNotFoundException{
        Zone zone = manager.find(Zone.class, id);
        if(zone == null)
            throw new ZoneIdNotFoundException(id);
        return zone;
    }

}
