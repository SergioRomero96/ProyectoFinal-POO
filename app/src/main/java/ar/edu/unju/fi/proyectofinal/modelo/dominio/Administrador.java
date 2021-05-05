package ar.edu.unju.fi.proyectofinal.modelo.dominio;

import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;

public class Administrador extends Usuario {


    public Administrador() {

    }

    public Administrador(Integer dni, String clave, String nombre) {
        super(dni, clave, nombre);
        super.setRol(Constantes.ADMINISTRADOR);
    }


    @Override
    public Boolean isAdministrador() {
        return true;
    }

    public Administrador(Integer id, Integer dni, String clave, String nombre) {
        super(id ,dni, clave, nombre);
        super.setRol(Constantes.ADMINISTRADOR);
    }
}
