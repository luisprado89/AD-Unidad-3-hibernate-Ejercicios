# Ejercicio 301 – Acceso a datos con Hibernate (AD)



**Unidad 3 – Administración de Datos (AD)**

**Enunciado**   
Ejercicio 301

Desarrolla en Java un programa que utilizando Hibernate permita crear una base de datos llamada `empleados` la cual permita representar los empleados (Emp) que hay en un departamento (Depto) sabiendo que:

-un departamento está formado por varios empleados  
- un empleado solo puede estar en un departamento.

Además, para la tabla de departamentos (`Depto`) se quiere almacenar:

- id de departamento que será auto incremental  
- nombre del departamento  
- localidad

Por otro lado, para la tabla de empleados (`Emp`) se quiere almacenar:

- id del empleado que será auto incremental  
- nombre  
- puesto  
- sueldo  
- edad  
- DNI  
- esJefe

Crea una clase `App` que permita crear 5 departamentos y 10 empleados de tal forma que cada departamento tenga 2 empleados.



---

## Descripción

En este ejercicio debes desarrollar un programa en **Java** que utilice **Hibernate** para crear y persistir dos tablas relacionadas en **MySQL**:

1. **Departamento**
    - `id` (auto incremental)
    - `nombre` (String)
    - `localidad` (String)

2. **Empleado**
    - `id` (auto incremental)
    - `nombre` (String)
    - `puesto` (String)
    - `sueldo` (double)
    - `edad` (int)
    - `DNI` (String)
    - `esJefe` (boolean)
    - Relación: muchos **Empleados** en un **Departamento**, cada empleado pertenece a un único departamento.

Asimismo, deberás crear una clase de arranque que inserte **5 Departamentos** y **10 Empleados**, de modo que cada departamento tenga exactamente **2 empleados** :contentReference[oaicite:0]{index=0}.

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


import com.AD.U3.entities.Emp;
import org.hibernate.Session;
import org.hibernate.Transaction;


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
}

```
**Repository/DptoRepositorio.java**
```java 
package com.AD.U3.repository;

import com.AD.U3.entities.Dpto;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class DptoRepositorio implements Repositorio<Dpto>{

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
}

```
**App.java**
```java 
package com.AD.U3;

import java.util.ArrayList;

import com.AD.U3.entities.Dpto;
import com.AD.U3.entities.Emp;
import com.AD.U3.repository.DptoRepositorio;
import org.hibernate.Session;


public class App {

    public static void main(String[] args) {
        System.out.println("Test");

        Session session = HibernateUtil.get().openSession();

        DptoRepositorio dptoRepositorio = new DptoRepositorio(session);

        Dpto dpto1 = new Dpto("Depto 1", "Vigo");
        Dpto dpto2 = new Dpto("Depto 2", "Vigo");
        Dpto dpto3 = new Dpto("Depto 3", "Santiago");
        Dpto dpto4 = new Dpto("Depto 4", "Santiago");
        Dpto dpto5 = new Dpto("Depto 5", "Ourense");

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

        dptoRepositorio.guardar(dpto1);
        dptoRepositorio.guardar(dpto2);
        dptoRepositorio.guardar(dpto3);
        dptoRepositorio.guardar(dpto4);
        dptoRepositorio.guardar(dpto5);

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
        <property name="hbm2ddl.auto">create</property>
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
