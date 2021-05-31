package fr.unice.polytech.entities;

import fr.unice.polytech.utils.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
public class Zone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String name;

    private String station;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "zone")
    Set<Screen> screens;


    public Set<Screen> getScreens() {
        return screens;
    }

    public void setScreens(Set<Screen> screens) {
        this.screens = screens;
    }

    public Zone(){

    }

    public Zone(String name, String station, Status status) {
        this.name = name;
        this.station = station;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(! (obj instanceof Zone)) return false;
        if(this == obj) return true;

        Zone zone = (Zone) obj;

        if(!this.name.equals(zone.name)) return false;
        return station.equals(zone.station);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (name == null ? 0 :name.hashCode());
        result = 31 * result + (name == null ? 0 :station.hashCode());
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", station='" + station + '\'' +
                ", status=" + status +
                ", screens=" + screens +
                '}';
    }
}