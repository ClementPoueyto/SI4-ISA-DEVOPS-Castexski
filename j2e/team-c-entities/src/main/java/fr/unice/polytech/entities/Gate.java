package fr.unice.polytech.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Gate implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String physicalId;

    @ManyToOne
    private SkiLift skiLift;

    public Gate() {
    }

    public Gate(String physicalId, SkiLift skiLift) {
        this.physicalId = physicalId;
        this.skiLift = skiLift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gate)) return false;
        Gate gate = (Gate) o;
        return physicalId.equals(gate.physicalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(physicalId);
    }

    public SkiLift getSkiLift() {
        return skiLift;
    }

    public void setSkiLift(SkiLift skiLift) {
        this.skiLift = skiLift;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhysicalId() {
        return physicalId;
    }

    public void setPhysicalId(String idGate) {
        this.physicalId = idGate;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "id='" + id + '\'' +
                ", physicalId='" + physicalId + '\'' +
                ", skiLift=" + skiLift.getName() +
                '}';
    }
}
