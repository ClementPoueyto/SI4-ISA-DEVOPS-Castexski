package fr.unice.polytech.card.interfaces;


import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Pass;


import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Local
public interface CardFinder {

  void putCard(Card card);

  void putPass(Pass pass);

  void linkPassToCard(Card card, Pass pass);

  Optional<Card> getCardById(String id);
  Optional<Pass> getPassById(String id);

  List<Card> getAllCards();

  List<Pass> getAllPasses();

}
