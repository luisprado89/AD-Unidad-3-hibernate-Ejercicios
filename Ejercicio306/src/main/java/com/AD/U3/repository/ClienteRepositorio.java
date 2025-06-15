package com.AD.U3.repository;


import com.AD.U3.entities.Cliente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class ClienteRepositorio {
    Session sesion;

    public ClienteRepositorio() {    }

    public ClienteRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    public Cliente obtenerClientePorDNI(String dni) {
        Transaction trx = this.sesion.beginTransaction();
        Cliente cliente;
        try {

            Query query = this.sesion.createQuery("select c from Cliente c where c.dni=:dni");
            query.setParameter("dni", dni);
            cliente = (Cliente) query.getSingleResult();

        }catch (Exception e) {
            cliente = new Cliente(-1);
        }

        trx.commit();

        return cliente;
    }

}
