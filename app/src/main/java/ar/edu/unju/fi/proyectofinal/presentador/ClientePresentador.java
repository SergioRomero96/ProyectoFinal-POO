package ar.edu.unju.fi.proyectofinal.presentador;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.ICliente;
import ar.edu.unju.fi.proyectofinal.modelo.ClienteModelo;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;
import ar.edu.unju.fi.proyectofinal.vista.cliente.AgregarClienteActivity;
import ar.edu.unju.fi.proyectofinal.vista.cliente.ModificarClienteActivity;

public class ClientePresentador implements ICliente.presentadorCliente {
    private ICliente.vistaCliente vistaCliente;
    private ICliente.vistaAgregarCliente vistaAgregar;
    private ICliente.vistaModificarCliente vistaModificar;
    private ICliente.modeloCliente modeloCliente;
    private Service service;

    public ClientePresentador(ICliente.vistaCliente vistaCliente, Context context) {
        this.vistaCliente = vistaCliente;
        modeloCliente = new ClienteModelo(this, context);
    }

    public ClientePresentador(ICliente.vistaAgregarCliente vistaAgregar, AgregarClienteActivity context) {
        this.vistaAgregar = vistaAgregar;
        modeloCliente = new ClienteModelo(this, context);
    }


    public ClientePresentador(ICliente.vistaModificarCliente vistaModificar, ModificarClienteActivity context) {
        this.vistaModificar = vistaModificar;
        modeloCliente = new ClienteModelo(this, context);
    }

    /**
     * Busca un cuit, si existe se asigna una lista de clientes
     * sino se muestra un mensaje de que no existe
     * @param cuit
     */
    @Override
    public void buscarCliente(String cuit) {
        Cliente cliente = modeloCliente.buscarCliente(cuit);
        if (cliente != null){
            List<Cliente> clientes = new ArrayList<>();
            clientes.add(cliente);
            vistaCliente.cargarClienteEncontrado(clientes);
        } else{
            vistaCliente.showInfo("cliente no encontrado");
        }
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoAgregar(String info) {
        vistaAgregar.showInfoAgregar(info);
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoModificar(String info) {
        vistaModificar.showInfoModificar(info);
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoEliminar(String info) {
        vistaModificar.showInfoEliminar(info);
    }

    /**
     * Valida campos vacios, si no esta vacio va a validar el cliente
     * sino muestra un mensaje por pantalla
     * @param campos
     */
    @Override
    public void validarCamposVaciosAgregar(Boolean campos) {
        if (!campos) {
            vistaAgregar.validarCliente();
        } else {
            vistaAgregar.showFormularioVacio(Constantes.OBLIGATORIO);
        }
    }

    /**
     * Valida campos vacios, si no esta vacio va a validar el cliente
     * sino muestra un mensaje por pantalla
     * @param campos
     */
    public void validarCamposVaciosModificar(Boolean campos) {
        if (!campos) {
            vistaModificar.validarCliente();
        } else {
            vistaModificar.showFormularioVacio(Constantes.OBLIGATORIO);
        }
    }

    /**
     * @return retorna una lista de clientes
     */
    @Override
    public List<Cliente> obtenerListadoClientes() {
        return modeloCliente.obtenerClientes();
    }

    /**
     * Valida un cliente por su cuit, si ya existe muestra un mensaje
     * por pantalla, sino manda a agregar el cliente
     * @param cuit
     */
    @Override
    public void validarCliente(String cuit) {
        if (modeloCliente.validarCliente(cuit))
            vistaAgregar.showInfoAgregar("El cliente ya existe");
        else
            vistaAgregar.agregarCliente();
    }

    /**
     * Valida un cliente de acuerdo al cuit o una bandera
     * Si la bandera es verdadera o el cuit es distinto modifica el cliente
     * Sino manda un mensaje por pantalla
     * @param band
     * @param cuit
     */
    @Override
    public void validarCliente(Boolean band, String cuit) {
        if (band || !(modeloCliente.validarCliente(cuit)))
            vistaModificar.modificarCliente();
        else
            vistaModificar.showInfoModificar("El cuit ya esta registrado");
    }

    /**
     * Agrega un cliente
     * @param cliente
     */
    @Override
    public void agregarCliente(Cliente cliente) {
        modeloCliente.agregarCliente(cliente);
    }

    /**
     * Modifica un cliente
     * @param cliente
     */
    @Override
    public void modificarCliente(Cliente cliente) {
        modeloCliente.modificarCliente(cliente);
    }

    /**
     * Elimina un cliente
     * @param cliente
     */
    @Override
    public void eliminarCliente(Cliente cliente) {
        modeloCliente.eliminarCliente(cliente);
    }

    /**
     * Valida que el cliente no tenga pedidos
     * Si tiene pedido muestra un mensaje por pantalla
     * Sino elimina el cliente
     * @param id
     */
    @Override
    public void validarRegistroDePedido(Integer id) {
        if (modeloCliente.validarRegistroDePedido(id))
            vistaModificar.showInfoEliminar("No se pudo eliminar, el cliente tiene pedidos registrados");
        else
            vistaModificar.eliminarCliente();
    }
}
