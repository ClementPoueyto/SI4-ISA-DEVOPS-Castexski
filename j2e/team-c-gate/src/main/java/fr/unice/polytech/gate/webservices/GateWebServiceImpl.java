package fr.unice.polytech.gate.webservices;

import fr.unice.polytech.card.interfaces.CardFinder;
import fr.unice.polytech.cart.exceptions.EmptyCartException;
import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.employee.interfaces.GateFinder;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Gate;
import fr.unice.polytech.exceptions.UncheckedException;
import fr.unice.polytech.exceptions.UnknownCardException;
import fr.unice.polytech.gate.exceptions.UnknownGateException;
import fr.unice.polytech.gate.exceptions.UnvalidatedPassException;
import fr.unice.polytech.gate.interfaces.GateCheck;
import fr.unice.polytech.utils.PassageResponse;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Optional;

@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/gate")
@Stateless(name = "GateWS")
public class GateWebServiceImpl implements GateWebService {

    @EJB(name="gateCheck") private GateCheck gateCheck;

    @EJB private GateFinder gateFinder;

    @EJB private CardFinder cardFinder;


    @Override
    public PassageResponse checkCard(String cardId, String gatePhysicalId) throws UnvalidatedPassException, UnknownCardException, PaymentException, UncheckedException, InvalidCardIdException, EmptyCartException, UnknownGateException {
        return gateCheck.isPassageValid(getCardById(cardId), getGateByPhysicalId(gatePhysicalId));
    }

    public Card getCardById(String id) {
        Optional<Card> card = cardFinder.getCardById(id);
        return card.orElse(null);
    }

    public Gate getGateByPhysicalId(String physicalId) {
        Optional<Gate> gate = gateFinder.getGateByPhysicalId(physicalId);
        return gate.orElse(null);
    }
}
