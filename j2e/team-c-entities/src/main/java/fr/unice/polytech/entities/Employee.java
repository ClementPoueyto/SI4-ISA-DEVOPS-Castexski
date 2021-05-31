package fr.unice.polytech.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotNull
    private String surname;

    @NotNull
    private String name;


    public Employee() {
        this.id = UUID.randomUUID().toString();
    }

    public Employee(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.id = UUID.randomUUID().toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}