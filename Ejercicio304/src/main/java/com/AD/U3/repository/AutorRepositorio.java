package com.AD.U3.repository;


import com.AD.U3.entities.Autor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AutorRepositorio implements Repositorio<Autor>{

    private Session sesion;

    public AutorRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    @Override
    public void insertarUno(Autor t) {
        Transaction trx = sesion.beginTransaction();
        sesion.persist(t);
        trx.commit();
    }

    @Override
    public void borrar(Autor t) {
        Transaction trx = sesion.beginTransaction();
        sesion.remove(t);
        trx.commit();
    }

    @Override
    public List<Autor> encontrarTodos() {
        Query query = sesion.createQuery("SELECT a FROM Autor a");
        List<Autor> listaAutores = query.getResultList();

        return listaAutores;
    }

    @Override
    public Autor encontrarUnoPorString(String dni) {
        Query query = sesion.createQuery("SELECT a FROM Autor a WHERE a.dniAutor=:dni");

        query.setParameter("dni", dni);

        return (Autor) query.getSingleResult();
    }

    @Override
    public void actualizar(Autor t) {
        Transaction trx = sesion.beginTransaction();
        sesion.update(t);
        trx.commit();

    }

}
