package ar.edu.unju.fi.proyectofinal.vista;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;
import ar.edu.unju.fi.proyectofinal.vista.cliente.AgregarClienteActivity;
import ar.edu.unju.fi.proyectofinal.vista.cliente.ClienteActivity;
import ar.edu.unju.fi.proyectofinal.vista.pedido.PedidoActivity;
import ar.edu.unju.fi.proyectofinal.vista.producto.ProductoActivity;
import ar.edu.unju.fi.proyectofinal.vista.usuario.ModificarUsuarioActivity;
import ar.edu.unju.fi.proyectofinal.vista.usuario.UsuarioActivity;

public class SuperActivity extends AppCompatActivity {
    private Context context;
    private Service service;

    public Service getService() {
        if (service == null)
            service = new Service(getContext());
        return service;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Integer getProxIdPedido(){
        Integer idProxId = 1;
        if(!getService().obtenerListaPedidos().isEmpty()){
            idProxId = getService().obtenerListaPedidos().size()+1;
        }
        return idProxId;
    }

    public void setupActionBar(ActionBar actionBar){
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void mostrarMensaje(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void limpiarCampos(TextView textView){
        textView.setText("");
    }


    public void llamarMenuAdmin(Context context){
        Intent intent = new Intent(context, MenuAdmin.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(new Intent(this, MenuAdmin.class));
        //overridePendingTransition(R.anim.left_in, R.anim.left_out);
        startActivity(intent);
    }

    /**
     * Llama a la actividad pedido
     */
    public void llamarPedido(Context context){
       Intent intent = new Intent(context,PedidoActivity.class)
               .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(new Intent(this, PedidoActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
       startActivity(intent);

    }

    /**
     * Llama a la actividad MenuVendedor
     * @param context
     */
    public void llamarMenuVendedor(Context context){
        Intent intent = new Intent(context, MenuVendedor.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);;
        startActivity(intent);
    }

    /**
     * los que hace es te metodo es llamar tabla cliente
     */
    public void llamarCliente(Context context){
        Intent intent = new Intent(context, ClienteActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(new Intent(this, ClienteActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        startActivity(intent);
    }

    /**
     * los que hace es te metodo es llamar tabla producto
     */
    public void llamarProducto(Context context){
        Intent intent = new Intent(context,ProductoActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                 //startActivity(new Intent(this, ProductoActivity.class));
                 overridePendingTransition(R.anim.left_in, R.anim.left_out);
        startActivity(intent);

    }

    /**
     * los que hace es te metodo es llamar tabla usuario
     */
    public void llamarUsuario(Context context){
        Intent intent = new Intent(context, UsuarioActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //overridePendingTransition(R.anim.left_in, R.anim.left_out);
        //startActivity(intent);
    }

    /**
     * llama a la actividad  AcercaDeActivity
     */
    public void llamarAcercaDe(Context context){
        Intent intent = new Intent(context, AcercaDeActivity.class);

        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
    }

    /**
     * cierra la sesion y llama a la actividad LoginActivity que es el login
     * y finaliza esa actividad
     */
    public void cerrarSesion(Context context){
        Intent intent = new Intent(context, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //overridePendingTransition(R.anim.right_in, R.anim.right_out);
        //startActivity(intent);
        finish();
    }


    public void llamarMenu(Context context){
        if (UsuarioUtil.getUsuarioEnSesion().isAdministrador())
            llamarMenuAdmin(context);
        else
            llamarMenuVendedor(context);
    }

    public void llamarEditarPerfil(Context context, Usuario usuario){
        Intent intent = new Intent(this, ModificarUsuarioActivity.class);
        intent.putExtra("usuarioSeleccionado",usuario);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idInicio: llamarMenu(this); break;
            case R.id.idAbout: llamarAcercaDe(this); break;
            case R.id.idLogout: cerrarSesion(this); break;
        }
        return super.onOptionsItemSelected(item);
    }
}
