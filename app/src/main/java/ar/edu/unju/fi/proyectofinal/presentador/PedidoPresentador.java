package ar.edu.unju.fi.proyectofinal.presentador;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.IPedido;
import ar.edu.unju.fi.proyectofinal.modelo.PedidoModelo;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.vista.MenuVendedor;
import ar.edu.unju.fi.proyectofinal.vista.pedido.DetallePedidoActivity;
import ar.edu.unju.fi.proyectofinal.vista.pedido.PedidoActivity;

public class PedidoPresentador implements IPedido.presentadorPedido {
    private IPedido.vistaAgregarPedido vistaAgregarPedido;
    private IPedido.vistaPedido vistaPedido;
    private IPedido.vistaMenuVendedor vistaMenuVendedor;
    private IPedido.modeloPedido modeloPedido;

    public PedidoPresentador(IPedido.vistaAgregarPedido vistaAgregarPedido, Context context) {
        this.vistaAgregarPedido = vistaAgregarPedido;
        modeloPedido = new PedidoModelo(this,context);
    }


    public PedidoPresentador(IPedido.vistaPedido vistaPedido, PedidoActivity context) {
        this.vistaPedido = vistaPedido;
        modeloPedido = new PedidoModelo(this,context);
    }

    public PedidoPresentador(IPedido.vistaMenuVendedor vistaMenuVendedor, MenuVendedor context) {
        this.vistaMenuVendedor = vistaMenuVendedor;
        modeloPedido = new PedidoModelo(this,context);
    }


    /**
     * Busca un pedido por fecha y por el id del vendedor
     * Si existe lo guarda en una lista y manda a cargar el pedido encontrado
     * Sino muestra mensaje por pantalla
     * @param fecha
     * @param id
     */
    @Override
    public void buscarPedido(String fecha, Integer id) {
            List<Pedido> pedidos = modeloPedido.obtenerPedidoPorFecha(fecha, id);
            vistaMenuVendedor.cargarPedidoEncontrado(pedidos);
        }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoAgregarItem(String info) {
        vistaAgregarPedido.showInfoAgregarItem(info);
    }

    /**
     * Valida que la cantidad sea distinta de cero
     * @param cantidad
     */
    @Override
    public void validarCantidad(Integer cantidad) {
        if (cantidad != 0)
            vistaAgregarPedido.validarCantidadInferior();
        else
            vistaAgregarPedido.showInfoAgregarItem("Unidad no valida");
    }

    /**
     * Valida que el stock no sea inferior a lo pedido
     * @param unidad
     * @param stock
     */
    @Override
    public void validarCantidadInferior(Integer unidad, Integer stock) {
        if (unidad <= stock)
            vistaAgregarPedido.validarItemPedido();
        else if (stock == 0)
            vistaAgregarPedido.showInfoAgregarItem("No hay stock");
        else
            vistaAgregarPedido.showInfoAgregarItem("Supera la cantidad, Stock maximo: "+stock);
    }

    @Override
    public void validarItemPedido(List<ItemPedido> listItems, ItemPedido itemPedido) {
        if (!modeloPedido.validarItemPedido(listItems, itemPedido)) {
            vistaAgregarPedido.agregarItemPedido();
        } else {
            vistaAgregarPedido.showInfoAgregarItem("el producto ya fue registrado");
        }
    }

    @Override
    public void agregarItemPedido(List<ItemPedido> listItems, ItemPedido itemPedido) {
        modeloPedido.agregarItemPedido(listItems, itemPedido);
    }

    @Override
    public void validarItemDelPedido(List<ItemPedido> listItems) {
        if (!listItems.isEmpty()) {
            vistaAgregarPedido.actualizarStockProducto();
        } else {
            vistaAgregarPedido.showInfoAgregarItem("No agrego ningun producto");
        }
    }

    /**
     * Actualiza el stock del producto
     * @param listItems
     */
    @Override
    public void actualizarStockProducto(List<ItemPedido> listItems) {
        modeloPedido.actualizarStockProductos(listItems);
    }

    /**
     * Agrega un pedido
     * @param pedido
     */
    @Override
    public void agregarPedido(Pedido pedido) {
        modeloPedido.agregarPedido(pedido);
    }

    @Override
    public void precargarItemsPedidos() {
        vistaAgregarPedido.guardarListaItems();
    }

    @Override
    public void guardarListaItems(List<ItemPedido> listItems) {
        modeloPedido.guardarListaItems(listItems);
    }

    /**
     * Agrega un pedido
     */
    @Override
    public void guardarPedido() {
        vistaAgregarPedido.agregarPedido();
    }


    /**
     * Obtiene una lista de pedidos de acuerdo a su id
     * @param id
     * @return
     */
    @Override
    public List<Pedido> obtenerPedidosPorId(Integer id) {
        return modeloPedido.obtenerPedidosPorId(id);
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(Integer id) {
        return modeloPedido.obtenerPedidosPorCliente(id);
    }

    /**
     * Retorna una lista de pedidos
     * @return
     */
    @Override
    public List<Pedido> obtenerPedidos() {
        return modeloPedido.obtenerPedidos();
    }


    /**
     * Devuelve el total de una lista de itemPedidos
     * @param listItems
     * @return
     */
    @Override
    public Double calcularTotal(List<ItemPedido> listItems) {
        return modeloPedido.calcularTotal(listItems);
    }

    @Override
    public Double calcularTotalPedidos(List<Pedido> listPedidos) {
        return modeloPedido.calcularTotalPedidos(listPedidos);
    }



}
