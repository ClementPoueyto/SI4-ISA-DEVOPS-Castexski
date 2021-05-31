package fr.unice.polytech.stepdefs;

import api.CastexSkiAPI;

import io.cucumber.java8.En;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import cli.utils.TimesOfDay;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import stubs.analytics.AnalyticsPass;
import stubs.analytics.AnalyticsVisit;
import stubs.analytics.AnalyticsWebService;
import stubs.cart.*;
import stubs.customer.AlreadyExistingCustomerException;
import stubs.customer.CustomerWebService;

import org.junit.runner.RunWith;
import stubs.employee.EmployeeWebService;
import stubs.gate.GateWebService;
import stubs.gate.PassageResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(value = Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/features")
public class CustomerStepDef implements En{
    CastexSkiAPI api = new CastexSkiAPI("134.59.213.133", "8080"); //134.59.213.133
    CustomerWebService customerWS = api.cws;
    CartWebService cartWS = api.carts;
    GateWebService gateWS = api.gateWebService;
    EmployeeWebService employeeWebService = api.employeeWebService;
    AnalyticsWebService analyticsWebService = api.analyticsWebService;

    String customerName;
    String customerMail;
    String customerCardNb;

    int counterPassInit = 0;
    int counterVisitInit = 0;

    List<String> cardIds;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CustomerStepDef() {
        Given("a zone {string} with skilift {string} and a gate", (String z, String s) -> {
            try {
                employeeWebService.addZone(z, "castxSki");
                employeeWebService.addSkilift(s, z);
                employeeWebService.addGate("51", s);

            }catch (Exception e){

            }
        });

        Then("an employee fetches pass analytics of the day and get {string} more", (String expected) -> {

            LocalDateTime day = LocalDateTime.now();

            List<AnalyticsPass> list= analyticsWebService.getPassAnalyticsByDay(day.getDayOfMonth(),day.getMonthValue(), day.getYear());
            int  counter = 0;
            for(AnalyticsPass pass : list){
                 counter +=pass.getCounter();
            }
            assertEquals(Integer.parseInt(expected), counter-counterPassInit);
            counterPassInit=counter;
        });

        And("an employee fetches visit analytics of the day and get {string} more", (String expected) -> {

            LocalDateTime day = LocalDateTime.now();

            List<AnalyticsVisit> list= analyticsWebService.getVisitAnalyticsByDay(day.getDayOfMonth(),day.getMonthValue(), day.getYear());
            int  counter = 0;
            for(AnalyticsVisit visit : list){
                counter +=visit.getCounter();
            }
            assertEquals(Integer.parseInt(expected), counter-counterVisitInit);
            counterVisitInit=counter;
        });

        And("he uses ski lift at gate {string} with his card", (String idGate)->{
            PassageResponse response = gateWS.checkCard(cardIds.get(0), idGate);
            assertEquals(response == PassageResponse.PASS_OK || response == PassageResponse.ZONE_CLOSED, true);
        });


         And("he adds {string} card", (String quantity) ->{
            CardItem cardItem = new CardItem();
            cardItem.setQuantity(Integer.parseInt(quantity));
            cardItem.setType(ItemType.CARD);
            cartWS.addItemToCustomerCart(customerMail, cardItem);
         });

        And("he checkouts", () -> {
            List<String> cardIdReceived = cartWS.validate(customerMail);
            assertNotNull(cardIdReceived);
            if(cardIdReceived.size() != 0) {
                cardIds = cardIdReceived;
            }
        });

        Then("he checkouts and get his card", () -> {
            List<String> cardIdReceived = cartWS.validate(customerMail);
            assertNotNull(cardIdReceived);
            assertTrue(cardIdReceived.size() > 0);
            cardIds = cardIdReceived;
        });


        When("a customer registers with {string} {string} {string}", (String mail, String name, String cardNb) ->{
            this.customerName = name;
            this.customerMail = mail;
            this.customerCardNb = cardNb;
            try {
                customerWS.registerCustomer(customerMail, customerName, customerCardNb);
            }
            catch (Exception e){

            }

        });


        And("he adds a pass for today to this card {string} {string} {string} {string} {string} {string}", (String quantity, String passTypeString, String wholeDay, String nbDay, String isChildString, String z) ->{
            PassType passType = PassType.valueOf(passTypeString.toUpperCase());
            TimesOfDay timesOfDay = TimesOfDay.valueOf(wholeDay.toUpperCase());
            int nbDays = Integer.parseInt(nbDay);
            LocalDateTime startDate = LocalDate.now().atStartOfDay();
            PersonType isChild = PersonType.valueOf(isChildString.toUpperCase());
            PassItem passItem = new PassItem();
            passItem.setQuantity(Integer.parseInt(quantity));
            passItem.setPassType(passType);
            passItem.setCardLinkId(cardIds.get(0));
            LocalDateTime endDate = startDate;
            startDate = startDate.withHour(0);

            endDate = endDate.withHour(23);
            endDate = endDate.withMinute(59);
            endDate = endDate.withSecond(59);

            if(nbDays > 1 && timesOfDay == TimesOfDay.FULL_DAY) {
                endDate = endDate.plusDays(nbDays - 1);
            }

            DateSerializable start = new DateSerializable();
            start.setYear(startDate.getYear());
            start.setMonth(startDate.getMonthValue());
            start.setDayOfMonth(startDate.getDayOfMonth());
            start.setHour(startDate.getHour());
            start.setMinute(startDate.getMinute());
            passItem.setStart(start);


            DateSerializable end  = new DateSerializable();
            end.setYear(endDate.getYear());
            end.setMonth(endDate.getMonthValue());
            end.setDayOfMonth(endDate.getDayOfMonth());
            end.setHour(endDate.getHour());
            end.setMinute(endDate.getMinute());
            passItem.setEnd(end);


            passItem.setPersonType(isChild);

            passItem.setZones(z);

            cartWS.addItemToCustomerCart(customerMail, passItem);
        });


        Given("a zone {string} with skilift {string}, a gate with id {string} and a customer registered with {string} {string} {string}", (String z, String s, String gateId, String email, String  name, String credit) -> {
            this.customerName = name;
            this.customerMail = email;
            this.customerCardNb = credit;
            LocalDateTime day = LocalDateTime.now();

            List<AnalyticsPass> list= analyticsWebService.getPassAnalyticsByDay(day.getDayOfMonth(),day.getMonthValue(), day.getYear());
            for(AnalyticsPass pass : list){
                counterPassInit +=pass.getCounter();
            }
            List<AnalyticsVisit> list2= analyticsWebService.getVisitAnalyticsByDay(day.getDayOfMonth(),day.getMonthValue(), day.getYear());
            for(AnalyticsVisit visit : list2){
                counterVisitInit +=visit.getCounter();
            }
            try {
                employeeWebService.addZone(z, "castexSki");
                employeeWebService.addSkilift(s, z);
                employeeWebService.addGate(gateId, s);
            } catch (Exception e){
                System.out.println(e);
            }
            try{
                customerWS.registerCustomer(customerMail, customerName, customerCardNb);

            }
             catch (Exception e){}
        });
    }


}
