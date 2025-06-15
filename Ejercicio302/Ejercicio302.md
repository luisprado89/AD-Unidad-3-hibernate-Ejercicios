# Ejercicio 302 – Acceso a datos con Hibernate (AD)



**Unidad 3 – Administración de Datos (AD)**

**Enunciado**   
Ejercicio 302


Realiza las modificaciones necesarias en el código del ejercicio anterior para crear los siguientes métodos:

- `visualizarDepartamento(id)`: permite visualizar la información de un determinado departamento así como el número y nombre de los empleados adscritos.

- `añadirEmpleado(idDepartamento, empleado)`: permite añadir un determinado departamento un nuevo empleado.
  - Prueba a añadir al `departamento` 10 un nuevo `empleado` que tenga los siguientes datos:
    - Nombre: ANTONIO
    - Puesto: INVESTIGADOR
    - Sueldo: 3500€
    - Edad: 45
    - DNI: 12345678A

- `actualizarJefeDepartamento(idDepart, idEmpl)`: permite actualizar qué empleado es el jefe de un determinado departamento
  - Prueba a cambiar el jefe de un determinado departamento

- `eliminarDepartamento(id)`: permite eliminar un determinado departamento.
  - Prueba a eliminar el departamento 4.

---


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


import java.util.ArrayList;
import java.util.List;

//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
import jakarta.persistence.*;
@Entity
@Table(name="dptos")
public class Dpto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dpto_id")
    private int id;

    private String nombre;
    private String localidad;

    @OneToMany(mappedBy = "dptoElement", cascade = CascadeType.ALL)
    private List<Emp> empts;

    public Dpto() {

    }

    public Dpto(String nombre, String localidad) {
        super();
        this.nombre = nombre;
        this.localidad = localidad;
        this.empts = new ArrayList<Emp>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setEmpts(ArrayList<Emp> lista) {
        this.empts = lista;
    }

    public void addEmps(Emp emp) {
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
@Entity
@Table(name = "emps")
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


   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "dpto_id")
   private Dpto dptoElement;



   public Emp() {
      super();
   }

   public Emp(String nombre, String puesto, double sueldo, int edad, String dNI, boolean esJefe) {
      super();
      this.nombre = nombre;
      this.puesto = puesto;
      this.sueldo = sueldo;
      this.edad = edad;
      DNI = dNI;
      this.esJefe = esJefe;
   }

   public long getId() {
      return id;
   }
   public void setId(long id) {
      this.id = id;
   }
   public String getNombre() {
      return nombre;
   }
   public void setNombre(String nombre) {
      this.nombre = nombre;
   }
   public String getPuesto() {
      return puesto;
   }
   public void setPuesto(String puesto) {
      this.puesto = puesto;
   }
   public double getSueldo() {
      return sueldo;
   }
   public void setSueldo(double sueldo) {
      this.sueldo = sueldo;
   }
   public int getEdad() {
      return edad;
   }
   public void setEdad(int edad) {
      this.edad = edad;
   }
   public String getDNI() {
      return DNI;
   }
   public void setDNI(String dNI) {
      DNI = dNI;
   }
   public boolean isEsJefe() {
      return esJefe;
   }
   public void setEsJefe(boolean esJefe) {
      this.esJefe = esJefe;
   }

   public Dpto getDptoElement() {
      return dptoElement;
   }

   public void setDptoElement(Dpto dptoElement) {
      this.dptoElement = dptoElement;
   }
   @Override
   public String toString() {
      // TODO Auto-generated method stub
      return "{Nombre: "+ this.nombre +", Puesto: " + this.puesto+
              ", Edad: " + this.edad + ", Salario: " + this.sueldo +
              ", DNI: " + this.DNI + ", esJefe: " + (this.esJefe ? "Si": "No" ) + "}";
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

```
**Repository/DptoRepositorio.java**
```java 
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

```
**App.java**
```java 
package com.AD.U3;

import com.AD.U3.entities.Emp;
import com.AD.U3.repository.DptoRepositorio;
import com.AD.U3.repository.EmpRepositorio;
import org.hibernate.Session;

//Para poder ejecutar esta app primero hay que ejecutar el Ejercicio301 quien crea la base de datos, inserta los datos
public class App {

   public static void main(String[] args) {
      System.out.println("Test");

      Session session = HibernateUtil.get().openSession();

      DptoRepositorio dptoRepositorio = new DptoRepositorio(session);
      EmpRepositorio empRepositorio = new EmpRepositorio(session);

      //Creación de datos del ejercicio 301

      dptoRepositorio.visualizarDepartamento(2);

      Emp empleadoEmp = new Emp("ANTONIO", "INVESTIGADOR", 3500, 45, "12345678A", false);
      empRepositorio.añadirEmpleado(1, empleadoEmp);
      empRepositorio.actualizarJefeDepartamento(2, 4);

      dptoRepositorio.eliminarDepartamento(4);

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
