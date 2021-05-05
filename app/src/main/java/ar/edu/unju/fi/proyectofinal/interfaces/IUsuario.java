package ar.edu.unju.fi.proyectofinal.interfaces;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;

public interface IUsuario {
    interface vistaUsuario{
        void cargarLista();
        void cargarUsuarioEncontrado(List<Usuario> usuarios);
        void showInfo(String info);
    }

    interface vistaAgregarUsuario{
        void validarUsuario();
        void validarFormularioVacio();
        void agregarUsuario();
        void showInfoAgregar(String mensaje);
        void showFormularioVacio(String info);
    }

    interface vistaModificarUsuario{
        void showInfoModificar(String info);
        void showInfoEliminar(String info);
        void modificarUsuario();
        void eliminarUsuario();
        void validarFormularioVacio();
        void showFormularioVacio(String info);
        void validarUsuario();
    }

    interface presentadorUsuario{
        void validarFormularioVacioAgregar(Boolean vacio);
        void buscarUsuarioDni(String dni);
        void showInfoAgregar(String info);
        void showInfoModificar(String info);
        void showInfoEliminar(String info);
        List<Usuario> obtenerListaUsuarios();
        void agregarUsuario(Usuario usuario);
        void modificarUsuario(Usuario usuario);
        void eliminarUsuario(Usuario usuario);
        void validarUsuario(String clave);
        void validarUsuario(Boolean band, String clave);
        void validarRegistroDePedidos(Integer id);
        void validarFormularioVacio(Boolean vacio);
    }

    interface modeloUsuario{
        void agregarUsuario(Usuario usuario);
        void modificarUsuario(Usuario usuario);
        void eliminarUsuario(Usuario usuario);
        List<Usuario> obtenerListaUsuarios(Integer id);
        Usuario buscarUsuarioDni(String dni);
        Boolean validarUsuario(String clave);
        Boolean validarRegistroDePedidos(Integer id);
    }
}
