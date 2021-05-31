package fr.unice.polytech.customer.interfaces;

import fr.unice.polytech.entities.Customer;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;
@Local
public interface CustomerFinder {

    Optional<Customer> findById(String id);

    Optional<Customer> findByEmail(String email);


}