package com.AD.U3;
/*
Ejercicio 307
Para este ejercicio usaremos la base de datos definida en el script bd-facturas.sql. El cual tiene el siguiente modelo relacional:



Deberás crear un proyecto Hibernate que mapee las tablas de la base de datos. La aplicación debe permitir hacer lo siguiente:

Mostrar todas las facturas de la base de datos. La información debe mostrarse de la siguiente forma:
ID: xxx
CLIENTE: nombre, dirección, telefonos (movil: xxx, otro: xxx, etc).
DESCRIPCION: xxx
PRECIO: xxx
Añadir una factura.
Modificar una factura.
Borrar una factura.
 */
import com.AD.U3.entities.Cliente;
import com.AD.U3.repository.ClienteRepositorio;
import com.AD.U3.repository.FacturaRepositorio;
import org.hibernate.Session;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    static Scanner scanner ;

    public static void main(String[] args) {
        System.out.println("Ejercicio 307");

        Session session = HibernateUtil.get().openSession();

        scanner = new Scanner(System.in);

        FacturaRepositorio facturaRepositorio = new FacturaRepositorio(session, scanner);
        ClienteRepositorio clienteRepositorio = new ClienteRepositorio(session);

        int opcion = -1;

        do {
            opcion = pedirInt("1. Mostrar todas las facturas\n2. Añadir factura\n"
                    + "3. Modificar factura\n4. Borrar factura\n5. Salir");

            switch (opcion) {
                case 1: {
                    facturaRepositorio.mostrarTodasFacturas();
                    break;
                }
                case 2: {
                    int idCliente = pedirInt("Introduzca el ID del cliente");
                    Cliente cliente = clienteRepositorio.getClientePorId(idCliente);
                    facturaRepositorio.addFactura(cliente);
                    break;
                }
                case 3: {
                    facturaRepositorio.modificarFactura();
                    break;
                }
                case 4: {
                    facturaRepositorio.borrarFactura();
                    break;
                }
                case 5: {
                    System.exit(0);
                    break;
                }
            }

        }while(opcion != 5);

        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }


    private static int pedirInt(String string) {
        System.out.println(string);
        return scanner.nextInt();
    }

    private Double pedirDouble(String string) {
        System.out.println(string);
        return scanner.nextDouble();
    }

    private String pedirString(String string) {
        System.out.println(string);
        return scanner.next();
    }

}