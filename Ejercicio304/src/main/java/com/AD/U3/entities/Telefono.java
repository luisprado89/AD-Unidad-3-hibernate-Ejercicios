package com.AD.U3.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Telefonos")
public class Telefono implements Serializable {

    @Id
    @Column(name = "dni_autor")
    private String dniAutor;

    @OneToOne
    @MapsId
    @JoinColumn(name = "dni_autor")
    private Autor autor;

    @NonNull
    @Column(name = "NumeroTf")
    private String numeroTf;
}
