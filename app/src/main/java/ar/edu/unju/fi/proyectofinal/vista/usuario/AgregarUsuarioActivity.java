package ar.edu.unju.fi.proyectofinal.vista.usuario;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.interfaces.IUsuario;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.presentador.UsuarioPresentador;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class AgregarUsuarioActivity extends SuperActivity implements View.OnClickListener, IUsuario.vistaAgregarUsuario {
    private EditText txtNombre, txtDni, txtClave;
    private Spinner spRol;
    private Button btnAgregar;
    private Usuario usuario;
    private IUsuario.presentadorUsuario presentadorUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);
        setupActionBar(getSupportActionBar());
        setContext(this);
        findViewByIdAgregarUsuario();
        presentadorUsuario = new UsuarioPresentador(this,this);
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdAgregarUsuario(){
        txtNombre = (EditText) findViewById(R.id.idNombreNuevo);
        txtDni = (EditText) findViewById(R.id.idDniNuevo);
        txtClave = (EditText) findViewById(R.id.idClaveNuevo);
        spRol = (Spinner) findViewById(R.id.idNuevoRol);
        btnAgregar = (Button) findViewById(R.id.idNuevoAgregar);
        btnAgregar.setOnClickListener(this);
    }

    /**
     * Valida un usuario
     */
    @Override
    public void validarUsuario() {
        cargarDatos();
        presentadorUsuario.validarUsuario(usuario.getClave());
    }

    /**
     * Controla si el formulario no esta vacio
     */
    @Override
    public void validarFormularioVacio() {
        presentadorUsuario.validarFormularioVacioAgregar(verificarFormularioVacio());
    }

    /**
     * Agrega un usuario
     */
    @Override
    public void agregarUsuario() {
        presentadorUsuario.agregarUsuario(usuario);
        llamarUsuario(this);
        finish();
    }

    /**
     * Muestra un mensaje por pantalla si el usuario se agrego correctamente
     * @param mensaje
     */
    @Override
    public void showInfoAgregar(String mensaje) {
        mostrarMensaje(mensaje);
    }

    /**
     * Controla que el formulario no este vacio, si lo esta marca un error y muestra
     * un mensaje por pantalla de acuerdo a los campos de la vista
     * @param info
     */
    @Override
    public void showFormularioVacio(String info) {
        if (txtNombre.getText().toString().isEmpty())
            txtNombre.setError(info);
        if (txtClave.getText().toString().isEmpty())
            txtClave.setError(info);
        if (txtDni.getText().toString().isEmpty())
            txtDni.setError(info);
    }

    /**
     * Carga los datos que estan en la vista del layout y los asigna a la clase usuario
     */
    private void cargarDatos(){
        usuario = new Usuario();
        usuario.setDni(Integer.parseInt(txtDni.getText().toString()));
        usuario.setNombre(txtNombre.getText().toString());
        usuario.setClave(txtClave.getText().toString());
        usuario.setRol(spRol.getSelectedItem().toString());
    }

    /**
     * Verifica que los campos de la vista del layout no esten vacios
     * @return true si estan vacios o false si tienen datos cargados
     */
    private boolean verificarFormularioVacio(){
        return txtNombre.getText().toString().isEmpty() || txtDni.getText().toString().isEmpty() ||
                txtClave.getText().toString().isEmpty();
    }

    /**
     * Segun el boton que se presione en la vista manda a una determinada actividad
     * @param view
     */
    @Override
    public void onClick(View view) {
        validarFormularioVacio();
    }
}
