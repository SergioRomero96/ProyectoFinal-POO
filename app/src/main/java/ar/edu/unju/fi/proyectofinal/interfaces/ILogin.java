package ar.edu.unju.fi.proyectofinal.interfaces;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;

public interface ILogin {
    interface vista{
        void showInfo(String info);
        void showFormularioVacio(String info);
        void validarFormularioVacio();
        void validarUsuario(String clave);
        void mostrarMenuAdmin();
        void mostrarMenuVendedor();
    }

    interface Presentador{
        void showInfo(String info);
        void validarFormularioVacio(String clave);
        void validarUsuario(String clave);
        void mostrarMenu(Usuario usuario);
    }

    interface Modelo{
        void validarUsuario(String clave);
    }
}
