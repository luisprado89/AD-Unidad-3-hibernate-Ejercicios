package com.AD.U3;

import com.AD.U3.entities.Cliente;
import com.AD.U3.repository.AlquilerRepositorio;
import com.AD.U3.repository.ClienteRepositorio;
import org.hibernate.Session;

public class App {

    public static void main(String[] args) {
        System.out.println("Ejercicio 305");

        Session session = HibernateUtil.get().openSession();

        ClienteRepositorio clienteRepositorio = new ClienteRepositorio(session);
        AlquilerRepositorio alquilerRepositorio = new AlquilerRepositorio(session);

        Cliente cliente = clienteRepositorio.obtenerCliente(2);
        System.out.println(cliente.toString());

        Cliente nuevoCliente = new Cliente("123456", "Cliente1", "Cliente1@cliente.es");
        clienteRepositorio.anhadirCliente(nuevoCliente);
        System.out.println("Se ha añadido al nuevo cliente"+nuevoCliente.toString());

        nuevoCliente.setDni("789456");
        clienteRepositorio.modificarCliente(nuevoCliente);
        System.out.println(nuevoCliente.toString());
        //Aqui borramos al nuevoCliente que se agrego
        clienteRepositorio.borrarCliente(nuevoCliente);
        System.out.println("Se ha borrado al nuevo cliente"+nuevoCliente.toString());

        // Ya está prestado daría error
        alquilerRepositorio.alquilar(3, 1);

        alquilerRepositorio.devolver(3);

        // No daría error porque ahora ya se ha devuelto
        alquilerRepositorio.alquilar(3, 1);

        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }
}