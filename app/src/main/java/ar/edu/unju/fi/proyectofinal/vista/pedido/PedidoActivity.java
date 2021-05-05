package ar.edu.unju.fi.proyectofinal.vista.pedido;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;
import ar.edu.unju.fi.proyectofinal.vista.adapter.AdaptadorPedido;
import ar.edu.unju.fi.proyectofinal.interfaces.IPedido;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.presentador.PedidoPresentador;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class PedidoActivity extends SuperActivity implements  AdapterView.OnItemClickListener,View.OnClickListener, IPedido.vistaPedido, AdapterView.OnItemSelectedListener {
    private IPedido.presentadorPedido presentadorPedido;
    private AdaptadorPedido adaptadorPedido;
    private Spinner  spVendedor;
    private ListView listViewPedidos;
    private Button btnBuscarPedido;
    private EditText txtTotal, etFecha;
    private List<Pedido> listaPedidos;
    private List<String> listaVendedores;
    private Usuario usuario;
    private Cliente clienteSeleccionado;
    private LinearLayout layoutBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pedido);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar(getSupportActionBar());
        setContext(this);
        findViewByIdButton();
        presentadorPedido = new PedidoPresentador(this,this);
        obtenerExtras();
        cargarListaPedidos();
        cargarListas();
        ocultarComponentes();
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    public void findViewByIdButton(){
        txtTotal = (EditText) findViewById(R.id.idTxtTotalListPedido);
        spVendedor = (Spinner) findViewById(R.id.idSpnPedVendedor);
        btnBuscarPedido = (Button) findViewById(R.id.idBtnFindPedido);
        layoutBusqueda = (LinearLayout) findViewById(R.id.idLayoutBusqueda);
        listViewPedidos = (ListView) findViewById(R.id.idLvItemPedidoVendedor);
        listViewPedidos.setOnItemClickListener(this);
        btnBuscarPedido.setOnClickListener(this);
    }

    /**
     * Oculta componentes de la vista de acuerdo al usuario en sesión
     * o el cliente seleccionado
     */
    private void ocultarComponentes() {
        if (usuario != null ){
            if (!(usuario.getIdUsuario().equals(UsuarioUtil.getUsuarioEnSesion().getIdUsuario())))
                layoutBusqueda.setVisibility(View.GONE);
        }
        else if (clienteSeleccionado != null)
            layoutBusqueda.setVisibility(View.GONE);
    }

    /**
     * Carga una lista de vendedores
     */
    private void cargarListas() {
        listaVendedores = new ArrayList<>();
        listaVendedores.add("Todos");
        for (Usuario usuario: getService().obtenerListaVendedores()) {
            listaVendedores.add(usuario.getNombre());
        }
        cargarSpinner();
    }

    /**
     * Carga el spinner con el nombre de los vendedore
     */
    private void cargarSpinner(){
        if (!listaVendedores.isEmpty()) {
            ArrayAdapter<CharSequence> adaptadorUsuario = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaVendedores);
            spVendedor.setAdapter(adaptadorUsuario);
            spVendedor.setOnItemSelectedListener(this);
        }
    }

    /**
     * Selecciona un usuario y cliente
     */
    private void obtenerExtras(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            usuario = (Usuario) extras.getSerializable("vendedorSeleccionado");
            clienteSeleccionado = (Cliente) extras.getSerializable("clienteSeleccionado");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Intent intent = new Intent(this, DetallePedidoActivity.class);
        intent.putExtra("pedidoSeleccionado", (Pedido) parent.getItemAtPosition(position));
        startActivity(intent);
    }

    /**
     * Carga una lista de pedidos de acuerdo al usuario o un cliente seleccionado
     */
    @Override
    public void cargarListaPedidos() {
        if (usuario != null)
            listaPedidos = presentadorPedido.obtenerPedidosPorId(usuario.getIdUsuario());
        else if(clienteSeleccionado != null)
            listaPedidos = presentadorPedido.obtenerPedidosPorCliente(clienteSeleccionado.getIdCliente());
        else
            listaPedidos = presentadorPedido.obtenerPedidos();
        adaptadorPedido = new AdaptadorPedido(this, listaPedidos);
        adaptadorPedido.notifyDataSetChanged();
        listViewPedidos.setAdapter(adaptadorPedido);
        txtTotal.setText("$" + String.format("%.2f",presentadorPedido.calcularTotalPedidos(listaPedidos)));
    }

    /**
     * Carga una lista de pedidos de acuerdo al encontrado
     * @param pedidos
     */
    @Override
    public void cargarPedidoEncontrado(List<Pedido> pedidos) {
        adaptadorPedido = new AdaptadorPedido(this, pedidos);
        adaptadorPedido.notifyDataSetChanged();
        listViewPedidos.setAdapter(adaptadorPedido);
    }

    /**
     * Llama a carga la lista de pedidos y si no tiene
     * muestra un mensaje por pantalla
     */
    private void llamarBuscarPedido() {
        cargarListaPedidos();
        if (listaPedidos.isEmpty())
            mostrarMensaje("No tiene pedidos registrados");
    }

    @Override
    public void showInfo(String info) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != 0) {
            usuario = getService().obtenerListaVendedores().get(i-1);
        } else {
            usuario = null;
        }
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
        int id = view.getId();
        switch (id){
            case R.id.idBtnAddPedido: break;
            case R.id.idBtnFindPedido: llamarBuscarPedido(); break;
        }
    }
}
