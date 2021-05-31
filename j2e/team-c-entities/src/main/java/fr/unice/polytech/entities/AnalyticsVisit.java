package fr.unice.polytech.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class AnalyticsVisit {

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
   private String skiLiftName;

    int counter;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnalyticsVisit)) return false;
        AnalyticsVisit that = (AnalyticsVisit) o;
        return year == that.year && month == that.month && dayOfMonth == that.dayOfMonth && skiLiftName.equals(that.skiLiftName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, dayOfMonth, skiLiftName);
    }

    public AnalyticsVisit(){
        this.setDate();
        this.id = hashCode();
        this.counter=1;
    }

    public AnalyticsVisit(LocalDateTime date){
        this.dayOfMonth = date.getDayOfMonth();
        this.month = date.getMonthValue();
        this.year = date.getYear();

        this.id = hashCode();
        this.counter=1;
    }
    public AnalyticsVisit(String name){
        this.setDate();
        this.skiLiftName = name;
        this.id = hashCode();
        this.counter=1;
    }

    public AnalyticsVisit(String name, LocalDateTime date){
        this.dayOfMonth = date.getDayOfMonth();
        this.month = date.getMonthValue();
        this.year = date.getYear();
        this.skiLiftName = name;
        this.id = hashCode();
        this.counter = 1;
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getSkiLiftName() {
        return skiLiftName;
    }

    public void setSkiLiftName(String skiLiftName) {
        this.skiLiftName = skiLiftName;
    }
}
