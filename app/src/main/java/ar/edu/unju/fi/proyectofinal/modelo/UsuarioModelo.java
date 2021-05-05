package ar.edu.unju.fi.proyectofinal.modelo;

import android.content.Context;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.IUsuario;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;

public class UsuarioModelo implements IUsuario.modeloUsuario {
    private IUsuario.presentadorUsuario presentador;
    Service service;

    /**
     * Constructor de la clase UsuarioModelo
     * @param presentadorUsuario
     * @param context
     */
    public UsuarioModelo(IUsuario.presentadorUsuario presentadorUsuario, Context context) {
        this.presentador = presentadorUsuario;
        this.service = new Service(context);
    }

    /**
     * Agrega un usuario si registro es mayor a cero
     * @param usuario
     */
    @Override
    public void agregarUsuario(Usuario usuario) {
        Long registro = service.agregarUsuario(usuario);
        if(registro > 0) {
            presentador.showInfoAgregar("Usuario agregado correctamente");
        }
    }

    /**
     * Modifica un usuario si registro es mayor a cero
     * Sino muestra un mensaje de error por pantalla
     * @param usuario
     */
    @Override
    public void modificarUsuario(Usuario usuario) {
        int registro = service.modificarUsuario(usuario);
        if(registro > 0)
            presentador.showInfoModificar("Usuario actualizado");
        else
            presentador.showInfoModificar("Error al actualizar");
    }

    /**
     * Elimina un usuario si registro es mayor a cero
     * @param usuario
     */
    @Override
    public void eliminarUsuario(Usuario usuario) {
        int registro = service.eliminarUsuario(usuario);
        if(registro > 0)
            presentador.showInfoEliminar("Usuario eliminado");
    }

    /**
     * Obtiene una lista de usuarios de acuerdo a su id
     * @param id
     * @return una lista de usuarios
     */
    @Override
    public List<Usuario> obtenerListaUsuarios(Integer id) {
        return service.obtenerListaUsuarios(id);
    }

    /**
     * Busca un usuario por su dni
     * @param dni
     * @return un usuario
     */
    @Override
    public Usuario buscarUsuarioDni(String dni) {
        return service.buscarUsuarioDni(dni);
    }

    /**
     * Valida un usuario por la clave
     * @param clave
     * @return true si esta la clase o false si no lo esta
     */
    @Override
    public Boolean validarUsuario(String clave) {
        return service.validarUsuario(clave);
    }

    /**
     * Valida si el usuario tiene pedidos asignados
     * @param id
     * @return true si tiene o false si no tiene pedidos
     */
    @Override
    public Boolean validarRegistroDePedidos(Integer id) {
        Boolean encontrado = false;
        for (Pedido pedido : service.obtenerListaPedidos()){
            if (pedido.getVendedor().getIdUsuario().equals(id)){
                encontrado = true; break;
            }
        }
        return encontrado;
    }
}
