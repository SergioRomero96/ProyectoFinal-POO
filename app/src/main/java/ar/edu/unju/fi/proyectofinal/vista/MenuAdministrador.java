package ar.edu.unju.fi.proyectofinal.vista;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.vista.cliente.ClienteActivity;
import ar.edu.unju.fi.proyectofinal.vista.pedido.AgregarPedidoActivity;
import ar.edu.unju.fi.proyectofinal.vista.producto.ProductoActivity;
import ar.edu.unju.fi.proyectofinal.vista.usuario.UsuarioActivity;

public class MenuAdministrador extends AppCompatActivity {
    TextView lblUsuarioSesion;
    Usuario usuarioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);
        setupActionBar();
        lblUsuarioSesion = findViewById(R.id.lblUsuarioSesion);
        recuperarUsuarioEnSesion();
    }

    public void llamarPedido(View view){
        Intent intent = new Intent(this,AgregarPedidoActivity.class);
        startActivity(intent);
    }

    /**
     * los que hace es te metodo es llamar tabla cliente
     * @param view
     */
    public void llamarCliente(View view){
        Intent intent = new Intent(this, ClienteActivity.class);
        startActivity(intent);
    }

    /**
     * los que hace es te metodo es llamar tabla producto
     * @param view
     */
    public void llamarProducto(View view){
        Intent intent = new Intent(this,ProductoActivity.class);
        startActivity(intent);
    }

    /**
     * los que hace es te metodo es llamar tabla usuario
     * @param view
     */
    public void llamarUsuario(View view){
        Intent intent = new Intent(this, UsuarioActivity.class);
        startActivity(intent);

    }

    public void recuperarUsuarioEnSesion(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            usuarioSesion = (Usuario) extras.getSerializable("usuarioSesion");
            String nombreUsuario = "Bienvenido: "+ usuarioSesion.getNombre();
            lblUsuarioSesion.setText(nombreUsuario);
        }
    }

    public void lanzarAcercaDe(){
        Intent intent = new Intent(MenuAdministrador.this, AcercaDeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

    public void cerrarSesion(){
        Intent intent = new Intent(MenuAdministrador.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle("Menu Administrador");
        }
    }
}
