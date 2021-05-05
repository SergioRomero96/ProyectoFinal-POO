package ar.edu.unju.fi.proyectofinal.modelo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.IPedido;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;
import ar.edu.unju.fi.proyectofinal.modelo.util.FechaUtil;

public class PedidoModelo implements IPedido.modeloPedido {
    private Service service;
    private IPedido.presentadorPedido presentadorPedido;

    /**
     * Constructor de la clase PedidoModelo
     * @param presentadorPedido
     * @param context
     */
    public PedidoModelo(IPedido.presentadorPedido presentadorPedido, Context context) {
        this.presentadorPedido = presentadorPedido;
        this.service = new Service(context);
    }

    /**
     * Busca un pedido de acuerdo a la fecha
     * @param fecha
     * @return un pedido
     */
    @Override
    public List<Pedido> obtenerPedidoPorFecha(String fecha, Integer id) {
        List<Pedido> pedidos = new ArrayList<>();
        for (Pedido pedido: service.obtenerListaPedidosPorId(id)) {
            System.out.println("fecha lista: "+FechaUtil.getFechaAsString(pedido.getFechaDelPedido()) + "fecha parametro: "+fecha);
            if (FechaUtil.getFechaAsString(pedido.getFechaDelPedido()).equals(fecha))
                pedidos.add(pedido);
        }
        return pedidos;
    }

    /**
     * Guarda una lista de Items
     * @param listItems
     */
    @Override
    public void guardarListaItems(List<ItemPedido> listItems) {
        for (ItemPedido i: listItems){
            Long resultado = service.agregarItemPedido(i);
        }
        presentadorPedido.guardarPedido();
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
     * Agrega un ItemPedido
     * @param listItems
     * @param itemPedido
     */
    public void agregarItemPedido(List<ItemPedido> listItems, ItemPedido itemPedido){
        listItems.add(itemPedido);
        presentadorPedido.showInfoAgregarItem("producto agregado con exito");
    }

    /**
     * Actualiza el stock de los productos
     * @param listItems
     */
    @Override
    public void actualizarStockProductos(List<ItemPedido> listItems) {
        for (ItemPedido itemPedido: listItems){
            for (Producto producto: service.obtenerListaProductos()){
                if (itemPedido.getIdProducto().equals(producto.getIdProducto())){
                    producto.setStock(producto.getStock() - itemPedido.getCantidad());
                    if (producto.getStock() == 0)
                        producto.setEstado(Constantes.NO_DISPONIBLE);
                    Integer resultado = service.modificarProducto(producto);
                    break;
                }
            }
        }
        presentadorPedido.precargarItemsPedidos();
    }

    /**
     * Agrega un pedido
     * @param pedido
     */
    @Override
    public void agregarPedido(Pedido pedido) {
        Long resultado = service.agregarPedido(pedido);
        if (resultado > 0)
            presentadorPedido.showInfoAgregarItem("Pedido registrado");
        else
            presentadorPedido.showInfoAgregarItem("error al registrar pedido");
    }


    /**
     * Se obtiene una lista de pedidos de acuerdo a su id
     * @param id
     * @return una lista de pedidos
     */
    @Override
    public List<Pedido> obtenerPedidosPorId(Integer id) {
        return service.obtenerListaPedidosPorId(id);
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(Integer id) {
        List<Pedido> pedidos = new ArrayList<>();
        for (Pedido pedido: service.obtenerListaPedidos()){
            if (pedido.getCliente().getIdCliente().equals(id))
                pedidos.add(pedido);
        }
        return pedidos;
    }

    /**
     * Se obtiene todos los pedidos cargados
     * @return una lista de pedidos
     */
    @Override
    public List<Pedido> obtenerPedidos() {
        return service.obtenerListaPedidos();
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

    @Override
    public Double calcularTotalPedidos(List<Pedido> listPedidos) {
        Double total = 0d;
        for (Pedido pedido: listPedidos)
            if (!pedido.getEstado().equals(Constantes.CANCELADO))
                total += pedido.getImporteTotal();
        return total;
    }

}
