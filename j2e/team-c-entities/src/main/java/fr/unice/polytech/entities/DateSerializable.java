package fr.unice.polytech.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class DateSerializable implements Serializable {
    private int year;
    private int month;
    private int dayOfMonth;
    private int hour;
    private int minute;

    public DateSerializable(int year, int month, int dayOfMonth, int hour, int minute) {
        this.setDate(year, month, dayOfMonth, hour, minute);
    }

    public DateSerializable() {
        this.setDate(LocalDateTime.now());
    }

    public void setDate(LocalDateTime d) {
        this.setDate(d.getYear(), d.getMonthValue(), d.getDayOfMonth(), d.getHour(), d.getMinute());
    }

    public void setDate(int year, int month, int dayOfMonth, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hour = hour;
        this.minute = minute;
    }

    public LocalDateTime getDate() {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }


    public void addDays(int n) {
        LocalDateTime d = this.getDate().plusDays(n);
        this.setDate(d);
    }

    @Override
    public String toString() {
        return getDate().toString();
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

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null){
            if (obj instanceof DateSerializable){
                DateSerializable date = (DateSerializable) obj;
                return this.getDate().equals(date.getDate());
            } else if(obj instanceof LocalDateTime){
                LocalDateTime date = ((LocalDateTime) obj);
                return date.equals(this.getDate());
            }
        }
        return false;
    }
}
