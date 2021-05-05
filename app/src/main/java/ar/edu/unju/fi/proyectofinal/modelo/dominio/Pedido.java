package ar.edu.unju.fi.proyectofinal.modelo.dominio;

import java.io.Serializable;
import java.util.Date;

import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;

public class Pedido implements Serializable {
    private Integer idPedido;
    private Usuario vendedor;
    private Cliente cliente;
    private Date fechaDelPedido = new Date();
    private Double importeTotal;
    private String estado = Constantes.INICIADO;

    /**
     * Constructor por defecto de la clase Pedido
     */
    public Pedido(){
        cliente = new Cliente();
        vendedor = new Vendedor();
    }

    /**
     * Constructor de la clase Pedido
     * @param idPedido
     * @param vendedor
     * @param cliente
     * @param importeTotal
     * @param estado
     */
    public Pedido(Integer idPedido, Vendedor vendedor, Cliente cliente, Double importeTotal, String estado) {
        this.idPedido = idPedido;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.importeTotal = importeTotal;
        this.estado = estado;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaDelPedido() {
        return fechaDelPedido;
    }

    public void setFechaDelPedido(Date fechaDelPedido) {
        this.fechaDelPedido = fechaDelPedido;
    }

    public Double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", vendedor=" + vendedor +
                ", cliente=" + cliente +
                ", fechaDelPedido=" + fechaDelPedido +
                ", importeTotal=" + importeTotal +
                ", estado='" + estado + '\'' +
                '}';
    }
}
