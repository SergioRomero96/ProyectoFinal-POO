package ar.edu.unju.fi.proyectofinal.modelo.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.constantes.SqlUtil;
import ar.edu.unju.fi.proyectofinal.modelo.dao.PedidoDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dao.SQLiteHelper;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.util.FechaUtil;

public class PedidoDAOImp extends BaseDAO implements PedidoDAO {
    private ContentValues contentValues;
    private Pedido pedido;

    /**
     * Constructor de la clase PedidoDAOImp
     * @param context
     */
    public PedidoDAOImp(Context context) {
        super(context);
    }

    /**
     * Inserta un registro en la tabla pedidos
     */
    @Override
    public Long insert(Pedido pedido) {
        abrir();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlUtil.CAMPOPED_FECHA, FechaUtil.getFechaAsString(pedido.getFechaDelPedido()));
        contentValues.put(SqlUtil.CAMPOPED_TOTAL, pedido.getImporteTotal());
        contentValues.put(SqlUtil.CAMPOPED_ESTADO, pedido.getEstado());
        contentValues.put(SqlUtil.CAMPOPED_CLIENTE, pedido.getCliente().getIdCliente());
        contentValues.put(SqlUtil.CAMPOPED_VENDEDOR, pedido.getVendedor().getIdUsuario());
        Long resultado = getDb().insert(SqlUtil.TABLA_PEDIDO,null,contentValues);
        cerrar();
        return resultado;
    }

    /**
     * Actualiza un registro en la tabla pedidos
     */
    @Override
    public Integer update(Pedido pedido) {
        abrir();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlUtil.CAMPOPED_FECHA, FechaUtil.getFechaAsString(pedido.getFechaDelPedido()));
        contentValues.put(SqlUtil.CAMPOPED_TOTAL, pedido.getImporteTotal());
        contentValues.put(SqlUtil.CAMPOPED_ESTADO, pedido.getEstado());
        contentValues.put(SqlUtil.CAMPOPED_CLIENTE, pedido.getCliente().getIdCliente());
        contentValues.put(SqlUtil.CAMPOPED_VENDEDOR, pedido.getVendedor().getIdUsuario());
        Integer resultado = getDb().update(SqlUtil.TABLA_PEDIDO,contentValues,SqlUtil.CAMPOPED_ID + "=" + pedido.getIdPedido(), null);
        cerrar();
        return resultado;
    }

    /**
     * Obtiene una lista de pedidos
     */
    @Override
    public List<Pedido> getAll() {
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.selectAll(SqlUtil.TABLA_PEDIDO),null);
        List<Pedido> pedidos = new ArrayList<>();
        while (cursor.moveToNext()){
            Pedido pedido = new Pedido();
            pedido.setIdPedido(cursor.getInt(0));
            pedido.setFechaDelPedido(FechaUtil.getFechaAsDate(cursor.getString(1)));
            pedido.setImporteTotal(cursor.getDouble(2));
            pedido.setEstado(cursor.getString(3));
            pedido.getCliente().setIdCliente(cursor.getInt(4));
            pedido.getVendedor().setIdUsuario(cursor.getInt(5));
            pedidos.add(pedido);
        }
        cerrar();
        return pedidos;
    }

    /**
     * Obtiene una lista de pedidos de acuerdo al id de usuario
     */
    @Override
    public List<Pedido> getPedidosPorId(Integer id) {
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.selectAll(SqlUtil.TABLA_PEDIDO),null);
        List<Pedido> pedidos = new ArrayList<>();
        while (cursor.moveToNext()){
            if (id.equals(cursor.getInt(5))) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(cursor.getInt(0));
                pedido.setFechaDelPedido(FechaUtil.getFechaAsDate(cursor.getString(1)));
                pedido.setImporteTotal(cursor.getDouble(2));
                pedido.setEstado(cursor.getString(3));
                pedido.getCliente().setIdCliente(cursor.getInt(4));
                pedido.getVendedor().setIdUsuario(cursor.getInt(5));
                pedidos.add(pedido);
            }
        }
        cerrar();
        return pedidos;
    }

    /**
     * Busca una fecha determina en la tabla pedidos
     */
    @Override
    public Pedido find(String fecha) {
        Pedido pedido = null;
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_PEDIDO, SqlUtil.CAMPOPED_FECHA, fecha.toString()), null);
        if (cursor.moveToFirst()) {
            pedido = new Pedido();
            pedido.setIdPedido(cursor.getInt(0));
            pedido.setFechaDelPedido(FechaUtil.getFechaAsDate(cursor.getString(1)));
            pedido.setImporteTotal(cursor.getDouble(2));
            pedido.setEstado(cursor.getString(3));
            pedido.getCliente().setIdCliente(cursor.getInt(4));
            pedido.getVendedor().setIdUsuario(cursor.getInt(5));
        }
        cursor.close();
        cerrar();
        return pedido;
    }

    /**
     * Metodo a implementar
     */
    private void cargarItemPedido(){

    }

    /**
     * Metodo a implementar
     */
    private void cargarContentValues(){

    }
}
