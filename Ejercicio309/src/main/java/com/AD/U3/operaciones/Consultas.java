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
