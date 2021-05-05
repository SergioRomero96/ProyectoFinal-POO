package ar.edu.unju.fi.proyectofinal.vista.pedido;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.interfaces.IDetallePedido;
import ar.edu.unju.fi.proyectofinal.presentador.DetallePedidoPresentador;
import ar.edu.unju.fi.proyectofinal.vista.adapter.AdaptadorItemPedido;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.modelo.util.FechaUtil;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class DetallePedidoActivity extends SuperActivity implements AdapterView.OnItemClickListener, IDetallePedido.vistaDetallePedido, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ListView listViewItemPedidos;
    private TextView tvFecha, tvVendedor, tvCliente, tvTotal;
    private Button btnVerDetalle, btnModificarDetalle, btnConfirmarPedido, btnCancelarPedido;
    private Button btnAgregarDetalle, btnCompartir;
    private Spinner spProductos;
    private EditText etUnidad;
    private AdaptadorItemPedido adaptadorItemPedido;
    private ArrayAdapter<CharSequence> adaptadorProductos;
    private LinearLayout layoutContenedorModificar, layoutContenedorDetalle, layoutContenedorPedido;
    private Pedido pedidoSeleccionado;
    private Boolean modificado = false, confirmarPedido = false, cancelarPedido = false;
    private List<String> listProductos = new ArrayList<>();
    private Producto productoSeleccionado;
    private ItemPedido itemPedido;
    private IDetallePedido.presentadorDetallePedido presentadorPedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_detalle_pedido);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar(getSupportActionBar());
        setContext(this);
        presentadorPedido = new DetallePedidoPresentador(this,this);
        findViewByIdDetallePedido();
        setComponentesDetallePedido();
        obtenerDetallesPedidos();
        verificarEstadoPedido();
        cargarLista();
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdDetallePedido(){
        btnCompartir = (Button) findViewById(R.id.idCompartir);
        spProductos = (Spinner) findViewById(R.id.idSpProductoModPedido);
        btnAgregarDetalle = (Button) findViewById(R.id.idBtnAddModDetallePedido);
        btnCancelarPedido = (Button) findViewById(R.id.idBtnCancelarPedido);
        btnConfirmarPedido = (Button) findViewById(R.id.idBtnConfirmarPedido);
        btnModificarDetalle = (Button) findViewById(R.id.idBtnModificarDetallePedido);
        btnVerDetalle = (Button) findViewById(R.id.idBtnVerDetallePedido);
        etUnidad = (EditText) findViewById(R.id.idTxtUnidadModPedido);
        layoutContenedorModificar = (LinearLayout) findViewById(R.id.idContenedorModificar);
        layoutContenedorDetalle = (LinearLayout) findViewById(R.id.idContenedorDetalle);
        layoutContenedorPedido = (LinearLayout) findViewById(R.id.idContenedorConfirmarPedido);
        listViewItemPedidos = (ListView) findViewById(R.id.idListItemPedido);
        tvCliente = (TextView) findViewById(R.id.idTvDetalleCliente);
        tvFecha = (TextView) findViewById(R.id.idTvDetalleFecha);
        tvTotal = (TextView) findViewById(R.id.idTvDetalleTotal);
        tvVendedor = (TextView) findViewById(R.id.idTvDetalleVendedor);
    }

    private void setComponentesDetallePedido(){
        layoutContenedorModificar.setVisibility(View.GONE);
        layoutContenedorDetalle.setVisibility(View.GONE);
        btnAgregarDetalle.setOnClickListener(this);
        btnCancelarPedido.setOnClickListener(this);
        btnConfirmarPedido.setOnClickListener(this);
        btnModificarDetalle.setOnClickListener(this);
        btnVerDetalle.setOnClickListener(this);
        listViewItemPedidos.setOnItemClickListener(this);
        btnCompartir.setOnClickListener(this);
    }

    /**
     * Carga una lista de productos y lo asigna a un adaptador de producto
     */
    private void cargarLista(){
        for (Producto producto: getService().obtenerListaProductos()) {
            listProductos.add(producto.toString());
        }
        if (!listProductos.isEmpty()) {
            adaptadorProductos = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listProductos);
            spProductos.setAdapter(adaptadorProductos);
            spProductos.setOnItemSelectedListener(this);
        }
    }

    /**
     * Verifica el estado el estado del pedido o si el usuario en sesion
     * es un administrador
     */
    private void verificarEstadoPedido(){
        if (pedidoSeleccionado.getEstado().equals(Constantes.CONFIRMADO) ||
                pedidoSeleccionado.getEstado().equals(Constantes.CANCELADO) ||
                UsuarioUtil.getUsuarioEnSesion().isAdministrador()) {
            ocultarComponentes();
        }
        if (pedidoSeleccionado.getEstado().equals(Constantes.CANCELADO) ||
                UsuarioUtil.getUsuarioEnSesion().isAdministrador())
            btnCompartir.setVisibility(View.GONE);
    }

    /**
     * Oculta componentes si estado del pedido es CONFIRMADO o CANCELADO,
     * o el usuario en sesión es un administrador
     */
    private void ocultarComponentes(){
        btnConfirmarPedido.setVisibility(View.GONE);
        btnModificarDetalle.setVisibility(View.GONE);
        btnCancelarPedido.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        if (modificado){
            itemPedido = (ItemPedido) parent.getItemAtPosition(i);
            dialog("Eliminar Producto","Desea eliminar el producto? ");
        }
    }

    private void dialog(String titulo, String info){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(info);
        builder.setTitle(titulo);
        confirmarDialog(builder);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void confirmarDialog(AlertDialog.Builder builder){
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (confirmarPedido)
                    confirmarPedido();
                else if (cancelarPedido)
                    cancelarPedido();
                else
                    eliminarDetallePedido();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfoDetallePedido(String info) {
        mostrarMensaje(info);
    }

    /**
     * Obtiene el detalle de un pedido seleccionado
     */
    @Override
    public void obtenerDetallesPedidos() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            pedidoSeleccionado = (Pedido) extras.getSerializable("pedidoSeleccionado");
            Integer id = pedidoSeleccionado.getIdPedido();
            cargarDatosPedido();
            adaptadorItemPedido = new AdaptadorItemPedido(this, presentadorPedido.obtenerItemsPedido(id));
            listViewItemPedidos.setAdapter(adaptadorItemPedido);
        }
    }

    /**
     * Obtiene el ultimo numero del itemPedido +1
     * para asignarle al proximo itempedido
     * @return
     */
    private Integer getNumeroItem(){
        Integer numeroItem = 1;
        if (!(getService().obtenerListaItemsPedido(pedidoSeleccionado.getIdPedido()).isEmpty())){
            Integer pos = getService().obtenerListaItemsPedido(pedidoSeleccionado.getIdPedido()).size()-1;
            numeroItem = getService().obtenerListaItemsPedido(pedidoSeleccionado.getIdPedido()).get(pos).getNumeroItem() + 1;
        }
        return numeroItem;
    }

    private void cargarItemPedido(){
        itemPedido = new ItemPedido();
        itemPedido.setNumeroItem(getNumeroItem());
        itemPedido.setIdProducto(productoSeleccionado.getIdProducto());
        itemPedido.setIdPedido(pedidoSeleccionado.getIdPedido());
        if (!etUnidad.getText().toString().isEmpty()) itemPedido.setCantidad(Integer.parseInt(etUnidad.getText().toString()));
        else itemPedido.setCantidad(0);
        itemPedido.setSubTotal(productoSeleccionado.getPrecioUnitario() * itemPedido.getCantidad());
    }

    /**
     * Agrega un detalle al pedido
     */
    @Override
    public void agregarDetallePedido() {
        presentadorPedido.agregarItemPedido(itemPedido);
        adaptadorItemPedido = new AdaptadorItemPedido(this,getService().obtenerListaItemsPedido(pedidoSeleccionado.getIdPedido()));
        listViewItemPedidos.setAdapter(adaptadorItemPedido);
        adaptadorItemPedido.notifyDataSetChanged();
        presentadorPedido.modificarPedido(pedidoSeleccionado);
        obtenerTotalActualizado();
        listProductos.clear();
        cargarLista();
    }

    /**
     * Elimina un detalle del pedido
     */
    @Override
    public void eliminarDetallePedido() {
        if (getService().obtenerListaItemsPedido(pedidoSeleccionado.getIdPedido()).size() > 1) {
            presentadorPedido.eliminarDetallePedido(itemPedido);
            adaptadorItemPedido = new AdaptadorItemPedido(this,getService().obtenerListaItemsPedido(pedidoSeleccionado.getIdPedido()));
            listViewItemPedidos.setAdapter(adaptadorItemPedido);
            adaptadorItemPedido.notifyDataSetChanged();
            presentadorPedido.modificarPedido(pedidoSeleccionado);
            obtenerTotalActualizado();
            listProductos.clear();
            cargarLista();
        } else {
            mostrarMensaje("El pedido tiene que tener al menos un producto");
        }
    }

    /**
     * Valida la cantidad del producto del itemPedido
     */
    @Override
    public void validarUnidadDetallePedido() {
        presentadorPedido.validarCantidadDetallePedido(itemPedido.getCantidad());
    }

    /**
     * Valida que la cantidad sea inferior o igual al stock disponible
     */
    @Override
    public void validarCantidadInferiorDetallePedido() {
        presentadorPedido.validarCantidadInferiorDetallePedido(itemPedido.getCantidad(), productoSeleccionado.getStock());
    }

    /**
     * Valida que si el itemPedido ya esta cargado
     */
    @Override
    public void validarItemDetallePedido() {
        presentadorPedido.validarItemDetallePedido(itemPedido);
    }

    /**
     * Obtiene el total actualizado de una lista de itemsPedidos
     */
    private void obtenerTotalActualizado(){
        Double total = presentadorPedido.calcularTotal(getService().obtenerListaItemsPedido(pedidoSeleccionado.getIdPedido()));
        tvTotal.setText("$" + String.format("%.2f", total));
    }

    /**
     * Carga el detalle de un pedido de acuerdo al cliente o vendedor seleccionado
     */
    private void cargarDatosPedido(){
        pedidoSeleccionado.setCliente(presentadorPedido.obtenerCliente(pedidoSeleccionado.getCliente().getIdCliente()));
        pedidoSeleccionado.setVendedor(presentadorPedido.obtenerVendedor(pedidoSeleccionado.getVendedor().getIdUsuario()));
        tvVendedor.setText(pedidoSeleccionado.getVendedor().getNombre());
        tvTotal.setText("$" + String.format("%.2f", pedidoSeleccionado.getImporteTotal()));
        tvFecha.setText(FechaUtil.getFechaAsString(pedidoSeleccionado.getFechaDelPedido()));
        tvCliente.setText(pedidoSeleccionado.getCliente().getNombre());
    }

    /**
     * Confirma un pedido
     */
    @Override
    public void confirmarPedido() {
        presentadorPedido.confirmarPedido(pedidoSeleccionado);
        llamarMenuVendedor(this);
    }

    /**
     * Cancela un pedido
     */
    @Override
    public void cancelarPedido() {
        presentadorPedido.cancelarPedido(pedidoSeleccionado);
        llamarMenuVendedor(this);
    }

    @Override
    public void compartirLista(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent = intent.putExtra(Intent.EXTRA_TEXT, text);
        Intent chooser = Intent.createChooser(intent, text);
        if (intent.resolveActivity(getPackageManager())!= null)
            startActivity(chooser);
    }

    @Override
    public void cargarListaDetallePedidoCompartir() {
        Integer id = pedidoSeleccionado.getIdPedido();
        presentadorPedido.cargarListaDetallePedidoCompartir(id);
    }

    /**
     * Carga un itemPedido y valida la unidad
     */
    private void precargarItemPedido() {
        cargarItemPedido();
        validarUnidadDetallePedido();
    }

    /**
     * Confirma un pedido
     */
    private void verConfirmarPedido(){
        confirmarPedido = true;
        dialog("Confirmar Pedido","¿Desea confirmar el pedido?");
    }

    /**
     * Cancela un pedido
     */
    private void verCancelarPedido(){
        cancelarPedido = true;
        dialog("Cancelar Pedido","¿Desea cancelar el pedido?");
    }

    /**
     * Pone en modificado en true y llama al metodo mostrarComponentesModificar
     */
    private void verModificarDetalle() {
        modificado = true;
        mostrarComponentesModificar();
    }

    /**
     * Pone en modificado en false y llama al metodo mostrarComponentesModificar
     */
    private void verDetalle() {
        modificado = false;
        mostrarComponentesModificar();
    }

    /**
     * Si modificado es true muestra el contenedor de modificar y detalle
     * Si es false, muestra el contenedor de pedido y detalle
     */
    private void mostrarComponentesModificar() {
        if (modificado){
            layoutContenedorModificar.setVisibility(View.VISIBLE);
            layoutContenedorDetalle.setVisibility(View.VISIBLE);
            layoutContenedorPedido.setVisibility(View.GONE);
        } else {
            layoutContenedorPedido.setVisibility(View.VISIBLE);
            layoutContenedorModificar.setVisibility(View.GONE);
            layoutContenedorDetalle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        productoSeleccionado = getService().obtenerListaProductos().get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
            case R.id.idBtnVerDetallePedido: verDetalle(); break;
            case R.id.idBtnModificarDetallePedido: verModificarDetalle(); break;
            case R.id.idBtnConfirmarPedido: verConfirmarPedido(); break;
            case R.id.idBtnCancelarPedido: verCancelarPedido(); break;
            case R.id.idBtnAddModDetallePedido: precargarItemPedido();break;
            case R.id.idCompartir: cargarListaDetallePedidoCompartir(); break;
        }
    }

}