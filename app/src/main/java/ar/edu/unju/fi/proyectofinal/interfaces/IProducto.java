package ar.edu.unju.fi.proyectofinal.interfaces;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;

public interface IProducto {
    interface vistaProducto {
        void cargarListaProducto();
        void cargarProductoEncontrado(List<Producto> productos);
        void showInfo(String info);
    }

    interface vistaAgregarProducto {
        void showInfoAgregar(String info);
        void validarProducto();
        void agregarProducto();
        void validarFormularioVacio();
        void showFormularioVacio(String info);
    }

    interface vistaModificarProducto {
        void showInfoModificar(String info);
        void showInfoEliminar(String info);
        void validarFormularioVacio();
        void showFormularioVacio(String info);
        void validarProducto();
        void modificarProducto();
        void eliminarProducto();
    }

    interface presentadorProducto {
        void validarFormularioVacioAgregar(Boolean vacio);
        void validarFormularioVacioModificar(Boolean vacio);
        void buscarProducto(String nombre);
        void showInfoAgregar(String info);
        void showInfoModificar(String info);
        void showInfoEliminar(String info);
        void validarProducto(Producto producto);
        void validarProducto(Boolean band, Producto producto);
        List<Producto> obtenerListadoProductos();
        void agregarProducto(Producto producto);
        void modificarProducto(Producto producto);
        void eliminarProducto(Producto producto);
        void validarRegistroDePedido(Integer id);
    }

    interface modeloProducto {
        void agregarProducto(Producto producto);
        void modificarProducto(Producto producto);
        void eliminarProducto(Producto producto);
        List<Producto> obtenerProductos();
        List<Producto> buscarProducto(String nombre);
        Boolean validarProducto(Producto producto);
        Boolean validarRegistroDePedido(Integer id);
    }
}