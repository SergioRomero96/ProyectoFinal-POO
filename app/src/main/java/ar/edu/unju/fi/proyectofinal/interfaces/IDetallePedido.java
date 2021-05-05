package ar.edu.unju.fi.proyectofinal.interfaces;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;

public interface IDetallePedido {
    interface vistaDetallePedido{
        void showInfoDetallePedido(String info);
        void obtenerDetallesPedidos();
        void agregarDetallePedido();
        void eliminarDetallePedido();
        void validarUnidadDetallePedido();
        void validarCantidadInferiorDetallePedido();
        void validarItemDetallePedido();
        void confirmarPedido();
        void cancelarPedido();
        void compartirLista(String text);
        void cargarListaDetallePedidoCompartir();
    }

    interface presentadorDetallePedido{
        Usuario obtenerVendedor(Integer id);
        Cliente obtenerCliente(Integer id);
        List<ItemPedido> obtenerItemsPedido(Integer id);
        Double calcularTotal(List<ItemPedido> listItems);
        void showInfoDetallePedido(String info);
        void confirmarPedido(Pedido pedido);
        void cancelarPedido(Pedido pedido);
        void agregarItemPedido(ItemPedido itemPedido);
        void eliminarDetallePedido(ItemPedido itemPedido);
        void validarCantidadDetallePedido(Integer cantidad);
        void validarCantidadInferiorDetallePedido(Integer cantidad, Integer stock);
        void validarItemDetallePedido(ItemPedido itemPedido);
        void modificarPedido(Pedido pedido);
        void cargarListaDetallePedidoCompartir(Integer id);
        void cargarListaDetalle(String text);
    }

    interface modeloDetallePedido{
        Usuario obtenerVendedor(Integer id);
        Cliente obtenerCliente(Integer id);
        List<ItemPedido> obtenerItemsPedido(Integer id);
        Double calcularTotal(List<ItemPedido> listItems);
        void confirmarPedido(Pedido pedido);
        void cancelarPedido(Pedido pedido);
        void agregarItemPedido(ItemPedido itemPedido);
        void eliminarItemPedido(ItemPedido itemPedido);
        Boolean validarItemPedido(ItemPedido itemPedido);
        void modificarPedido(Pedido pedido);
        void cargarListaDetallePedido(Integer id);
    }
}
