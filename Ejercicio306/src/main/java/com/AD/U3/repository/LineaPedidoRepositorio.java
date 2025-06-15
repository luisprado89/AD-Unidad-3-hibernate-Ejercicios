package com.AD.U3.repository;


import com.AD.U3.entities.Cliente;
import com.AD.U3.entities.LineaPedido;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;



public class LineaPedidoRepositorio {
    Session sesion;

    public LineaPedidoRepositorio() {    }

    public LineaPedidoRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    public void mostrarTodosPedidos() {
        Transaction trx = this.sesion.beginTransaction();

        Query query = this.sesion.createQuery("select lp from LineaPedido lp");
        ArrayList<LineaPedido> listaPedidos = (ArrayList<LineaPedido>) query.getResultList();

        for(LineaPedido lPedido : listaPedidos)
            System.out.println(lPedido.toString());

        trx.commit();
    }

    public void mostrarPedidosCliente(Cliente cliente) {
        Transaction trx = this.sesion.beginTransaction();

        Query query = this.sesion.createQuery("select lp from LineaPedido lp where lp.pedido.cliente.idCliente=:idCliente");

        query.setParameter("idCliente", cliente.getIdCliente());

        ArrayList<LineaPedido> listaPedidos = (ArrayList<LineaPedido>) query.getResultList();

        for(LineaPedido lPedido : listaPedidos)
            System.out.println(lPedido.toString());

        trx.commit();
    }

}
