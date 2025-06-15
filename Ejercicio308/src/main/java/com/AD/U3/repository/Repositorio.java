package com.AD.U3.repository;


import java.util.List;

public interface Repositorio<T> {

    public void guardar(T t);
    public void eliminar(T t);
    public T buscarPorId(int id);
    public List<T> buscarPorCadena(String cadena);
    public void actualizar(T t);

}
