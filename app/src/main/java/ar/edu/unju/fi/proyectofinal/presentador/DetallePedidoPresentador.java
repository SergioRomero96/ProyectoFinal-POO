package ar.edu.unju.fi.proyectofinal.presentador;

import android.content.Context;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.IDetallePedido;
import ar.edu.unju.fi.proyectofinal.modelo.DetallePedidoModelo;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;

public class DetallePedidoPresentador implements IDetallePedido.presentadorDetallePedido {
    private IDetallePedido.modeloDetallePedido modeloPedido;
    private IDetallePedido.vistaDetallePedido vistaDetallePedido;

    public DetallePedidoPresentador(IDetallePedido.vistaDetallePedido vistaDetallePedido, Context context) {
        this.vistaDetallePedido = vistaDetallePedido;
        modeloPedido = new DetallePedidoModelo(this,context);
    }


    /**
     * Obtiene un vendedor por su id
     * @param id
     * @return
     */
    @Override
    public Usuario obtenerVendedor(Integer id) {
        return modeloPedido.obtenerVendedor(id);
    }

    /**
     * Obtiene un cliente por su id
     * @param id
     * @return
     */
    @Override
    public Cliente obtenerCliente(Integer id) {
        return modeloPedido.obtenerCliente(id);
    }

    /**
     * Obtiene una lista de ItemsPedido de acuerdo a su id
     * @param id
     * @return
     */
    @Override
    public List<ItemPedido> obtenerItemsPedido(Integer id) {
        return modeloPedido.obtenerItemsPedido(id);
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

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoDetallePedido(String info) {
        vistaDetallePedido.showInfoDetallePedido(info);
    }

    /**
     * Confirma un pedido
     * @param pedido
     */
    @Override
    public void confirmarPedido(Pedido pedido) {
        modeloPedido.confirmarPedido(pedido);
    }

    /**
     * Cancela un pedido
     * @param pedido
     */
    @Override
    public void cancelarPedido(Pedido pedido) {
        modeloPedido.cancelarPedido(pedido);
    }

    /**
     * Agrega un itemPedido
     * @param itemPedido
     */
    @Override
    public void agregarItemPedido(ItemPedido itemPedido) {
        modeloPedido.agregarItemPedido(itemPedido);
    }

    /**
     * Elimina un itemPedido
     * @param itemPedido
     */
    @Override
    public void eliminarDetallePedido(ItemPedido itemPedido) {
        modeloPedido.eliminarItemPedido(itemPedido);
    }

    @Override
    public void validarCantidadDetallePedido(Integer cantidad) {
        if (cantidad != 0)
            vistaDetallePedido.validarCantidadInferiorDetallePedido();
        else
            vistaDetallePedido.showInfoDetallePedido("Unidad no valida");

    }

    @Override
    public void validarCantidadInferiorDetallePedido(Integer cantidad, Integer stock) {
        if (cantidad <= stock)
            vistaDetallePedido.validarItemDetallePedido();
        else if (stock == 0)
            vistaDetallePedido.showInfoDetallePedido("No hay stock");
        else
            vistaDetallePedido.showInfoDetallePedido("Supera la cantidad, Stock maximo: "+stock);
    }

    @Override
    public void validarItemDetallePedido(ItemPedido itemPedido) {
        if (!modeloPedido.validarItemPedido(itemPedido)){
            vistaDetallePedido.agregarDetallePedido();
        }
        else
            vistaDetallePedido.showInfoDetallePedido("El producto ya fue registrado");
    }

    /**
     * Modifica un pedido
     * @param pedido
     */
    @Override
    public void modificarPedido(Pedido pedido) {
        modeloPedido.modificarPedido(pedido);
    }

    @Override
    public void cargarListaDetallePedidoCompartir(Integer id) {
        modeloPedido.cargarListaDetallePedido(id);
    }

    @Override
    public void cargarListaDetalle(String text) {
        vistaDetallePedido.compartirLista(text);
    }
}
