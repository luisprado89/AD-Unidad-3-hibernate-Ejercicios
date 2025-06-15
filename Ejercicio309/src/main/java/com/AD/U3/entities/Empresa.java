package com.AD.U3.entities;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "EMPRESAS")
@NamedQueries({
   @NamedQuery(name="empleadosEmpresa", query="select empleados from Empresa where cif=:cif"),
    @NamedQuery(name="productosEmpresa", query="select productos from Empresa where cif=:cif"),
   @NamedQuery(name="empresas", query="from Empresa")
})
public class Empresa implements Serializable {

    @Id
    @Column(name = "CIF", unique = true, nullable = false)
    private String cif;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "TELEFONO")
    private String telefono;

    @OneToMany(mappedBy = "empresa")
    private Set<Producto> productos;

    @OneToMany(mappedBy = "empresa")
    private Set<Empleado> empleados;

    public Empresa() {
    }

    public Empresa(String cif) {
        this.cif = cif;
    }

    public Empresa(String cif, String nombre, String direccion, String telefono) {
        this.cif = cif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public String toString() {
        String empresa = "CIF: " + getCif() + "\n" + "NOMBRE: " + getNombre() + "\n"
                + "DIRECCIÓN: " + getDireccion() + "\n" + "TELÉFONO: " + getTelefono() + "\n";
        return empresa;
    }
}
