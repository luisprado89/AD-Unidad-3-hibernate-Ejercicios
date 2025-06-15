package com.AD.U3.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @Column(name = "idPelicula")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idPelicula;

    private String titulo;
    private int anhoPublicacion;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idGenero")
    Genero genero;

    @ManyToMany(mappedBy = "listaPeliculas")
    List<Persona> listaPersonas;

    public Pelicula() {
        super();
    }

    public Pelicula(String titulo, int anhoPublicacion, Genero genero) {
        super();
        this.titulo = titulo;
        this.anhoPublicacion = anhoPublicacion;
        this.genero = genero;
        this.listaPersonas = new ArrayList<Persona>();
        genero.addPelicula(this);
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnhoPublicacion() {
        return anhoPublicacion;
    }

    public void setAnhoPublicacion(int anhoPublicacion) {
        this.anhoPublicacion = anhoPublicacion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    @Override
    public String toString() {
        return "Pelicula [idPelicula=" + idPelicula + ", titulo=" + titulo + ", anhoPublicacion=" + anhoPublicacion
                + "]";
    }
}
