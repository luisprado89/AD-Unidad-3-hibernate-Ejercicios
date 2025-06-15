package com.AD.U3.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dptos")
public class Dpto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dpto_id")
    private int id;

    private String nombre;
    private String localidad;

    @OneToMany(mappedBy = "dptoElement", cascade = CascadeType.ALL)
    private List<Emp> empts;

    public Dpto() {

    }

    public Dpto(String nombre, String localidad) {
        super();
        this.nombre = nombre;
        this.localidad = localidad;
        this.empts = new ArrayList<Emp>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setEmpts(ArrayList<Emp> lista) {
        this.empts = lista;
    }

    public void addEmps(Emp emp) {
        this.empts.add(emp);
        emp.setDptoElement(this);
    }
}
