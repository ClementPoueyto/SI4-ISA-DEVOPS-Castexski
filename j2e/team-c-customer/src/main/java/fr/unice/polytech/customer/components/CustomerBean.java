package fr.unice.polytech.customer.components;

import fr.unice.polytech.card.interfaces.CardFinder;
import fr.unice.polytech.customer.exceptions.AlreadyAssociatedCard;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Customer;
import fr.unice.polytech.customer.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.customer.interfaces.CustomerFinder;
import fr.unice.polytech.customer.interfaces.CustomerRegistry;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class CustomerBean implements CustomerFinder, CustomerRegistry {


    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    public List<Customer> findAll() {
        return manager
                .createQuery("Select c from Customer c", Customer.class)
                .getResultList();
    }

    @Override
    public Optional<Customer> findById(String id) {
        Optional<Customer> customer = Optional.ofNullable(manager.find(Customer.class,id));
        if(customer.isPresent()) {
            log.log(Level.INFO, "Customer "+customer.get().getName());
        }
        else{
            log.log(Level.INFO, "No customer found with id "+id);

        }
        return customer;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
        Root<Customer> root =  criteria.from(Customer.class);
        criteria.select(root).where(builder.equal(root.get("email"), email));
        TypedQuery<Customer> query = manager.createQuery(criteria);
        Optional<Customer> customer;
        try {
            customer= Optional.of(query.getSingleResult());
        } catch (NoResultException nre){
            customer = Optional.empty();
        }
        if(customer.isPresent()) {
            log.log(Level.INFO, "Customer "+customer.get().getName());
        }
        else{
            log.log(Level.INFO, "No customer found with email :"+email);

        }
        return customer;
    }



    @Override
    public void registerCustomer(String customerEmail, String name, String creditCard) throws AlreadyExistingCustomerException {
        if (findByEmail(customerEmail).isPresent()){
            throw new AlreadyExistingCustomerException(customerEmail);
        }

        Customer customer =  new Customer(customerEmail, name);
        customer.setCreditCard(creditCard);

        manager.persist(customer);
        log.log(Level.INFO, "Client enregistré : " + customer.toString());

    }

    public void registerCustomer(Customer customer) throws AlreadyExistingCustomerException {

        if(customer.getEmail()!=null&&customer.getName()!=null){
            if (findByEmail(customer.getEmail()).isPresent()){
                throw new AlreadyExistingCustomerException(customer.getEmail());
            }
            manager.persist(customer);
        }
    }

    @Override
    public void associateCardToCustomer(Customer customer, Card card) throws AlreadyAssociatedCard {
        if(card.getOwner() != null) {

            throw new AlreadyAssociatedCard();
        }
        card.setOwner(customer);
        customer.setCard(card);
        manager.merge(customer);
        //
        //cardFinder.putCard(card);
        log.log(Level.INFO, "Carte : " + card.toString() + "\nassociée au compte :" + customer.toString());

    }


}
