package ar.edu.unju.fi.proyectofinal.modelo.dominio;

public class ItemPedido {
    private Integer idItemPedido;
    private Integer idPedido;
    private Integer numeroItem;
    private Integer idProducto;
    private Integer cantidad;
    private Double subTotal;

    /**
     * Constructor por defecto de la clase ItemPedido
     */
    public ItemPedido(){
    }

    /**
     * Constructor de la clase ItemPedido
     * @param idItemPedido
     * @param idPedido
     * @param idProducto
     * @param cantidad
     * @param subTotal
     */
    public ItemPedido(Integer idItemPedido, Integer idPedido, Integer idProducto, Integer cantidad, Double subTotal) {
        this.idItemPedido = idItemPedido;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public Integer getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Integer idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(Integer numeroItem) {
        this.numeroItem = numeroItem;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItemPedido=" + idItemPedido +
                ", idPedido=" + idPedido +
                ", numeroItem=" + numeroItem +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", subTotal=" + subTotal +
                '}';
    }
}
