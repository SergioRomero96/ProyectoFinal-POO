package ar.edu.unju.fi.proyectofinal.modelo.dao;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;

public interface ClienteDAO {
      long insert(Cliente cliente);
      int delete(Cliente cliente);
      int update(Cliente cliente);
      List<Cliente> getAll();
      Cliente find(String cuit);
      Cliente select(Integer id);
      boolean validarCliente(String cuit);
}
