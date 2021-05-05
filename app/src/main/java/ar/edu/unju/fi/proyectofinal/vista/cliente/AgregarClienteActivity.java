package ar.edu.unju.fi.proyectofinal.vista.cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.interfaces.ICliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.presentador.ClientePresentador;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class AgregarClienteActivity extends SuperActivity implements View.OnClickListener, ICliente.vistaAgregarCliente {
    private EditText  edtTextCuit, edtTxtNombre, edtTxtDomicilio;
    private Button btnGuardar;
    private ICliente.presentadorCliente presentadorCliente;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);
        setupActionBar(getSupportActionBar());
        presentadorCliente = new ClientePresentador(this,this);
        findViewByIdAgregarCliente();
    }

    /**
     * Hace referencia a los componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdAgregarCliente(){
        edtTextCuit = (EditText) findViewById(R.id.idCuitNuevo);
        edtTxtNombre = (EditText) findViewById(R.id.idNombreNuevoCliente);
        edtTxtDomicilio = (EditText) findViewById(R.id.idDomicilioNuevo);
        btnGuardar = (Button) findViewById(R.id.idNuevoAgregarCliente);
        btnGuardar.setOnClickListener(this);
    }

    /**
     * Valida un cliente
     */
    @Override
    public void validarCliente() {
        cargarDatos();
        presentadorCliente.validarCliente(cliente.getCuit());
    }

    /**
     * Valida campos vacios de la vista
     */
    @Override
    public void validarCamposVacios() {
        presentadorCliente.validarCamposVaciosAgregar(verificarCamposVacios());
    }

    /**
     * Mustra un mensaje por pantalla si el cliente se
     * agrego correctamente
     * @param info
     */
    @Override
    public void showInfoAgregar(String info) {
        mostrarMensaje(info);
    }

    /**
     * Agrega un cliente
     */
    @Override
    public void agregarCliente() {
        presentadorCliente.agregarCliente(cliente);
        llamarCliente(this);
        finish();
    }

    /**
     * Carga los datos que estan en la vista del layout y
     * los asigna a la clase cliente
     */
    private void cargarDatos(){
        cliente = new Cliente();
        cliente.setCuit(edtTextCuit.getText().toString());
        cliente.setNombre(edtTxtNombre.getText().toString());
        cliente.setDomicilio(edtTxtDomicilio.getText().toString());
    }

    /**
     * Controla que el formulario no este vacio, si lo esta marca un error y muestra
     * un mensaje por pantalla de acuerdo a los campos de la vista
     * @param info
     */
    @Override
    public void showFormularioVacio(String info) {
        if (edtTextCuit.getText().toString().isEmpty()) {
            edtTextCuit.setError(info);
        }
        if (edtTxtNombre.getText().toString().isEmpty()) {
            edtTxtNombre.setError(info);
        }
        if (edtTxtDomicilio.getText().toString().isEmpty()) {
            edtTxtDomicilio.setError(info);
        }
    }

    /**
     * Verifica que los campos de la vista del layout no
     * esten vacios
     * @return true si estan vacios o false si tienen datos cargados
     */
    private boolean verificarCamposVacios(){
        return edtTextCuit.getText().toString().isEmpty() ||
                edtTxtNombre.getText().toString().isEmpty() ||
                edtTxtDomicilio.getText().toString().isEmpty();
    }

    /**
     * Segun el boton que se presione en la vista
     * manda a una determinada actividad
     * @param view
     */
    @Override
    public void onClick(View view) {
        validarCamposVacios();
    }   
}
