package fr.unice.polytech.entities;

import fr.unice.polytech.utils.PassType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class AnalyticsPass {

    @Id
    @NotNull
    private int id;

    @NotNull
    private int year;

    @NotNull
    private int month;

    @NotNull
    private int dayOfMonth;

    @NotNull
    private PassType passType;

    @OneToMany(cascade = {CascadeType.PERSIST})
    private Set<Zone> zones;

    int counter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnalyticsPass)) return false;
        AnalyticsPass analytics = (AnalyticsPass) o;
        return year == analytics.year && month == analytics.month && dayOfMonth == analytics.dayOfMonth && passType == analytics.passType && zones == analytics.zones;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, dayOfMonth, passType, zones);
    }

    @Override
    public String toString() {
        return "AnalyticsPass{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", dayOfMonth=" + dayOfMonth +
                ", passType=" + passType +
                ", zones=" + zones +
                ", counter=" + counter +
                '}';
    }

    public AnalyticsPass(){
        this.setDate();
        PassItem it = new PassItem();
        this.passType = it.getPassType();
        this.zones = new HashSet<>();
        this.id = hashCode();
        this.counter=1;
    }

    public AnalyticsPass(LocalDateTime date){
        this.dayOfMonth = date.getDayOfMonth();
        this.month = date.getMonthValue();
        this.year = date.getYear();

        PassItem it = new PassItem();
        this.passType = it.getPassType();
        this.zones = new HashSet<>();
        this.id = hashCode();
        this.counter=1;
    }
    public AnalyticsPass(PassItem item){
        this.setDate();
        this.zones = new HashSet<>();
        this.passType = item.getPassType();
        this.id = hashCode();
        this.counter = item.getQuantity();
    }

    public AnalyticsPass(PassItem item, LocalDateTime date){
        this.dayOfMonth = date.getDayOfMonth();
        this.month = date.getMonthValue();
        this.year = date.getYear();
        this.zones = new HashSet<>();
        this.passType = item.getPassType();
        this.id = hashCode();
        this.counter = item.getQuantity();
    }


    public void setDate() {
        LocalDateTime d = LocalDateTime.now();
        this.dayOfMonth = d.getDayOfMonth();
        this.month = d.getMonthValue();
        this.year = d.getYear();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public PassType getPassType() {
        return passType;
    }

    public void setPassType(PassType passType) {
        this.passType = passType;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
