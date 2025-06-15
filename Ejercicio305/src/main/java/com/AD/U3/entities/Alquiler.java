package com.AD.U3.entities;


import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "alquiler")
public class Alquiler {

    @Id // Indica que este campo es la clave primaria de la tabla.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Especifica que el ID se generará automáticamente en la base de datos.
    private int idAlquiler; // Campo de la tabla: almacena el identificador único del alquiler.

    private Date fecha; // Campo de la tabla: almacena la fecha del alquiler.

    private boolean alquilado; // Campo de la tabla: indica si el libro está alquilado o no.

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER) // Relación muchos a uno con la entidad Libro.
    @JoinColumn(name = "idLibro") // Especifica que este campo corresponde a la clave foránea "idLibro" en la tabla "alquiler".
    private Libro libro; // Campo de la tabla: referencia al libro alquilado.

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Relación muchos a uno con la entidad Cliente.
    @JoinColumn(name = "idCliente") // Especifica que este campo corresponde a la clave foránea "idCliente" en la tabla "alquiler".
    private Cliente cliente; // Campo de la tabla: referencia al cliente que realiza el alquiler.

    public Alquiler() {} // Constructor vacío necesario para JPA. No es un campo de la tabla.

    public Alquiler(Date fecha, boolean alquilado) {
        this.fecha = fecha;
        this.alquilado = alquilado;
    }

    // Métodos getter y setter para acceder y modificar los atributos de la clase.

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isAlquilado() {
        return alquilado;
    }

    public void setAlquilado(boolean alquilado) {
        this.alquilado = alquilado;
    }

    public Libro getLibro() {
        return libro;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
        libro.addAlquiler(this); // Agrega este alquiler a la lista de alquileres del libro.
    }



    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        cliente.addAlquiler(this); // Agrega este alquiler a la lista de alquileres del cliente.
    }
}
