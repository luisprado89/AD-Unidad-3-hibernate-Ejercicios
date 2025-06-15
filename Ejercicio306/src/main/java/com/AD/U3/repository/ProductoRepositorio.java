package com.AD.U3.repository;



import com.AD.U3.entities.Producto;
import org.hibernate.Session;
import org.hibernate.query.Query;



public class ProductoRepositorio {
    Session sesion;

    public ProductoRepositorio() {    }

    public ProductoRepositorio(Session sesion) {
        this.sesion = sesion;
    }

    public Producto getProducto(int idProducto) {
        try {
            Query query = this.sesion.createQuery("select p from Producto p where p.idProducto=:idProducto");
            query.setParameter("idProducto", idProducto);

            Producto producto = (Producto) query.getSingleResult();

            return producto;
        } catch (Exception e) {
            return new Producto(-1);
        }
    }

}
