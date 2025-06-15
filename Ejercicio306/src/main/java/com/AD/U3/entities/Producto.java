package com.AD.U3.entities;


import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private int idProducto;

    private String nombre;
    private String descripcion;
    private double precio;
    private String imagen;

    @OneToMany(mappedBy = "producto",  cascade = CascadeType.ALL)
    private List<LineaPedido> listaPedidos;

    public Producto() {
        super();
    }

    public Producto(int idProducto, String nombre, String descripcion, double precio, String imagen,
                    List<LineaPedido> listaPedidos) {
        super();
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.listaPedidos = listaPedidos;
    }

    public Producto(int idProducto) {
        super();
        this.idProducto = idProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<LineaPedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<LineaPedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @Override
    public String toString() {
        return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", precio=" + precio + ", imagen=" + imagen + ", listaPedidos=" + listaPedidos + "]";
    }

}
