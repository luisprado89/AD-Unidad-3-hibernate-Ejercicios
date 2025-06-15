package com.AD.U3.repository;


import com.AD.U3.entities.Cliente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class ClienteRepositorio {

    Session session;

    public ClienteRepositorio(Session session) {
        this.session = session;
    }

    public Cliente getClientePorId(int idCliente) {
        Transaction trxTransaction = this.session.beginTransaction();

        Query query = this.session.createQuery("select c from Cliente c where c.idCliente=:idCliente");
        query.setParameter("idCliente", idCliente);
        Cliente cliente = (Cliente) query.getSingleResult();

        trxTransaction.commit();

        return cliente;
    }
}
