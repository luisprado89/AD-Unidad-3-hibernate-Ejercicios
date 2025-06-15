package com.AD.U3.entities;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido")
    private int idPedido;

    private Date fecha;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<LineaPedido> listaPedidos;

    public Pedido() {
        super();
    }

    public Pedido(Date fecha, Cliente cliente) {
        super();
        this.fecha = fecha;
        this.cliente = cliente;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<LineaPedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<LineaPedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @Override
    public String toString() {
        return "Pedido [idPedido=" + idPedido + ", fecha=" + fecha + ", cliente=" + cliente + ", listaPedidos="
                + listaPedidos + "]";
    }

}
