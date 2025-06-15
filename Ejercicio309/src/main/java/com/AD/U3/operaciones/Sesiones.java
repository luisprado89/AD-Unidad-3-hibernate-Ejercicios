package com.AD.U3.operaciones;

import org.hibernate.Session;

public class Sesiones {

	private Session session;
	
	public Sesiones(Session sesiones) {
		this.session = sesiones;
	}
	
    public void guardar(Object object) {
        this.session.beginTransaction();
        this.session.save(object);
        this.session.getTransaction().commit();
    }

    public void actualizar(Object object) {
        this.session.beginTransaction();
        this.session.update(object);
        this.session.getTransaction().commit();
    }

    public void borrar(Object object) {
        this.session.beginTransaction();
        this.session.delete(object);
        this.session.getTransaction().commit();
    }

}
