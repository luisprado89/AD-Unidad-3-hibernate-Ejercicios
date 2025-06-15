package com.AD.U3.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="EMPLEADOS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Empleado implements Serializable{
    
    @Id
    @Column(name="DNI",unique=true,nullable=false)
    private String dni;
    
    @Column(name="NOMBRE")
    private String nombre;
    
    @Column(name="TELEFONO")
    private String telefono;
    
    @Column(name="PORCENTAJE_RETENCION")
    private float porcentajeRetencion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIF_EMPRESA")
    private Empresa empresa;

    public Empleado() {
    }

    public Empleado(String dni, String nombre, String telefono, float porcentajeRetencion) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.porcentajeRetencion = porcentajeRetencion;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    public void setPorcentajeRetencion(float porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void calcularNomina() {}

}
