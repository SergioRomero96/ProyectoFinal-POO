package ar.edu.unju.fi.proyectofinal.modelo.util;

import android.content.Intent;
import android.os.Bundle;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;

public class UsuarioUtil {
    private static Usuario usuarioEnSesion;
    private static Integer ultimoId;
    public static Usuario getUsuarioEnSesion() {
        return usuarioEnSesion;
    }

    public static Integer getUltimoId() {
        return ultimoId;
    }

    public static void setUltimoId(Integer ultimoId) {
        UsuarioUtil.ultimoId = ultimoId;
    }

    public static void guardarUsuarioEnSesion(Intent intent){
        Bundle extras = ((Intent)intent).getExtras();
        if(extras != null)
            usuarioEnSesion = ((Usuario) extras.getSerializable("usuarioSesion"));
    }

    public static void saveUsuarioEnSesion(Usuario usuario){
        usuarioEnSesion = usuario;
    }

    public static String formatCuit(String cuit){
        return cuit.substring(0,2)+"-"+cuit.substring(2,10)
                + "-" +cuit.substring(10);
    }
}
