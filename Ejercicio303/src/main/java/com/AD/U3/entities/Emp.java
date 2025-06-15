package com.AD.U3.entities;


//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emps")
@Data
@NoArgsConstructor

public class Emp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emp")
    private long id;
    private String nombre;
    private String puesto;
    private double sueldo;
    private int edad;
    private String DNI;
    private boolean esJefe;


    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "dpto_id")
    private Dpto dptoElement;

    public Emp(String nombre, String puesto, double sueldo, int edad, String DNI, boolean esJefe) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.sueldo = sueldo;
        this.edad = edad;
        this.DNI = DNI;
        this.esJefe = esJefe;
}

    public void setDptoElement(Dpto dptoElement) {
        this.dptoElement = dptoElement;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "nombre='" + nombre + '\'' +
                ", puesto='" + puesto + '\'' +
                ", sueldo=" + sueldo +
                ", edad=" + edad +
                ", DNI='" + DNI + '\'' +
                ", esJefe=" + esJefe +
                '}';
    }


}
