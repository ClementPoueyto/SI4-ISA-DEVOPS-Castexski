package fr.unice.polytech.employee.components;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fr.unice.polytech.employee.exceptions.ScreenNotFoundException;
import fr.unice.polytech.employee.exceptions.ZoneIdNotFoundException;
import fr.unice.polytech.employee.exceptions.ZoneNameNotFoundException;
import fr.unice.polytech.employee.interfaces.DisplayMessage;
import fr.unice.polytech.employee.interfaces.ScreenAdder;
import fr.unice.polytech.employee.interfaces.ScreenFinder;
import fr.unice.polytech.employee.interfaces.ZoneFinder;
import fr.unice.polytech.entities.Screen;
import fr.unice.polytech.entities.Zone;

@Stateless
public class ScreenBean implements DisplayMessage, ScreenAdder, ScreenFinder{

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;

    @EJB
    ZoneFinder zoneFinder;

    @Override
    public void showMessage(String message, String... ids) {
        Arrays.asList(ids).forEach(id -> {
            try{
                Screen screen = findById(id);
                screen.setMessage(message);
                manager.persist(screen);
                log.log(Level.INFO, "Send " + message + " to Screen : " +  screen.toString());
            } catch (ScreenNotFoundException e){
                log.log(Level.INFO, e.getMessage());
            }
        });
    }

    @Override
    public void showMessageOnZonesScreens(String message, String... names) {
        Arrays.asList(names).forEach(name -> {
            Optional<Zone> zone = zoneFinder.getZoneByName(name);
            if(zone.isPresent()) {
                manager.refresh(zone.get());
                showMessage(message, (String[]) zone.get().getScreens().stream().map(s -> s.getId()).toArray(String[]::new));
            } else {
                log.log(Level.INFO, "No zone found with name " + name);
            }
        });
    }

    @Override
    public List<Screen> findAll() {
        return manager
                .createQuery("Select c from Screen c", Screen.class)
                .getResultList();
    }

    @Override
    public String addScreen(String zoneName) throws ZoneNameNotFoundException{
        Optional<Zone> zone = zoneFinder.getZoneByName(zoneName);
        if(!zone.isPresent()) {
            throw new ZoneNameNotFoundException(zoneName);
        }
        Screen screen = new Screen(zone.get());
        manager.persist(screen);
        manager.flush();
        log.log(Level.INFO, "Screen added with the id : " + screen.getId());
        return screen.getId();
    }

    @Override
    public Screen findById(String id) throws ScreenNotFoundException {
        Screen screen = manager.find(Screen.class, id);
        if(screen == null)
            throw new ScreenNotFoundException(id);
        return screen;
    }
    
}
