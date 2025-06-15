package com.AD.U3.repository;

import com.AD.U3.entities.Dpto;
import com.AD.U3.entities.Emp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

//Clase que implementa un repositorio para gestionar objetos de tipo "Dpto"
public class DptoRepositorio implements Repositorio<Dpto>{
    //Variable privada que almacena la session de Hibernate
    private Session session;
    //Constructor que recibe una sesion de Hibernate y la asigna a la variable de clase
    public DptoRepositorio(Session session){
        super();
        this.session=session;//Seguarda la session de Hibernate proporcionada
    }
    //Metodo que guarda un objeto de tipo "Dpto" en la base de datos
    @Override
    public void guardar(Dpto t){
        //Inicia una trasaccion para asegurar la atomicidad de la operacion
        Transaction trx = this.session.beginTransaction();
        //Usa el metodo "persist" de Hibernate para guardar el objeto en el contexto de persistencia
        session.persist(t);
        //Confirmamos la transaccion, lo que asegudar que los camnbios se reflejen en la base de datos
        trx.commit();
    }


    public void visualizarDepartamento(int id){
        Dpto depto = (Dpto) session.createQuery("SELECT d FROM Dpto d where id="+id).getSingleResult();
        System.out.println("Id: "+depto.getId()+"\nNombre: "+depto.getNombre()+"\nlocalidad: "+depto.getLocalidad());
        Query query = session.createQuery("SELECT e FROM Emp e WHERE e.dptoElement.id=:id");
        query.setParameter("id", id);
        List<Emp> empleados = query.getResultList();
        for (Emp empleado : empleados){
            System.out.println(empleado.toString());
        }

    }
    public void eliminarDepartamento(int id){
        Transaction trx = this.session.beginTransaction();
        //Dpto depto = (Dpto) session.createQuery("SELECT d FROM Dpto d where id='"+id+"' ").getSingleResult();
        Dpto depto = (Dpto) session.createQuery("SELECT d FROM Dpto d WHERE d.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        session.remove(depto);
        trx.commit();
    }
}
