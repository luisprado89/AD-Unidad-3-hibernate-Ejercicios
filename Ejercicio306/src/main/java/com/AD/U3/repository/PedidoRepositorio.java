package com.AD.U3.repository;


import com.AD.U3.entities.Cliente;
import com.AD.U3.entities.LineaPedido;
import com.AD.U3.entities.Pedido;
import com.AD.U3.entities.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class PedidoRepositorio {
    Session sesion;
    Scanner scanner;
    ProductoRepositorio productoRepositorio;

    public PedidoRepositorio() {    }

    public PedidoRepositorio(Session sesion, Scanner sc) {
        this.sesion = sesion;
        this.scanner = sc;
        this.productoRepositorio = new ProductoRepositorio(sesion);
    }

    public void eliminarPedido(int idPedido) {
        Transaction trx = this.sesion.beginTransaction();

        try {

            Query query = this.sesion.createQuery("select p from Pedido p where p.idPedido=:idPedido");
            query.setParameter("idPedido", idPedido);
            Pedido pedido = (Pedido) query.getSingleResult();

            this.sesion.remove(pedido);

        } catch (Exception e) {
            System.out.println("No existe el pedido con ese ID");
        }

        trx.commit();
    }

    public void addPedido(Cliente cliente) {
        ArrayList<LineaPedido> lineaPedidos = new ArrayList<LineaPedido>();

        Pedido pedido = new Pedido(new Date(), cliente);

        String opcion = "";
        do {
            int idProducto = pintarPedirInt("Introduce el id del producto");
            Producto producto = productoRepositorio.getProducto(idProducto);
            if(producto.getIdProducto() != -1) {
                LineaPedido lPedido = new LineaPedido(idProducto, producto, pedido);
                int cantidad = pintarPedirInt("Introduzca la cantidad de productos");
                lPedido.setCantidad(cantidad);
                lineaPedidos.add(lPedido);
            }
            opcion = pintarPedirString("Quieres seguir introduciendo productos al pedido? (y/n)");
        }while(!opcion.toLowerCase().equals("n"));

        pedido.setListaPedidos(lineaPedidos);

        this.sesion.persist(pedido);

        Transaction trxTransaction = this.sesion.beginTransaction();

        this.sesion.persist(pedido);

        trxTransaction.commit();

    }

    public int pintarPedirInt(String mensaje) {
        System.out.println(mensaje);
        return this.scanner.nextInt();
    }

    public String pintarPedirString(String mensaje) {
        System.out.println(mensaje);
        return this.scanner.next();
    }

}
