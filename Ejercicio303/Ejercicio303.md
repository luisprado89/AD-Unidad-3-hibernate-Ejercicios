# Ejercicio 303 – Acceso a datos con Hibernate (AD)



**Unidad 3 – Administración de Datos (AD)**

**Enunciado**   
Ejercicio 303

Realiza las modificaciones necesarias en el código del ejercicio anterior para crear los siguientes métodos:
- `empleadosTécnicos()`: permite recuperar el número y nombre de los empleados que son Técnicos.

- `empleadoConMayorSueldo()`: devuelve el empleado que tenga mayor sueldo.

- `aumentarSalarioJefes(int cantidad)`: permite aumentar el sueldo de todos los jefes una determinada cantidad.

- `borrarEmpleadosDepartamento(int idDepartamento)`: elimina los empleados de un determinado departamento.



---

## Estructura del proyecto

```text
Ejercicio301/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.AD.U3/
│   │   │       ├── entities/
│   │   │       │   ├── Dpto.java
│   │   │       │   └── Emp.java
│   │   │       ├── repository/
│   │   │       │   ├── Repositorio.java
│   │   │       │   ├── DptoRepositorio.java
│   │   │       │   └── EmpRepositorio.java
│   │   │       ├── App.java
│   │   │       └── HibernateUtil.java
│   │   └── resources/
│   │       └── hibernate.cfg.xml
│   └── test/
├── target/
│   ├── classes/
│   └── generated-sources/
├── Ejercicio301.md
└── pom.xml
```
### Solucion

**Entities/Dpto.java**
```java 
package com.AD.U3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//Marca esta clase como una entidad
@Entity
//Especificamos el nombre de la tabla en la base ded atos asociada con esta entidad
@Table(name = "dptos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dpto {
    @Id
    //Congfiguramos la estrategia de generacion automatica del valor de la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Asociamos este campo con una columna de la tabla de la base ded atos llamada dpto_id
    @Column(name = "dpto_id")
    private int id;

    private String nombre;
    private String localidad;
    //Definimos una relacion de uno a muchos con la entidad "Emp"
    @OneToMany(
            mappedBy = "dptoElement",
            cascade = CascadeType.ALL
    )
    private List<Emp> empts = new ArrayList<>();//Lista de empleados asociados

    public Dpto(String nombre, String localidad) {
        this.nombre = nombre;
        this.localidad = localidad;
    }

    public void addEmps(Emp emp){
        this.empts.add(emp);
        emp.setDptoElement(this);
    }
}

```
**Entities/Emp.java**
```java 
package com.AD.U3.entities;


//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emps")
@Data
@NoArgsConstructor

public class Emp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_emp")
  private long id;
  private String nombre;
  private String puesto;
  private double sueldo;
  private int edad;
  private String DNI;
  private boolean esJefe;


  @ManyToOne(
          cascade = CascadeType.ALL,
          fetch = FetchType.EAGER
  )
  @JoinColumn(name = "dpto_id")
  private Dpto dptoElement;

  public Emp(String nombre, String puesto, double sueldo, int edad, String DNI, boolean esJefe) {
    this.nombre = nombre;
    this.puesto = puesto;
    this.sueldo = sueldo;
    this.edad = edad;
    this.DNI = DNI;
    this.esJefe = esJefe;
  }

  public void setDptoElement(Dpto dptoElement) {
    this.dptoElement = dptoElement;
  }

  @Override
  public String toString() {
    return "Emp{" +
            "nombre='" + nombre + '\'' +
            ", puesto='" + puesto + '\'' +
            ", sueldo=" + sueldo +
            ", edad=" + edad +
            ", DNI='" + DNI + '\'' +
            ", esJefe=" + esJefe +
            '}';
  }


}


```
**Repository/Repositorio.java**
```java 
package com.AD.U3.repository;

public interface Repositorio<T> {
    void guardar(T t);
}

```
**Repository/EmpRepositorio.java**
```java 
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


```
**Repository/DptoRepositorio.java**
```java 
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

```
**App.java**
```java 
package com.AD.U3;

import com.AD.U3.entities.Dpto;
import com.AD.U3.entities.Emp;
import com.AD.U3.repository.DptoRepositorio;
import com.AD.U3.repository.EmpRepositorio;
import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    System.out.println("Test");

    Session session = HibernateUtil.get().openSession();

    DptoRepositorio dptoRepositorio = new DptoRepositorio(session);
    EmpRepositorio empRepositorio = new EmpRepositorio(session);
    //Creacion de datos del ejercicio 301
    // Crea objetos de tipo "Dpto" que representan departamentos, con un nombre y una localidad.
    Dpto dpto1 = new Dpto("Depto 1", "Vigo");
    Dpto dpto2 = new Dpto("Depto 2", "Vigo");
    Dpto dpto3 = new Dpto("Depto 3", "Santiago");
    Dpto dpto4 = new Dpto("Depto 4", "Santiago");
    Dpto dpto5 = new Dpto("Depto 5", "Ourense");
    // Crea objetos de tipo "Emp" que representan empleados, con sus atributos (nombre, puesto, sueldo, etc.).

    Emp emp1 = new Emp("Emp 1", "Ingeniero", 3000, 45, "12345678A", true);
    Emp emp2 = new Emp("Emp 2", "Técnico", 1000, 18, "12345678B", false);
    Emp emp3 = new Emp("Emp 3", "Ingeniero", 4000, 50, "12345678C", true);
    Emp emp4 = new Emp("Emp 4", "Técnico", 1500, 30, "12345678D", false);
    Emp emp5 = new Emp("Emp 5", "Ingeniero", 2000, 35, "12345678E", false);
    Emp emp6 = new Emp("Emp 6", "Técnico", 1250, 20, "12345678F", false);
    Emp emp7 = new Emp("Emp 7", "Ingeniero", 2500, 40, "12345678G", false);
    Emp emp8 = new Emp("Emp 8", "Técnico", 1300, 22, "12345678H", false);
    Emp emp9 = new Emp("Emp 9", "Ingeniero", 2750, 43, "12345678I", false);
    Emp emp10 = new Emp("Emp 10", "Técnico", 1450, 25, "12345678J", false);

    // Asocia empleados a los departamentos correspondientes utilizando el método "addEmps".
    // Esto también establece la relación bidireccional entre los empleados y los departamentos.
    dpto1.addEmps(emp1);
    dpto1.addEmps(emp2);
    dpto2.addEmps(emp3);
    dpto2.addEmps(emp4);
    dpto3.addEmps(emp5);
    dpto3.addEmps(emp6);
    dpto4.addEmps(emp7);
    dpto4.addEmps(emp8);
    dpto5.addEmps(emp9);
    dpto5.addEmps(emp10);

    // Guarda cada departamento (y sus empleados asociados) en la base de datos.
    // Gracias a la configuración de cascade, los empleados también se guardarán automáticamente.
    dptoRepositorio.guardar(dpto1);
    dptoRepositorio.guardar(dpto2);
    dptoRepositorio.guardar(dpto3);
    dptoRepositorio.guardar(dpto4);
    dptoRepositorio.guardar(dpto5);



    dptoRepositorio.visualizarDepartamento(2);
    Emp empleadoEmp = new Emp("ANTONIO","INVESTIGADOR",3500,45,"12345678a",false);
    empRepositorio.añadirEmpleado(1,empleadoEmp);
    empRepositorio.actualizarJefeDepartamento(2,4);
    dptoRepositorio.eliminarDepartamento(4);

    empRepositorio.empleadosTecnicos();

    empRepositorio.empleadoConMayorSueldo();

    empRepositorio.aumentarSalarioJefes(2000.0);

    empRepositorio.borrarEmpleadosDepartamento(4);


    session.close();
    System.out.println("Finalizando la conexion a MySQL");
  }
}

```
**HibernateUtil.java**
```java 
package com.AD.U3;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
//            String hibernatePropsFilePath = "src/main/resources/hibernate.cfg.xml"; // Ruta al fichero
//
//            File hibernatePropsFile = new File(hibernatePropsFilePath);
//
//            SESSION_FACTORY = new Configuration().configure(hibernatePropsFile).buildSessionFactory();
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        }catch(Throwable ex) {
            System.err.println("Error al crear la configuración de hibernate" + ex.getMessage());
            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory get() {
        return SESSION_FACTORY;
    }
}
```
**hibernate.cfg.xml**
```xml 
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN" "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
   <session-factory>
      <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
      <property name="connection.url">jdbc:mysql://localhost:3306/empleados?serverTimezone=UTC</property>
      <property name="connection.username">root</property>
      <property name="connection.password">abc123.</property>
      <property name="hbm2ddl.auto">validate</property>
      <!--        <property name="dialect">org.hibernate.dialect.MySQL57Dialect</property>-->
      <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>

      <property name="hibernate.dialect.storage_engine">innodb</property>
      <property name="hibernate.show_sql">true</property>

      <!--  mapping de las clases-->
      <!--        <mapping class="entities.Dpto"/>-->
      <!--        <mapping class="entities.Emp"/>-->
      <mapping class="com.AD.U3.entities.Dpto"/>
      <mapping class="com.AD.U3.entities.Emp"/>
   </session-factory>
</hibernate-configuration>
```
