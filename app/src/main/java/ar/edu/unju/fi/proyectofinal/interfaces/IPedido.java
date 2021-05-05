package ar.edu.unju.fi.proyectofinal.interfaces;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;

public interface IPedido {


    interface vistaAgregarPedido{
        void showInfoAgregarItem(String info);
        void agregarItemPedido();
        void validarUnidad();
        void validarCantidadInferior();
        void validarItemPedido();
        void validarItemRegistrados();
        void actualizarStockProducto();
        void agregarPedido();
        void guardarListaItems();
    }

    interface  vistaMenuVendedor{
        void cargarListaPedido();
        void cargarPedidoEncontrado(List<Pedido> pedidos);
        void showInfo(String info);
    }


    interface vistaPedido{
        void cargarListaPedidos();
        void cargarPedidoEncontrado(List<Pedido> pedidos);
        void showInfo(String info);
    }

    interface presentadorPedido{
        void buscarPedido(String fecha, Integer id);
        void showInfoAgregarItem(String info);
        void validarCantidad(Integer cantidad);
        void validarCantidadInferior(Integer unidad, Integer stock);
        void validarItemPedido(List<ItemPedido> listItems, ItemPedido itemPedido);
        void agregarItemPedido(List<ItemPedido> listItems, ItemPedido itemPedido);
        void validarItemDelPedido(List<ItemPedido> listItems);
        void actualizarStockProducto(List<ItemPedido> listItems);
        void agregarPedido(Pedido pedido);
        void precargarItemsPedidos();
        void guardarListaItems(List<ItemPedido> listItems);
        void guardarPedido();
        List<Pedido> obtenerPedidosPorId(Integer id);
        List<Pedido> obtenerPedidosPorCliente(Integer id);
        List<Pedido> obtenerPedidos();
        Double calcularTotalPedidos(List<Pedido> listPedidos);

        Double calcularTotal(List<ItemPedido> listItems);
    }

    interface modeloPedido{
        List<Pedido> obtenerPedidoPorFecha(String fecha, Integer id);
        void guardarListaItems(List<ItemPedido> listItems);
        Boolean validarItemPedido(List<ItemPedido> listItems, ItemPedido itemPedido);
        void agregarItemPedido(List<ItemPedido> listItems, ItemPedido itemPedido);
        void actualizarStockProductos(List<ItemPedido> listItems);
        void agregarPedido(Pedido pedido);
        List<Pedido> obtenerPedidosPorId(Integer id);
        List<Pedido> obtenerPedidosPorCliente(Integer id);
        List<Pedido> obtenerPedidos();
        Double calcularTotalPedidos(List<Pedido> listPedidos);
        Double calcularTotal(List<ItemPedido> listItems);
    }
}
