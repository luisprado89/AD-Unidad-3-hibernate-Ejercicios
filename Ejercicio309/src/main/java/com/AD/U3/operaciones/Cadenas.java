package com.AD.U3.operaciones;

public class Cadenas {

    public static void menuPrincipal() {
        System.out.println("----------------------------------------------------\n"
                + "\t\tPROGRAMACIÓN S.A.\n"
                + "1.-ALTAS\n"
                + "2.-BAJAS\n"
                + "3.-MODIFICACIONES\n"
                + "4.-CONSULTAS\n\n"
                + "0.-SALIR\n");
    }

    public static void menuAltas() {
        System.out.println("--------------------------------------\n"
                + "\t\tALTAS\n"
                + "1.-EMPRESAS\n"
                + "2.-EMPLEADOS\n"
                + "3.-PRODUCTOS\n"
                + "4.-VENTAS\n\n"
                + "0.-VOLVER AL MENÚ ANTERIOR");
    }

    public static void menuEmpleados() {
        System.out.println("\tEMPLEADOS\n"
                + "1.-FIJOS\n"
                + "2.-TEMPORALES\n\n"
                + "0.-VOLVER AL MENÚ ANTERIOR");
    }

    public static void menuModificaciones() {
        System.out.println("----------------------------------------\n"
                + "\t\tMODIFICACIONES\n"
                + "1.-PRECIO DE UN PRODUCTO\n"
                + "2.-SUELDO BASE EMPLEADO FIJO\n"
                + "3.-PORCENTAJE RETENCIÓN\n"
                + "4.-IMPORTE DÍA EMPLEADO TEMPORAL\n\n"
                + "0.-VOLVER AL MENÚ ANTERIOR");
    }

    public static void menuConsultas() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------\n"
                + "\t\tCONSULTAS\n"
                + "1.-LISTADO de las EMPRESAS con todos sus EMPLEADOS FIJOS Y TEMPORALES\n"
                + "2.-LISTADO de los PRODUCTOS de una EMPRESA determinada\n"
                + "0.-VOLVER AL MENÚ ANTERIOR");
    }

    public static void fin() {
        System.out.println("PROGRAMA FINALIZADO");
    }

    public static void mesajeDefaultMenu() {
        System.out.println("No ha introducido ninguna de las opciones.");
    }

    public static void vueltaAnteriorMenu() {
        System.out.println("Volviendo al menú anterior...");
    }

}
