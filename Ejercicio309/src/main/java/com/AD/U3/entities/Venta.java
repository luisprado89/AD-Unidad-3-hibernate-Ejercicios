package com.AD.U3.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import jakarta.persistence.*;
@Entity
@Table(name = "VENTAS")
public class Venta implements Serializable {

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CODIGO_ARTICULO")
    private Producto codigoArticulo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DNI_EMPLEADO")
    private Empleado dniEmpleado;

    @Id
    @Column(name = "FECHA_VENTA")
    private Date fechaVenta;

    @Id
    @Column(name = "HORA")
    private Time hora;

    @Column(name = "NUMERO_UNIDADES")
    private int numeroUnidades;

    @Column(name = "IMPORTE")
    private float importe;

    public Venta() {
    }

    public Venta(Producto codigoArticulo, Empleado dniEmpleado, Date fechaVenta, Time hora, int numeroUnidades, float importe) {
        this.codigoArticulo = codigoArticulo;
        this.dniEmpleado = dniEmpleado;
        this.fechaVenta = fechaVenta;
        this.hora = hora;
        this.numeroUnidades = numeroUnidades;
        this.importe = importe;
    }
    
    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Producto getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(Producto codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public int getNumeroUnidades() {
        return numeroUnidades;
    }

    public void setNumeroUnidades(int numeroUnidades) {
        this.numeroUnidades = numeroUnidades;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

}
