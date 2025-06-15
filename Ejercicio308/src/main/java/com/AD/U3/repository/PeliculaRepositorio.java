package com.AD.U3.repository;


import com.AD.U3.entities.Pelicula;
import com.AD.U3.entities.Persona;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class PeliculaRepositorio implements Repositorio<Pelicula>{

    private Session sesion;

    public PeliculaRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    @Override
    public void guardar(Pelicula t) {
        this.sesion.persist(t);
    }

    @Override
    public void eliminar(Pelicula t) {
        this.sesion.remove(t);
    }

    @Override
    public Pelicula buscarPorId(int id) {
        Transaction trx = this.sesion.beginTransaction();
        Query query = this.sesion.createQuery("select p from Pelicula p where p.idPelicula=:idPelicula");
        query.setParameter("idPelicula", id);

        Pelicula pelicula = (Pelicula) query.getSingleResult();
        trx.commit();

        return pelicula;
    }

    @Override
    public List<Pelicula> buscarPorCadena(String cadena) {
        Transaction trx = this.sesion.beginTransaction();
        Query query = this.sesion.createQuery("select p from Pelicula p where p.titulo = :cadena");
        query.setParameter("cadena", cadena);

        ArrayList<Pelicula> listaPelicula = (ArrayList<Pelicula>) query.getResultList();
        trx.commit();
        return listaPelicula;
    }

    @Override
    public void actualizar(Pelicula t) {
        this.sesion.update(t);
    }

    public List<Pelicula> buscarTodo() {
        Transaction trx = this.sesion.beginTransaction();
        Query query = this.sesion.createQuery("select p from Pelicula p");

        ArrayList<Pelicula> listaPelicula = (ArrayList<Pelicula>) query.getResultList();
        trx.commit();
        return listaPelicula;
    }

    public List<Pelicula> peliculasPorPersona(Persona p) {
        Transaction trx = this.sesion.beginTransaction();
        Query query = this.sesion.createQuery("select pel from Pelicula pel, Persona p, listaPeliculas lp "
                + "where pel.idPelicula=lp.idPelicula and lp.idPersona=:idPersona");
        query.setParameter("idPersona", p.getIdPersona());
        ArrayList<Pelicula> listaPelicula = (ArrayList<Pelicula>) query.getResultList();
        trx.commit();
        return listaPelicula;
    }
}
