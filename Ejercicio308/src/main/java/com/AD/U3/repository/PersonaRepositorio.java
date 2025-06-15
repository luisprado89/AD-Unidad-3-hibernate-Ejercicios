package com.AD.U3.repository;


import com.AD.U3.entities.Persona;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class PersonaRepositorio implements Repositorio<Persona>{

    private Session sesion;

    public PersonaRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    @Override
    public void guardar(Persona t) {
        Transaction trxTransaction = sesion.beginTransaction();
        this.sesion.persist(t);
        trxTransaction.commit();
    }

    @Override
    public void eliminar(Persona t) {
        this.sesion.remove(t);
    }

    @Override
    public Persona buscarPorId(int id) {
        Transaction trx = this.sesion.beginTransaction();
        Query query = null;
        Persona persona = null;
        try {
            query = this.sesion.createQuery("select p from Actriz p where p.idPersona=:idPersona");
            query.setParameter("idPersona", id);
            persona = (Persona) query.getSingleResult();
        }catch (Exception e) {
            try {
                query = this.sesion.createQuery("select p from Actor p where p.idPersona=:idPersona");
                query.setParameter("idPersona", id);
                persona = (Persona) query.getSingleResult();
            }catch (Exception e1) {
                System.out.println("Error recuperando valores actor");
            }

        }
        trx.commit();

        return persona;
    }

    @Override
    public List<Persona> buscarPorCadena(String cadena) {
        Transaction trx = this.sesion.beginTransaction();
        ArrayList<Persona> listaPersona = null;
        Query query = null;
        try {
            query = this.sesion.createQuery("select p from Persona p where p.anhoNacimiento > :cadena");
            query.setParameter("cadena", cadena);
            listaPersona = (ArrayList<Persona>) query.getResultList();
        }catch (Exception e) {
            try {
                query = this.sesion.createQuery("select p from Actor p where p.anhoNacimiento > :cadena");
                query.setParameter("cadena", cadena);
                listaPersona = (ArrayList<Persona>) query.getResultList();
            }catch (Exception e1) {
                System.out.println("Error recuperando valores actor");
            }

        }

        trx.commit();
        return listaPersona;
    }

    public List<Persona> buscarPorAnho(int cadena) {
        Transaction trx = this.sesion.beginTransaction();
        ArrayList<Persona> listaPersona = null;
        Query query = null;
        try {
            query = this.sesion.createQuery("select p from Actriz p where p.anhoNacimiento > :cadena");
            query.setParameter("cadena", cadena);
            listaPersona = (ArrayList<Persona>) query.getResultList();
        }catch (Exception e) {
            try {
                query = this.sesion.createQuery("select p from Actor p where p.anhoNacimiento > :cadena");
                query.setParameter("cadena", cadena);
                listaPersona = (ArrayList<Persona>) query.getResultList();
            }catch (Exception e1) {
                System.out.println("Error recuperando valores actor");
            }

        }

        trx.commit();
        return listaPersona;
    }

    @Override
    public void actualizar(Persona t) {
        this.sesion.update(t);
    }

    public List<Persona> buscarPorMejorActriz(int mejorActriz) {
        Transaction trx = this.sesion.beginTransaction();
        Query query = this.sesion.createQuery("select p from Actriz p where p.numeroMejorActriz >= :mejorActriz");
        query.setParameter("mejorActriz", mejorActriz);

        ArrayList<Persona> listaPersona = (ArrayList<Persona>) query.getResultList();
        trx.commit();
        return listaPersona;
    }

    public List<Persona> buscarPorMejorActor(int mejorActor) {
        Transaction trx = this.sesion.beginTransaction();
        Query query = this.sesion.createQuery("select p from Actor p where p.numeroOscars >= :numeroOscars");
        query.setParameter("numeroOscars", mejorActor);

        ArrayList<Persona> listaPersona = (ArrayList<Persona>) query.getResultList();
        trx.commit();
        return listaPersona;
    }
}
