package com.AD.U3.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "personas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_persona", discriminatorType = DiscriminatorType.INTEGER)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idPersona")
    private int idPersona;
    private String nombre;
    private int anhoNacimiento;
    private String DNI;

    @ManyToMany
    @JoinTable(name="actores_peliculas",
            joinColumns = @JoinColumn(name="idPersona"),
            inverseJoinColumns = @JoinColumn(name="idPelicula"))
    List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();

    public Persona() {
        super();
    }

    public Persona(String nombre, String dNI, int anhoNacimiento) {
        super();
        this.nombre = nombre;
        this.DNI = dNI;
        this.anhoNacimiento = anhoNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String dNI) {
        DNI = dNI;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public List<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    public int getAnhoNacimiento() {
        return anhoNacimiento;
    }

    public void setAnhoNacimiento(int anhoNacimiento) {
        this.anhoNacimiento = anhoNacimiento;
    }

    @Override
    public String toString() {
        return "Persona [idPersona=" + idPersona + ", nombre=" + nombre + ", anhoNacimiento=" + anhoNacimiento
                + ", DNI=" + DNI + "]";
    }
}
