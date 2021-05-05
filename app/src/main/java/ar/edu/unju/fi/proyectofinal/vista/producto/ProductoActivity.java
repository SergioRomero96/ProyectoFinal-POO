package ar.edu.unju.fi.proyectofinal.vista.producto;

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
import ar.edu.unju.fi.proyectofinal.vista.adapter.AdaptadorProducto;
import ar.edu.unju.fi.proyectofinal.interfaces.IProducto;
import ar.edu.unju.fi.proyectofinal.modelo.dao.impl.ProductoDAOImp;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.presentador.ProductoPresentador;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class ProductoActivity extends SuperActivity implements View.OnClickListener, AdapterView.OnItemClickListener, IProducto.vistaProducto {
    private ListView listViewProductos;
    private Button btnAgregar, btnBuscarProducto;
    private EditText etBusquedaProducto;
    private AdaptadorProducto adaptadorProducto;
    private IProducto.presentadorProducto presentadorProducto;
    private ProductoDAOImp productoDAOImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__producto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar(getSupportActionBar());
        findViewByIdProducto();
        presentadorProducto = new ProductoPresentador(this, this);
        cargarListaProducto();
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdProducto() {
        listViewProductos = (ListView) findViewById(R.id.idListProducto);
        listViewProductos.setOnItemClickListener(this);
        etBusquedaProducto = (EditText) findViewById(R.id.idTxtBusquedaProducto);
        btnAgregar = (Button) findViewById(R.id.idBtnAddProd);
        btnAgregar.setOnClickListener(this);
        btnBuscarProducto = (Button) findViewById(R.id.idBtnBuscarProducto);
        btnBuscarProducto.setOnClickListener(this);
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
            case R.id.idBtnAddProd:{
                llamarAgregarProducto();
                break;
            }
            case R.id.idBtnBuscarProducto:{
                llamarBuscarProducto();
                break;
            }
        }
    }

    /**
     * asigna a un objeto de tipo producto segun lo que se seleccione en el spinner
     * @param parent
     * @param view
     * @param position
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Intent intent = new Intent(this, ModificarProductoActivity.class);
        intent.putExtra("productoSeleccionado", (Producto) parent.getItemAtPosition(position));
        startActivity(intent);
    }

    /**
     * cargar la lista de productos en el adaptador para poder mostrarlo en el listview
     */
    @Override
    public void cargarListaProducto() {
        adaptadorProducto = new AdaptadorProducto(this, presentadorProducto.obtenerListadoProductos());
        adaptadorProducto.notifyDataSetChanged();
        listViewProductos.setAdapter(adaptadorProducto);
    }

    /**
     * Llama a la vista Agregar Producto e inicia la actividad
     * correspondiente
     */
    private void llamarAgregarProducto(){
        Intent intent = new Intent(this, AgregarProductoActivity.class);
        startActivity(intent);
    }

    /**
     * Busca un producto si en la vista esta cargado
     * sino devuelve una lista de productos
     */
    private void llamarBuscarProducto(){
        if(!etBusquedaProducto.getText().toString().isEmpty()) {
            presentadorProducto.buscarProducto(etBusquedaProducto.getText().toString());
        } else {
            cargarListaProducto();
        }
    }

    /**
     * carga los producto encontrados en el adaptador
     * para luego mostrarlo en el spinner
     * @param productos
     */
    @Override
    public void cargarProductoEncontrado(List<Producto> productos) {
        adaptadorProducto = new AdaptadorProducto(this, productos);
        adaptadorProducto.notifyDataSetChanged();
        listViewProductos.setAdapter(adaptadorProducto);
        if (productos.isEmpty())
            showInfo("No se encontro ningun producto");
    }

    @Override
    public void showInfo(String info) {
        mostrarMensaje(info);
    }
}
