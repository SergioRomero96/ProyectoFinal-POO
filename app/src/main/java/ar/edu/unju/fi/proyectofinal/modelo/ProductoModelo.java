package ar.edu.unju.fi.proyectofinal.modelo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.IProducto;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;

public class ProductoModelo implements IProducto.modeloProducto {
    private Service service;
    private IProducto.presentadorProducto presentadorProducto;

    /**
     * Constructor de la clase ProductoModelo
     * @param presentadorProducto
     * @param context
     */
    public ProductoModelo(IProducto.presentadorProducto presentadorProducto, Context context) {
        this.service = new Service(context);
        this.presentadorProducto = presentadorProducto;
    }

    /**
     * Agrega un producto si este devuelve un Long
     * Sino muestra un mensaje de error por pantalla
     * @param producto
     */
    @Override
    public void agregarProducto(Producto producto) {
        Long resultado = service.agregarProducto(producto);
        if (resultado > 0) {
            presentadorProducto.showInfoAgregar("Producto agregado correctamente");
        } else {
            presentadorProducto.showInfoAgregar("Error al agregar");
        }
    }

    /**
     * Modifica un producto si este devuelve un Integer
     * Sino muestra un mensaje de error por pantalla
     * @param producto
     */
    @Override
    public void modificarProducto(Producto producto) {
        Integer resultado = service.modificarProducto(producto);
        if (resultado > 0) {
            presentadorProducto.showInfoModificar("Producto actualizado");
        } else {
            presentadorProducto.showInfoModificar("Error al actualizar");
        }
    }

    /**
     * Elimina un producto si este devuelve un Integer
     * Sino muestra un mensaje de error por pantalla
     * @param producto
     */
    @Override
    public void eliminarProducto(Producto producto) {
        Integer resultado = service.eliminarProducto(producto);
        if (resultado > 0) {
            presentadorProducto.showInfoEliminar("Producto eliminado");
        } else {
            presentadorProducto.showInfoEliminar("Error al eliminar");
        }
    }

    /**
     * Obtiene una lista de productos
     * @return una lista de productos
     */
    @Override
    public List<Producto> obtenerProductos() {
        return service.obtenerListaProductos();
    }

    /**
     * Busca un producto de acuerdo a su nombre
     * @param nombre
     * @return un producto
     */
    @Override
    public List<Producto> buscarProducto(String nombre) {
        List<Producto> listProductos = new ArrayList<>();
        for (Producto producto: service.obtenerListaProductos()){
            if (producto.getNombre().equals(nombre)){
                listProductos.add(producto);
            }
        }
        return listProductos;
    }

    /**
     * Valida un producto por su nombre
     * @param producto
     * @return true si lo ecuentra o false si no lo encontro
     */
    @Override
    public Boolean validarProducto(Producto producto) {
        return service.validarProducto(producto);
    }

    /**
     * Valida si el producto tiene pedidos asignados
     * @param id
     * @return true si tiene pedidos o false si no lo tiene
     */
    @Override
    public Boolean validarRegistroDePedido(Integer id) {
        Boolean encontrado = false;
        for (ItemPedido itemPedido: service.obtenerListaItemsPedido()) {
            if (itemPedido.getIdProducto().equals(id)) {
                encontrado = true;
                break;
            }
        }
        return encontrado;
    }
}
