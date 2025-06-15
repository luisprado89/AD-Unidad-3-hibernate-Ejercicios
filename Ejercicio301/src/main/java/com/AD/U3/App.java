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