package ar.edu.unju.fi.proyectofinal.modelo.dominio;

import java.io.Serializable;

public class Cliente implements Serializable {
    private Integer idCliente;
    private String cuit;
    private String nombre;
    private String domicilio;

    /**
     * Constructor por defecto de la clase Cliente
     */
    public Cliente() {}

    /**
     * Constructor de la clase Cliente
     * @param cuit
     * @param nombre
     * @param domicilio
     */
    public Cliente(String cuit, String nombre, String domicilio) {
        this.cuit = cuit;
        this.nombre = nombre;
        this.domicilio = domicilio;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", cuit='" + cuit + '\'' +
                ", nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                '}';
    }
}
