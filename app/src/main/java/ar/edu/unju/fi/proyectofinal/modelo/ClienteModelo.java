package ar.edu.unju.fi.proyectofinal.modelo;

import android.content.Context;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.ICliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;

public class ClienteModelo implements ICliente.modeloCliente {
    private Service service;
    private ICliente.presentadorCliente presentadorCliente;

    /**
     * Constructor de la clase de ClienteModelo
     * @param presentadorCliente
     * @param context
     */
    public ClienteModelo(ICliente.presentadorCliente presentadorCliente, Context context) {
        this.service = new Service(context);
        this.presentadorCliente = presentadorCliente;
    }

    /**
     * Agrega un cliente si este devuelve un Long
     * Sino muestra un mensaje por pantalla
     * @param cliente
     */
    @Override
    public void agregarCliente(Cliente cliente) {
        Long resultado = service.agregarCliente(cliente);
        if (resultado > 0)
            presentadorCliente.showInfoAgregar("Cliente agregado correctamente");
        else
            presentadorCliente.showInfoAgregar("Error al agregar");
    }

    /**
     * Modifica un cliente si se devuelve un Integer
     * Sino muestra un mensaje por pantall
     * @param cliente
     */
    @Override
    public void modificarCliente(Cliente cliente) {
        Integer resultado = service.modificarCliente(cliente);
        if (resultado > 0){
            presentadorCliente.showInfoModificar("Cliente actualizado");
        }
        else
            presentadorCliente.showInfoModificar("Error al actualizar");
    }

    /**
     * Elimina un cliente si se devuelve un Integer
     * Sino muestra un mensaje por pantalla
     * @param cliente
     */
    @Override
    public void eliminarCliente(Cliente cliente) {
        Integer resultado = service.eliminarCliente(cliente);
        if (resultado > 0)
            presentadorCliente.showInfoEliminar("Cliente eliminado");
        else
            presentadorCliente.showInfoEliminar("Error al eliminar");
    }

    /**
     * Obtiene un lista de clientes
     * @return
     */
    @Override
    public List<Cliente> obtenerClientes() {
        return service.obtenerListaClientes();
    }

    /**
     * Busca un cliente por su cuit
     * @param cuit
     * @return un cliente
     */
    @Override
    public Cliente buscarCliente(String cuit) {
        return service.buscarCliente(cuit);
    }

    /**
     * Valida si el cliente tiene pedidos asignados
     * @param id
     * @return
     */
    @Override
    public Boolean validarRegistroDePedido(Integer id) {
        Boolean encontrado = false;
        for(Pedido pedido: service.obtenerListaPedidos()){
            if (pedido.getCliente().getIdCliente().equals(id)){
                encontrado = true; break;
            }
        }
        return encontrado;
    }

    /**
     * Valida un cliente por su cuit
     * @param cuit
     * @return
     */
    @Override
    public Boolean validarCliente(String cuit) {
        return service.validarCliente(cuit);
    }
}
