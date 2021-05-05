package ar.edu.unju.fi.proyectofinal.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.interfaces.ILogin;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.presentador.LoginPresentador;

public class LoginActivity extends SuperActivity implements View.OnClickListener , ILogin.vista {
    private Button botonIngresar;
    private EditText txtClave;
    private ILogin.Presentador presentador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContext(this);
        findViewByIdLogin();
        presentador = new LoginPresentador(this,this);
    }

    /**
     * asigna los componentes de las vistas a los objetos
     * para luego poder manipular los componentes
     */
    public void findViewByIdLogin(){
        botonIngresar =findViewById(R.id.btnIngresar);
        txtClave = findViewById(R.id.txtPassword);
        botonIngresar.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * Muestra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfo(String info) {
        mostrarMensaje(info);
        limpiarClave();
    }

    /**
     * Controla que el formulario no este vacio y manda un error
     * @param info
     */
    @Override
    public void showFormularioVacio(String info) {
        txtClave.setError(info);
    }

    /**
     * Valida que el formulario no este vacio
     */
    @Override
    public void validarFormularioVacio() {
        presentador.validarFormularioVacio(txtClave.getText().toString());
    }

    /**
     * Valida si el usuario existe o no por su clave
     * @param clave
     */
    @Override
    public void validarUsuario(String clave) {
        presentador.validarUsuario(clave);
    }

    /**
     * Muestra el menu administrador
     */
    @Override
    public void mostrarMenuAdmin() {
        llamarMenuAdmin(this);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

    /**
     * Muestra el menu vendedor
     */
    @Override
    public void mostrarMenuVendedor() {
        llamarMenuVendedor(this);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

    /**
     * Limpia el componente textView de la clave
     */
    public void limpiarClave(){
        super.limpiarCampos(txtClave);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Segun el boton que se presione en la vista manda a una determinada actividad
     * @param v
     */
    @Override
    public void onClick(View v) {
        validarFormularioVacio();
    }
}
