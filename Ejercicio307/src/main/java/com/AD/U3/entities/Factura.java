package com.AD.U3.entities;


import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFactura")
    private int idFactura;
    private String descripcion;
    private double precio;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE) // Se ajusta a DATE en la BD, cuando date da error se soluciona de esta manera
    private Date fecha;


    @ManyToOne
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    private Cliente cliente;

    public Factura() {
        super();
    }

    public Factura(String descripcion, double precio, Date fechaDate) {
        super();
        this.descripcion = descripcion;
        this.precio = precio;
        this.fecha = fechaDate;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaDate() {
        return fecha;
    }

    public void setFechaDate(Date fechaDate) {
        this.fecha = fechaDate;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        cliente.addFactura(this);
    }

    @Override
    public String toString() {
        return "ID: " + idFactura +"\nCLIENTE: " + cliente +
                "\nDESCRIPCION: " + descripcion + "\nPRECIO:" + precio;
    }
}
