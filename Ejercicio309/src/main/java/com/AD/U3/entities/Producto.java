/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AD.U3.entities;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.*;
/**
 *
 * @author Estefania
 */
@Entity
@Table(name="PRODUCTOS")
public class Producto implements Serializable{
    
    @Id
    @Column(name="CODIGO", unique=true, nullable=false)
    private String codigo;
    
    @Column(name="STOCK_ALMACEN")
    private int stockActualAlmacen;
    
    @Column(name="STOCK_MINIMO")
    private final int STOCK_MINIMO = 30;
    
    @Column(name="PRECIO_UNITARIO")
    private float precioUnitario;
   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIF_EMPRESA")
    private Empresa empresa;
    
    @OneToMany(mappedBy="codigoArticulo")
    private Set<Venta> ventas;

    public Producto() {

    }

    public Producto(String codigo, int stockAlmacen, float precioUnitario) {
        this.codigo = codigo;
        this.stockActualAlmacen = stockAlmacen;
        this.precioUnitario = precioUnitario;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the stockActualAlmacen
     */
    public int getStockActualAlmacen() {
        return stockActualAlmacen;
    }

    /**
     * @param stockActualAlmacen the stockActualAlmacen to set
     */
    public void setStockActualAlmacen(int stockActualAlmacen) {
        this.stockActualAlmacen = stockActualAlmacen;
    }

    /**
     * @return the STOCK_MINIMO
     */
    public int getSTOCK_MINIMO() {
        return STOCK_MINIMO;
    }

    /**
     * @return the precioUnitario
     */
    public float getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * @param precioUnitario the precioUnitario to set
     */
    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * @return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the ventas
     */
    public Set<Venta> getVentas() {
        return ventas;
    }

    /**
     * @param ventas the ventas to set
     */
    public void setVentas(Set<Venta> ventas) {
        this.ventas = ventas;
    }
    
        @Override
    public String toString() {
        String producto = "CÓDIGO: " + codigo + "\n"
                + "STOCK ALMACÉN: " + stockActualAlmacen + "\n"
                + "STOCK MÍNIMO: " + STOCK_MINIMO + "\n"
                + "PRECIO UNITARIO: " + precioUnitario + "\n";

        return producto;
    }

}
