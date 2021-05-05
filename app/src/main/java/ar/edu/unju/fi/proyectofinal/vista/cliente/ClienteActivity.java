package ar.edu.unju.fi.proyectofinal.vista.cliente;

import android.content.Context;
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
import ar.edu.unju.fi.proyectofinal.vista.adapter.AdaptadorCliente;
import ar.edu.unju.fi.proyectofinal.interfaces.ICliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.presentador.ClientePresentador;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class ClienteActivity extends SuperActivity implements View.OnClickListener, ListView.OnItemClickListener, ICliente.vistaCliente {
    private ListView listViewCliente;
    private Button btnAgregar, btnBuscarCliente;
    private EditText etBusquedaCliente;
    private AdaptadorCliente adaptadorCliente;
    private ICliente.presentadorCliente presentadorCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cliente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar(getSupportActionBar());
        findViewByIdCliente();
        presentadorCliente = new ClientePresentador(this,this);
        cargarListaCliente();
    }

    /**
     * Hace referencia a los componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdCliente(){
        listViewCliente = (ListView) findViewById(R.id.idListCliente);
        listViewCliente.setOnItemClickListener(this);
        etBusquedaCliente = (EditText) findViewById(R.id.idTxtBusquedaCliente);
        btnAgregar = (Button) findViewById(R.id.idBotonACliente);
        btnAgregar.setOnClickListener(this);
        btnBuscarCliente = (Button) findViewById(R.id.idBtnBuscarCliente);
        btnBuscarCliente.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Intent intent = new Intent(this, ModificarClienteActivity.class);
        intent.putExtra("clienteSeleccionado", (Cliente) parent.getItemAtPosition(position));
        startActivity(intent);
    }

    /**
     * Obtiene una lista de clientes y lo guarda en listViewCiente
     */
    @Override
    public void cargarListaCliente() {
        adaptadorCliente = new AdaptadorCliente(this, presentadorCliente.obtenerListadoClientes());
        adaptadorCliente.notifyDataSetChanged();
        listViewCliente.setAdapter(adaptadorCliente);
    }

    /**
     * Busca un cliente si no esta vacio, sino carga una lista de clientes
     */
    private void llamarBuscarCliente() {
        if (!etBusquedaCliente.getText().toString().isEmpty()) {
            presentadorCliente.buscarCliente(etBusquedaCliente.getText().toString());
        } else {
            //presentadorCliente.showInfoModificar();
            cargarListaCliente();
        }
    }

    /**
     * Carga los clientes encontrados
     * @param clientes
     */
    public void cargarClienteEncontrado(List<Cliente> clientes){
        adaptadorCliente = new AdaptadorCliente(this, clientes);
        adaptadorCliente.notifyDataSetChanged();
        listViewCliente.setAdapter(adaptadorCliente);
    }

    /**
     * Mustra un mensaje por pantalla
     * @param info
     */
    @Override
    public void showInfo(String info) {
        mostrarMensaje(info);
    }

    /**
     * Segun el boton que se presione en la vista
     * manda a una determinada actividad
     * @param view
     */
    @Override
    public void onClick(View view) {
        Integer id = view.getId();
        switch (id) {
            case R.id.idBotonACliente:llamarAgregarCliente(); break;
            case R.id.idBtnBuscarCliente:llamarBuscarCliente(); break;
        }
    }

    /**
     * Llama a la actividad AgregarCliente
     */
    public void llamarAgregarCliente() {
        Intent intent = new Intent(this, AgregarClienteActivity.class);
        startActivity(intent);
    }
}

