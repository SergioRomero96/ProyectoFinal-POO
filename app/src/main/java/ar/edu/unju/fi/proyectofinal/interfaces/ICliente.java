package ar.edu.unju.fi.proyectofinal.interfaces;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;

public interface ICliente {
    interface vistaCliente{
        void cargarListaCliente();
        void cargarClienteEncontrado(List<Cliente> clientes);
        void showInfo(String info);
    }

    interface vistaAgregarCliente{
        void showInfoAgregar(String info);
        void validarCamposVacios();
        void showFormularioVacio(String info);
        void validarCliente();
        void agregarCliente();
    }

    interface vistaModificarCliente{
        void showInfoModificar(String info);
        void showInfoEliminar(String info);
        void validarFormularioVacio();
        void showFormularioVacio(String info);
        void validarCliente();
        void modificarCliente();
        void eliminarCliente();
    }

    interface presentadorCliente{
        void buscarCliente(String cuit);
        void showInfoAgregar(String info);
        void showInfoModificar(String info);
        void showInfoEliminar(String info);
        void validarCamposVaciosAgregar(Boolean campos);
        void validarCamposVaciosModificar(Boolean campos);
        List<Cliente> obtenerListadoClientes();
        void validarCliente(String cuit);
        void validarCliente(Boolean band, String cuit);
        void agregarCliente(Cliente cliente);
        void modificarCliente(Cliente cliente);
        void eliminarCliente(Cliente cliente);
        void validarRegistroDePedido(Integer id);
    }

    interface modeloCliente{
        void agregarCliente(Cliente cliente);
        void modificarCliente(Cliente cliente);
        void eliminarCliente(Cliente cliente);
        List<Cliente> obtenerClientes();
        Cliente buscarCliente(String cuit);
        Boolean validarRegistroDePedido(Integer id);
        Boolean validarCliente(String cuit);
    }

}
