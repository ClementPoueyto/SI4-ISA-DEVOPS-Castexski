package fr.unice.polytech.entities;

import fr.unice.polytech.utils.CoeffTarifs;
import fr.unice.polytech.utils.PassType;
import fr.unice.polytech.utils.PersonType;


import javax.persistence.*;
import java.time.Duration;
import java.util.Objects;

@Embeddable
public class PassItem extends OrderItem{

    // Card Id provided by the client to link the pass to
    private String cardLinkId;

    private PassType passType;

    private String zones;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "hour", column = @Column(name = "hour_start")),
            @AttributeOverride( name = "minute", column = @Column(name = "minute_start")),
            @AttributeOverride( name = "year", column = @Column(name = "year_start")),
            @AttributeOverride( name = "month", column = @Column(name = "month_start")),
            @AttributeOverride( name = "dayOfMonth", column = @Column(name = "dayOfMonth_start")),
    })
    private DateSerializable start;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "hour", column = @Column(name = "hour_end")),
            @AttributeOverride( name = "minute", column = @Column(name = "minute_end")),
            @AttributeOverride( name = "year", column = @Column(name = "year_end")),
            @AttributeOverride( name = "month", column = @Column(name = "month_end")),
            @AttributeOverride( name = "dayOfMonth", column = @Column(name = "dayOfMonth_end")),
    })
    private DateSerializable end;

    private PersonType personType;

    @Override
    public double getPrice(){
        return this.zones.split(",").length * PassType.CLASSIC.getPrice() * personType.getCoeff() * getNbDaysCoeff();
    }

    private double getNbDaysCoeff() {
        Duration duration = Duration.between(start.getDate(), end.getDate());
        long days = duration.toDays() + 1;
        if(days == 1) {
            if(duration.toHours() < 9) {
                return CoeffTarifs.HALF_DAY.getCoeff(); // Coeff Demie journée
            }
            return CoeffTarifs.DAY.getCoeff();
        } else {
            if (days >= 7) {
                return days * CoeffTarifs.ONE_WEEK_AND_MORE.getCoeff();
            } if (days >= 3) {
                return days * CoeffTarifs.THREE_DAYS_AND_MORE.getCoeff();
            }
            return days * CoeffTarifs.DAY.getCoeff();
        }
    }

    public PassItem(){
        super();
        this.start = new DateSerializable();
        DateSerializable end = new DateSerializable();
        end.addDays(1);
        this.end = end;
        this.passType = PassType.CLASSIC;
        this.personType = PersonType.ADULT;
        this.zones = "debutant";
    }

    public PassItem(int quantity, String cardLinkId, PassType passType, DateSerializable start, DateSerializable end, PersonType personType, String zones) {
        super(quantity);
        this.cardLinkId = cardLinkId;
        this.passType = passType;
        this.start = start;
        this.end = end;
        this.personType = personType;
        this.zones = zones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PassItem)) return false;
        if (!super.equals(o)) return false;
        PassItem passItem = (PassItem) o;
        return passType == passItem.passType && personType == passItem.personType &&
                start.equals(passItem.start) && end.equals(passItem.end) && zones.equals(passItem.zones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), passType, zones);
    }

    public DateSerializable getStart() {
        return start;
    }

    public void setStart(DateSerializable start) {
        this.start = start;
    }

    public String getCardLinkId() {
        return cardLinkId;
    }

    public void setCardLinkId(String cardLinkId) {
        this.cardLinkId = cardLinkId;
    }

    public PassType getPassType() {
        return passType;
    }

    public void setPassType(PassType passType) {
        this.passType = passType;
    }

    public DateSerializable getEnd() {
        return end;
    }

    public void setEnd(DateSerializable end) {
        this.end = end;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public String getZones() {
        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }

    @Override
    public String toString() {
        return "PassItem{" +
                "type=" + type +
                ", quantity=" + quantity +
                ", available=" + available +
                ", price=" + price +
                ", cardLinkId='" + cardLinkId + '\'' +
                ", passType=" + passType +
                ", zones='" + zones + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", personType=" + personType +
                '}';
    }
}
