package ar.edu.unju.fi.proyectofinal.modelo.dominio;

import java.io.Serializable;

public class Usuario implements Serializable {
    private Integer idUsuario;
    private Integer dni;
    private String clave;
    private String nombre;
    private String rol;
    private static Integer idProxDisponible = 1;

    /**
     * Constructor por defecto de la clase Usuario
     */
    public Usuario(){}

    /**
     * Constructor de la clase Usuario
     * @param dni
     * @param clave
     * @param nombre
     */
    public Usuario(Integer dni, String clave, String nombre) {
        this.dni = dni;
        this.clave = clave;
        this.nombre = nombre;
        idProxDisponible++;
    }

    /**
     * Constructor de la clase Usuario
     * @param id
     * @param dni
     * @param clave
     * @param nombre
     */
    public Usuario(Integer id, Integer dni, String clave, String nombre) {
        this.idUsuario = id;
        this.dni = dni;
        this.clave = clave;
        this.nombre = nombre;
        idProxDisponible++;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public Boolean isAdministrador(){
        return false;
    }

    public Boolean isVendedor(){
        return false;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public static Integer getIdProxDisponible() {
        return idProxDisponible;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", dni=" + dni +
                ", clave='" + clave + '\'' +
                ", nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
