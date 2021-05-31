package fr.unice.polytech.entities;

import fr.unice.polytech.utils.PassType;
import fr.unice.polytech.utils.PersonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

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

    @Enumerated(EnumType.STRING)
    @NotNull
    private PassType passType = PassType.CLASSIC;

    @ElementCollection
    private Set<String> zones = new HashSet<>();


    private PersonType personType = PersonType.ADULT;
    //private String cardLinkId;


    public Pass() {
        this.init7DaysPass();
    }

    public Pass(PassType passType) {
        this.init7DaysPass();
        this.setPassType(passType);
    }

    public Pass(PassItem passItem) {
        this.passType = passItem.getPassType();
        this.personType = passItem.getPersonType();
        this.start = passItem.getStart();
        this.end = passItem.getEnd();
        this.zones = new HashSet<>(Arrays.asList(passItem.getZones().split(",")));
    }

    public Pass(DateSerializable start, DateSerializable end, Set<String> zones) {
        this.start = start;
        this.end = end;
        this.zones = zones;
    }

    private void init7DaysPass() {
        this.start = new DateSerializable();
        DateSerializable end = new DateSerializable();
        end.addDays(7);
        this.end = end;
    }

    private void initDaysPass(DateSerializable start, int duree) {
        this.start = start;
        start.addDays(duree);
        this.end = start;
    }


    public PassType getPassType() {
        return passType;
    }

    public void setPassType(PassType passType) {
        this.passType = passType;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public DateSerializable getStart() {
        return start;
    }

    public void setStart(DateSerializable start) {
        this.start = start;
    }

    public void setStart(int year, int month, int dayOfMonth, int hour, int minute) {
        this.start = new DateSerializable(year, month, dayOfMonth, hour, minute);
    }

    public DateSerializable getEnd() {
        return end;
    }

    public void setEnd(DateSerializable end) {
        this.end = end;
    }


    public Set<String> getZones() {
        return zones;
    }

    public void setZones(Set<String> zones) {
        this.zones = zones;
    }

    public void addZone(String z) {
        this.zones.add(z);
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pass)) return false;
        Pass item = (Pass) o;
        return this.passType == item.passType;



    }

    @Override
    public int hashCode() {
        return this.passType.hashCode();
    }

    @Override
    public String toString() {
        return "Pass{" +
                "id='" + id + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", passType=" + passType +
                ", zones=" + zones +
                ", personType=" + personType +
                '}';
    }

    public boolean isValid(){
        return LocalDateTime.now().isAfter(this.start.getDate()) && LocalDateTime.now().isBefore(this.end.getDate());
    }
}

