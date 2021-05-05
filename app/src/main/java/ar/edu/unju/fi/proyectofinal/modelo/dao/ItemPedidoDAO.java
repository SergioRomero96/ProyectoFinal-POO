package ar.edu.unju.fi.proyectofinal.modelo.dao;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;

public interface ItemPedidoDAO {
     Long insert(ItemPedido itemPedido);
     Integer delete(ItemPedido itemPedido);
     List<ItemPedido> getAll();
     List<ItemPedido> getAll(Integer idPedido);
     Boolean validarItemPedido(ItemPedido itemPedido);
}