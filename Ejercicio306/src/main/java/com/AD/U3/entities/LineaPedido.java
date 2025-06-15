package com.AD.U3.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "lineaPedido")
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLineaPedido")
    private int idLineaPedido;
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    public LineaPedido() {
        super();
    }

    public LineaPedido(int cantidad, Producto producto, Pedido pedido) {
        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.pedido = pedido;
    }

    public int getIdLineaPedido() {
        return idLineaPedido;
    }

    public void setIdLineaPedido(int idLineaPedido) {
        this.idLineaPedido = idLineaPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "LineaPedido [idLineaPedido=" + idLineaPedido + ", cantidad=" + cantidad + ", producto=" + producto.getNombre()
                + ", pedido=" + pedido.getIdPedido() + "]";
    }

}
