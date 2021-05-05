package ar.edu.unju.fi.proyectofinal.modelo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaUtil {
    public static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public static String getFechaAsString(){
        return format.format(new Date());
    }
    public static String getFechaAsString(Date date){
        return format.format(date);
    }

    public static Date getFechaAsDate(String fechaString){
        try {
            return format.parse(fechaString);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
