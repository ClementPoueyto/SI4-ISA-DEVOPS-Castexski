package fr.unice.polytech.employee.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import fr.unice.polytech.employee.exceptions.*;


@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/employee")
public interface EmployeeWebService {

    @WebMethod
    void addANewScreen(String zoneName) throws ZoneNameNotFoundException;

    @WebMethod
    void displayMessageOnScreens(String message, String...screenIds) throws ZoneIdNotFoundException;

    @WebMethod
    void diplayMessageOnZonesScreens(String message, String...zoneNames) throws ZoneNameNotFoundException;

    @WebMethod
    void addZone(@WebParam(name = "zone_name") String name, @WebParam(name = "station_name") String station) throws AlreadyExistingZoneException;

    @WebMethod
    void addSkilift(@WebParam(name = "skilift_name") String name, @WebParam(name = "zone_name") String zoneName) throws AlreadyExistingSkiliftException, UnknownZoneException;

    @WebMethod
    void addGate(@WebParam(name = "physical_id") String physicalId, @WebParam(name = "skilift_name") String skiliftName) throws UnknownSkiliftException, AlreadyExistingGateException;
}
