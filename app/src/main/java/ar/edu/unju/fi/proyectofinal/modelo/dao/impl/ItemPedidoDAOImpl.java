package ar.edu.unju.fi.proyectofinal.modelo.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.constantes.SqlUtil;
import ar.edu.unju.fi.proyectofinal.modelo.dao.ItemPedidoDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;

public class ItemPedidoDAOImpl extends BaseDAO implements ItemPedidoDAO {
    private ItemPedido itemPedido;
    private ContentValues contentValues;

    /**
     * Constructor de la clse ItemPedidoDAOImpl
     * @param context
     */
    public ItemPedidoDAOImpl(Context context) {
        super(context);
    }

    /**
     * Inserta un registro en tabla itemPedidos
     */
    @Override
    public Long insert(ItemPedido itemPedido) {
        abrir();
        contentValues = new ContentValues();
        cargarContentValues(itemPedido);
        Long resultado = getDb().insert(SqlUtil.TABLA_ITEM_PEDIDO, null, contentValues);
        cerrar();
        return resultado;
    }

    /**
     * Elimina un registro en tabla itemPedidos
     */
    @Override
    public Integer delete(ItemPedido itemPedido) {
        abrir();
        Integer resultado = getDb().delete(SqlUtil.TABLA_ITEM_PEDIDO, SqlUtil.CAMPOITEM_ID_ITEM + "=" + itemPedido.getIdItemPedido(), null);
        cerrar();
        return resultado;
    }

    /**
     * Obtiene una lista de itemPedidos
     */
    @Override
    public List<ItemPedido> getAll() {
        List<ItemPedido> listaItemPedidos = new ArrayList<>();
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.selectAll(SqlUtil.TABLA_ITEM_PEDIDO),null);
        while (cursor.moveToNext()){
            itemPedido = new ItemPedido();
            cargarItemPedido(cursor);
            listaItemPedidos.add(itemPedido);
        }
        cerrar();
        return listaItemPedidos;
    }

    /**
     * Obtiene una lista de itemPedidos de acuerdo al id del pedido
     */
    @Override
    public List<ItemPedido> getAll(Integer idPedido) {
        List<ItemPedido> listaItemPedidos = new ArrayList<>();
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.selectAll(SqlUtil.TABLA_ITEM_PEDIDO),null);
        while (cursor.moveToNext()){
            itemPedido = new ItemPedido();
            itemPedido.setIdItemPedido(cursor.getInt(0));
            cargarItemPedido(cursor);
            if (itemPedido.getIdPedido().equals(idPedido))
                listaItemPedidos.add(itemPedido);
        }
        cerrar();
        return listaItemPedidos;
    }

    /**
     * Valida si un itenPedido ya esta cargado
     */
    @Override
    public Boolean validarItemPedido(ItemPedido itemPedido) {
        abrir();
        Cursor cursor = getDb().rawQuery("SELECT * FROM "+SqlUtil.TABLA_ITEM_PEDIDO+" WHERE "+
                SqlUtil.CAMPOITEM_ID_PRODUCTO+" LIKE "+"'"+itemPedido.getIdProducto().toString()+"' and " +
                SqlUtil.CAMPOITEM_ID_PEDIDO+" LIKE "+"'"+itemPedido.getIdPedido().toString()+"'", null);
        Boolean encontrado = cursor.moveToFirst();
        cerrar();
        return encontrado;
    }

    private void cargarItemPedido(Cursor cursor){
        itemPedido.setNumeroItem(cursor.getInt(1));
        itemPedido.setCantidad(cursor.getInt(2));
        itemPedido.setSubTotal(cursor.getDouble(3));
        itemPedido.setIdProducto(cursor.getInt(4));
        itemPedido.setIdPedido(cursor.getInt(5));
    }

    private void cargarContentValues(ItemPedido itemPedido){
        contentValues.put(SqlUtil.CAMPOITEM_NUM, itemPedido.getNumeroItem());
        contentValues.put(SqlUtil.CAMPOITEM_UNIDADES, itemPedido.getCantidad());
        contentValues.put(SqlUtil.CAMPOITEM_SUBTOTAL, itemPedido.getSubTotal());
        contentValues.put(SqlUtil.CAMPOITEM_ID_PRODUCTO, itemPedido.getIdProducto());
        contentValues.put(SqlUtil.CAMPOITEM_ID_PEDIDO, itemPedido.getIdPedido());
    }
}
