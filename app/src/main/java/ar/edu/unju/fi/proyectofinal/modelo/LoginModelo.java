package ar.edu.unju.fi.proyectofinal.modelo;

import android.content.Context;

import ar.edu.unju.fi.proyectofinal.interfaces.ILogin;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;

public class LoginModelo implements ILogin.Modelo {
    private ILogin.Presentador presentador;
    private Service service;

    /**
     * Constructor de la clase LoginModelo
     * @param presentador
     * @param context
     */
    public LoginModelo(ILogin.Presentador presentador, Context context) {
        this.presentador = presentador;
        service = new Service(context);
    }

    /**
     * Valida un usuario por su clave
     * Si es correcta muestra el menu de administrador o vendedor
     * Sino muestra un mensaje por pantalla
     * @param clave
     */
    @Override
    public void validarUsuario(String clave) {
        if (service.validarUsuario(clave)) {
            Usuario usuarioSesion = service.buscarUsuario(clave);
            UsuarioUtil.saveUsuarioEnSesion(usuarioSesion);
            presentador.mostrarMenu(usuarioSesion);
        } else {
            presentador.showInfo(Constantes.INCORRECTA);
        }
    }
}
