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
