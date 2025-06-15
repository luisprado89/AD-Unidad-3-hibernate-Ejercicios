package com.AD.U3.repository;

import java.util.List;

import com.AD.U3.entities.Dpto;
import com.AD.U3.entities.Emp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import jakarta.persistence.NoResultException;

public class DptoRepositorio implements Repositorio<Dpto> {

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

    public void visualizarDepartamento(int id) {
        try {
            Dpto depto = session.createQuery("SELECT d FROM Dpto d WHERE d.id = :id", Dpto.class)
                    .setParameter("id", id)
                    .getSingleResult();

            System.out.println("Id: " + depto.getId() + "\nNombre: " + depto.getNombre() + "\nlocalidad: " + depto.getLocalidad());

            Query<Emp> query = session.createQuery("SELECT e FROM Emp e WHERE e.dptoElement.id = :id", Emp.class);
            query.setParameter("id", id);

            List<Emp> empleados = query.getResultList();

            for (Emp empleado : empleados) {
                System.out.println(empleado.toString());
            }
        } catch (NoResultException e) {
            System.out.println("No existe el departamento con id=" + id);
        }
    }

    public void eliminarDepartamento(int id) {
        Transaction trx = this.session.beginTransaction();
        try {
            Dpto depto = session.createQuery("SELECT d FROM Dpto d WHERE d.id = :id", Dpto.class)
                    .setParameter("id", id)
                    .getSingleResult();
            session.remove(depto);
            trx.commit();
        } catch (NoResultException e) {
            System.out.println("No existe el departamento con id=" + id);
            trx.rollback();
        }
    }
}