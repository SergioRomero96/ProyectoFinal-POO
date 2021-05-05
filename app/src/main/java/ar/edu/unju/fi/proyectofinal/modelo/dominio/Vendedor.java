package ar.edu.unju.fi.proyectofinal.modelo.dominio;

import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;

public class Vendedor extends Usuario{


    @Override
    public Boolean isVendedor() {
        return true;
    }

    public Vendedor() {
        super();
    }

    public Vendedor(Integer dni, String clave, String nombre) {
        super(dni, clave, nombre);
        super.setRol(Constantes.VENDEDOR);
    }

    public Vendedor(Integer id, Integer dni, String clave, String nombre) {
        super(id ,dni, clave, nombre);
        super.setRol(Constantes.VENDEDOR);
    }
}
