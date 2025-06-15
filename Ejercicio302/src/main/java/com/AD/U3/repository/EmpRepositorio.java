package com.AD.U3.repository;


import com.AD.U3.entities.Dpto;
import com.AD.U3.entities.Emp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import jakarta.persistence.NoResultException;

public class EmpRepositorio implements Repositorio<Emp>{

    private Session session;

    public EmpRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Emp t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }

    // En EmpRepositorio.java
    public void añadirEmpleado(int idDpto, Emp emp) {
        try {
            Dpto dpto = session.createQuery("SELECT d FROM Dpto d WHERE d.id = :id", Dpto.class)
                    .setParameter("id", idDpto)
                    .getSingleResult();
            emp.setDptoElement(dpto);
            Transaction trx = session.beginTransaction();
            session.persist(emp);
            trx.commit();
        } catch (NoResultException e) {
            System.out.println("No existe el departamento con id=" + idDpto + ". No se puede añadir el empleado.");
        }
    }


    // ...
    public void actualizarJefeDepartamento(int idDpto, int idEmp) {
        try {
            Emp emp = session.createQuery(
                            "SELECT e FROM Emp e WHERE e.dptoElement.id=:id1 AND e.id=:id2", Emp.class)
                    .setParameter("id1", idDpto)
                    .setParameter("id2", idEmp)
                    .getSingleResult();

            emp.setEsJefe(true);
            Transaction trx = session.beginTransaction();
            session.merge(emp);
            trx.commit();
            System.out.println("Empleado actualizado como jefe.");
        } catch (NoResultException e) {
            System.out.println("No existe el empleado con id=" + idEmp + " en el departamento id=" + idDpto);
        }
    }

}