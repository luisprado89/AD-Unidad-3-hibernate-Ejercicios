package com.AD.U3.repository;


import com.AD.U3.entities.Libro;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LibroRepositorio implements Repositorio<Libro>{

    private Session sesion;

    public LibroRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    @Override
    public void insertarUno(Libro t) {
        Transaction trx = sesion.beginTransaction();
        sesion.persist(t);
        trx.commit();

    }

    @Override
    public void borrar(Libro t) {
        Transaction trx = sesion.beginTransaction();
        sesion.remove(t);
        trx.commit();

    }

    @Override
    public List<Libro> encontrarTodos() {
        Query query = sesion.createQuery("SELECT l FROM Libro l");
        List<Libro> listaLibros = query.getResultList();

        return listaLibros;
    }

    @Override
    public Libro encontrarUnoPorString(String nombre) {
        Query query = sesion.createQuery("SELECT l FROM Libro l WHERE l.titulo=:nombre");

        query.setParameter("nombre", nombre);

        return (Libro) query.getSingleResult();
    }

    @Override
    public void actualizar(Libro t) {
        Transaction trx = sesion.beginTransaction();
        sesion.update(t);
        trx.commit();

    }

}
