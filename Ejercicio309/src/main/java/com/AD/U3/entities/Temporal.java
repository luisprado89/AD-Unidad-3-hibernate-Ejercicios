package com.AD.U3.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "DNI_EMPLEADO")
@Table(name = "EMPLEADOS_TEMPORALES")
public class Temporal extends Empleado implements Serializable {

    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;

    @Column(name = "FECHA_FIN")
    private Date fechaFin;

    @Column(name = "PAGO_DIA")
    private float pagoDia;

    @Column(name = "SUPLEMENTO")
    private float suplemento;

    @Column(name = "SULEDO")
    private float sueldo;

    @OneToMany(mappedBy = "dniEmpleado")
    private Set<Venta> ventas;

    public Temporal() {
        super();
    }

    public Temporal(String dni, String nombre, String telefono, float porcentajeRetencion, Date fechaInicio, Date fechaFin, float pagoDia) {
        super(dni, nombre, telefono, porcentajeRetencion);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pagoDia = pagoDia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public float getPagoDia() {
        return pagoDia;
    }

    public void setPagoDia(float pagoDia) {
        this.pagoDia = pagoDia;
    }

    public float getSueldo() {
        return sueldo;
    }

    public float getSuplemento() {
        return suplemento;
    }

    public void setSuplemento(float suplemento) {
        this.suplemento = suplemento;
    }

    @Override
    public void calcularNomina() {
        sueldo = pagoDia * ((fechaFin.getTime() - fechaInicio.getTime()) / (1000 * 60 * 60 * 24)) * 
        		( 1 - getPorcentajeRetencion()) + getSuplemento();
    }

    @Override
    public String toString() {
        String temporal = "DNI: " + super.getDni() + "\n"
                + "NOMBRE: " + super.getNombre() + "\n"
                + "TELÉFONO: " + super.getTelefono() + "\n"
                + "PORCENTAJE DE RENTENCIÓN: " + super.getPorcentajeRetencion() + "\n"
                + "FECHA DE INICIO: " + getFechaInicio() + "\n"
                + "FECHA DE FIN: " + getFechaFin() + "\n"
                + "PAGO DÍA: " + getPagoDia() + "\n"
                + "SUPLEMENTO: " + getSuplemento() + "\n"
                + "SUELDO: " + getSueldo() + "\n";
        return temporal;
    }

}
