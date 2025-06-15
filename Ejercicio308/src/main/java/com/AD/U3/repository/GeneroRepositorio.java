package com.AD.U3.repository;


import com.AD.U3.entities.Genero;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class GeneroRepositorio implements Repositorio<Genero>{

    private Session sesion;

    public GeneroRepositorio(Session session) {
        this.sesion = session;
    }

    @Override
    public void guardar(Genero t) {
        this.sesion.persist(t);
    }

    @Override
    public void eliminar(Genero t) {
        this.sesion.remove(t);
    }

    @Override
    public Genero buscarPorId(int id) {
        Transaction trx = this.sesion.beginTransaction();
        Query query = this.sesion.createQuery("select g from Genero g where g.idGenero=:idGenero");
        query.setParameter("idGenero", id);

        Genero genero = (Genero) query.getSingleResult();
        trx.commit();

        return genero;
    }

    @Override
    public List<Genero> buscarPorCadena(String cadena) {
        Transaction trx = this.sesion.beginTransaction();
        Query query = this.sesion.createQuery("select g from Genero g where g.nombre=:cadena");
        query.setParameter("cadena", cadena);

        ArrayList<Genero> listaGenero = (ArrayList<Genero>) query.getResultList();
        trx.commit();
        return listaGenero;
    }

    @Override
    public void actualizar(Genero t) {
        this.sesion.update(t);
    }

    public List<Genero> buscarTodo() {
        Transaction trx = this.sesion.beginTransaction();
        Query query = this.sesion.createQuery("select g from Genero g");

        ArrayList<Genero> listaGenero = (ArrayList<Genero>) query.getResultList();
        trx.commit();
        return listaGenero;
    }
}
