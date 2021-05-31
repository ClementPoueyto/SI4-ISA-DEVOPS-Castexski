package fr.unice.polytech.gate.webservices;

import fr.unice.polytech.gate.exceptions.*;
import fr.unice.polytech.cart.exceptions.EmptyCartException;
import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.exceptions.UncheckedException;
import fr.unice.polytech.exceptions.UnknownCardException;
import fr.unice.polytech.gate.exceptions.UnvalidatedPassException;
import fr.unice.polytech.utils.PassageResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/gate")
public interface GateWebService {

    @WebMethod
    PassageResponse checkCard(@WebParam(name = "card") String cardId, @WebParam(name = "gate") String gateId) throws UnvalidatedPassException, UnknownCardException, PaymentException, InvalidCardIdException, EmptyCartException, UnknownGateException;

}
