# Ejercicio 309 – Acceso a datos con Hibernate (AD)



**Unidad 3 – Acceso a Datos (AD)**

**Enunciado**   
Ejercicio 309

Una empresa que se dedica a programar **Programación.S.A** ,recibe el encargo de un proyecto para informatizar un conjunto de empresas.

Realizar en Java con hibernate un programa que desarrolle **CRUD** (altas, bajas, modificaciones, consultas) de sus `empleados` y de los `productos` que comercializan, los empleados pueden ser fijos ó temporales ,tienen una relación de herencia con una clase abstracta llamada Empleados, cada empleado solo puede trabajar en una empresa.

La clase `Empleados` será abstracta y tiene una relación de herencia con `empleadosFijos` y `Temporales`, de estos se registrarán todas las ventas efectuadas durante su período de contrato..

Disponemos de los siguientes POJOs:

 - `Empresas`
   - string `cif`
   - string `nombre`
   - string `dirección`
   - string `teléfono`
   - Set<Productos>
   - Set<Empleados>

- `Productos`
  - String `codigo`
  - int `StockActualAlmacén`
  - final int `stockMínimo` = 30
  - float `precioUnitario`
  - En la tabla clave foránea es el `cif` de la empresa.

- `Empleados`
  - string `dni`, en la tabla clave primaria.
  - string `nombre`.
  - String `teléfono`.cif
  - float `porcentaRetención.
  - Tendrá un método abstracto `calculoNómina()`.
  - En la tabla clave foránea es el `cif` de la empresa.

- `Fijos`
  - int `salarioBase`.
  - int `trienios`.
  - En la tabla la clave primaria y foránea es el dni del empleado
  - Se redefine por polimorfismo: calculoNómina()
  - float sueldo = (salarioBase + trienios) * (1 - porcentaRetencion).

- `Temporales`
  - en la tabla la clave primaria y foránea es el dni del empleado
  - date `fechaInicio`
  - date `fechaFin`
  - float `pagoDia`
  - Se redefine por polimorfismo: `calculoNómina()`
  - float sueldo = PagoDia * (fechaFin - fechaInicio) * ( 1 - porcentaRetencion ) + suplemento.
  - Set<Ventas>,en la tabla no se pone el set<>.

- Ventas
  - date `fechaVenta`. (La fecha y la hora introducirla del sistema dentro del constructor)
  - Time `hora`.
  - String `CódigoArtículo`.
  - int `númeroUnidades`.
  - float `importe`.
  - En la tabla (CódigoArtículo+Empleado+fechaVenta+hora) es la clave primaria compuesta
  - En la tabla el dni del empleado es la clave foránea.

Se tendrá que implementar el siguiente Menú:

   1. Altas
      1. Empresas
      2. Empleados a. Fijos b. Temporales
      3. Productos.
      4. Ventas

   2. Bajas
      1. Empleados
      2. Fijos
      3. Temporales.

   3. Modificaciones
      1. Precio de un producto
      2. Sueldo base empleadoFijo.
      3. PorcentaRetención.
      4. ImporteDía empleado Temporal.

   4. Consultas
      1. Listado de las empresas con todos sus empleados fijos y temporales.
      2. Listado de los productos de una empresa determinada.

> **ℹ️ INFORMACIÓN**  
> Antes de realizar una `venta`, tenemos que comprobar:
>
> - Que las existencias actuales son superiores a las que nos solicitan.
> - Que la diferencia entre las existencias actuales menos las solicitadas no sea inferior a las existencias mínimas que debemos tener en el almacén:
    >   - Si son inferiores, emitir el mensaje **Existencias insuficientes**.
>   - En caso contrario, antes de realizar el alta de la venta (`objetoVenta`), habrá que modificar el stock actual en el producto (lo que sería una modificación) y luego dar de alta la venta.
> - En la tabla de `Empleados`, si ponemos la clave primaria como autoincrement, podemos definir además una clave alternativa única de tipo:
    
>   UNIQUE INDEX U_DNI(dni);   




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
│   │   │       │   ├── Empleado.java
│   │   │       │   └── Empresa.java
│   │   │       │   └── Fijo.java
│   │   │       │   └── Producto.java
│   │   │       │   └── Temporal.java
│   │   │       │   └── Venta.java
│   │   │       ├── operaciones/
│   │   │       │   ├── Altas.java
│   │   │       │   ├── Bajas.java
│   │   │       │   ├── Cadenas.java
│   │   │       │   ├── Consultas.java
│   │   │       │   ├── ControlData.java
│   │   │       │   ├── Modificaciones.java
│   │   │       │   ├── Sesiones.java
│   │   │       ├── App.java
│   │   │       └── HibernateUtil.java
│   │   └── resources/
│   │       └── hibernate.cfg.xml
│   └── test/
├── target/
│   ├── classes/
│   └── generated-sources/
├── Ejercicio309.md
└── pom.xml
```
### Solucion

**Entities/Empleado.java**
```java 
package com.AD.U3.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="EMPLEADOS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Empleado implements Serializable{

   @Id
   @Column(name="DNI",unique=true,nullable=false)
   private String dni;

   @Column(name="NOMBRE")
   private String nombre;

   @Column(name="TELEFONO")
   private String telefono;

   @Column(name="PORCENTAJE_RETENCION")
   private float porcentajeRetencion;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "CIF_EMPRESA")
   private Empresa empresa;

   public Empleado() {
   }

   public Empleado(String dni, String nombre, String telefono, float porcentajeRetencion) {
      this.dni = dni;
      this.nombre = nombre;
      this.telefono = telefono;
      this.porcentajeRetencion = porcentajeRetencion;
   }

   public String getDni() {
      return dni;
   }

   public void setDni(String dni) {
      this.dni = dni;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getTelefono() {
      return telefono;
   }

   public void setTelefono(String telefono) {
      this.telefono = telefono;
   }

   public float getPorcentajeRetencion() {
      return porcentajeRetencion;
   }

   public void setPorcentajeRetencion(float porcentajeRetencion) {
      this.porcentajeRetencion = porcentajeRetencion;
   }

   public Empresa getEmpresa() {
      return empresa;
   }

   public void setEmpresa(Empresa empresa) {
      this.empresa = empresa;
   }

   public void calcularNomina() {}

}


```
**Entities/Empresa.java**
```java 
package com.AD.U3.entities;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "EMPRESAS")
@NamedQueries({
        @NamedQuery(name="empleadosEmpresa", query="select empleados from Empresa where cif=:cif"),
        @NamedQuery(name="productosEmpresa", query="select productos from Empresa where cif=:cif"),
        @NamedQuery(name="empresas", query="from Empresa")
})
public class Empresa implements Serializable {

   @Id
   @Column(name = "CIF", unique = true, nullable = false)
   private String cif;

   @Column(name = "NOMBRE")
   private String nombre;

   @Column(name = "DIRECCION")
   private String direccion;

   @Column(name = "TELEFONO")
   private String telefono;

   @OneToMany(mappedBy = "empresa")
   private Set<Producto> productos;

   @OneToMany(mappedBy = "empresa")
   private Set<Empleado> empleados;

   public Empresa() {
   }

   public Empresa(String cif) {
      this.cif = cif;
   }

   public Empresa(String cif, String nombre, String direccion, String telefono) {
      this.cif = cif;
      this.nombre = nombre;
      this.direccion = direccion;
      this.telefono = telefono;
   }

   public String getCif() {
      return cif;
   }

   public void setCif(String cif) {
      this.cif = cif;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getDireccion() {
      return direccion;
   }

   public void setDireccion(String direccion) {
      this.direccion = direccion;
   }

   public String getTelefono() {
      return telefono;
   }

   public void setTelefono(String telefono) {
      this.telefono = telefono;
   }

   public Set<Empleado> getEmpleados() {
      return empleados;
   }

   public void setEmpleados(Set<Empleado> empleados) {
      this.empleados = empleados;
   }

   @Override
   public String toString() {
      String empresa = "CIF: " + getCif() + "\n" + "NOMBRE: " + getNombre() + "\n"
              + "DIRECCIÓN: " + getDireccion() + "\n" + "TELÉFONO: " + getTelefono() + "\n";
      return empresa;
   }
}


```

**Entities/Fijo.java**
```java 
package com.AD.U3.entities;

import java.io.Serializable;
import jakarta.persistence.*;
@Entity
@PrimaryKeyJoinColumn(name = "DNI_EMPLEADO")
@Table(name = "EMPLEADOS_FIJOS")
public class Fijo extends Empleado implements Serializable {

   @Column(name = "SALARIO_BASE")
   private int salarioBase;

   @Column(name = "TRIENIOS")
   private int trienios;

   @Column(name = "SUELDO")
   private float sueldo;

   public Fijo() {
      super();
   }

   public Fijo(String dni, String nombre, String telefono, float porcentajeRetencion, int salarioBase, int trienios) {
      super(dni, nombre, telefono, porcentajeRetencion);
      this.salarioBase = salarioBase;
      this.trienios = trienios;
   }

   public int getSalarioBase() {
      return salarioBase;
   }

   public void setSalarioBase(int salarioBase) {
      this.salarioBase = salarioBase;
   }

   public int getTrienios() {
      return trienios;
   }

   public void setTrienios(int trienios) {
      this.trienios = trienios;
   }

   public float getSueldo() {
      return sueldo;
   }

   @Override
   public void calcularNomina() {
      sueldo = (salarioBase + trienios) * (1 - getPorcentajeRetencion());
   }

   @Override
   public String toString() {
      String fijo = "DNI: " + super.getDni() + "\n"
              + "NOMBRE: " + super.getNombre() + "\n" + "TELÉFONO: " + super.getTelefono() + "\n"
              + "PORCENTAJE DE RENTENCIÓN: " + super.getPorcentajeRetencion() + "\n"
              + "SALARIO BASE: " + getSalarioBase() + "\n" + "TRIENIOS: " + getTrienios() + "\n"
              + "SULEDO: " + getSueldo() + "\n";
      return fijo;
   }

}


```

**Entities/Producto.java**
```java 
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AD.U3.entities;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.*;
/**
 *
 * @author Estefania
 */
@Entity
@Table(name="PRODUCTOS")
public class Producto implements Serializable{

   @Id
   @Column(name="CODIGO", unique=true, nullable=false)
   private String codigo;

   @Column(name="STOCK_ALMACEN")
   private int stockActualAlmacen;

   @Column(name="STOCK_MINIMO")
   private final int STOCK_MINIMO = 30;

   @Column(name="PRECIO_UNITARIO")
   private float precioUnitario;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "CIF_EMPRESA")
   private Empresa empresa;

   @OneToMany(mappedBy="codigoArticulo")
   private Set<Venta> ventas;

   public Producto() {

   }

   public Producto(String codigo, int stockAlmacen, float precioUnitario) {
      this.codigo = codigo;
      this.stockActualAlmacen = stockAlmacen;
      this.precioUnitario = precioUnitario;
   }

   /**
    * @return the codigo
    */
   public String getCodigo() {
      return codigo;
   }

   /**
    * @param codigo the codigo to set
    */
   public void setCodigo(String codigo) {
      this.codigo = codigo;
   }

   /**
    * @return the stockActualAlmacen
    */
   public int getStockActualAlmacen() {
      return stockActualAlmacen;
   }

   /**
    * @param stockActualAlmacen the stockActualAlmacen to set
    */
   public void setStockActualAlmacen(int stockActualAlmacen) {
      this.stockActualAlmacen = stockActualAlmacen;
   }

   /**
    * @return the STOCK_MINIMO
    */
   public int getSTOCK_MINIMO() {
      return STOCK_MINIMO;
   }

   /**
    * @return the precioUnitario
    */
   public float getPrecioUnitario() {
      return precioUnitario;
   }

   /**
    * @param precioUnitario the precioUnitario to set
    */
   public void setPrecioUnitario(float precioUnitario) {
      this.precioUnitario = precioUnitario;
   }

   /**
    * @return the empresa
    */
   public Empresa getEmpresa() {
      return empresa;
   }

   /**
    * @param empresa the empresa to set
    */
   public void setEmpresa(Empresa empresa) {
      this.empresa = empresa;
   }

   /**
    * @return the ventas
    */
   public Set<Venta> getVentas() {
      return ventas;
   }

   /**
    * @param ventas the ventas to set
    */
   public void setVentas(Set<Venta> ventas) {
      this.ventas = ventas;
   }

   @Override
   public String toString() {
      String producto = "CÓDIGO: " + codigo + "\n"
              + "STOCK ALMACÉN: " + stockActualAlmacen + "\n"
              + "STOCK MÍNIMO: " + STOCK_MINIMO + "\n"
              + "PRECIO UNITARIO: " + precioUnitario + "\n";

      return producto;
   }

}

```

**Entities/Temporal.java**
```java 
package com.AD.U3.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "DNI_EMPLEADO")
@Table(name = "EMPLEADOS_TEMPORALES")
public class Temporal extends Empleado implements Serializable {

   @Column(name = "FECHA_INICIO")
   private Date fechaInicio;

   @Column(name = "FECHA_FIN")
   private Date fechaFin;

   @Column(name = "PAGO_DIA")
   private float pagoDia;

   @Column(name = "SUPLEMENTO")
   private float suplemento;

   @Column(name = "SULEDO")
   private float sueldo;

   @OneToMany(mappedBy = "dniEmpleado")
   private Set<Venta> ventas;

   public Temporal() {
      super();
   }

   public Temporal(String dni, String nombre, String telefono, float porcentajeRetencion, Date fechaInicio, Date fechaFin, float pagoDia) {
      super(dni, nombre, telefono, porcentajeRetencion);
      this.fechaInicio = fechaInicio;
      this.fechaFin = fechaFin;
      this.pagoDia = pagoDia;
   }

   public Date getFechaInicio() {
      return fechaInicio;
   }

   public void setFechaInicio(Date fechaInicio) {
      this.fechaInicio = fechaInicio;
   }

   public Date getFechaFin() {
      return fechaFin;
   }

   public void setFechaFin(Date fechaFin) {
      this.fechaFin = fechaFin;
   }

   public float getPagoDia() {
      return pagoDia;
   }

   public void setPagoDia(float pagoDia) {
      this.pagoDia = pagoDia;
   }

   public float getSueldo() {
      return sueldo;
   }

   public float getSuplemento() {
      return suplemento;
   }

   public void setSuplemento(float suplemento) {
      this.suplemento = suplemento;
   }

   @Override
   public void calcularNomina() {
      sueldo = pagoDia * ((fechaFin.getTime() - fechaInicio.getTime()) / (1000 * 60 * 60 * 24)) *
              ( 1 - getPorcentajeRetencion()) + getSuplemento();
   }

   @Override
   public String toString() {
      String temporal = "DNI: " + super.getDni() + "\n"
              + "NOMBRE: " + super.getNombre() + "\n"
              + "TELÉFONO: " + super.getTelefono() + "\n"
              + "PORCENTAJE DE RENTENCIÓN: " + super.getPorcentajeRetencion() + "\n"
              + "FECHA DE INICIO: " + getFechaInicio() + "\n"
              + "FECHA DE FIN: " + getFechaFin() + "\n"
              + "PAGO DÍA: " + getPagoDia() + "\n"
              + "SUPLEMENTO: " + getSuplemento() + "\n"
              + "SUELDO: " + getSueldo() + "\n";
      return temporal;
   }

}

```

**operaciones/Altas.java**
```java 
package com.AD.U3.operaciones;

import com.AD.U3.entities.*;
import org.hibernate.Transaction;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;
import java.util.Objects;
import java.util.Scanner;
import org.hibernate.Session;

public class Altas {

   Session session;
   Sesiones sesiones;

   public Altas(Session session) {
      this.session = session;
      sesiones = new Sesiones(session);
   }

   public void alta(Scanner input) {
      byte op = 0;
      do {
         Cadenas.menuAltas();
         op = ControlData.leerByte(input);
         switch (op) {
            case 1:
               empresa(input);
               break;
            case 2:
               empleado(input);
               break;
            case 3:
               producto(input);
               break;
            case 4:
               venta(input);
               break;
            case 0:
               Cadenas.vueltaAnteriorMenu();
               break;
            default:
               Cadenas.mesajeDefaultMenu();
               break;
         }
      } while (op != 0);

   }

   private void empresa(Scanner input) {

      System.out.println("\nNUEVA EMPRESA");

      System.out.println("CIF");
      String cif = ControlData.leerString(input);

      Transaction trx = this.session.beginTransaction();
      Empresa empresa = (Empresa) session.get(Empresa.class, cif);
      trx.commit();

      if (!Objects.isNull(empresa)) {
         System.out.println("\nNO ES POSIBLE AÑADIR LA EMPRESA " + cif + ". YA ESTÁ REGISTARDA.");
         System.out.println("\n" + empresa);

      } else {

         System.out.println("NOMBRE:");
         String nombre = ControlData.leerString(input);

         System.out.println("DIRECCIÓN:");
         String direccion = ControlData.leerString(input);

         System.out.println("TELEFONO:");
         String telefono = ControlData.leerString(input);

         empresa = new Empresa(cif, nombre, direccion, telefono);

         this.sesiones.guardar(empresa);

         System.out.println("\nEMPRESA AÑADIDA");

      }
   }

   private void empleado(Scanner input) {

      byte op = 0;
      do {
         System.out.println("-----------------\n" + "\t\tALTAS");
         Cadenas.menuEmpleados();
         op = ControlData.leerByte(input);
         switch (op) {
            case 1:
               fijo(input);
               break;
            case 2:
               temporal(input);
               break;
            case 0:
               Cadenas.vueltaAnteriorMenu();
               break;
            default:
               Cadenas.mesajeDefaultMenu();
               break;
         }
      } while (op != 0);
   }

   private void producto(Scanner input) {

      System.out.println("\nNUEVO PRODUCTO");

      System.out.println("CÓDIGO:");
      String codigo = ControlData.leerString(input);

      Transaction trxTransaction  = this.session.beginTransaction();
      Producto producto = (Producto) session.get(Producto.class, codigo);
      trxTransaction.commit();

      if (!Objects.isNull(producto)) {
         System.out.println("\nNO ES POSIBLE AÑADIR EL PRODUCTO " + codigo + ". YA ESTÁ REGISTARDO.");
         System.out.println("\n" + producto);

      } else {

         System.out.println("STOCK:");
         int stock = ControlData.leerInt(input);
         if (stock < 30) {
            System.out.println("AVISO: EL STOCK MÍNIMO PARA PODER REALIZAR VENTAS ES DE 30 UNIDADES.\nESTE TENDRÁ QUE SER MODIFICADO EN EL FUTURO.\n");
         }
         System.out.println("PRECIO UNITARIO:");
         float precio = ControlData.leerFloat(input);

         producto = new Producto(codigo, stock, precio);

         System.out.println("EMPRESA A LA QUE SE ASOCIARÁ EL PRODUCTO (CIF):");
         String cif = ControlData.leerString(input);

         trxTransaction = this.session.beginTransaction();
         Empresa empresa = (Empresa) session.get(Empresa.class, cif);
         trxTransaction.commit();

         if (Objects.isNull(empresa)) {
            System.out.println("\nLA EMPRESA NO EXISTE");
            byte op = 0;
            do {
               System.out.println("GUARDAR EL PRODUCTO SIN ASOCIARLO A NINGUNA EMPRESA:");
               System.out.println("1.-SI");
               System.out.println("2.-NO");
               op = ControlData.leerByte(input);
               switch (op) {
                  case 1:
                     this.sesiones.guardar(producto);
                     System.out.println("\nPRODUCTO AÑADIDO");
                     break;
                  case 2:
                     System.out.println("\nPRODUCTO NO AÑADIDO");
                     break;
                  default:
                     Cadenas.mesajeDefaultMenu();
                     break;
               }
            } while (op != 2 && op != 1);

         } else {
            producto.setEmpresa(empresa);
            this.sesiones.guardar(producto);
            System.out.println("\nPRODUCTO AÑADIDO");
         }

      }
   }

   private void venta(Scanner input) {

      System.out.println("\nNUEVA VENTA");

      System.out.println("CÓDIGO DEL PRODUCTO:");
      String codigo = ControlData.leerString(input);

      Transaction trxTransaction = this.session.beginTransaction();
      Producto producto = (Producto) session.get(Producto.class, codigo);
      trxTransaction.commit();

      if (Objects.isNull(producto)) {
         System.out.println("\nEL PRODUCTO " + codigo + " NO EXISTE.");
      } else {
         Empresa empresa = producto.getEmpresa();
         if (Objects.isNull(empresa)) {
            System.out.println("\nEL PRODUCTO " + codigo + " NO TIENE NINGUNA EMPRESA ASOCIDA. NO SE PUEDE VENDER.");
         } else {

            System.out.println("DNI DEL EMPLEADO:");
            String dni = ControlData.leerDni(input);

            trxTransaction = this.session.beginTransaction();
            Temporal empleadoTemporal = (Temporal) session.get(Temporal.class, dni);
            trxTransaction.commit();

            if (Objects.isNull(empleadoTemporal)) {
               System.out.println("\nEL EMPLEADO " + dni + " NO EXISTE.");
            } else {
               Empresa empresaEmpleado = empleadoTemporal.getEmpresa();
               if (Objects.isNull(empresaEmpleado) || !empresa.getCif().equals(empresaEmpleado.getCif())) {
                  System.out.println("\nEL EMPLEADO " + dni + " NO TRABAJA PARA LA EMPRESA QUE VENDE EL PRODUCTO " + codigo + ".\nNO SE PUEDE REALIZAR LA VENTA.");
               } else {

                  Date fechaVenta = new Date(Calendar.getInstance().getTime().getTime());

                  LocalTime now = LocalTime.now();
                  Time hora = Time.valueOf(now);

                  System.out.println("\nPRODUCTO\n" + producto);

                  System.out.println("NÚMERO DE UNIDADES:");
                  int unidades = ControlData.leerInt(input);

                  int unidadesDisponiblesVenta = producto.getStockActualAlmacen() - producto.getSTOCK_MINIMO();

                  if (unidades <= unidadesDisponiblesVenta) {
                     float importe = unidades * producto.getPrecioUnitario();

                     int unidadesRestantes = producto.getStockActualAlmacen() - unidades;
                     producto.setStockActualAlmacen(unidadesRestantes);

                     this.sesiones.actualizar(producto);

                     Venta venta = new Venta(producto, empleadoTemporal, fechaVenta, hora, unidades, importe);
                     this.sesiones.guardar(venta);
                     System.out.println("\nVENTA REALIZADA");

                  } else {
                     System.out.println("\nNO ES POSIBLE REALIZAR LA VENTA");
                     System.out.println("UNIDADES DISPONIBLES: " + unidadesDisponiblesVenta);

                  }
               }
            }

         }
      }
   }

   private void fijo(Scanner input) {

      System.out.println("NUEVO EMPLEADO FIJO");

      System.out.println("DNI:");
      String dni = ControlData.leerDni(input);

      Transaction trxTransaction = this.session.beginTransaction();
      Fijo empleadoFijo = (Fijo) session.get(Empleado.class, dni);
      trxTransaction.commit();

      if (!Objects.isNull(empleadoFijo)) {
         System.out.println("\nNO ES POSIBLE AÑADIR AL EMPLEADO " + dni + ". YA ESTÁ REGISTARDO.");
         System.out.println(empleadoFijo);

      } else {
         System.out.println("NOMBRE:");
         String nombre = ControlData.leerString(input);

         System.out.println("TELEFONO:");
         String telefono = ControlData.leerString(input);

         System.out.println("PORCENTAJE RETENCIÓN:");
         float retencion = ControlData.leerFloat(input);

         System.out.println("SALARIO BASE:");
         int salario = ControlData.leerInt(input);

         System.out.println("TRIENIOS:");
         int trienios = ControlData.leerInt(input);

         empleadoFijo = new Fijo(dni, nombre, telefono, retencion, salario, trienios);
         empleadoFijo.calcularNomina();

         System.out.println("EMPRESA PARA LA QUE TRABAJARÁ (CIF):");
         String cif = ControlData.leerString(input);

         trxTransaction = this.session.beginTransaction();
         Empresa empresa = (Empresa) session.get(Empresa.class, cif);
         trxTransaction.commit();

         if (Objects.isNull(empresa)) {
            System.out.println("LA EMPRESA NO EXISTE");
            byte op = 0;
            do {
               System.out.println("GUARDAR EL EMPLEADO SIN EMPRESA ASOCIADA:");
               System.out.println("1.-SI");
               System.out.println("2.-NO");
               op = ControlData.leerByte(input);
               switch (op) {
                  case 1:
                     this.sesiones.guardar(empleadoFijo);
                     System.out.println("\nEMPLEADO FIJO AÑADIDO");
                     break;
                  case 2:
                     System.out.println("\nEMPLEADO FIJO NO AÑADIDO");
                     break;
                  default:
                     Cadenas.mesajeDefaultMenu();
                     break;
               }
            } while (op != 2 && op != 1);

         } else {
            empleadoFijo.setEmpresa(empresa);
            this.sesiones.guardar(empleadoFijo);
            System.out.println("\nEMPLEADO FIJO AÑADIDO");
         }
      }
   }

   private void temporal(Scanner input) {

      System.out.println("NUEVO EMPLEADO TEMPORAL");

      System.out.println("DNI:");
      String dni = ControlData.leerDni(input);

      Transaction trxTransaction = this.session.beginTransaction();
      Temporal empleadoTemporal = (Temporal) session.get(Empleado.class, dni);
      trxTransaction.commit();

      if (!Objects.isNull(empleadoTemporal)) {
         System.out.println("\nNO ES POSIBLE AÑADIR AL EMPLEADO " + dni + ". YA ESTÁ REGISTARDO.");
         System.out.println(empleadoTemporal);

      } else {
         System.out.println("NOMBRE:");
         String nombre = ControlData.leerString(input);

         System.out.println("TELEFONO:");
         String telefono = ControlData.leerString(input);

         System.out.println("PORCENTAJE RETENCIÓN:");
         float retencion = ControlData.leerFloat(input);

         Date dateInicio;
         Date dateFin;
         do {
            System.out.println("FECHA DE INICIO (aaaa-mm-dd):");
            String fechaInicio = ControlData.leerFecha(input);
            dateInicio = Date.valueOf(fechaInicio);

            System.out.println("FECHA FIN (aaaa-mm-dd):");
            String fechaFin = ControlData.leerFecha(input);
            dateFin = Date.valueOf(fechaFin);

            if (dateInicio.after(dateFin)) {
               System.out.println("LA FECHA DE INICIO DEBE SER ANTERIOR A LA FECHA FIN\n");
            }

         } while (dateInicio.after(dateFin));

         System.out.println("PAGO POR DÍA:");
         float pago = ControlData.leerFloat(input);

         empleadoTemporal = new Temporal(dni, nombre, telefono, retencion, dateInicio, dateFin, pago);
         empleadoTemporal.calcularNomina();

         System.out.println("EMPRESA PARA LA QUE TRABAJARÁ (CIF):");
         String cif = ControlData.leerString(input);

         trxTransaction = this.session.beginTransaction();
         Empresa empresa = (Empresa) session.get(Empresa.class, cif);
         trxTransaction.commit();

         if (Objects.isNull(empresa)) {
            System.out.println("LA EMPRESA NO EXISTE");
            byte op = 0;
            do {
               System.out.println("GUARDAR EL EMPLEADO SIN EMPRESA ASOCIADA:");
               System.out.println("1.-SI");
               System.out.println("2.-NO");
               op = ControlData.leerByte(input);
               switch (op) {
                  case 1:
                     this.sesiones.guardar(empleadoTemporal);
                     System.out.println("\nEMPLEADO TEMPORAL AÑADIDO");
                     break;
                  case 2:
                     System.out.println("\nEMPLEADO TEMPORAL NO AÑADIDO");
                     break;
                  default:
                     Cadenas.mesajeDefaultMenu();
                     break;
               }
            } while (op != 2 && op != 1);

         } else {
            empleadoTemporal.setEmpresa(empresa);
            this.sesiones.guardar(empleadoTemporal);
            System.out.println("\nEMPLEADO TEMPORAL AÑADIDO");
         }
      }
   }

}

```

**operaciones/Bajas.java**
```java 
package com.AD.U3.operaciones;

import java.time.temporal.Temporal;
import java.util.Scanner;

import com.AD.U3.entities.Fijo;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class Bajas {

   Session session;

   public Bajas(Session session) {
      this.session = session;
   }

   public void baja(Scanner input) {
      byte op = 0;
      do {
         System.out.println("---------------------------------------------\n"
                 + "\t\tBAJAS\n\n");
         Cadenas.menuEmpleados();
         op = ControlData.leerByte(input);
         switch (op) {
            case 1:
               fijo(input);
               break;
            case 2:
               temporal(input);
               break;
            case 0:
               Cadenas.vueltaAnteriorMenu();
               break;
            default:
               Cadenas.mesajeDefaultMenu();
               break;
         }
      } while (op != 0);

   }

   private void fijo(Scanner input) {
      System.out.println("BAJA EMPLEADO FIJO");
      System.out.println("DNI:");
      String dni = ControlData.leerDni(input);


      Transaction trx = this.session.beginTransaction();

      Fijo emplFijo = (Fijo) session.get(Fijo.class, dni);
      trx.commit();

      if (emplFijo == null) {
         System.out.println("\nEL EMPLEADO FIJO " + dni + " NO EXISTE Y POR LO TANTO NO PUEDE SER BORRADO");
      } else {
         System.out.println("\n" + emplFijo);
         System.out.println("\nEL AUTOR, SUS DATOS Y SUS LIBROS SERÁN BORRADOS (SI ESTOS LIBROS TAMBIÉN TIENEN OTROS AUTORES ESTOS SERÁN BORRADOS CON TODOS SUS LIBROS)\n");
         this.session.delete(emplFijo);
      }

   }

   private void temporal(Scanner input) {
      System.out.println("BAJA EMPLEADO TEMPORAL");
      System.out.println("DNI:");
      String dni = ControlData.leerDni(input);


      Transaction trx = this.session.beginTransaction();

      Temporal emplTmp = (Temporal) session.get(Temporal.class, dni);
      trx.commit();

      if (emplTmp == null) {
         System.out.println("\nEL EMPLEADO FIJO " + dni + " NO EXISTE Y POR LO TANTO NO PUEDE SER BORRADO");
      } else {
         System.out.println("\n" + emplTmp);
         System.out.println("\nEL AUTOR, SUS DATOS Y SUS LIBROS SERÁN BORRADOS (SI ESTOS LIBROS TAMBIÉN TIENEN OTROS AUTORES ESTOS SERÁN BORRADOS CON TODOS SUS LIBROS)\n");
         this.session.delete(emplTmp);
      }
   }

}


```
**operaciones/Cadenas.java**
```java 
package com.AD.U3.operaciones;

public class Cadenas {

   public static void menuPrincipal() {
      System.out.println("----------------------------------------------------\n"
              + "\t\tPROGRAMACIÓN S.A.\n"
              + "1.-ALTAS\n"
              + "2.-BAJAS\n"
              + "3.-MODIFICACIONES\n"
              + "4.-CONSULTAS\n\n"
              + "0.-SALIR\n");
   }

   public static void menuAltas() {
      System.out.println("--------------------------------------\n"
              + "\t\tALTAS\n"
              + "1.-EMPRESAS\n"
              + "2.-EMPLEADOS\n"
              + "3.-PRODUCTOS\n"
              + "4.-VENTAS\n\n"
              + "0.-VOLVER AL MENÚ ANTERIOR");
   }

   public static void menuEmpleados() {
      System.out.println("\tEMPLEADOS\n"
              + "1.-FIJOS\n"
              + "2.-TEMPORALES\n\n"
              + "0.-VOLVER AL MENÚ ANTERIOR");
   }

   public static void menuModificaciones() {
      System.out.println("----------------------------------------\n"
              + "\t\tMODIFICACIONES\n"
              + "1.-PRECIO DE UN PRODUCTO\n"
              + "2.-SUELDO BASE EMPLEADO FIJO\n"
              + "3.-PORCENTAJE RETENCIÓN\n"
              + "4.-IMPORTE DÍA EMPLEADO TEMPORAL\n\n"
              + "0.-VOLVER AL MENÚ ANTERIOR");
   }

   public static void menuConsultas() {
      System.out.println("-----------------------------------------------------------------------------------------------------------------\n"
              + "\t\tCONSULTAS\n"
              + "1.-LISTADO de las EMPRESAS con todos sus EMPLEADOS FIJOS Y TEMPORALES\n"
              + "2.-LISTADO de los PRODUCTOS de una EMPRESA determinada\n"
              + "0.-VOLVER AL MENÚ ANTERIOR");
   }

   public static void fin() {
      System.out.println("PROGRAMA FINALIZADO");
   }

   public static void mesajeDefaultMenu() {
      System.out.println("No ha introducido ninguna de las opciones.");
   }

   public static void vueltaAnteriorMenu() {
      System.out.println("Volviendo al menú anterior...");
   }

}

```
**operaciones/Consultas.java**
```java 
package com.AD.U3.operaciones;


import java.util.List;
import java.util.Scanner;

import com.AD.U3.entities.Empleado;
import com.AD.U3.entities.Empresa;
import com.AD.U3.entities.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Consultas {

   Session session;

   public Consultas(Session session) {
      this.session = session;
   }

   public void consulta(Scanner input) {
      byte op = 0;
      do {
         Cadenas.menuConsultas();
         op = ControlData.leerByte(input);
         switch (op) {
            case 1:
               empresasEmpleados(input);
               break;
            case 2:
               productosEmpresa(input);
               break;
            case 0:
               Cadenas.vueltaAnteriorMenu();
               break;
            default:
               Cadenas.mesajeDefaultMenu();
               break;
         }
      } while (op != 0);

   }

   public void empresasEmpleados(Scanner input) {

      Transaction trx = this.session.beginTransaction();
      List<Empresa> totalEmp = session.createNamedQuery("empresas").list();

      for (Empresa empresa : totalEmp) {
         System.out.println("\tEMPRESA "+empresa);
         System.out.println("\tLSITA DE EMPLEADOS:");
         for (Empleado empleado : empresa.getEmpleados()) {
            System.out.println(empleado);
         }
         System.out.println("-------------------------");
      }

      trx.commit();
   }

   public void productosEmpresa(Scanner input) {

      System.out.println("CIF DE LA EMPRESA:");
      String cif = ControlData.leerString(input);

      Transaction trx = this.session.beginTransaction();

      System.out.println("\nLISTA DE PRODUCTOS DE LA EMPRESA:\n");
      List<Producto> productos = session.createNamedQuery("productosEmpresa").setParameter("cif", cif).list();
      for (Producto producto : productos) {
         System.out.println(producto);
      }

      trx.commit();
   }


}

```
**operaciones/ControlData.java**
```java 
package com.AD.U3.operaciones;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControlData {

   public static String leerCodigo(Scanner sc) {
      String codigo = null;
      boolean codigoValido = true;
      do {
         codigo = sc.next();
         Pattern pat = Pattern.compile("[0-9]{3}[A-Z]{1}");
         Matcher mat = pat.matcher(codigo);
         if (mat.matches()) {
            codigoValido = true;
         } else {
            System.out.println("ERROR. El código debe cumplir el formato: 3 dígitos seguido de una letra mayúscula.");
            sc.nextLine();
            codigoValido = false;
         }
      } while (!codigoValido);

      return codigo;

   }

   public static String leerFecha(Scanner sc) {

      String fecha = null;
      boolean fechaValida = true;
      do {
         fecha = sc.next();
         Pattern pat = Pattern.compile("[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}");
         Matcher mat = pat.matcher(fecha);
         if (mat.matches()) {
            int dia = Integer.parseInt(Character.toString(fecha.charAt(8)) + Character.toString(fecha.charAt(9)));
            int mes = Integer.parseInt(Character.toString(fecha.charAt(5)) + Character.toString(fecha.charAt(6)));
            int ano = Integer.parseInt(Character.toString(fecha.charAt(0)) + Character.toString(fecha.charAt(1)) + Character.toString(fecha.charAt(2)) + Character.toString(fecha.charAt(3)));
            if (mes > 12 || mes < 1) {
               fechaValida = false;
            } else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
               if (dia > 31 || dia < 1) {
                  fechaValida = false;
               } else {
                  fechaValida = true;
               }
            } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
               if (dia > 30 || dia < 1) {
                  fechaValida = false;
               } else {
                  fechaValida = true;
               }
            } else if (mes == 2) {
               if (((ano % 4 == 0) && ((ano % 100 != 0) || (ano % 400 == 0))) && (dia <= 29 && dia > 0)) {
                  fechaValida = true;
               } else if (dia <= 28 && dia > 0) {
                  fechaValida = true;
               } else {
                  fechaValida = false;
               }
            }

         } else {
            fechaValida = false;
         }
         if (!fechaValida) {
            System.out.println("ERROR. No ha introducido una fecha válida.\nEscriba otra fecha. Formato: yyyy-mm-dd");
            sc.nextLine();
         }

      } while (!fechaValida);

      return fecha;

   }

   public static String leerDni(Scanner sc) {

      char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
      String dni = null;
      boolean dniValido = true;
      do {
         dni = sc.next();
         dni = dni.toUpperCase();
         if (dni.length() != 9) {
            dniValido = false;
         } else {
            String dniNumero = "";
            for (int i = 0; i < 8; i++) {
               dniNumero = dniNumero + dni.charAt(i);
            }
            Pattern pat = Pattern.compile("[0-9]{8}");
            Matcher mat = pat.matcher(dniNumero);
            if (!mat.matches()) {
               dniValido = false;
            } else {
               int numero = Integer.parseInt(dniNumero);
               if (numero % 23 < 0 || numero % 23 > 22) {
                  dniValido = false;
               } else if (letras[numero % 23] == dni.charAt(8)) {
                  dniValido = true;
               }

            }

         }

         if (!dniValido) {
            System.out.println("ERROR. No ha introducido un DNI válido.");
            sc.nextLine();

         }
      } while (!dniValido);

      return dni;

   }

   public static boolean rango(int l1, int l2, int op) {
      boolean enrango = true;
      if (op < l1 || op > l2) {
         enrango = false;
         System.out.println("\tERROR: debe introducir un valor dentro do rango de números posibles. "
                 + "\n\t\tVuelva a introducir un número: \n");
      }
      return enrango;
   }

   public static byte leerByte(Scanner sc) {

      byte valor = 0;
      boolean correcto;

      do {
         correcto = true;
         try {
            valor = sc.nextByte();
         } catch (Exception e) {
            System.out.println("ERROR. No ha introducido un valor válido. Introduzca un número: ");
            sc.next();
            correcto = false;
         }
      } while (!correcto);

      return valor;

   }

   public static short leerShort(Scanner sc) {
      short valor = 0;
      boolean correcto;

      do {
         correcto = true;
         try {
            valor = sc.nextShort();
         } catch (Exception e) {
            System.out.println("ERROR. No ha introducido un valor válido. Introduzca un número: ");
            sc.next();
            correcto = false;
         }
      } while (!correcto);

      return valor;
   }

   public static int leerInt(Scanner sc) {
      int valor = 0;
      boolean correcto;

      do {
         correcto = true;
         try {
            valor = sc.nextInt();
         } catch (Exception e) {
            System.out.println("ERROR. No ha introducido un valor válido. Introduzca un número: ");
            sc.next();
            correcto = false;
         }
      } while (!correcto);

      return valor;
   }

   public static long leerLong(Scanner sc) {
      long valor = 0;
      boolean correcto;

      do {
         correcto = true;
         try {
            valor = sc.nextLong();
         } catch (Exception e) {
            System.out.println("ERROR. No ha introducido un valor válido. Introduzca otro número: ");
            sc.next();
            correcto = false;
         }
      } while (!correcto);

      return valor;
   }

   public static float leerFloat(Scanner sc) {
      float valor = 0;
      boolean correcto;

      do {
         correcto = true;
         try {
            valor = sc.nextFloat();
         } catch (Exception e) {
            System.out.println("ERROR. No ha introducido un valor válido. Introduzca otro número: ");
            sc.next();
            correcto = false;
         }
      } while (!correcto);

      return valor;
   }

   public static double leerDouble(Scanner sc) {
      double valor = 0;
      boolean correcto;

      do {
         correcto = true;
         try {
            valor = sc.nextDouble();
         } catch (Exception e) {
            System.out.println("ERROR. No ha introducido un valor válido. Introduzca otro número: ");
            sc.next();
            correcto = false;
         }
      } while (!correcto);

      return valor;
   }

   public static boolean leerBoolean(Scanner sc) {
      boolean valor = false;
      boolean correcto;

      do {
         correcto = true;
         try {
            valor = sc.nextBoolean();
         } catch (Exception e) {
            System.out.println("ERROR. No ha introducido un valor válido. Introduzca otro 'true' o 'false': ");
            sc.next();
            correcto = false;
         }
      } while (!correcto);

      return valor;
   }

   public static String leerString(Scanner sc) {
      String resultado = null;

      do {
         resultado = sc.nextLine();
      } while (resultado.isEmpty());

      return resultado;
   }

   public static char leerChar(Scanner sc) {
      String resultado = null;

      do {
         resultado = sc.nextLine();
      } while (resultado.isEmpty());

      return resultado.charAt(0);
   }

   public static char leerLetra(Scanner sc) {
      char caracter = '\0';

      do {
         caracter = (sc.nextLine()).charAt(0);
      } while (!Character.isLetter(caracter));

      return caracter;
   }

   public static String leerNome(Scanner sc) {
      String nome = null;
      boolean repetir = true;

      do {
         nome = sc.nextLine();
         if (nome.isEmpty() || !nome.toUpperCase().matches("[A-ZÁÉÍÓÚÜÑ\\-\\s]*")) {
            System.out.println("\tERROR: debe introducir algún nombre válido "
                    + "\n\t\tVuelva a introducir: ");
         } else {
            repetir = false;
         }
      } while (repetir);

      return nome;
   }

   public static int leerDia(Scanner sc) {
      int valor = 0;
      boolean correcto;

      do {
         correcto = true;
         try {
            valor = sc.nextInt();
            if (valor < 1 || valor > 31) {
               System.out.println("ERROR. No ha introducido un valor válido. Introduzca un número entre 1 y 31: ");
               correcto = false;
            }
         } catch (Exception e) {
            System.out.println("ERROR. No ha introducido un valor válido. Introduzca un número: ");
            sc.next();
            correcto = false;
         }
      } while (!correcto);

      return valor;
   }
}

```
**operaciones/Modificaciones.java**
```java 
package com.AD.U3.operaciones;

import java.util.Scanner;

import com.AD.U3.entities.Empleado;
import com.AD.U3.entities.Fijo;
import com.AD.U3.entities.Producto;
import com.AD.U3.entities.Temporal;
import org.hibernate.Session;


public class Modificaciones {
	
	Session sesion;
	
	public Modificaciones(Session session) {
		this.sesion = session;
	}

    public void modificacion(Scanner input) {
        byte op = 0;
        do {
            Cadenas.menuModificaciones();
            op = ControlData.leerByte(input);
            switch (op) {
                case 1:
                    precioProducto(input);
                    break;
                case 2:
                    sueldoBaseEmpleado(input);
                    break;
                case 3:
                    porcentajeRetencion(input);
                    break;
                case 4:
                    importeDiaEmpleadoTemporal(input);
                    break;
                case 0:
                    Cadenas.vueltaAnteriorMenu();
                    break;
                default:
                    Cadenas.mesajeDefaultMenu();
                    break;
            }
        } while (op != 0);

    }

    private void precioProducto(Scanner input){
    	System.out.println("Código del producto");
    	String codigo = ControlData.leerString(input);
    	System.out.println("Nuevo precio del producto");
    	float nuevoPrecio = input.nextFloat();
    	
    	Producto producto = (Producto) this.sesion.get(Producto.class, codigo);
    	if (producto == null) {
    		System.out.println("No existe el producto");
    	}else {
    		producto.setPrecioUnitario(nuevoPrecio);
    		this.sesion.update(producto);
    	}
    }

    private void sueldoBaseEmpleado(Scanner input){
    	System.out.println("DNI del empleado");
    	String dni = ControlData.leerString(input);
    	System.out.println("Nuevo sueldo base");
    	int nuevoPrecio = input.nextInt();
    	
    	Fijo fijo = (Fijo) this.sesion.get(Fijo.class, dni);
    	if (fijo == null) {
    		System.out.println("No existe el empleado");
    	}else {
    		fijo.setSalarioBase(nuevoPrecio);
    		this.sesion.update(fijo);
    	}
    }

    private void porcentajeRetencion(Scanner input){
    	System.out.println("DNI del empleado");
    	String dni = ControlData.leerString(input);
    	System.out.println("Nuevo porcentaje de retención");
    	float porcentaje = input.nextFloat();
    	
    	Empleado empleado = (Empleado) this.sesion.get(Empleado.class, dni);
    	if (empleado == null) {
    		System.out.println("No existe el empleado");
    	}else {
    		empleado.setPorcentajeRetencion(porcentaje);
    		this.sesion.update(empleado);
    	}
    }

    private void importeDiaEmpleadoTemporal(Scanner input){
    	System.out.println("DNI del empleado temporal");
    	String dni = ControlData.leerString(input);
    	System.out.println("Nuevo precio por día");
    	float pagoDia = input.nextFloat();
    	
    	Temporal temporal = (Temporal) this.sesion.get(Temporal.class, dni);
    	if (temporal == null) {
    		System.out.println("No existe el empleado");
    	}else {
    		temporal.setPagoDia(pagoDia);
    		this.sesion.update(temporal);
    	}
    }

}

```

**operaciones/Sesiones.java**
```java 
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

```
**App.java**
```java 
package com.AD.U3;

import java.util.Scanner;

import com.AD.U3.operaciones.*;
import org.hibernate.Session;



public class App {

   static Scanner scanner ;

   public static void main(String[] args) {

      Scanner input = new Scanner(System.in);

      Session session = HibernateUtil.get().openSession();

      byte op=0;
      do{
         Cadenas.menuPrincipal();
         op= ControlData.leerByte(input);
         switch(op){
            case 1: // Check
               new Altas(session).alta(input);
               break;
            case 2: // Check
               new Bajas(session).baja(input);
               break;
            case 3:
               new Modificaciones(session).modificacion(input);
               break;
            case 4:
               new Consultas(session).consulta(input);
               break;
            case 0:
               Cadenas.fin();
               break;
            default:
               Cadenas.mesajeDefaultMenu();
               break;
         }
      }while(op!=0);

      input.close();

   }


   public static int pedirInt(String string) {
      System.out.println(string);
      return scanner.nextInt();
   }

   public static Double pedirDouble(String string) {
      System.out.println(string);
      return scanner.nextDouble();
   }

   public static String pedirString(String string) {
      System.out.println(string);
      return scanner.next();
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
//            String hibernatePropsFilePath = "./src/hibernate.cfg.xml"; // Ruta al fichero
//
//            File hibernatePropsFile = new File(hibernatePropsFilePath);
//
//            SESSION_FACTORY = new Configuration().configure(hibernatePropsFile).buildSessionFactory();
// Hibernate buscará hibernate.cfg.xml en el classpath (src/main/resources)
            SESSION_FACTORY = new Configuration()
                    .configure()    // sin argumentos
                    .buildSessionFactory();
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
      <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
      <property name="connection.url">jdbc:mysql://localhost:3306/programacionsa?serverTimezone=UTC</property>
      <property name="connection.username">root</property>
      <property name="connection.password">abc123.</property>
      <property name="hbm2ddl.auto">create</property>
      <property name="dialect">org.hibernate.dialect.MySQL8Dialect</property>
      <property name="hibernate.dialect.storage_engine">innodb</property>
      <property name="hibernate.show_sql">true</property>

      <!--Mapping de las clases-->
      <mapping class="com.AD.U3.entities.Empleado"/>
      <mapping class="com.AD.U3.entities.Fijo"/>
      <mapping class="com.AD.U3.entities.Temporal"/>
      <mapping class="com.AD.U3.entities.Empresa"/>
      <mapping class="com.AD.U3.entities.Producto"/>
      <mapping class="com.AD.U3.entities.Venta"/>

   </session-factory>
</hibernate-configuration>
```
