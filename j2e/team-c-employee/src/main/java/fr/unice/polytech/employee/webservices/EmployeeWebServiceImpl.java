package fr.unice.polytech.employee.webservices;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fr.unice.polytech.employee.exceptions.*;
import fr.unice.polytech.employee.interfaces.*;

@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/employee")
@Stateless(name = "EmployeeWS")
public class EmployeeWebServiceImpl implements EmployeeWebService {

    @EJB private ScreenAdder screenAdder;

    @EJB private ZoneAdder zoneAdder;

    @EJB private SkiliftAdder skiliftAdder;

    @EJB private GateAdder gateAdder;

    @EJB private DisplayMessage displayMessage;

    @Override
    public void addZone(String name, String station) throws AlreadyExistingZoneException{
        zoneAdder.addZone(name, station);
    }

    @Override
    public void addSkilift(String name, String zoneName) throws AlreadyExistingSkiliftException, UnknownZoneException {
        skiliftAdder.addSkilift(name, zoneName);
    }

    @Override
    public void addANewScreen(String zoneName) throws ZoneNameNotFoundException {
        screenAdder.addScreen(zoneName);
    }

    @Override
    public void displayMessageOnScreens(String message, String... screenIds) throws ZoneIdNotFoundException {
        displayMessage.showMessage(message, screenIds);
        
    }

    @Override
    public void diplayMessageOnZonesScreens(String message, String... zoneNames) throws ZoneNameNotFoundException {
        displayMessage.showMessageOnZonesScreens(message, zoneNames);
        
    }

    @Override
    public void addGate(String physicalId, String skiliftName) throws UnknownSkiliftException, AlreadyExistingGateException {
        gateAdder.addGate(physicalId, skiliftName);
    }
}
