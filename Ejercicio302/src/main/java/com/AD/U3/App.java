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