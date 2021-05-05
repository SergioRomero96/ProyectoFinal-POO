package ar.edu.unju.fi.proyectofinal.modelo.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ar.edu.unju.fi.proyectofinal.modelo.dao.SQLiteHelper;

public class BaseDAO {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    public BaseDAO(Context context) {
        this.sqLiteHelper = new SQLiteHelper(context);
    }

    /**
     * Abre la conexion a la base de datos
     */
    public void abrir(){
        db = sqLiteHelper.getWritableDatabase();
    }

    /**
     * Cierra la conexion de la base de datos
     */
    public void cerrar(){
        sqLiteHelper.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
