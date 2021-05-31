package fr.unice.polytech.analytics.webservices;
import fr.unice.polytech.entities.*;

import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;
import java.util.Set;

@WebService(targetNamespace = "http://www.polytech.unice.fr/castexski/analytics")
public interface AnalyticsWebService {

    @WebMethod
    List<AnalyticsVisit> getVisitAnalyticsByDay(@WebParam(name = "day") int day,
                                                @WebParam(name = "month") int month,
                                                @WebParam(name = "year") int year) ;

    @WebMethod
    List<AnalyticsPass> getPassAnalyticsByDay(@WebParam(name = "day") int day,
                                        @WebParam(name = "month") int month,
                                        @WebParam(name = "year") int year) ;

    @WebMethod
    String getDailyEmail(@WebParam(name = "day") int day,
                                              @WebParam(name = "month") int month,
                                              @WebParam(name = "year") int year) ;
}
