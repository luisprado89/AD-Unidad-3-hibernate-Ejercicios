package com.AD.U3.repository;

import com.AD.U3.entities.Dpto;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class DptoRepositorio implements Repositorio<Dpto>{

    private Session session;

    public DptoRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Dpto t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }
}