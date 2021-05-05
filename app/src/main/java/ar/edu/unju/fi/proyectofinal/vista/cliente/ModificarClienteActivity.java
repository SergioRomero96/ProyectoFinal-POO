package ar.edu.unju.fi.proyectofinal.vista.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.interfaces.ICliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.presentador.ClientePresentador;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;
import ar.edu.unju.fi.proyectofinal.vista.pedido.PedidoActivity;

public class ModificarClienteActivity extends SuperActivity implements View.OnClickListener, ICliente.vistaModificarCliente {
    private EditText etCuit, etNombre, etDomicilio;
    private Button btnModificar, btnEliminar, btnVerPedidos;
    private ICliente.presentadorCliente presentadorCliente;
    private Cliente cliente, clienteMod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cliente);
        findViewByIdModificarCliente();
        obtenerUsuarioAModificar();
        presentadorCliente = new ClientePresentador(this,this);
    }

    /**
     * Hace referencia a los componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdModificarCliente(){
        etCuit = (EditText) findViewById(R.id.idTxtModCuit);
        etNombre = (EditText) findViewById(R.id.idTxtModNombreCliente);
        etDomicilio = (EditText) findViewById(R.id.idTxtModDomicilio);
        btnModificar = (Button) findViewById(R.id.idBtnModCliente);
        btnModificar.setOnClickListener(this);
        btnEliminar = (Button) findViewById(R.id.idBtnElimCliente);
        btnEliminar.setOnClickListener(this);
        btnVerPedidos = (Button) findViewById(R.id.idBtnVerPedidosCliente);
        btnVerPedidos.setOnClickListener(this);
    }

    /**
     * Obtiene un cliente a modificar
     */
    private void obtenerUsuarioAModificar(){
        Intent intent = getIntent();
        Bundle extras = ((Intent) intent).getExtras();
        if(extras != null) {
            cliente = (Cliente) extras.getSerializable("clienteSeleccionado");
            etCuit.setText(cliente.getCuit());
            etNombre.setText(cliente.getNombre());
            etDomicilio.setText(cliente.getDomicilio());
        }
    }

    /**
     * Verifica que los campos de la vista del layout no
     * esten vacios
     * @return true si estan vacios o false si tiene datos cargados
     */
    private boolean verificarFormularioVacio(){
        return etCuit.getText().toString().isEmpty() ||
                etNombre.getText().toString().isEmpty() ||
                etDomicilio.getText().toString().isEmpty();
    }

    /**
     * Muestra un mensaje por pantalla si el cliente
     * se modifico correctamente
     * @param info
     */
    @Override
    public void showInfoModificar(String info) {
        mostrarMensaje(info);
    }

    /**
     * Muestra un mensaje por pantalla si el cliente se
     * elimino correctamente
     * @param info
     */
    @Override
    public void showInfoEliminar(String info) {
        mostrarMensaje(info);
        llamarCliente(this);
        finish();
    }

    /**
     * Valida que el formulario no este vacio
     */
    @Override
    public void validarFormularioVacio() {
        presentadorCliente.validarCamposVaciosModificar(verificarFormularioVacio());
    }

    /**
     * Controla que el formulario no este vacio, si lo esta
     * marca un error y muestra un mensaje por pantalla de
     * acuerdo a los campos de la vista
     * @param info
     */
    @Override
    public void showFormularioVacio(String info) {
        if (etCuit.getText().toString().isEmpty()) {
            etCuit.setError(info);
        }
        if (etNombre.getText().toString().isEmpty()) {
            etNombre.setError(info);
        }
        if (etDomicilio.getText().toString().isEmpty()) {
            etDomicilio.setError(info);
        }
    }

    /**
     * Valida un cliente
     */
    @Override
    public void validarCliente() {
        cargarDatosAModificar();
        presentadorCliente.validarCliente(cuitIsEqual(), clienteMod.getCuit());
    }

    /**
     * Compara si el nombre del cliente es igual al cliente
     * que esta en la vista del layout
     * @return true si son iguales o false si son distintos
     */
    private boolean cuitIsEqual(){
        return cliente.getCuit().equals(etCuit.getText().toString());
    }

    /**
     * Modifica un cliente
     */
    @Override
    public void modificarCliente() {
        cargarDatosAModificar();
        presentadorCliente.modificarCliente(clienteMod);
        llamarCliente(this);
        finish();
    }

    private void dialogEliminar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea eliminar el cliente: "+cliente.getNombre());
        builder.setTitle("Eliminar Cliente");
        confirmarEliminar(builder);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void confirmarEliminar(AlertDialog.Builder builder){
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presentadorCliente.validarRegistroDePedido(cliente.getIdCliente());
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
     * Elimina un cliente
     */
    @Override
    public void eliminarCliente() {
        presentadorCliente.eliminarCliente(cliente);
    }

    /**
     * Carga los datos que estan en la vista del layout y
     * los asigna a la clase cliente
     */
    private void cargarDatosAModificar(){
        clienteMod = new Cliente();
        clienteMod.setIdCliente(cliente.getIdCliente());
        clienteMod.setCuit(etCuit.getText().toString());
        clienteMod.setNombre(etNombre.getText().toString());
        clienteMod.setDomicilio(etDomicilio.getText().toString());
    }

    /**
     * De acuerdo al cliente obtiene los pedidos que tiene
     */
    private void llamarVerPedido(){
        Intent intent = new Intent(this, PedidoActivity.class);
        intent.putExtra("clienteSeleccionado",cliente);
        startActivity(intent);
    }

    /**
     * Segun el boton que se presione en la vista
     * manda a una determinada actividad
     * @param view
     */
    @Override
    public void onClick(View view) {
        Integer id = view.getId();
        switch (id){
            case R.id.idBtnModCliente:  validarFormularioVacio(); break;
            case R.id.idBtnElimCliente :  dialogEliminar(); break;
            case R.id.idBtnVerPedidosCliente:  llamarVerPedido(); break;
        }
    }
}
