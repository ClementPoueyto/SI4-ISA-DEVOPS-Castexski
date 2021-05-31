package fr.unice.polytech.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import fr.unice.polytech.employee.exceptions.*;
import fr.unice.polytech.employee.interfaces.*;
import fr.unice.polytech.entities.Gate;
import fr.unice.polytech.entities.SkiLift;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import arquillian.AbstractArquillianTest;
import fr.unice.polytech.entities.Screen;
import fr.unice.polytech.entities.Zone;

import java.util.Optional;


@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class EmployeeTest extends AbstractArquillianTest {

    @EJB
    GateAdder gateAdder;

    @EJB
    SkiliftAdder skiliftAdder;

    @EJB
    ScreenAdder screenAdder;

    @EJB
    ScreenFinder screenFinder;

    @EJB
    DisplayMessage displayMessage;

    @EJB
    ZoneAdder zoneAdder;

    @EJB
    ZoneFinder zoneFinder;

    private String screenId;

    private String screenId2;

    private String screenId3;

    private String screenId4;

    private String zoneId;

    private String zoneId2;

    @Inject
    private UserTransaction utx;
    
    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;
    

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpContext()  {
        
    }

    @After
    public void cleaningUp() throws Exception {
        
        
            utx.begin();
            Zone z = manager.find(Zone.class, zoneId);
            z = manager.merge(z);
            manager.remove(z);
            zoneId = null;
            Zone z2 = manager.find(Zone.class, zoneId2);
            z2 = manager.merge(z2);
            manager.remove(z2);
            zoneId2 = null;
            Screen s = manager.find(Screen.class, screenId);
            s = manager.merge(s);
            manager.remove(s);
            screenId = null;
            Screen s2 = manager.find(Screen.class, screenId2);
            s2 = manager.merge(s2);
            manager.remove(s2);
            screenId2 = null;
            Screen s3 = manager.find(Screen.class, screenId3);
            s3 = manager.merge(s3);
            manager.remove(s3);
            screenId3 = null;
            Screen s4 = manager.find(Screen.class, screenId4);
            s4 = manager.merge(s4);
            manager.remove(s4);
            screenId4 = null;
            utx.commit(); 
        
    }

    @Test
    public void addAndFindZoneTest() throws AlreadyExistingZoneException {
        zoneAdder.addZone("z", "CastexStation");
        Optional<Zone> zone = zoneFinder.getZoneByName("z");
        assertTrue(zone.isPresent());
        assertEquals("CastexStation", zone.get().getStation());
    }

    @Test(expected = AlreadyExistingZoneException.class)
    public void AlreadyExistingZoneException() throws AlreadyExistingZoneException{
        zoneAdder.addZone("newzone", "CastexStation");
        zoneAdder.addZone("newzone", "CastexStation");
        thrown.expect(AlreadyExistingZoneException.class);
    }


    @Test
    public void addAndFindScreenTest() throws ZoneNameNotFoundException, AlreadyExistingZoneException, ZoneIdNotFoundException, ScreenNotFoundException{
        zoneAdder.addZone("test", "CastexStation");
        Optional<Zone> zone = zoneFinder.getZoneByName("test");
        assertTrue(zone.isPresent());
        zoneId = zone.get().getId();
        screenId = screenAdder.addScreen(zone.get().getName());
        assertEquals(1, screenFinder.findAll().size());
        assertEquals(zone.get(), screenFinder.findById(screenId).getZone());
    }

    @Test(expected = ZoneIdNotFoundException.class)
    public void ZoneIdNotFoundException() throws ZoneIdNotFoundException{
        
        zoneFinder.getZoneById("id");
        thrown.expect(ZoneIdNotFoundException.class);
    }

    @Test(expected = ScreenNotFoundException.class)
    public void ScreenNotFoundException() throws ScreenNotFoundException{
        screenFinder.findById("id");
        thrown.expect(ScreenNotFoundException.class);
    }

    @Test
    public void successDisplayMessageTest() throws ScreenNotFoundException, AlreadyExistingZoneException, ZoneNameNotFoundException{
        zoneAdder.addZone("test1", "CastexStation");
        screenId = screenAdder.addScreen("test1");
        screenId2 = screenAdder.addScreen("test1");
        displayMessage.showMessage("test1", screenId, screenId2);
        assertEquals("test1", screenFinder.findById(screenId).getMessage());
        assertEquals(true, screenFinder.findById(screenId).isOn());
        assertEquals("test1", screenFinder.findById(screenId2).getMessage());
        assertEquals(true, screenFinder.findById(screenId2).isOn());
    }

    @Test
    public void displayMessageZoneTest() throws ScreenNotFoundException, AlreadyExistingZoneException, ZoneNameNotFoundException{
        zoneAdder.addZone("test3", "CastexStation");
        zoneAdder.addZone("test2", "CastexStation");
        screenId = screenAdder.addScreen("test3");
        screenId2 = screenAdder.addScreen("test3");
        screenId3 = screenAdder.addScreen("test2");
        screenId4 = screenAdder.addScreen("test2");
        displayMessage.showMessageOnZonesScreens("test message", "test3", "test2");
        assertEquals("test message", screenFinder.findById(screenId).getMessage());
        assertEquals(true, screenFinder.findById(screenId).isOn());
        assertEquals("test message", screenFinder.findById(screenId2).getMessage());
        assertEquals(true, screenFinder.findById(screenId2).isOn());
        assertEquals("test message", screenFinder.findById(screenId3).getMessage());
        assertEquals(true, screenFinder.findById(screenId3).isOn());
        assertEquals("test message", screenFinder.findById(screenId4).getMessage());
        assertEquals(true, screenFinder.findById(screenId4).isOn());
    }




}