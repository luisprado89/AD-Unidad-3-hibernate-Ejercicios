package com.AD.U3.entities;

import java.io.Serializable;
import jakarta.persistence.*;
@Entity
@PrimaryKeyJoinColumn(name = "DNI_EMPLEADO")
@Table(name = "EMPLEADOS_FIJOS")
public class Fijo extends Empleado implements Serializable {

    @Column(name = "SALARIO_BASE")
    private int salarioBase;

    @Column(name = "TRIENIOS")
    private int trienios;

    @Column(name = "SUELDO")
    private float sueldo;

    public Fijo() {
        super();
    }

    public Fijo(String dni, String nombre, String telefono, float porcentajeRetencion, int salarioBase, int trienios) {
        super(dni, nombre, telefono, porcentajeRetencion);
        this.salarioBase = salarioBase;
        this.trienios = trienios;
    }

    public int getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(int salarioBase) {
        this.salarioBase = salarioBase;
    }

    public int getTrienios() {
        return trienios;
    }

    public void setTrienios(int trienios) {
        this.trienios = trienios;
    }

    public float getSueldo() {
        return sueldo;
    }

    @Override
    public void calcularNomina() {
        sueldo = (salarioBase + trienios) * (1 - getPorcentajeRetencion());
    }

    @Override
    public String toString() {
        String fijo = "DNI: " + super.getDni() + "\n"
                + "NOMBRE: " + super.getNombre() + "\n" + "TELÉFONO: " + super.getTelefono() + "\n"
                + "PORCENTAJE DE RENTENCIÓN: " + super.getPorcentajeRetencion() + "\n"
                + "SALARIO BASE: " + getSalarioBase() + "\n" + "TRIENIOS: " + getTrienios() + "\n"
                + "SULEDO: " + getSueldo() + "\n";
        return fijo;
    }

}
