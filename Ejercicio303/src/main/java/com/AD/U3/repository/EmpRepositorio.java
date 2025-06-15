package com.AD.U3.repository;

import com.AD.U3.entities.Dpto;
import com.AD.U3.entities.Emp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class EmpRepositorio implements Repositorio<Emp>{
    //Atributo privado que almacena la sesion de Hibernate.
    private Session session;
    //Constructor que inicializa el repositorio con una sesion de Hibernate
    public EmpRepositorio(Session session){
        super();
        this.session=session;//Asignacion de la sesion proporcionada
    }
    //Metodo que guarda un objeto de tipo "Emp" en la base de datos
    @Override
    public void guardar(Emp t){
        //Inicia una transaccion para garantizar la atomicidad de la operacion
        Transaction trx = this.session.beginTransaction();
        //Usa "persist" para agregar el objeto "t" al contexto de persistencia
        session.persist(t);
        //Confirma la transaccion, aplicando los cambios a la base de datos
        trx.commit();
    }
public void añadirEmpleado(int idDepatamento, Emp empleado){
        Transaction trx = this.session.beginTransaction();
    Dpto dpto = (Dpto) session.createQuery("SELECT d FROM Dpto d WHERE id=:id").setParameter("id", idDepatamento).getSingleResult();
    dpto.addEmps(empleado);
    this.session.persist(dpto);
    this.session.persist(empleado);
    trx.commit();
}
public void actualizarJefeDepartamento(int idDepart,int idEmpl){
        Transaction trx = this.session.beginTransaction();
    Query query = session.createQuery("SELECT e FROM Emp e WHERE e.dptoElement.id=:id1 AND e.id=:id2");
    query.setParameter("id1", idDepart);
    query.setParameter("id2", (long) idEmpl);
    Emp emp =(Emp) query.getSingleResult();
    emp.setEsJefe(true);
    this.session.persist(emp);
    trx.commit();
}
public void empleadosTecnicos(){
        Query query = session.createQuery("select e from Emp e where e.puesto='Técnico'");
    List<Emp> listaEmpleados = query.getResultList();
    System.out.println("Total de empleados: "+listaEmpleados.size());
    for (Emp emp : listaEmpleados)
        System.out.println(emp.toString());
}
public List<Emp> empleadoConMayorSueldo(){
        Query query = session.createQuery("SELECT e FROM Emp e WHERE e.sueldo=(SELECT MAX(em.sueldo) FROM Emp em)");
        List<Emp> emps = query.getResultList();
        for (Emp e : emps)
            System.out.println(e.toString());
        return emps;
}
public void aumentarSalarioJefes(double cantidad){
    Transaction trx = session.beginTransaction();

    Query query = session.createQuery("update Emp e set e.sueldo = e.sueldo + :cantidad where e.esJefe=true");

    query.setParameter("cantidad", cantidad);

    int elementos_afectados = query.executeUpdate();

    System.out.println("Se han modificado: " + elementos_afectados + " elementos");

    trx.commit();
}
public void borrarEmpleadosDepartamento(int idDepartamento){
        Transaction trx = session.beginTransaction();
        Query query = session.createQuery("select e from Emp e where e.dptoElement.id=:id");
        query.setParameter("id",idDepartamento);
        List<Emp> empleados = query.getResultList();
        for (Emp e:empleados)
            session.remove(e);
        trx.commit();
}
}
