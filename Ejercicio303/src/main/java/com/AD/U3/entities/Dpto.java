package com.AD.U3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//Marca esta clase como una entidad
@Entity
//Especificamos el nombre de la tabla en la base ded atos asociada con esta entidad
@Table(name = "dptos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dpto {
    @Id
    //Congfiguramos la estrategia de generacion automatica del valor de la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Asociamos este campo con una columna de la tabla de la base ded atos llamada dpto_id
    @Column(name = "dpto_id")
    private int id;

    private String nombre;
    private String localidad;
    //Definimos una relacion de uno a muchos con la entidad "Emp"
    @OneToMany(
            mappedBy = "dptoElement",
            cascade = CascadeType.ALL
    )
    private List<Emp> empts = new ArrayList<>();//Lista de empleados asociados

    public Dpto(String nombre, String localidad) {
        this.nombre = nombre;
        this.localidad = localidad;
    }

    public void addEmps(Emp emp){
        this.empts.add(emp);
        emp.setDptoElement(this);
    }
}
