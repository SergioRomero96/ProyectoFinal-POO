package ar.edu.unju.fi.proyectofinal.modelo.services;

import android.content.Context;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dao.ClienteDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dao.ItemPedidoDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dao.PedidoDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dao.ProductoDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dao.UsuarioDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dao.impl.ClienteDAOImp;
import ar.edu.unju.fi.proyectofinal.modelo.dao.impl.ItemPedidoDAOImpl;
import ar.edu.unju.fi.proyectofinal.modelo.dao.impl.PedidoDAOImp;
import ar.edu.unju.fi.proyectofinal.modelo.dao.impl.ProductoDAOImp;
import ar.edu.unju.fi.proyectofinal.modelo.dao.impl.UsuarioDAOImp;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;

/**
 * Implementacion del patron facade
 */
public class Service {
    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    private PedidoDAO pedidoDAO;
    private ItemPedidoDAO itemPedidoDAO;

    /**
     * Constructor de la clase Service
     * @param context
     */
    public Service(Context context) {
        usuarioDAO = new UsuarioDAOImp(context);
        productoDAO = new ProductoDAOImp(context);
        clienteDAO = new ClienteDAOImp(context);
        pedidoDAO = new PedidoDAOImp(context);
        itemPedidoDAO = new ItemPedidoDAOImpl(context);
    }

    /**
     * Operaciones para usuario
     */
    public Boolean validarUsuario(String clave){
        return usuarioDAO.validarUsuario(clave);
    }
    public Usuario buscarUsuario(String clave){
        return usuarioDAO.find(clave);
    }
    public Usuario buscarUsuarioDni(String dni){
        return usuarioDAO.findDni(dni);
    }
    public Long agregarUsuario(Usuario usuario){
        return usuarioDAO.insert(usuario);
    }
    public Integer modificarUsuario(Usuario usuario){
        return usuarioDAO.update(usuario);
    }
    public Integer eliminarUsuario(Usuario usuario){
        return usuarioDAO.delete(usuario);
    }
    public List<Usuario> obtenerListaUsuarios(Integer id){
        return usuarioDAO.getAll(id);
    }
    public Usuario obtenerUsuario(Integer idUsuario) {
        return usuarioDAO.select(idUsuario);
    }
    public List<Usuario> obtenerListaVendedores() {
        return usuarioDAO.obtenerListaVendedores();
    }

    /**
     * Operaciones para Cliente
     */
    public Boolean validarCliente(String cuit){
        return clienteDAO.validarCliente(cuit);
    }
    public Cliente buscarCliente(String cuit){
        return clienteDAO.find(cuit);
    }
    public Long agregarCliente(Cliente cliente){
        return clienteDAO.insert(cliente);
    }
    public Integer modificarCliente(Cliente cliente){
        return clienteDAO.update(cliente);
    }
    public Integer eliminarCliente(Cliente cliente){
        return clienteDAO.delete(cliente);
    }
    public List<Cliente> obtenerListaClientes(){
        return clienteDAO.getAll();
    }
    public Cliente obtenerCliente(Integer idCliente) {
        return clienteDAO.select(idCliente);
    }

    /**
     * Operaciones para Producto
     */
    public Boolean validarProducto(Producto producto){
        return productoDAO.validarProducto(producto);
    }
    public Producto buscarProducto(String nombre){
        return productoDAO.find(nombre);
    }
    public Producto obtenerProducto(Integer idPedido){
        return productoDAO.select(idPedido);
    }
    public Long agregarProducto(Producto producto){
        return productoDAO.insert(producto);
    }
    public Integer modificarProducto(Producto producto){
        return productoDAO.update(producto);
    }
    public Integer eliminarProducto(Producto producto){
        return productoDAO.delete(producto);
    }
    public List<Producto> obtenerListaProductos(){
        return productoDAO.getAll();
    }

    /**
     * Operaciones para ItemPedido
     */
    public Boolean validarItemPedido(ItemPedido itemPedido){
        return itemPedidoDAO.validarItemPedido(itemPedido);
    }
    public Long agregarItemPedido(ItemPedido itemPedido){
        return  itemPedidoDAO.insert(itemPedido);
    }
    public Integer eliminarItemPedido(ItemPedido itemPedido){
        return itemPedidoDAO.delete(itemPedido);
    }
    public List<ItemPedido> obtenerListaItemsPedido(Integer idPedido){
        return itemPedidoDAO.getAll(idPedido);
    }
    public List<ItemPedido> obtenerListaItemsPedido() {
        return itemPedidoDAO.getAll();
    }

    /**
     * Operaciones para pedido
     */
    public Long agregarPedido(Pedido pedido){
        return pedidoDAO.insert(pedido);
    }
    public Pedido buscarPedido(String fecha){
        return pedidoDAO.find(fecha);
    }
    public Integer modificarPedido(Pedido pedido){
        return pedidoDAO.update(pedido);
    }
    public List<Pedido> obtenerListaPedidos(){
        return pedidoDAO.getAll();
    }
    public List<Pedido> obtenerListaPedidosPorId(Integer id){
        return pedidoDAO.getPedidosPorId(id);
    }
}
