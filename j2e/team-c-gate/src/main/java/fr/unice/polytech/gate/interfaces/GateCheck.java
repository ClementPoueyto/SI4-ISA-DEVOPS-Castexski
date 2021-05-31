package fr.unice.polytech.gate.interfaces;

import fr.unice.polytech.cart.exceptions.EmptyCartException;
import fr.unice.polytech.cart.interfaces.CartModifier;
import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.entities.Card;
import fr.unice.polytech.entities.Gate;
import fr.unice.polytech.exceptions.UncheckedException;
import fr.unice.polytech.exceptions.UnknownCardException;
import fr.unice.polytech.gate.exceptions.UnknownGateException;
import fr.unice.polytech.gate.exceptions.UnvalidatedPassException;
import fr.unice.polytech.utils.PassageResponse;
import fr.unice.polytech.utils.WeatherAPI;

import javax.ejb.Local;

@Local
public interface GateCheck {

    PassageResponse isPassageValid(Card card, Gate gate) throws UnvalidatedPassException, PaymentException, UnknownCardException, InvalidCardIdException, EmptyCartException, UnknownGateException;

    void useWeatherReference(WeatherAPI weatherAPI);

    void setCartModifier(CartModifier cM);

}
