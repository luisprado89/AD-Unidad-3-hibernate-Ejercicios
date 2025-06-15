package com.AD.U3.entities;


import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    private int idCliente;

    @Column(name = "DNI", columnDefinition = "char")
    private String dni;

    private String nombre;
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Alquiler> listaAlquileres;

    public Cliente() {
        super();
    }

    public Cliente(String dni, String nombre, String email) {
        super();
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Alquiler> getListaAlquileres() {
        return listaAlquileres;
    }

    public void setListaAlquileres(List<Alquiler> listaAlquileres) {
        this.listaAlquileres = listaAlquileres;
    }

    public void addAlquiler(Alquiler alquiler) {
        this.listaAlquileres.add(alquiler);
    }

    @Override
    public String toString() {
        return "Cliente [idCliente=" + idCliente + ", dni=" + dni + ", nombre=" + nombre + ", email=" + email + "]";
    }

}
