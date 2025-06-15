package com.AD.U3;

import com.AD.U3.entities.Autor;
import com.AD.U3.entities.Libro;
import com.AD.U3.entities.Telefono;
import com.AD.U3.repository.AutorRepositorio;
import com.AD.U3.repository.LibroRepositorio;
import com.AD.U3.repository.TelefonoRepositorio;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class App {

    private static Scanner sc;
    private static AutorRepositorio aRepositorio;
    private static LibroRepositorio libroRepositorio;
    private static TelefonoRepositorio tRepositorio;

    public static void main(String[] args) {
        System.out.println("Ejercicio 304");

        sc = new Scanner(System.in);
        Session session = HibernateUtil.get().openSession();
        aRepositorio = new AutorRepositorio(session);
        libroRepositorio = new LibroRepositorio(session);
        tRepositorio = new TelefonoRepositorio(session);

        mostrarMenu();

        session.close();
        System.out.println("Finalizando la conexión a MySQL");
    }

    public static void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println();
            System.out.println("1. Insertar nueva fila");
            System.out.println("2. Borrar fila");
            System.out.println("3. Consultar");
            System.out.println("4. Salir");
            System.out.print("Elija opción: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    mostrarMenuInsertar();
                    break;
                case 2:
                    mostrarMenuBorrar();
                    break;
                case 3:
                    mostrarMenuConsultas();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 4);
    }

    public static void mostrarMenuInsertar() {
        int opcion = -1;
        do {
            System.out.println();
            System.out.println("1. Insertar nuevo autor");
            System.out.println("2. Insertar nuevo libro");
            System.out.println("3. Enlazar autor-libro");
            System.out.println("4. Insertar teléfono para un autor");
            System.out.println("5. Atrás");
            System.out.print("Elija opción: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    insertarAutor();
                    break;
                case 2:
                    insertarLibro();
                    break;
                case 3:
                    enlazarAutorLibro();
                    break;
                case 4:
                    insertarTelefonoAutor();
                    break;
                case 5:
                    // volver
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion < 1 || opcion > 5);
    }

    private static void insertarTelefonoAutor() {
        sc.nextLine(); // limpiar buffer
        String dni    = pedirString("Introduzca el DNI del autor:");
        String numTel = pedirString("Introduzca el número de teléfono:");

        Telefono telefono = new Telefono(numTel);
        Autor autor = aRepositorio.encontrarUnoPorString(dni);

        autor.setTelefono(telefono);
        aRepositorio.actualizar(autor);
        tRepositorio.insertarUno(telefono);
    }

    public static void insertarLibro() {
        sc.nextLine(); // limpiar buffer
        String titulo = pedirString("Introduzca el título del libro:");
        System.out.print("Introduzca el precio del libro: ");
        double precio = sc.nextDouble();

        Libro libro = new Libro(titulo, precio);
        libroRepositorio.insertarUno(libro);
    }

    public static void insertarAutor() {
        sc.nextLine(); // limpiar buffer
        String dni       = pedirString("Introduzca el DNI del autor:");
        String nombre    = pedirString("Introduzca el nombre del autor:");
        String localidad = pedirString("Introduzca la localidad del autor:");

        Autor autor = new Autor(dni, nombre, localidad);
        aRepositorio.insertarUno(autor);
    }

    public static void enlazarAutorLibro() {
        sc.nextLine(); // limpiar buffer
        String dni    = pedirString("Introduzca el DNI del autor:");
        String titulo = pedirString("Introduzca el título del libro:");

        Autor autor = aRepositorio.encontrarUnoPorString(dni);
        Libro libro = libroRepositorio.encontrarUnoPorString(titulo);

        autor.getListaLibros().add(libro);
        libro.getListaAutores().add(autor);

        aRepositorio.actualizar(autor);
        libroRepositorio.actualizar(libro);
    }

    public static void mostrarMenuBorrar() {
        int opcion = -1;
        do {
            System.out.println();
            System.out.println("1. Borrar autor");
            System.out.println("2. Borrar libro");
            System.out.println("3. Atrás");
            System.out.print("Elija opción: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    sc.nextLine();
                    String dni = pedirString("Introduzca el DNI del autor:");
                    Autor autor = aRepositorio.encontrarUnoPorString(dni);
                    aRepositorio.borrar(autor);
                    break;
                case 2:
                    sc.nextLine();
                    String titulo = pedirString("Introduzca el título del libro:");
                    Libro libro = libroRepositorio.encontrarUnoPorString(titulo);
                    libroRepositorio.borrar(libro);
                    break;
                case 3:
                    // volver
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion < 1 || opcion > 3);
    }

    public static void mostrarMenuConsultas() {
        int opcion = -1;
        do {
            System.out.println();
            System.out.println("1. Visualizar datos de un libro a partir del título");
            System.out.println("2. Visualizar libros de un determinado autor");
            System.out.println("3. Visualizar todos los libros");
            System.out.println("4. Visualizar todos los autores y sus libros");
            System.out.println("5. Atrás");
            System.out.print("Elija opción: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    sc.nextLine();
                    String titulo = pedirString("Introduzca el título de un libro:");
                    Libro libro = libroRepositorio.encontrarUnoPorString(titulo);
                    System.out.println(libro);
                    break;
                case 2:
                    sc.nextLine();
                    String dni = pedirString("Introduzca el DNI del autor:");
                    Autor autor = aRepositorio.encontrarUnoPorString(dni);
                    for (Libro l : autor.getListaLibros()) {
                        System.out.println(l);
                    }
                    break;
                case 3:
                    List<Libro> listaLibros = libroRepositorio.encontrarTodos();
                    for (Libro l : listaLibros) {
                        System.out.println(l);
                    }
                    break;
                case 4:
                    List<Autor> listaAutores = aRepositorio.encontrarTodos();
                    for (Autor a : listaAutores) {
                        System.out.println(a);
                        System.out.println("Libros:");
                        for (Libro l : a.getListaLibros()) {
                            System.out.println(l);
                        }
                    }
                    break;
                case 5:
                    mostrarMenu();
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion < 1 || opcion > 5);
    }

    public static String pedirString(String mensaje) {
        System.out.print(mensaje + " ");
        return sc.nextLine();
    }
}
