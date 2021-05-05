package ar.edu.unju.fi.proyectofinal.vista.usuario;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.vista.adapter.AdaptadorUsuario;
import ar.edu.unju.fi.proyectofinal.interfaces.IUsuario;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.presentador.UsuarioPresentador;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class UsuarioActivity extends SuperActivity implements View.OnClickListener, AdapterView.OnItemClickListener, IUsuario.vistaUsuario {
    private ListView listViewUsuarios;
    private AdaptadorUsuario adaptadorUsuario;
    private Button btnAgregar, btnBuscarUsuario;
    private EditText etBusquedaUsuario;
    private IUsuario.presentadorUsuario presentadorUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__usuario);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar(getSupportActionBar());
        setContext(this);
        findViewByIdUsuario();
        presentadorUsuario = new UsuarioPresentador(this,this);
        cargarLista();
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdUsuario(){
        listViewUsuarios = (ListView) findViewById(R.id.idListUsuario);
        etBusquedaUsuario = (EditText) findViewById(R.id.idTxtBusquedaUsuario);
        btnAgregar = (Button) findViewById(R.id.idBtnAgregar);
        btnAgregar.setOnClickListener(this);
        btnBuscarUsuario = (Button) findViewById(R.id.idBtnBuscarUsuario);
        btnBuscarUsuario.setOnClickListener(this);
    }

    /**
     * Llama a la actividad agregar usuario
     */
    public void llamarAgregarUsuario(){
         Intent intent = new Intent(this, AgregarUsuarioActivity.class);
         //startActivity(new Intent(this, AgregarUsuarioActivity.class));
         //overridePendingTransition(R.anim.left_in, R.anim.left_out);
         startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Intent intent = new Intent(this, ModificarUsuarioActivity.class);
        intent.putExtra("usuarioSeleccionado",(Usuario) parent.getItemAtPosition(position));
        startActivity(intent);
    }

    /**
     * Carga una lista de usuarios
     */
    @Override
    public void cargarLista() {
        adaptadorUsuario = new AdaptadorUsuario(this, presentadorUsuario.obtenerListaUsuarios());
        listViewUsuarios.setAdapter(adaptadorUsuario);
        listViewUsuarios.setOnItemClickListener(this);
    }

    /**
     * Busca un usuario de acuerdo al dni
     */
    private void llamarBuscarUsuario() {
        if (!etBusquedaUsuario.getText().toString().isEmpty()) {
            presentadorUsuario.buscarUsuarioDni(etBusquedaUsuario.getText().toString());
        } else {
            cargarLista();
        }
    }

    /**
     * Carga una lista de usuarios
     * @param usuarios
     */
    public void cargarUsuarioEncontrado(List<Usuario> usuarios){
        adaptadorUsuario = new AdaptadorUsuario(this, usuarios);
        adaptadorUsuario.notifyDataSetChanged();
        listViewUsuarios.setAdapter(adaptadorUsuario);
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfo(String info) {
        mostrarMensaje(info);
    }

    /**
     * Segun el boton que se presione en la vista manda a una determinada actividad
     * @param view
     */
    @Override
    public void onClick(View view) {
        Integer id = view.getId();
        switch (id) {
            case R.id.idBtnAgregar: {
                llamarAgregarUsuario();
                break;
            }
            case R.id.idBtnBuscarUsuario: {
                llamarBuscarUsuario();
                break;
            }
        }
    }
}
