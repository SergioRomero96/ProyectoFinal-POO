package ar.edu.unju.fi.proyectofinal.presentador;

import android.content.Context;
import android.content.Intent;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.interfaces.ILogin;
import ar.edu.unju.fi.proyectofinal.modelo.LoginModelo;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Administrador;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Vendedor;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;
import ar.edu.unju.fi.proyectofinal.vista.MenuAdmin;
import ar.edu.unju.fi.proyectofinal.vista.MenuVendedor;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class LoginPresentador extends SuperActivity implements ILogin.Presentador {
    private ILogin.vista vista;
    private ILogin.Modelo modelo;
    private Context context;

    public LoginPresentador(ILogin.vista vista,Context context) {
        this.vista = vista;
        this.context = context;
        modelo = new LoginModelo(this, context);
    }

    @Override
    public void showInfo(String info) {
        if (vista != null)
            vista.showInfo(info);
    }

    @Override
    public void validarFormularioVacio(String clave) {
        if (!clave.isEmpty()){
            vista.validarUsuario(clave);
        } else {
            vista.showFormularioVacio(Constantes.OBLIGATORIO);
        }
    }

    @Override
    public void validarUsuario(String clave) {
        if (vista != null){
            modelo.validarUsuario(clave);
        }
    }

    @Override
    public void mostrarMenu(Usuario usuario) {
        if (vista != null){
            //Usuario usuarioSesion = obtenerTipoUsuario(UsuarioUtil.getUsuarioEnSesion());
            if(usuario.isAdministrador()) {
                vista.mostrarMenuAdmin();
            } else {
                vista.mostrarMenuVendedor();
            }
        }
    }
}
