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
