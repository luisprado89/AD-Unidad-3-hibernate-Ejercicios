package com.AD.U3.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Actriz extends Persona{

    private int numeroMejorActriz;

    public Actriz(String nombre, String dNI, int anhoNacimiento) {
        super(nombre, dNI, anhoNacimiento);
    }

    public Actriz(String nombre, String dNI, int numeroMejorActriz, int anhoNacimiento) {
        super(nombre, dNI, anhoNacimiento);
        this.numeroMejorActriz = numeroMejorActriz;
    }

    public int getNumeroMejorActriz() {
        return numeroMejorActriz;
    }

    public void setNumeroMejorActriz(int numeroMejorActriz) {
        this.numeroMejorActriz = numeroMejorActriz;
    }

}
