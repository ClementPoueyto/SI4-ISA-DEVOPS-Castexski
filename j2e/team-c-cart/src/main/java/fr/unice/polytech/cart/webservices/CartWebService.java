package fr.unice.polytech.cart.webservices;

import fr.unice.polytech.cart.exceptions.EmptyCartException;
import fr.unice.polytech.cart.exceptions.UnknownCustomerException;

import fr.unice.polytech.cart.interceptors.AnalyticsPassCounter;
import fr.unice.polytech.cart.interceptors.ItemVerifier;
import fr.unice.polytech.cashier.exceptions.InvalidCardIdException;
import fr.unice.polytech.cashier.exceptions.PaymentException;
import fr.unice.polytech.entities.CardItem;
import fr.unice.polytech.entities.OrderItem;
import fr.unice.polytech.entities.PassItem;
import fr.unice.polytech.exceptions.UnknownCardException;

import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Set;

@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/cart")
@XmlSeeAlso({PassItem.class, CardItem.class})
public interface CartWebService {

    @WebMethod
    void addItemToCustomerCart(@WebParam(name = "customer_id") String customerId,
                                  @WebParam(name = "item") OrderItem it) throws UnknownCustomerException;

    @WebMethod
    void removeItemToCustomerCart(@WebParam(name = "customer_id") String customerId,
                                  @WebParam(name = "item") OrderItem it) throws UnknownCustomerException;


    @WebMethod
    @WebResult(name = "cart_contents")
    Set<OrderItem> getCustomerCartContents(@WebParam(name = "customer_id") String customerEmail) throws UnknownCustomerException;



    @WebMethod
    Set<String> validate(@WebParam(name = "customer_name") String customerEmail)
           throws PaymentException, UnknownCustomerException, EmptyCartException, InvalidCardIdException, UnknownCardException;

}
