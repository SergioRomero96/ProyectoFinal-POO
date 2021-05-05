package ar.edu.unju.fi.proyectofinal.modelo.dao;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;

public interface ProductoDAO {
	 Long insert(Producto producto);
	 int delete(Producto producto);
	 int update(Producto producto);
	 List<Producto> getAll();
	 Producto select(Integer id);
	 Producto find(String nombre);
	 Boolean validarProducto(Producto producto);
}
