package com.AD.U3.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class Actor extends Persona{

    private int numeroOscars;

    public Actor(String nombre, String dNI, int anhoNacimiento) {
        super(nombre, dNI, anhoNacimiento);
    }

    public Actor(String nombre, String dNI, int numeroOscars, int anhoNacimiento) {
        super(nombre, dNI, anhoNacimiento);
        this.numeroOscars = numeroOscars;
    }

    public int getNumeroOscars() {
        return numeroOscars;
    }

    public void setNumeroOscars(int numeroOscars) {
        this.numeroOscars = numeroOscars;
    }

}
