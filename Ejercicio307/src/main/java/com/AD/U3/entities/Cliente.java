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
    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "idTelefono", cascade = CascadeType.ALL)
    private List<Telefono> listaTelefonos;

    @OneToMany(mappedBy = "idFactura", cascade = CascadeType.ALL)
    private List<Factura> listaFacturas;

    public Cliente() {
        super();
    }

    public Cliente(String nombre, String direccion) {
        super();
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void addFactura(Factura factura) {
        this.listaFacturas.add(factura);
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        for(Telefono tlTelefono : listaTelefonos)
            sBuilder.append(tlTelefono.getDescripcion() + ": " + tlTelefono.getNumero() + " ");

        return nombre + ", " + direccion + ", telefonos (" + sBuilder.toString() + ")";
    }
}
