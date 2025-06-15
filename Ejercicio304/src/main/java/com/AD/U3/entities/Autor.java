package com.AD.U3.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @NonNull
    @Column(name = "DniAutor")
    private String dniAutor;

    @NonNull
    @Column(name = "Nombre")
    private String nombre;

    @NonNull
    @Column(name = "Nacionalidad")
    private String nacionalidad;

    @ManyToMany
    @JoinTable(name = "Libros_Autores",
            joinColumns = @JoinColumn(name = "DniAutor"),
            inverseJoinColumns = @JoinColumn(name = "idLibro"))
    private List<Libro> listaLibros = new ArrayList<>();

    @OneToOne(mappedBy = "autor", cascade = CascadeType.ALL)
    private Telefono telefono;

    public void addLibro(Libro libro) {
        if (this.listaLibros == null) {
            this.listaLibros = new ArrayList<>();
        }
        this.listaLibros.add(libro);
        // Además asegúrate de sincronizar el otro lado:
        if (libro.getListaAutores() == null) {
            libro.setListaAutores(new ArrayList<>());
        }
        libro.getListaAutores().add(this);
    }
}
