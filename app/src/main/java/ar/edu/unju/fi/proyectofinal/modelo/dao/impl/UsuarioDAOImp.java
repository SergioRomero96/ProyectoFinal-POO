package ar.edu.unju.fi.proyectofinal.modelo.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.SqlUtil;
import ar.edu.unju.fi.proyectofinal.modelo.dao.SQLiteHelper;
import ar.edu.unju.fi.proyectofinal.modelo.dao.UsuarioDAO;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Administrador;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Vendedor;

public class UsuarioDAOImp extends BaseDAO implements UsuarioDAO {
    private Usuario usuario;
    private ContentValues contentValues;

    /**
     * Constructor de la clase UsuarioDAOImp
     * @param context
     */
    public UsuarioDAOImp(Context context){
        super(context);
    }

    /**
     * Inserta un registro en la tabla usuarios
     */
    @Override
    public Long insert(Usuario usuario) {
        abrir();
        contentValues = new ContentValues();
        cargarContentValues(usuario);
        Long idResultante = getDb().insert(SqlUtil.TABLA_USUARIO, null, contentValues);
        cerrar();
        return idResultante;
    }

    /**
     * Elimina un registro de la tabla usuarios
     */
    @Override
    public int delete(Usuario usuario) {
        abrir();
        Integer resultado = getDb().delete(SqlUtil.TABLA_USUARIO, SqlUtil.CAMPO_ID+" = "+usuario.getIdUsuario(),null);
        cerrar();
        return resultado;
    }

    /**
     * Actualiza un registro de la tabla usuarios
     */
    @Override
    public int update(Usuario usuario) {
        abrir();
        contentValues = new ContentValues();
        cargarContentValues(usuario);
        Integer resultado = getDb().update(SqlUtil.TABLA_USUARIO, contentValues, SqlUtil.CAMPO_ID+"="+usuario.getIdUsuario(),null);
        cerrar();
        return resultado;
    }

    /**
     * Retorna una lista de usuarios de acuerdo al idUsuarioSesion
     */
    @Override
    public List<Usuario> getAll(Integer idUsuarioSesion) {
        usuario = null;
        List<Usuario> listUsuarios = new ArrayList<Usuario>();
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.selectAll(SqlUtil.TABLA_USUARIO),null);
        while(cursor.moveToNext()){
            //usuario = new Usuario();
            cargarUsuario(cursor);
            if(!usuario.getIdUsuario().equals(idUsuarioSesion)){
                listUsuarios.add(usuario);
            }
        }
        cerrar();
        return listUsuarios;
    }

    /**
     * Obtiene una lista de usuarios de vendedores
     */
    @Override
    public List<Usuario> obtenerListaVendedores() {
        usuario = null;
        List<Usuario> listUsuarios = new ArrayList<Usuario>();
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.selectAll(SqlUtil.TABLA_USUARIO),null);
        while(cursor.moveToNext()){
            //usuario = new Usuario();
            cargarUsuario(cursor);
            if(!usuario.getRol().equals("Administrador")){
                listUsuarios.add(usuario);
            }
        }
        cerrar();
        return listUsuarios;
    }

    /**
     * Busca si existe la clave en la tabla usuarios
     */
    @Override
    public Usuario find(String clave) {
        usuario = null;
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_USUARIO,SqlUtil.CAMPO_CLAVE,clave), null);
        try{
            cursor.moveToFirst();
            //usuario = new Usuario();
            cargarUsuario(cursor);
            cursor.close();
            cerrar();
        }catch (Exception e){
            e.printStackTrace();
        }
        return usuario;
    }

    /**
     * Busca si existe el dni en la tabla usuarios
     */
    @Override
    public Usuario findDni(String dni) {
        usuario = null;
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_USUARIO, SqlUtil.CAMPO_DNI, dni), null);
        if (cursor.moveToFirst()) {
            usuario = new Usuario();
            cargarUsuario(cursor);
        }
        cursor.close();
        cerrar();
        return usuario;
    }

    /**
     * Busca un usuario de acuerdo a su id
     */
    @Override
    public Usuario select(Integer id) {
        usuario = null;
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_USUARIO, SqlUtil.CAMPO_ID, id.toString()), null);
        if (cursor.moveToFirst()) {
           //usuario = new Usuario();
            cargarUsuario(cursor);
        }
        cursor.close();
        cerrar();
        return usuario;
    }

    /**
     * Valida si ya existe el usuario de acuerdo a la clave
     */
    @Override
    public Boolean validarUsuario(String clave) {
        Boolean usuarioEncontrado = false;
        abrir();
        Cursor cursor = getDb().rawQuery(SqlUtil.buscarEnLaTabla(SqlUtil.TABLA_USUARIO,SqlUtil.CAMPO_CLAVE,clave), null);
        if(cursor != null){
            if(cursor.getCount() > 0){
                usuarioEncontrado = true;
            }
        }
        cursor.close();
        cerrar();
        return usuarioEncontrado;
    }

    private void cargarUsuario(Cursor cursor){
        if (cursor.getString(4).equals(Constantes.ADMINISTRADOR))
            usuario = new Administrador();
        else usuario = new Vendedor();
        usuario.setIdUsuario(cursor.getInt(0));
        usuario.setDni(cursor.getInt(1));
        usuario.setNombre(cursor.getString(2));
        usuario.setClave(cursor.getString(3));
        usuario.setRol(cursor.getString(4));
    }

    private void cargarContentValues(Usuario usuario){
        contentValues.put(SqlUtil.CAMPO_ID, usuario.getIdUsuario());
        contentValues.put(SqlUtil.CAMPO_DNI, usuario.getDni());
        contentValues.put(SqlUtil.CAMPO_NOMBRE, usuario.getNombre());
        contentValues.put(SqlUtil.CAMPO_CLAVE, usuario.getClave());
        contentValues.put(SqlUtil.CAMPO_ROL, usuario.getRol());
    }
}
