package com.AD.U3.repository;


import com.AD.U3.entities.Cliente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ClienteRepositorio {

    private Session sesion;

    public ClienteRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    public Cliente obtenerCliente(int idCliente) {
        Transaction trx = this.sesion.beginTransaction();

        Query query  = this.sesion.createQuery("SELECT c FROM Cliente c where c.idCliente=:id");

        query.setParameter("id", idCliente);

        Cliente cliente = (Cliente) query.getSingleResult();

        trx.commit();

        return cliente;
    }

    public void anhadirCliente(Cliente cliente) {
        Transaction trx = this.sesion.beginTransaction();

        this.sesion.persist(cliente);

        trx.commit();
    }

    public void modificarCliente(Cliente cliente) {
        Transaction trx = this.sesion.beginTransaction();

        this.sesion.update(cliente);

        trx.commit();
    }

    public boolean borrarCliente(Cliente cliente) {
        try {
            Transaction trx = this.sesion.beginTransaction();

            this.sesion.remove(cliente);

            trx.commit();
            return true;

        }catch (Exception e) {
            return false;
        }
    }
}
