package ar.edu.unju.fi.proyectofinal.modelo.dao;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;

public interface PedidoDAO {
     Long insert(Pedido pedido);
     Integer update(Pedido pedido);
     List<Pedido> getAll();
     List<Pedido> getPedidosPorId(Integer id);
     Pedido find(String pedido);
}
