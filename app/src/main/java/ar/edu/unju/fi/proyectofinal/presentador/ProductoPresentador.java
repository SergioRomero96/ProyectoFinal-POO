package ar.edu.unju.fi.proyectofinal.presentador;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.IProducto;
import ar.edu.unju.fi.proyectofinal.modelo.ProductoModelo;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.vista.producto.AgregarProductoActivity;
import ar.edu.unju.fi.proyectofinal.vista.producto.ModificarProductoActivity;

public class ProductoPresentador implements IProducto.presentadorProducto {
    private IProducto.vistaProducto vistaProducto;
    private IProducto.vistaAgregarProducto vistaAgregar;
    private IProducto.vistaModificarProducto vistaModificar;
    private IProducto.modeloProducto modeloProducto;

    public ProductoPresentador(IProducto.vistaProducto vistaProducto, Context context) {
        this.vistaProducto = vistaProducto;
        modeloProducto = new ProductoModelo(this, context);
    }

    public ProductoPresentador(IProducto.vistaAgregarProducto vistaAgregar, AgregarProductoActivity context) {
        this.vistaAgregar = vistaAgregar;
        modeloProducto = new ProductoModelo(this, context);
    }

    public ProductoPresentador(IProducto.vistaModificarProducto vistaModificar, ModificarProductoActivity context) {
        this.vistaModificar = vistaModificar;
        modeloProducto = new ProductoModelo(this, context);
    }

    /**
     * Valida que el formulario no este vacio
     * Si no esta vacio va a validar el producto
     * Sino muestra mensaje por pantalla
     * @param vacio
     */
    @Override
    public void validarFormularioVacioAgregar(Boolean vacio) {
        if (!vacio) {
            vistaAgregar.validarProducto();
        } else {
            vistaAgregar.showFormularioVacio(Constantes.OBLIGATORIO);
        }
    }

    /**
     * Valida que el formulario no este vacio
     * Si no esta vacio va a validar el producto
     * Sino muestra mensaje por pantalla
     * @param vacio
     */
    @Override
    public void validarFormularioVacioModificar(Boolean vacio) {
        if (!vacio) {
            vistaModificar.validarProducto();
        } else {
            vistaModificar.showFormularioVacio(Constantes.OBLIGATORIO);
        }
    }

    /**
     * Busca un producto por nombre
     * Si existe lo guarda en una lista y manda a cargar el producto encontrado
     * Sino muestra mensaje por pantalla
     * @param nombre
     */
    @Override
    public void buscarProducto(String nombre) {
        List<Producto> listProductos = modeloProducto.buscarProducto(nombre);
        vistaProducto.cargarProductoEncontrado(listProductos);
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoAgregar(String info) {
        vistaAgregar.showInfoAgregar(info);
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoModificar(String info) {
        vistaModificar.showInfoModificar(info);
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoEliminar(String info) {
        vistaModificar.showInfoEliminar(info);
    }

    /**
     * Valida un producto por su nombre
     * Si es verdadero muestra un mensaje por pantalla
     * Sino va a agregar el producto
     * @param producto
     */
    @Override
    public void validarProducto(Producto producto) {
        if (modeloProducto.validarProducto(producto)) {
            vistaAgregar.showInfoAgregar("El producto ya existe");
        } else {
            vistaAgregar.agregarProducto();
        }
    }

    /**
     * Valida un producto de acuerdo al nombre o una bandera
     * Si la bandera es verdadera o el nombre es distinto modifica el produco
     * Sino muestra un mensaje por pantalla
     * @param band
     * @param producto
     */
    @Override
    public void validarProducto(Boolean band, Producto producto) {
        if (band || !(modeloProducto.validarProducto(producto))) {
            vistaModificar.modificarProducto();
        } else {
            vistaModificar.showInfoModificar("El producto ya esta registrado");
        }
    }

    /**
     * Obtiene un listado de productos
     * @return
     */
    @Override
    public List<Producto> obtenerListadoProductos() {
        return modeloProducto.obtenerProductos();
    }

    /**
     * Agrega un producto
     * @param producto
     */
    @Override
    public void agregarProducto(Producto producto) {
        modeloProducto.agregarProducto(producto);
    }

    /**
     * Modifica un producto
     * @param producto
     */
    @Override
    public void modificarProducto(Producto producto) {
        modeloProducto.modificarProducto(producto);
    }

    /**
     * Elimina un producto
     * @param producto
     */
    @Override
    public void eliminarProducto(Producto producto) {
        modeloProducto.eliminarProducto(producto);
    }

    /**
     * Valida que el producto no tenga pedidos
     * Si tiene pedido muestra un mensaje por pantalla
     * Sino elimina el producto
     * @param id
     */
    @Override
    public void validarRegistroDePedido(Integer id) {
        if (modeloProducto.validarRegistroDePedido(id)) {
            vistaModificar.showInfoEliminar("No se pudo eliminar, el producto tiene pedidos asignados");
        } else {
            vistaModificar.eliminarProducto();
        }
    }
}
