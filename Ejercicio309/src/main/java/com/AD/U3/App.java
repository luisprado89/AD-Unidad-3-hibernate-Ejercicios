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