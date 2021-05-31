package fr.unice.polytech.entities;

import fr.unice.polytech.utils.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
public class SkiLift implements Serializable {

    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "skiLift")
    private Set<Gate> gates;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private Zone zone;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public SkiLift() {
    }

    public SkiLift(String name, Zone zone, Status status) {
        this.name = name;
        this.zone = zone;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkiLift)) return false;
        SkiLift skiLift = (SkiLift) o;
        return Objects.equals(gates, skiLift.gates) && Objects.equals(zone, skiLift.zone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gates, zone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Set<Gate> getGates() {
        return gates;
    }

    public void setGates(Set<Gate> gates) {
        this.gates = gates;
    }

    @Override
    public String toString() {
        return "SkiLift{" +
                "name='" + name + '\'' +
                ", gates=" + gates +
                ", zone=" + zone +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
