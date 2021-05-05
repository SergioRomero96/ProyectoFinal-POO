package ar.edu.unju.fi.proyectofinal.modelo.dao;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;

public interface UsuarioDAO {
     Long insert(Usuario usuario);
     int delete(Usuario usuario);
     int update(Usuario usuario);
     List<Usuario> getAll(Integer idUsuarioSesion);
     List<Usuario> obtenerListaVendedores();
     Usuario find(String clave);
     Usuario findDni(String dni);
     Usuario select(Integer id);
     Boolean validarUsuario(String clave);
}
