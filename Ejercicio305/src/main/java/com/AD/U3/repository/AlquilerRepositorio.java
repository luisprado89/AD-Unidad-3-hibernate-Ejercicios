package com.AD.U3.repository;


import com.AD.U3.entities.Alquiler;
import com.AD.U3.entities.Cliente;
import com.AD.U3.entities.Libro;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.List;

public class AlquilerRepositorio {

    private Session sesion;

    public AlquilerRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    public boolean esAlquilado(int idLibro) {
        //Query query = this.sesion.createQuery("select a from Alquiler a where a.idAlquiler.libro.idLibro=:idLibro");
        Query query = this.sesion.createQuery("select a from Alquiler a where a.libro.idLibro = :idLibro");
        query.setParameter("idLibro", idLibro);

        try {
            List<Alquiler> listaAlquileres = query.getResultList();
            for(Alquiler alquiler: listaAlquileres) {
                if (alquiler.isAlquilado())
                    return true;
            }
            return false;

        }catch (Exception e) {
            return false;
        }
    }

    public void alquilar(int idLibro, int idCliente) {
        Transaction trx = this.sesion.beginTransaction();
        try {

            Query comprobarLibro = this.sesion.createQuery("select l from Libro l where l.idLibro=:idLibro");
            comprobarLibro.setParameter("idLibro", idLibro);
            Libro libro = (Libro) comprobarLibro.getSingleResult();

            if(!esAlquilado(idLibro)) {

                try {

                    Query comprobarCliente = this.sesion.createQuery("select c from Cliente c where c.idCliente=:idcliente");
                    comprobarCliente.setParameter("idcliente", idCliente);
                    Cliente cliente = (Cliente) comprobarCliente.getSingleResult();

                    Alquiler alquiler = new Alquiler(new Date(System.currentTimeMillis()), true);// Crea un objeto Date con la fecha actual
                    alquiler.setCliente(cliente);
                    alquiler.setLibro(libro);
                    alquiler.setAlquilado(true);
                    this.sesion.persist(alquiler);
                    System.out.println("Libro alquilado");

                }catch (Exception e) {
                    System.out.println("No existe el cliente");
                }
            }else {

                System.out.println("Libro ya alquilado");

            }
            trx.commit();
        }catch (Exception e) {
            System.out.println("Libro no existe");
        }
    }

    public void devolver(int idLibro) {
        if(esAlquilado(idLibro)) {
            Transaction trx = this.sesion.beginTransaction();

            Query query = this.sesion.createQuery("select a from Alquiler a where a.libro.idLibro=:idLibro and a.alquilado=true");
            query.setParameter("idLibro", idLibro);
            Alquiler alquiler = (Alquiler) query.getSingleResult();

            alquiler.setAlquilado(false);

            this.sesion.update(alquiler);

            trx.commit();


        }else {
            System.out.println("No se puede devolver un libro que no fue alquilado");
        }
    }
}

