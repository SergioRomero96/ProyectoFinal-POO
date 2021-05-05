package ar.edu.unju.fi.proyectofinal.modelo;

import android.content.Context;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.IDetallePedido;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;
import ar.edu.unju.fi.proyectofinal.presentador.DetallePedidoPresentador;

public class DetallePedidoModelo implements IDetallePedido.modeloDetallePedido {
    private Service service;
    private IDetallePedido.presentadorDetallePedido presentadorPedido;

    /**
     * Constructor del modelo detallePedido
     * @param detallePedidoPresentador
     * @param context
     */
    public DetallePedidoModelo(DetallePedidoPresentador detallePedidoPresentador, Context context) {
        presentadorPedido = detallePedidoPresentador;
        service = new Service(context);
    }


    /**
     * Valida si un ItemPedido ya esta cargado
     * @param listItems
     * @param itemPedido
     * @return true si ya esta cargado o falso si es lo contrario
     */
    public Boolean validarItemPedido(List<ItemPedido> listItems, ItemPedido itemPedido){
        Boolean encontrado = false;
        if (!listItems.isEmpty()){
            for (ItemPedido i: listItems){
                if (i.getIdProducto().equals(itemPedido.getIdProducto())){
                    encontrado = true;
                }
            }
        }
        return encontrado;
    }

    /**
     * Obtiene el id del vendedor
     * @param id
     * @return un usuario
     */
    @Override
    public Usuario obtenerVendedor(Integer id) {
        return service.obtenerUsuario(id);
    }

    /**
     * Obtiene el id de un cliente
     * @param id
     * @return un cliente
     */
    @Override
    public Cliente obtenerCliente(Integer id) {
        return service.obtenerCliente(id);
    }

    /**
     * Obtiene una lista de items pedido de acuerdo a su id
     * @param id
     * @return retorna una lista de items pedido
     */
    @Override
    public List<ItemPedido> obtenerItemsPedido(Integer id) {
        return service.obtenerListaItemsPedido(id);
    }

    /**
     * Calcula el total de itemPedido cargados
     * @param listItems
     * @return el total de items
     */
    @Override
    public Double calcularTotal(List<ItemPedido> listItems) {
        Double suma = 0d;
        for (ItemPedido itemPedido : listItems){
            suma = suma + itemPedido.getSubTotal();
        }
        return suma;
    }

    /**
     * Confirma un pedido
     * @param pedido
     */
    @Override
    public void confirmarPedido(Pedido pedido) {
        pedido.setEstado(Constantes.CONFIRMADO);
        Integer resultado = service.modificarPedido(pedido);
        if (resultado > 0)
            presentadorPedido.showInfoDetallePedido("Pedido confirmado.");
        else
            presentadorPedido.showInfoDetallePedido("Error, No se pudo confirmar");
    }

    /**
     * Actualiza el stock de un pedido cancelado
     * @param pedido
     */
    private void actualizarStockPedidoCancelado(Pedido pedido) {
        List<ItemPedido> listItems = service.obtenerListaItemsPedido(pedido.getIdPedido());
        for (ItemPedido itemPedido: listItems){
            for (Producto producto: service.obtenerListaProductos()){
                if (itemPedido.getIdProducto().equals(producto.getIdProducto())){
                    producto.setStock(producto.getStock() + itemPedido.getCantidad());
                    if (producto.getStock() > 0)
                        producto.setEstado(Constantes.DISPONIBLE);
                    Integer resultado = service.modificarProducto(producto);
                }
            }
        }
    }

    /**
     * Cancela un pedido
     * @param pedido
     */
    @Override
    public void cancelarPedido(Pedido pedido) {
        actualizarStockPedidoCancelado(pedido);
        pedido.setEstado(Constantes.CANCELADO);
        Integer resultado = service.modificarPedido(pedido);
        if (resultado > 0)
            presentadorPedido.showInfoDetallePedido("Pedido Cancelado");
        else
            presentadorPedido.showInfoDetallePedido("Error, No se pudo cancelar el pedido");
    }


    /**
     * Agrega un itenPedido
     * @param itemPedido
     */
    @Override
    public void agregarItemPedido(ItemPedido itemPedido) {
        buscarProducto(itemPedido, true);
        Long resultado = service.agregarItemPedido(itemPedido);
        if (resultado > 0)
            presentadorPedido.showInfoDetallePedido("El Item fue agregado");
        else
            presentadorPedido.showInfoDetallePedido("Error, No se pudo agregar");
    }

    /**
     * Actualiza el stock de un producto de acuerdo al itemPedido
     * @param itemPedido
     */
    private void buscarProducto(ItemPedido itemPedido, Boolean band){
        for (Producto producto: service.obtenerListaProductos()){
            if (producto.getIdProducto().equals(itemPedido.getIdProducto())){
                actualizarStockProducto(itemPedido, producto, band);
            }
        }
    }

    /**
     * Actualiza el stock del producto si el valor logico que se recibe
     * por parametro es True entonces esta agregando un producto al pedido
     * sino es porque elimino un producto del pedido
     * @param itemPedido
     * @param producto
     * @param band
     */
    private void actualizarStockProducto(ItemPedido itemPedido, Producto producto, Boolean band){
        if (band){
            producto.setStock(producto.getStock()-itemPedido.getCantidad());
            if (producto.getStock() == 0)
                producto.setEstado(Constantes.NO_DISPONIBLE);
        }
        else{
            producto.setStock(producto.getStock() + itemPedido.getCantidad());
            if (producto.getStock() > 0)
                producto.setEstado(Constantes.DISPONIBLE);
        }
        Integer resultado = service.modificarProducto(producto);
    }


    /**
     * Elimina un itemPedido
     * @param itemPedido
     */
    @Override
    public void eliminarItemPedido(ItemPedido itemPedido) {
        buscarProducto(itemPedido, false);
        Integer resultado = service.eliminarItemPedido(itemPedido);
        if (resultado > 0)
            presentadorPedido.showInfoDetallePedido("Se elimino el Item del pedido");
        else
            presentadorPedido.showInfoDetallePedido("Error, no se pudo eliminar");
    }

    /**
     * Valida si un itemPedido ya esta asignado
     * @param itemPedido
     * @return
     */
    @Override
    public Boolean validarItemPedido(ItemPedido itemPedido) {
        return service.validarItemPedido(itemPedido);
    }

    /**
     * Modifica un pedido
     * @param pedido
     */
    @Override
    public void modificarPedido(Pedido pedido) {
        pedido.setImporteTotal(calcularTotal(service.obtenerListaItemsPedido(pedido.getIdPedido())));
        Integer resultado = service.modificarPedido(pedido);
    }

    /**
     * asigna los detalles de un pedido en una cadena
     * @param id
     */
    @Override
    public void cargarListaDetallePedido(Integer id) {
        String text = "*************Detalle*************";
        for (ItemPedido itemPedido: service.obtenerListaItemsPedido(id)){
            Producto producto = service.obtenerProducto(itemPedido.getIdProducto());
            text = text +   "\n--------------------------------"+
                    "\nProducto: "+producto.getNombre()
                    + "\nTamaño: "+producto.getTamanio().toString()+"cm3"+ "\nCantidad: "+ itemPedido.getCantidad()
                    +"\nPU: $"+ producto.getPrecioUnitario().toString()+"\nSubtotal: $"+itemPedido.getSubTotal()+
                    "\n--------------------------------";
        }
        text = text + "\nTotal: $" + calcularTotal(service.obtenerListaItemsPedido(id))+"\n";
        presentadorPedido.cargarListaDetalle(text);
    }


}
