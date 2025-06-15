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
@Entity
@Table(name = "emps")
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


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dpto_id")
    private Dpto dptoElement;



    public Emp() {
        super();
    }

    public Emp(String nombre, String puesto, double sueldo, int edad, String dNI, boolean esJefe) {
        super();
        this.nombre = nombre;
        this.puesto = puesto;
        this.sueldo = sueldo;
        this.edad = edad;
        DNI = dNI;
        this.esJefe = esJefe;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPuesto() {
        return puesto;
    }
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    public double getSueldo() {
        return sueldo;
    }
    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getDNI() {
        return DNI;
    }
    public void setDNI(String dNI) {
        DNI = dNI;
    }
    public boolean isEsJefe() {
        return esJefe;
    }
    public void setEsJefe(boolean esJefe) {
        this.esJefe = esJefe;
    }

    public Dpto getDptoElement() {
        return dptoElement;
    }

    public void setDptoElement(Dpto dptoElement) {
        this.dptoElement = dptoElement;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "{Nombre: "+ this.nombre +", Puesto: " + this.puesto+
                ", Edad: " + this.edad + ", Salario: " + this.sueldo +
                ", DNI: " + this.DNI + ", esJefe: " + (this.esJefe ? "Si": "No" ) + "}";
    }
}
