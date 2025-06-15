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
@Table(name = "Libros",
        uniqueConstraints = @UniqueConstraint(columnNames = "Titulo",
                name = "tituloUniqueConstraint"))
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLibro")
    private int id;

    @NonNull
    @Column(name = "Titulo")
    private String titulo;

    @NonNull
    @Column(name = "Precio")
    private double precio;

    @ManyToMany(mappedBy = "listaLibros")
    private List<Autor> listaAutores = new ArrayList<>();
}
