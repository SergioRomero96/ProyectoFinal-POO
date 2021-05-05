package ar.edu.unju.fi.proyectofinal.presentador;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.IUsuario;
import ar.edu.unju.fi.proyectofinal.modelo.UsuarioModelo;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;
import ar.edu.unju.fi.proyectofinal.vista.usuario.AgregarUsuarioActivity;
import ar.edu.unju.fi.proyectofinal.vista.usuario.ModificarUsuarioActivity;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class UsuarioPresentador extends SuperActivity implements IUsuario.presentadorUsuario {
    private IUsuario.modeloUsuario modelo;
    private IUsuario.vistaUsuario vista;
    private IUsuario.vistaAgregarUsuario vistaAgregar;
    private IUsuario.vistaModificarUsuario vistaModificar;
    private Service service;

    public UsuarioPresentador(IUsuario.vistaUsuario vista, Context context) {
        this.vista = vista;
        modelo = new UsuarioModelo(this, context);
    }

    public UsuarioPresentador(IUsuario.vistaAgregarUsuario vista, AgregarUsuarioActivity context) {
        this.vistaAgregar = vista;
        modelo = new UsuarioModelo(this,context);
    }

    public UsuarioPresentador(IUsuario.vistaModificarUsuario vista, ModificarUsuarioActivity context) {
        this.vistaModificar = vista;
        modelo = new UsuarioModelo(this,context);
    }

    /**
     * Valida campos vacios, si no esta vacio va a validar el usuario
     * sino muestra un mensaje por pantalla
     * @param vacio
     */
    @Override
    public void validarFormularioVacioAgregar(Boolean vacio) {
        if (!vacio){
            vistaAgregar.validarUsuario();
        } else {
            vistaAgregar.showFormularioVacio(Constantes.OBLIGATORIO);
        }
    }

    /**
     * Busca un usuario por dni
     * Si existe lo guarda en una lista y manda a cargar el usuario encontrado
     * Sino muestra mensaje por pantalla
     * @param dni
     */
    @Override
    public void buscarUsuarioDni(String dni) {
        Usuario usuario = modelo.buscarUsuarioDni(dni);
        if (usuario != null){
            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(usuario);
            vista.cargarUsuarioEncontrado(usuarios);
        } else {
            vista.showInfo("Dni no encontrado");
        }
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoAgregar(String info) {
        vistaAgregar.showInfoAgregar(info);
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoModificar(String info) {
        vistaModificar.showInfoModificar(info);
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoEliminar(String info) {
        vistaModificar.showInfoEliminar(info);
    }

    /**
     * Obtiene un lista de usuarios de acuerdo al id
     * @return
     */
    @Override
    public List<Usuario> obtenerListaUsuarios() {
        Integer id = UsuarioUtil.getUsuarioEnSesion().getIdUsuario();
        return modelo.obtenerListaUsuarios(id);
    }

    /**
     * Agrega un usuario
     * @param usuario
     */
    @Override
    public void agregarUsuario(Usuario usuario) {
        modelo.agregarUsuario(usuario);
    }

    /**
     * Modifica un usuario
     * @param usuario
     */
    @Override
    public void modificarUsuario(Usuario usuario) {
        modelo.modificarUsuario(usuario);
    }

    /**
     * Elimina un usuario
     * @param usuario
     */
    @Override
    public void eliminarUsuario(Usuario usuario) {
        modelo.eliminarUsuario(usuario);
    }

    /**
     * Valida un usuario por su clave
     * Si es verdadero muestra un mensaje por pantalla
     * Sino va a agregar el usuario
     * @param clave
     */
    @Override
    public void validarUsuario(String clave) {
        if (!modelo.validarUsuario(clave)){
            vistaAgregar.agregarUsuario();
        } else {
            vistaAgregar.showInfoAgregar("Clave ya registrada");
        }
    }

    /**
     * Valida un usuario de acuerdo a la clave o una bandera
     * Si la bandera es verdadera o la clave es distinto modifica el usuario
     * Sino muestra un mensaje por pantalla
     * @param band
     * @param clave
     */
    @Override
    public void validarUsuario(Boolean band, String clave) {
        if (band || !(modelo.validarUsuario(clave))){
            vistaModificar.modificarUsuario();
        } else {
            vistaModificar.showInfoModificar("Clave ya registrada");
        }
    }

    /**
     * Valida que el usuario no tenga pedidos
     * Si tiene pedido muestra un mensaje por pantalla
     * Sino elimina el usuario
     * @param id
     */
    @Override
    public void validarRegistroDePedidos(Integer id) {
        if (modelo.validarRegistroDePedidos(id))
            vistaModificar.showInfoEliminar("No se pudo eliminar, el vendedor tiene pedidos registrados");
        else
            vistaModificar.eliminarUsuario();
    }

    /**
     * Valida que el formulario no este vacio
     * Si no esta vacio va a validar el usuario
     * Sino muestra mensaje por pantalla
     * @param vacio
     */
    @Override
    public void validarFormularioVacio(Boolean vacio) {
        if (!vacio) {
            vistaModificar.validarUsuario();
        } else {
            vistaModificar.showFormularioVacio(Constantes.OBLIGATORIO);
        }
    }

}
