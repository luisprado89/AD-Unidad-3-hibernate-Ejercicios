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
