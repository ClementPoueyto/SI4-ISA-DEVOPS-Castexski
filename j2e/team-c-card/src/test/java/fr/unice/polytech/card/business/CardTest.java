package fr.unice.polytech.card.business;

import arquillian.AbstractArquillianTest;

import fr.unice.polytech.card.interfaces.CardFinder;
import fr.unice.polytech.entities.*;
import fr.unice.polytech.utils.CardType;
import fr.unice.polytech.utils.PassType;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class CardTest extends AbstractArquillianTest {


    @EJB
    private CardFinder cardFinder;

    @Inject
    private UserTransaction utx;

    @PersistenceContext(unitName = "castexski_customer_persistence_unit")
    private EntityManager manager;

    Card card;

    @Before
    public void setUpContext() {
        card = new Card();

    }

    @After
    public void cleaningUp() throws Exception {
        utx.begin();
        card = manager.merge(card);
        manager.remove(card);
        card = null;
        utx.commit();
    }

    @Test
    public void putCardTest(){
        // card doesn't exist before in the database
        card.setCardType(CardType.CARTEX);
        cardFinder.putCard(card);
        String idCard = card.getId();
        Card stored = manager.find(Card.class, idCard);
        assertEquals(card, stored);
        assertEquals(card.getCardType(), stored.getCardType());

        // change card type and put it again => card is updated in the database
        card.setCardType(CardType.SUPERCARTEX);
        cardFinder.putCard(card);
        stored = manager.find(Card.class, idCard);
        assertEquals(card, stored);
        assertEquals(card.getCardType(), stored.getCardType());
    }



    @Test
    public void linkCardToNewPassTest() {
        card.setCardType(CardType.CARTEX);
        Pass pass = new Pass();
        pass.setPassType(PassType.WEEK);
        // store card in database
        cardFinder.putCard(card);

        // here, we test linkPassToCard when Card is already existing in the database
        cardFinder.linkPassToCard(card, pass);

        assertEquals(manager.find(Card.class, card.getId()), card);
        assertEquals(manager.find(Pass.class, pass.getId()), pass);

        assertEquals(cardFinder.getCardById(card.getId()).get().getPass(),cardFinder.getPassById(pass.getId()).get());

    }

    @Test
    public void linkNewCardToNewPassTest() {
        card.setCardType(CardType.CARTEX);
        Pass pass = new Pass();
        pass.setPassType(PassType.WEEK);

        // neither card and pass already exist in database
        cardFinder.linkPassToCard(card, pass);

        assertEquals(manager.find(Card.class, card.getId()), card);
        assertEquals(manager.find(Pass.class, pass.getId()), pass);
        assertEquals(cardFinder.getCardById(card.getId()).get().getPass(),cardFinder.getPassById(pass.getId()).get());
    }


    @Test
    public void linkNewCardToPassTest(){
        card.setCardType(CardType.CARTEX);
        Pass pass = new Pass();
        pass.setPassType(PassType.WEEK);
        // add pass to database
        cardFinder.putPass(pass);

        // link card (doesn't exist in database) to a pass already existing in database
        cardFinder.linkPassToCard(card, pass);
        assertEquals(manager.find(Card.class, card.getId()), card);
        assertEquals(manager.find(Pass.class, pass.getId()), pass);
        assertEquals(cardFinder.getCardById(card.getId()).get().getPass(),cardFinder.getPassById(pass.getId()).get());
    }

    @Test
    public void getNonExistingCardByIdTest(){
        assertEquals(cardFinder.getCardById("0000"), Optional.empty());
    }

    @Test
    public void getAllCardsTest(){
        card.setCardType(CardType.CARTEX);
        Card card2 = new Card();
        card2.setCardType(CardType.CARTEX);
        cardFinder.putCard(card);
        cardFinder.putCard(card2);
        List<Card> cards = cardFinder.getAllCards();
        assertTrue(cards.contains(card));
        assertTrue(cards.contains(card2));

    }


}
