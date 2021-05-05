package ar.edu.unju.fi.proyectofinal.vista.pedido;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.vista.adapter.AdaptadorItemPedido;
import ar.edu.unju.fi.proyectofinal.interfaces.IPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.modelo.util.FechaUtil;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;
import ar.edu.unju.fi.proyectofinal.presentador.PedidoPresentador;
import ar.edu.unju.fi.proyectofinal.vista.MenuVendedor;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class AgregarPedidoActivity extends SuperActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener ,IPedido.vistaAgregarPedido{
    private Button btnAgregarItem, btnConfirmar;
    private Spinner spCliente, spProducto;
    private  List<String> listaProductos, listaClientes;
    private ListView lvListaItems;
    private EditText etFecha,etUnidad,etTotal;
    private IPedido.presentadorPedido presentadorPedido;
    private ItemPedido itemPedido;
    private Pedido pedido;
    private Producto productoSeleccionado;
    private Cliente clienteSeleccionado;
    private AdaptadorItemPedido adaptadorItemPedido;
    private List<ItemPedido> listaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pedido);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar(getSupportActionBar());
        setContext(this);
        findViewByIdAgregarPedido();
        listaItems = new ArrayList<>();
        cargarLista();
        presentadorPedido = new PedidoPresentador(this,this);
        pedido = new Pedido();
        pedido.setIdPedido(getProxIdPedido());
    }

    /**
     * Hace referencia a los componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdAgregarPedido(){
        spProducto = (Spinner) findViewById(R.id.idSpProductoPedido);
        spCliente = (Spinner) findViewById(R.id.idSpClientePedido);
        etFecha = (EditText) findViewById(R.id.idTxtFechaPedido);
        etTotal = (EditText) findViewById(R.id.idTxtTotalPedido);
        etUnidad = (EditText) findViewById(R.id.idTxtUnidadPedido);
        btnAgregarItem =(Button ) findViewById(R.id.idBtnAddProductoPedido);
        btnConfirmar = (Button) findViewById(R.id.idBtnRegistrarPedido);
        lvListaItems = (ListView) findViewById(R.id.idLvItemPedido);
        etFecha.setText(FechaUtil.getFechaAsString());
        btnAgregarItem.setOnClickListener(this);
        btnConfirmar.setOnClickListener(this);
    }

    /**
     * Obtiene listas de productos y clientes y los guarda en dos
     * arraylist de string los nombres de productos y clientes
     */
    private void cargarLista() {
        listaProductos = new ArrayList<>();
        listaClientes = new ArrayList<>();
        for (Producto producto: getService().obtenerListaProductos()) {
            listaProductos.add(producto.toString());
        }
        for (Cliente cliente: getService().obtenerListaClientes()) {
            listaClientes.add(cliente.getNombre());
        }
        cargarSpinner();
    }

    /**
     * Cargar los spinners correspondientes con una lista de nombres
     * de productos y clientes
     */
    private void cargarSpinner(){
        if (!listaProductos.isEmpty()) {
            ArrayAdapter<CharSequence> adaptadorProducto = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProductos);
            spProducto.setAdapter(adaptadorProducto);
            spProducto.setOnItemSelectedListener(this);
        }
        if (!listaClientes.isEmpty()) {
            ArrayAdapter<CharSequence> adaptadorCliente = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listaClientes);
            spCliente.setAdapter(adaptadorCliente);
            spCliente.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        if (parent.getId() == R.id.idSpClientePedido) {
            clienteSeleccionado = getService().obtenerListaClientes().get(i);
        } else if (parent.getId() == R.id.idSpProductoPedido) {
            productoSeleccionado = getService().obtenerListaProductos().get(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Carga los datos de un pedido
     */
    private void cargarPedido(){
        pedido.setCliente(clienteSeleccionado);
        pedido.setVendedor(UsuarioUtil.getUsuarioEnSesion());
        pedido.setFechaDelPedido(FechaUtil.getFechaAsDate(etFecha.getText().toString()));
        pedido.setImporteTotal(presentadorPedido.calcularTotal(listaItems));
    }

    /**
     * carga los detalles de un pedido
     */
    private void cargarItemPedido() {
        itemPedido = new ItemPedido();
        if (listaItems.isEmpty()) itemPedido.setNumeroItem(1);
        else itemPedido.setNumeroItem(listaItems.size()+1);
        itemPedido.setIdProducto(productoSeleccionado.getIdProducto());
        itemPedido.setIdPedido(pedido.getIdPedido());
        if (!etUnidad.getText().toString().isEmpty()) itemPedido.setCantidad(Integer.parseInt(etUnidad.getText().toString()));
        else itemPedido.setCantidad(0);
        itemPedido.setSubTotal(productoSeleccionado.getPrecioUnitario() * itemPedido.getCantidad());
    }

    /**
     * Muestra un mensaje por pantalla si un itemPedido
     * se agrego correctamente
     * @param info
     */
    @Override
    public void showInfoAgregarItem(String info) {
        mostrarMensaje(info);
    }

    /**
     * Agrega un item(producto) a la lista
     *
     */
    @Override
    public void agregarItemPedido() {
        presentadorPedido.agregarItemPedido(listaItems, itemPedido);
        adaptadorItemPedido = new AdaptadorItemPedido(this,listaItems);
        lvListaItems.setAdapter(adaptadorItemPedido);
        adaptadorItemPedido.notifyDataSetChanged();
        Double total = presentadorPedido.calcularTotal(listaItems);
        etTotal.setText("$" + String.format("%.2f", total));
        etUnidad.setText("");
    }

    /**
     * Valida la cantidad del producto del itemPedido
     */
    @Override
    public void validarUnidad() {
        cargarItemPedido();
        presentadorPedido.validarCantidad(itemPedido.getCantidad());
    }

    /**
     * Valida que la cantidad sea inferior o igual al stock disponible
     */
    @Override
    public void validarCantidadInferior() {
        presentadorPedido.validarCantidadInferior(itemPedido.getCantidad(), productoSeleccionado.getStock());
    }

    /**
     * Valida que si el itemPedido ya esta cargado
     */
    @Override
    public void validarItemPedido() {
        presentadorPedido.validarItemPedido(listaItems, itemPedido);
    }

    /**
     * Valida que haya items cargados
     */
    @Override
    public void validarItemRegistrados() {
        presentadorPedido.validarItemDelPedido(listaItems);
    }

    /**
     * Actualiza el valor del/los producto/s
     */
    @Override
    public void actualizarStockProducto() {
        presentadorPedido.actualizarStockProducto(listaItems);
    }

    /**
     * Agrega un pedido
     */
    @Override
    public void agregarPedido() {
        cargarPedido();
        presentadorPedido.agregarPedido(pedido);
        llamarMenuVendedor(this);
        finish();
    }

    /**
     * Guarda una lista de items cargados
     */
    @Override
    public void guardarListaItems() {
        presentadorPedido.guardarListaItems(listaItems);
    }

    /**
     * Segun el boton que se presione en la vista
     * manda a una determinada actividad
     * @param view
     */
    @Override
    public void onClick(View view) {
        Integer id = view.getId();
        switch (id){
            case R.id.idBtnAddProductoPedido:{
                validarUnidad();
                break;
            }
            case R.id.idBtnRegistrarPedido:{
                validarItemRegistrados();
                break;
            }
        }
    }
}
