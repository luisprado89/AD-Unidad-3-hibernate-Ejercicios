package com.AD.U3.entities;


import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLibro")
    private int idLibro;
    private String codigo;
    private String titulo;
    private String autores;

    @Column(name = "año")
    private int anho;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    private List<Alquiler> alquiler;

    public Libro() {
        super();
    }

    public Libro(String codigo, String titulo, String autores, int anho) {
        super();
        this.codigo = codigo;
        this.titulo = titulo;
        this.autores = autores;
        this.anho = anho;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public List<Alquiler> getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(List<Alquiler> alquiler) {
        this.alquiler = alquiler;
    }

    public void addAlquiler(Alquiler alquiler) {
        this.alquiler.add(alquiler);
    }

}