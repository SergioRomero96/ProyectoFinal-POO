package ar.edu.unju.fi.proyectofinal.modelo.constantes;

import ar.edu.unju.fi.proyectofinal.modelo.dominio.Administrador;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Vendedor;

public class SqlUtil {
    /**
     * Declaración de los nombres de la tabla de la bd
     */
    public static final String TABLA_USUARIO = "usuarios";
    public static final String TABLA_PRODUCTO = "productos";
    public static final String TABLA_CLIENTE = "clientes";
    public static final String TABLA_ITEM_PEDIDO = "items_pedidos";
    public static final String TABLA_PEDIDO = "pedidos";

    /**
     * Declaración de los campos de la tabla usuarios
     */
    public static final String CAMPO_ID = "id_usuario";
    public static final String CAMPO_DNI = "dni_usuario";
    public static final String CAMPO_NOMBRE = "nombre_usuario";
    public static final String CAMPO_CLAVE = "clave_usuario";
    public static final String CAMPO_ROL = "rol_usuario";

    /**
     * Declaración de los campos de la tabla productos
     */
    public static final String CAMPOPROD_ID = "id_producto";
    public static final String CAMPOPROD_NOMBRE = "nombre_producto";
    public static final String CAMPOPROD_TAMANIO = "tamanio_producto";
    public static final String CAMPOPROD_PRECIO = "precio_producto";
    public static final String CAMPOPROD_ESTADO = "estado_producto";
    public static final String CAMPOPROD_STOCK = "stock_producto";

    /**
     * Declaración de los campos de la tabla clientes
     */
    public static final String CAMPOCLIEN_ID = "id_cliente";
    public static final String CAMPOCLIEN_CUIT = "cuit_cliente";
    public static final String CAMPOCLIEN_NOMBRE = "nombre_cliente";
    public static final String CAMPOCLIEN_DOMICILIO = "domicilio_cliente";

    /**
     * Declaración de los campos de la tabla itemPedidos
     */
    public static final String CAMPOITEM_ID_ITEM = "id_item";
    public static final String CAMPOITEM_NUM = "numItem";
    public static final String CAMPOITEM_UNIDADES = "unidades_producto";
    public static final String CAMPOITEM_SUBTOTAL = "subtotal";
    public static final String CAMPOITEM_ID_PRODUCTO = "id_producto";
    public static final String CAMPOITEM_ID_PEDIDO = "id_pedido";

    /**
     * Declaración de los campos de la tabla pedidos
     */
    public static final String CAMPOPED_ID = "id_pedido";
    public static final String CAMPOPED_FECHA = "fecha_pedido";
    public static final String CAMPOPED_TOTAL = "total_pedido";
    public static final String CAMPOPED_ESTADO = "estado_pedido";
    public static final String CAMPOPED_VENDEDOR = "id_vendedor";
    public static final String CAMPOPED_CLIENTE = "id_cliente";

    /**
     * Instrucción SQL para crear la tabla usuarios en la bd
     */
    public static final String CREATE_TABLE_USUARIO = "CREATE TABLE "+TABLA_USUARIO+" ("+CAMPO_ID
            +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+CAMPO_DNI+" INTEGER, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_CLAVE
            +" TEXT, "+CAMPO_ROL+" TEXT)";

    /**
     * Instrucción SQL para crear la tabla productos en la bd
     */
    public static final String CREATE_TABLE_PRODUCTO = "CREATE TABLE " + TABLA_PRODUCTO + "(" + CAMPOPROD_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + CAMPOPROD_NOMBRE + " TEXT, " + CAMPOPROD_TAMANIO
            + " INTEGER, " + CAMPOPROD_PRECIO + " REAL, " + CAMPOPROD_ESTADO + " TEXT, " + CAMPOPROD_STOCK + " INTEGER)";

    /**
     * Instrucción SQL para crear la tabla clientes en la bd
     */
    public static final String CREATE_TABLE_CLIENTE = "CREATE TABLE " + TABLA_CLIENTE + "(" + CAMPOCLIEN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + CAMPOCLIEN_CUIT + " TEXT, " + CAMPOCLIEN_NOMBRE
            + " TEXT, " + CAMPOCLIEN_DOMICILIO + " TEXT )";

    /**
     * Instrucción SQL para crear la tabla itemPedidos en la bd
     */
    public static final String CREATE_TABLE_ITEM_PEDIDO = "CREATE TABLE " + TABLA_ITEM_PEDIDO + "(" + CAMPOITEM_ID_ITEM
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + CAMPOITEM_NUM + " INTEGER, " + CAMPOITEM_UNIDADES
            + " INTEGER, " + CAMPOITEM_SUBTOTAL + " REAL, " + CAMPOITEM_ID_PRODUCTO + " INTEGER, " + CAMPOITEM_ID_PEDIDO + " INTEGER )";

    /**
     * Instrucción SQL para crear la tabla pedidos en la bd
     */
    public static final String CREATE_TABLE_PEDIDO = "CREATE TABLE " + TABLA_PEDIDO + "(" + CAMPOPED_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + CAMPOPED_FECHA + " TEXT, " + CAMPOPED_TOTAL
            + " REAL, " + CAMPOPED_ESTADO + " TEXT, " + CAMPOPED_CLIENTE + " INTEGER, " + CAMPOPED_VENDEDOR + " INTEGER )";

    public static String dropTable(String nombre){
        return "DROP TABLE IF EXISTS " + nombre;
    }

    public static String buscarEnLaTabla(String tabla, String campoBuscar, String dato){
        return "SELECT * FROM "+tabla+" WHERE "+campoBuscar+" LIKE "+"'"+dato+"'";
    }

    public static String selectAll(String tabla){
        return "SELECT * FROM "+tabla;
    }

    public static String getAdminDefault(){
        Usuario admin = new Administrador(11111111, "admin", "admin");
        return "insert into usuarios(dni_usuario, nombre_usuario, clave_usuario, rol_usuario)"+
                " values ("+admin.getDni()+"," + "'"+admin.getNombre()+"'," +
                "'"+admin.getClave()+"'," + "'"+admin.getRol()+"')";
    }

    public static String getVendedorDefault(){
        Usuario vendedor = new Vendedor(22222222,"1234","vendedor");
        return "insert into usuarios(dni_usuario, nombre_usuario, clave_usuario, rol_usuario)"+
                " values ("+vendedor.getDni()+"," + "'"+vendedor.getNombre()+"'," +
                "'"+vendedor.getClave()+"'," + "'"+vendedor.getRol()+"')";
    }
}
