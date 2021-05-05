package ar.edu.unju.fi.proyectofinal.vista;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ar.edu.unju.fi.proyectofinal.R;
import  ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;
import ar.edu.unju.fi.proyectofinal.vista.usuario.ModificarUsuarioActivity;

public class MenuAdmin  extends SuperActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    TextView lblUsuarioSesion, tipoUsuario;
    Usuario usuarioSesion;
    Button botonCliente, botonUsuario, botonProducto, botonPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContext(this);
        usuarioSesion = getService().obtenerUsuario(UsuarioUtil.getUsuarioEnSesion().getIdUsuario());
        setupNavigationView();
        cargarUsuarioEnSesion();
        findViewByIdMenuAdmin();
    }

    @Override
    public void onBackPressed() {
        mostrarMensaje("Para salir, debe Cerrar Sesion");
    }

    /**
     * configura el navigation view
     * y hace referencia a los componentes TextView del NavigationView para luego seteear
     * el nombre del usuario en sesion y tipo
     */
    public void setupNavigationView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView navigationView = (NavigationView)  findViewById(R.id.navigation);
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
    public void findViewByIdMenuAdmin(){
        botonCliente = (Button) findViewById(R.id.idBotCliente);
        botonCliente.setOnClickListener(this);
        botonUsuario = (Button) findViewById(R.id.idBotUsuario);
        botonUsuario.setOnClickListener(this);
        botonProducto = (Button) findViewById(R.id.idBotProducto);
        botonProducto.setOnClickListener(this);
        botonPedido = (Button) findViewById(R.id.idBotPedido);
        botonPedido.setOnClickListener(this);
    }

    /**
     * carga los datos del usuario en sesion a los TextView
     */
    public void cargarUsuarioEnSesion(){
        lblUsuarioSesion.setText("Bienvenido: "+ usuarioSesion.getNombre());
        if (usuarioSesion.isAdministrador())
            tipoUsuario.setText("Rol: "+usuarioSesion.getRol());
        else tipoUsuario.setText("Rol: "+usuarioSesion.getRol());
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
            case R.id.idNavSalir: cerrarSesion(this); break;
            case R.id.idNavCliente: llamarCliente(this); break;
            case R.id.idNavUsuario: llamarUsuario(this); break;
            case R.id.idNavProducto: llamarProducto(this); break;
            case R.id.idNavPedido: llamarPedido(this); break;
            case R.id.idNavPerfil: llamarEditarPerfil(this, usuarioSesion); break;
            case R.id.idNavCerca:llamarAcercaDe(this); break;
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
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
            case R.id.idBotCliente: llamarCliente(this); break;
            case R.id.idBotUsuario: llamarUsuario(this); break;
            case R.id.idBotProducto: llamarProducto(this); break;
            case R.id.idBotPedido: llamarPedido(this); break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
