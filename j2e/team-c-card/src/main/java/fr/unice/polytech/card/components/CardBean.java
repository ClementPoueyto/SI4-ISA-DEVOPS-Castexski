package fr.unice.polytech.card.components;

import fr.unice.polytech.card.interfaces.CardFinder;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Pass;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Stateless
public class CardBean implements CardFinder {

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;

    @Override
    public void putCard(Card card) {
        if(getCardById(card.getId()).isPresent()){
            manager.merge(card);
            log.log(Level.INFO, "Card modified with success");

        }
        else{
            manager.persist(card);
            log.log(Level.INFO, "Card added with success");

        }
    }

    @Override
    public void putPass(Pass pass) {
        if(getPassById(pass.getId()).isPresent()){
            manager.merge(pass);
            log.log(Level.INFO, "Pass modified with success");

        }
        else{
            manager.persist(pass);
            log.log(Level.INFO, "Pass added with success");

        }

    }

    @Override
    public void linkPassToCard(Card card, Pass pass){
        if(card.getPass() != null){
            manager.remove(card.getPass());
        }
        if(!getCardById(card.getId()).isPresent()){
            manager.persist(card);

        }
        if(!getPassById(pass.getId()).isPresent()){
            manager.persist(pass);
        }
        card.setPass(pass);
        manager.merge(card);
        log.log(Level.INFO, "Pass added at Card with success");

    }

    @Override
    public Optional<Card> getCardById(String id) {
        return Optional.ofNullable(manager.find(Card.class,id));
    }

    @Override
    public Optional<Pass> getPassById(String id) {
        return Optional.ofNullable(manager.find(Pass.class,id));
    }

    @Override
    public List<Card> getAllCards() {
        return manager
                .createQuery("Select c from Card c", Card.class)
                .getResultList();
    }

    @Override
    public List<Pass> getAllPasses() {
        return manager
                .createQuery("Select c from Pass c", Pass.class)
                .getResultList();
    }


}
