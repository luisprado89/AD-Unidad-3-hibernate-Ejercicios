package com.AD.U3.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genero")
public class Genero {

    @Id
    @Column(name = "idGenero")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idGenero;
    private String nombre;

    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();

    public Genero() {
        super();
    }

    public Genero(String nombre) {
        super();
        this.nombre = nombre;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addPelicula(Pelicula pelicula) {
        this.listaPeliculas.add(pelicula);
    }

    @Override
    public String toString() {
        return "Genero [idGenero=" + idGenero + ", nombre=" + nombre + ", listaPeliculas=" + listaPeliculas + "]";
    }

}
