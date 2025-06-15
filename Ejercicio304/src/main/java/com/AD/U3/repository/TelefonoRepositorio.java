package com.AD.U3.repository;


import com.AD.U3.entities.Telefono;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TelefonoRepositorio implements Repositorio<Telefono>{

    private Session sesion;

    public TelefonoRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    @Override
    public void insertarUno(Telefono t) {
        Transaction trx = sesion.beginTransaction();
        sesion.persist(t);
        trx.commit();

    }

    @Override
    public void borrar(Telefono t) {
        Transaction trx = sesion.beginTransaction();
        sesion.remove(t);
        trx.commit();
    }

    @Override
    public List<Telefono> encontrarTodos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Telefono encontrarUnoPorString(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actualizar(Telefono t) {
        Transaction trx = sesion.beginTransaction();
        sesion.update(t);
        trx.commit();
    }

}
