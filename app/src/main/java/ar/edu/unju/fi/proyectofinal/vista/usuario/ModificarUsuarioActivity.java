package ar.edu.unju.fi.proyectofinal.vista.usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.interfaces.IUsuario;
import ar.edu.unju.fi.proyectofinal.modelo.constantes.Constantes;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;
import ar.edu.unju.fi.proyectofinal.presentador.UsuarioPresentador;
import ar.edu.unju.fi.proyectofinal.vista.pedido.PedidoActivity;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class ModificarUsuarioActivity extends SuperActivity implements View.OnClickListener, IUsuario.vistaModificarUsuario {
    private EditText etDni, etNombre, etClave;
    private TextView tvRol;
    private Spinner spRol;
    private LinearLayout layoutRol;
    private Button btnModificar, btnEliminar, btnVerPedidos;
    private Usuario usuario, usuarioMod;
    private IUsuario.presentadorUsuario presentadorUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        setContext(this);
        findViewByModificarUsuario();
        obtenerUsuarioAModificar();
        ocultarComponentes();
        presentadorUsuario = new UsuarioPresentador(this,this);
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByModificarUsuario(){
        etDni = (EditText) findViewById(R.id.idTxtModDni);
        etNombre = (EditText) findViewById(R.id.idTxtModNombre);
        etClave = (EditText) findViewById(R.id.idTxtModClave);
        tvRol = (TextView) findViewById(R.id.idTvModRol);
        spRol = (Spinner) findViewById(R.id.idSpModRol);
        btnModificar = (Button) findViewById(R.id.idBtnMod);
        layoutRol = (LinearLayout) findViewById(R.id.idLayoutRol);
        btnModificar.setOnClickListener(this);
        btnEliminar = (Button) findViewById(R.id.idBtnElim);
        btnEliminar.setOnClickListener(this);
        btnVerPedidos = (Button) findViewById(R.id.idBtnVerPedidosVendedor);
        btnVerPedidos.setOnClickListener(this);
    }

    /**
     * Si el usuario es un administrador muestra el boton de ver pedidos
     * Si es disitinto al administrador muestra los botones de pedidos y eliminar
     */
    private void ocultarComponentes(){
        if (usuario.getRol().equals(Constantes.ADMINISTRADOR)){
            btnVerPedidos.setVisibility(View.GONE);
        }
        if (usuario.getIdUsuario().equals(UsuarioUtil.getUsuarioEnSesion().getIdUsuario())){
            btnVerPedidos.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);
            layoutRol.setVisibility(View.GONE);
        }
        if (!getService().obtenerListaPedidosPorId(usuario.getIdUsuario()).isEmpty()){
            layoutRol.setVisibility(View.GONE);
        }
    }

    /**
     * Obtiene un usuario a modificar
     */
    private void obtenerUsuarioAModificar(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            usuario = (Usuario) extras.getSerializable("usuarioSeleccionado");
        }
        cargarUsuarioAModificar();
    }

    /**
     * Carga el usuario a modificar
     */
    private void cargarUsuarioAModificar(){
        etDni.setText(usuario.getDni().toString());
        etNombre.setText(usuario.getNombre());
        etClave.setText(usuario.getClave());
        int pos = 0;
        for (int i = 0; i < spRol.getCount(); i++)
            if (spRol.getItemAtPosition(i).toString().equals(usuario.getRol())){
                pos = i;
                break;
            }
        spRol.setSelection(pos);
    }


    /**
     * Obtiene informacion de un pedido de acuerdo al vendedor
     */
    private void llamarVerPedido(){
        Intent intent = new Intent(this, PedidoActivity.class);
        intent.putExtra("vendedorSeleccionado",usuario);
        startActivity(intent);
    }

    private void dialogEliminar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea eliminar el usuario: "+usuario.getNombre());
        builder.setTitle("Eliminar Usuario");
        confirmarEliminar(builder);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void confirmarEliminar(AlertDialog.Builder builder){
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presentadorUsuario.validarRegistroDePedidos(usuario.getIdUsuario());
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
     * Carga los datos que estan en la vista del layout y
     * los asigna a la clase usuario
     */
    private void cargarDatosUsuarioModificado(){
        usuarioMod = new Usuario();
        usuarioMod.setIdUsuario(usuario.getIdUsuario());
        usuarioMod.setNombre(etNombre.getText().toString());
        usuarioMod.setDni(Integer.parseInt(etDni.getText().toString()));
        usuarioMod.setClave(etClave.getText().toString());
        usuarioMod.setRol(spRol.getSelectedItem().toString());
    }

    /**
     * Compara si la clave del usuario es igual a la clave del usuario
     * que esta en la vista del layout
     * @return true si son iguales o false si son distintos
     */
    private boolean claveIsEqual(){
        return usuario.getClave().equals(usuarioMod.getClave());
    }

    /**
     * Verifica que los campos de la vista del layout no esten vacios
     * @return true si estan vacios o false si tienen datos cargados
     */
    private boolean verificarFormularioVacio(){
        return etDni.getText().toString().isEmpty() ||
                etNombre.getText().toString().isEmpty() ||
                etClave.getText().toString().isEmpty();
    }

    /**
     * Muestra un mensaje por pantalla si el usuario se modifico correctamente
     * @param info
     */
    @Override
    public void showInfoModificar(String info) {
        mostrarMensaje(info);
    }

    /**
     * Muestra un mensaje por pantalla si el usuario se elimino correctamente
     * y finaliza la actividad
     * @param info
     */
    @Override
    public void showInfoEliminar(String info) {
        mostrarMensaje(info);
        llamarUsuario(this);
        finish();
    }

    /**
     * Modifica un usuario
     */
    @Override
    public void modificarUsuario() {
        presentadorUsuario.modificarUsuario(usuarioMod);
        if (usuarioMod.getIdUsuario().equals(UsuarioUtil.getUsuarioEnSesion().getIdUsuario()))
            llamarMenu(this);
        else
            llamarUsuario(this);
        finish();
    }

    /**
     * Elimina un usuario
     */
    @Override
    public void eliminarUsuario() {
        presentadorUsuario.eliminarUsuario(usuario);
    }

    /**
     * Valida que el fomulario no este vacio
     */
    @Override
    public void validarFormularioVacio() {
        presentadorUsuario.validarFormularioVacio(verificarFormularioVacio());
    }

    /**
     * Controla que el formulario no este vacio, si lo esta marca un error y muestra
     * un mensaje por pantalla de acuerdo a los campos de la vista
     * @param info
     */
    @Override
    public void showFormularioVacio(String info) {
        if (etDni.getText().toString().isEmpty()) {
            etDni.setError(info);
        }
        if (etNombre.getText().toString().isEmpty()) {
            etNombre.setError(info);
        }
        if (etClave.getText().toString().isEmpty()) {
            etClave.setError(info);
        }
    }

    /**
     * Valida que un usuario no tenga la misma la clave que otro
     */
    @Override
    public void validarUsuario() {
        cargarDatosUsuarioModificado();
        presentadorUsuario.validarUsuario(claveIsEqual(), usuarioMod.getClave());
    }

    /**
     * Segun el boton que se presione en la vista manda a una determinada actividad
     * @param view
     */
    @Override
    public void onClick(View view) {
        Integer id = view.getId();
        switch (id){
            case R.id.idBtnMod:  validarFormularioVacio(); break;
            case R.id.idBtnElim :  dialogEliminar(); break;
            case R.id.idBtnVerPedidosVendedor:  llamarVerPedido(); break;
        }
    }
}
