package ar.edu.unju.fi.proyectofinal.modelo.dominio;

import java.io.Serializable;

public class Producto implements Serializable {
    private Integer idProducto;
    private String nombre;
    private Integer tamanio;
    private Double precioUnitario;
    private String estado;
    private Integer stock;

    /**
     * Constructor por defecto de la clase Producto
     */
    public Producto() {}

    /**
     * Constructor de la clase Producto
     * @param idProducto
     * @param nombre
     * @param tamanio
     * @param precioUnitario
     * @param estado
     * @param stock
     */
    public Producto(Integer idProducto, String nombre, Integer tamanio, Double precioUnitario, String estado, Integer stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.precioUnitario = precioUnitario;
        this.estado = estado;
        this.stock = stock;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTamanio() {
        return tamanio;
    }

    public void setTamanio(Integer tamanio) {
        this.tamanio = tamanio;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return getNombre()+" "+getTamanio()+"cm3";
    }
}
