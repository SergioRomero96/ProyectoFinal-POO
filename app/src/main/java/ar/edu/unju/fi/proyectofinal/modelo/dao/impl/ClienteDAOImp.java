package ar.edu.unju.fi.proyectofinal.modelo.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.constantes.SqlUtil;
import ar.edu.unju.fi.proyectofinal.modelo.dao.ClienteDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;

public class ClienteDAOImp extends BaseDAO implements ClienteDAO {
    private Cliente cliente;
    private ContentValues contentValues;

    /**
     * Constructor de la clase ClienteDAOImp
     * @param context
     */
    public ClienteDAOImp(Context context) {
        super(context);
    }

    /**
     * Inserta un registro en la tabla productos
     */
    @Override
    public long insert(Cliente cliente) {
        abrir();
        contentValues = new ContentValues();
        cargarContentValues(cliente);
        Long resultado = getDb().insert(SqlUtil.TABLA_CLIENTE, null, contentValues);
        cerrar();
        return resultado;
    }

    /**
     * Elimina un registro en la tabla productos
     */
    @Override
    public int delete(Cliente cliente) {
        abrir();
        Integer resultado = getDb().delete(SqlUtil.TABLA_CLIENTE, SqlUtil.CAMPOCLIEN_ID + "=" + cliente.getIdCliente(), null);
        cerrar();
        return resultado;
    }

    /**
     * Actualiza un registro en la tabla productos
     */
    @Override
    public int update(Cliente cliente) {
        abrir();
        contentValues = new ContentValues();
        cargarContentValues(cliente); // agregado
        Integer resultado = getDb().update(SqlUtil.TABLA_CLIENTE, contentValues, SqlUtil.CAMPOCLIEN_ID + "=" + cliente.getIdCliente(), null);
        cerrar();
        return resultado;
    }

    /**
     * Retorna una lista de cliente
     */
    @Override
    public List<Cliente> getAll() {
        cliente = null;
        List<Cliente> listaCliente = new ArrayList<Cliente>();
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.selectAll(SqlUtil.TABLA_CLIENTE), null);
        while (cursor.moveToNext()) {
            cliente = new Cliente();

            cargarCliente(cursor);

            listaCliente.add(cliente);
        }
        cerrar();
        return listaCliente;
    }

    /**
     * Busca si existe un cliente con el cuit ingresado
     */
    @Override
    public Cliente find(String cuit) {
        cliente = null;
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_CLIENTE, SqlUtil.CAMPOCLIEN_CUIT, cuit), null);
        if (cursor.moveToFirst()) {
            cliente = new Cliente();
            cargarCliente(cursor);
        }
        cursor.close();
        cerrar();
        return cliente;
    }

    /**
     * Retorna el id de un cliente
     */
    @Override
    public Cliente select(Integer id) {
        cliente = null;
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_CLIENTE, SqlUtil.CAMPOCLIEN_ID, id.toString()), null);
        if (cursor.moveToFirst()) {
            cliente = new Cliente();
            cargarCliente(cursor);
        }
        cursor.close();
        cerrar();
        return cliente;
    }

    /**
     * Valida si el cliente esta en la tabla de acuerdo a su cuit
     */
    @Override
    public boolean validarCliente(String cuit) {
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_CLIENTE, SqlUtil.CAMPOCLIEN_CUIT, cuit.toString()), null);
        try{
            Boolean encontrado = cursor.moveToFirst();
            cursor.close();
            cerrar();
            return encontrado;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void cargarCliente(Cursor cursor){
        cliente.setIdCliente(cursor.getInt(0));
        cliente.setCuit(cursor.getString(1));
        cliente.setNombre(cursor.getString(2));
        cliente.setDomicilio(cursor.getString(3));
    }

    private void cargarContentValues(Cliente cliente){
        contentValues.put(SqlUtil.CAMPOCLIEN_CUIT, cliente.getCuit());
        contentValues.put(SqlUtil.CAMPOCLIEN_NOMBRE, cliente.getNombre());
        contentValues.put(SqlUtil.CAMPOCLIEN_DOMICILIO, cliente.getDomicilio());
    }
}
