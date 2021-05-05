package ar.edu.unju.fi.proyectofinal.modelo.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.constantes.SqlUtil;
import ar.edu.unju.fi.proyectofinal.modelo.dao.ProductoDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;

public class ProductoDAOImp extends BaseDAO implements ProductoDAO {
    private Producto producto;
    private ContentValues contentValues;

    /**
     * Constructor de la clase ProductoDAOImp
     * @param context
     */
    public ProductoDAOImp(Context context) {
        super(context);
    }

    /**
     * Inserta un registro de la tabla productos
     */
    @Override
    public Long insert(Producto producto) {
        abrir();
        contentValues = new ContentValues();
        cargarContentValues(producto);
        long resultado = getDb().insert(SqlUtil.TABLA_PRODUCTO, null, contentValues);
        cerrar();
        return resultado;
    }

    /**
     * Elimina un registro de la tabla productos
     */
    @Override
    public int delete(Producto producto) {
        abrir();
        Integer resultado = getDb().delete(SqlUtil.TABLA_PRODUCTO, SqlUtil.CAMPOPROD_ID + "=" + producto.getIdProducto(), null);
        cerrar();
        return resultado;
    }

    /**
     * Actualiza un registro de la tabla productos
     */
    @Override
    public int update(Producto producto) {
        abrir();
        contentValues = new ContentValues();
        cargarContentValues(producto);
        Integer resultado = getDb().update(SqlUtil.TABLA_PRODUCTO, contentValues, SqlUtil.CAMPOPROD_ID + "=" + producto.getIdProducto(), null);
        cerrar();
        return resultado;
    }

    /**
     * Retorna una lista de productos
     */
    @Override
    public List<Producto> getAll() {
        producto = null;
        abrir();
        Cursor cursor = getDb().rawQuery("SELECT * FROM " + SqlUtil.TABLA_PRODUCTO, null);
        List<Producto> listaProductos = new ArrayList<>();
        while (cursor.moveToNext()) {
            producto = new Producto();
            cargarProducto(cursor);

            listaProductos.add(producto);
        }
        cerrar();
        return listaProductos;
    }

    /**
     * Busca si existe un nombre en la tabla productos
     */
    @Override
    public Producto find(String nombre){
        producto = null;
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_PRODUCTO, SqlUtil.CAMPOPROD_NOMBRE, nombre), null);
        if (cursor.moveToFirst()){
            producto = new Producto();
            cargarProducto(cursor);
        }
        cursor.close();
        cerrar();
        return producto;
    }

    /**
     * Recupera el id de un producto
     */
    @Override
    public Producto select(Integer id) {
        producto = null;
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_PRODUCTO, SqlUtil.CAMPOPROD_ID, id.toString()), null);
        if (cursor.moveToFirst()){
            producto = new Producto();
            cargarProducto(cursor);
        }
        cursor.close();
        cerrar();
        return producto;
    }

    /**
     * Valida si ya existe un producto de acuerdo a su nombre
     */
    @Override
    public Boolean validarProducto(Producto producto) {
        abrir();
        Cursor cursor = getDb().rawQuery("SELECT * FROM "+SqlUtil.TABLA_PRODUCTO+" WHERE "+
                SqlUtil.CAMPOPROD_NOMBRE+" LIKE "+"'"+producto.getNombre()+"' and " +
                SqlUtil.CAMPOPROD_TAMANIO+" LIKE "+"'"+producto.getTamanio().toString()+"'", null);
        Boolean encontrado = cursor.moveToFirst();
        cerrar();
        return encontrado;
    }

    private void cargarProducto(Cursor cursor){
        producto.setIdProducto(cursor.getInt(0));
        producto.setNombre(cursor.getString(1));
        producto.setTamanio(cursor.getInt(2));
        producto.setPrecioUnitario(cursor.getDouble(3));
        producto.setEstado(cursor.getString(4));
        producto.setStock(cursor.getInt(5));
    }

    private void cargarContentValues(Producto producto){
        contentValues.put(SqlUtil.CAMPOPROD_NOMBRE, producto.getNombre());
        contentValues.put(SqlUtil.CAMPOPROD_TAMANIO, producto.getTamanio());
        contentValues.put(SqlUtil.CAMPOPROD_PRECIO, producto.getPrecioUnitario());
        contentValues.put(SqlUtil.CAMPOPROD_ESTADO, producto.getEstado());
        contentValues.put(SqlUtil.CAMPOPROD_STOCK, producto.getStock());
    }
}