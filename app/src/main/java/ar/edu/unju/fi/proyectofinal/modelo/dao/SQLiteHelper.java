package ar.edu.unju.fi.proyectofinal.modelo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ar.edu.unju.fi.proyectofinal.modelo.constantes.SqlUtil;

public class SQLiteHelper extends SQLiteOpenHelper {
    /**
     * Nombre de la Base de Datos
     */
    private static final String NAME_DATABASE = "GestionPedido";
    /**
     * Versión de la Base de Datos
     */
    private static final int VERSION = 15;

    public SQLiteHelper(Context context){
        super(context, NAME_DATABASE, null, VERSION);
    }

    /**
     * Genera las tablas con script correspondiente a la entidad
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlUtil.CREATE_TABLE_USUARIO);
        db.execSQL(SqlUtil.CREATE_TABLE_PRODUCTO);
        db.execSQL(SqlUtil.CREATE_TABLE_CLIENTE);
        db.execSQL(SqlUtil.CREATE_TABLE_ITEM_PEDIDO);
        db.execSQL(SqlUtil.CREATE_TABLE_PEDIDO);
        db.execSQL(SqlUtil.getAdminDefault());
        db.execSQL(SqlUtil.getVendedorDefault());
    }

    /**
     * Cada vez que se instale, verifica si ya existe una version antigua en la bd
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            //Borra las tablas si existen en la bd
            db.execSQL("DROP TABLE IF EXISTS " + SqlUtil.TABLA_USUARIO);
            db.execSQL("DROP TABLE IF EXISTS " + SqlUtil.TABLA_PRODUCTO);
            db.execSQL("DROP TABLE IF EXISTS " + SqlUtil.TABLA_CLIENTE);
            db.execSQL("DROP TABLE IF EXISTS " + SqlUtil.TABLA_ITEM_PEDIDO);
            db.execSQL("DROP TABLE IF EXISTS " + SqlUtil.TABLA_PEDIDO);
            //Crea las nuevas tablas en la bd
            onCreate(db);
        }
    }
}
