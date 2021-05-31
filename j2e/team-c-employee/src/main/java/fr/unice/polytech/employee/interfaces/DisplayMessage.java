package fr.unice.polytech.employee.interfaces;

import javax.ejb.Local;

import fr.unice.polytech.entities.Zone;

import java.util.ArrayList;

@Local
public interface DisplayMessage {

   void showMessage(String message, String...ids);

   void showMessageOnZonesScreens(String message, String...ids);

}
