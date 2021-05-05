package ar.edu.unju.fi.proyectofinal.vista;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.Calendar;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.interfaces.IPedido;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.util.FechaUtil;
import ar.edu.unju.fi.proyectofinal.presentador.PedidoPresentador;
import ar.edu.unju.fi.proyectofinal.vista.adapter.AdaptadorPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;
import ar.edu.unju.fi.proyectofinal.vista.pedido.AgregarPedidoActivity;
import ar.edu.unju.fi.proyectofinal.vista.pedido.DetallePedidoActivity;

public class MenuVendedor extends SuperActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener, IPedido.vistaMenuVendedor {
    private DrawerLayout drawerLayout;
    private Button btnAgregarPedido, btnBuscarPedido;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private AdaptadorPedido adaptadorPedido;
    private ListView listViewPedidos;
    private IPedido.presentadorPedido presentadorPedido;
    private EditText etBusquedaFecha;
    private TextView lblUsuarioSesion, tipoUsuario;
    private Usuario usuarioSesion;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_vendedor);
        setContext(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setupNavigationView();
        usuarioSesion = getService().obtenerUsuario(UsuarioUtil.getUsuarioEnSesion().getIdUsuario());
        cargarUsuarioEnSesion();
        presentadorPedido = new PedidoPresentador(this, this);
        findViewByIdButton();
        listViewPedidos = (ListView) findViewById(R.id.idListPedido);
        listViewPedidos.setOnItemClickListener(this);
        cargarListaPedido();
        showDialogFecha();


    }

    /**
     * Muestra el datePicker para que podamos seetear una fecha en un EditText
     */
    private void showDialogFecha(){
        etBusquedaFecha = (EditText) findViewById(R.id.idTxtBusquedaFechaPedido);
        etBusquedaFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etBusquedaFecha.setText("");

                new DatePickerDialog(MenuVendedor.this,R.style.DialogTheme, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        calendar = Calendar.getInstance();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            etBusquedaFecha.setText(FechaUtil.getFechaAsString(calendar.getTime()));
        }
    };


    @Override
    public void onBackPressed() {
        mostrarMensaje("Para salir, debe Cerrar Sesion");
    }


    /**
     * configura el navigation view del menu vendedor
     */
    public void setupNavigationView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerVend);
        NavigationView navigationView = (NavigationView)  findViewById(R.id.navigationVen);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lblUsuarioSesion = (TextView) headerView.findViewById(R.id.lblUser);
        tipoUsuario = (TextView) headerView.findViewById(R.id.lblTypeUser);
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    public void findViewByIdButton(){
        btnAgregarPedido = (Button) findViewById(R.id.idBtnAddPedido);
        btnAgregarPedido.setOnClickListener(this);
        btnBuscarPedido = (Button) findViewById(R.id.idBtnBuscarPedido1);
        btnBuscarPedido.setOnClickListener(this);

    }

    /**
     * carga los datos del usuario en sesion a los TextView
     */
    public void cargarUsuarioEnSesion(){
        String nombreUsuario = "Bienvenido: "+ usuarioSesion.getNombre();
        lblUsuarioSesion.setText(nombreUsuario);
        if (usuarioSesion.isAdministrador())
            tipoUsuario.setText("Rol: "+Constantes.ADMINISTRADOR);
        else tipoUsuario.setText("Rol: "+Constantes.VENDEDOR);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * segun el item del navigation view que se seleccione manda a determinada actividad
     * @param item
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idNavVenAgregarPedido: llamarAgregarPedido(); break;
            case R.id.idNavVenPerfil: llamarEditarPerfil(this, usuarioSesion); break;
            case R.id.idNavVenSalir: cerrarSesion(this); break;
            case R.id.idNavVenCerca:llamarAcercaDe(this); break;
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerVend);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
            case R.id.idBtnAddPedido:  llamarAgregarPedido();break;
            case R.id.idBtnBuscarPedido1: llamarBuscarPedido(); break;
        }

    }

    /**
     * llama a la activity AgregarPedidoActivity
     */
    private void llamarAgregarPedido(){
        Intent intent = new Intent(this,AgregarPedidoActivity.class);
        startActivity(intent);
    }


    /**
     * Al seleccionar un pedido manda a la activity DetallePedido
     * @param parent
     * @param view
     * @param position
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Intent intent = new Intent(this, DetallePedidoActivity.class);
        intent.putExtra("pedidoSeleccionado", (Pedido) parent.getItemAtPosition(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    /**
     * Metodo que encarga de llamar a la busqueda de pedido
     */
    private void llamarBuscarPedido() {
        if (!etBusquedaFecha.getText().toString().isEmpty()) {
            presentadorPedido.buscarPedido(etBusquedaFecha.getText().toString(), usuarioSesion.getIdUsuario());
        } else {
            cargarListaPedido();
        }
    }

    /**
     * Carga la lista de pedidos del usuario en sesion en el adaptadorPedido
     */
    @Override
    public void cargarListaPedido() {
        adaptadorPedido = new AdaptadorPedido(this, getService().obtenerListaPedidosPorId(usuarioSesion.getIdUsuario()));
        if (!adaptadorPedido.isEmpty()) {
            listViewPedidos.setAdapter(adaptadorPedido);
        }

    }

    /**
     * Carga los pedidos encontrados al adaptador y lo muestra en un listview
     * @param pedidos
     */
    @Override
    public void cargarPedidoEncontrado(List<Pedido> pedidos) {
        adaptadorPedido = new AdaptadorPedido(this, pedidos);
        adaptadorPedido.notifyDataSetChanged();
        listViewPedidos.setAdapter(adaptadorPedido);
        if (pedidos.isEmpty()){
            showInfo("No se encontro ningun pedido");
        }
    }

    @Override
    public void showInfo(String info) {
        mostrarMensaje(info);
    }


}
