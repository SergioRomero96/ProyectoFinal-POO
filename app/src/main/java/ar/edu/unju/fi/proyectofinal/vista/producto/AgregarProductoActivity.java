package ar.edu.unju.fi.proyectofinal.vista.producto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.interfaces.IProducto;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.presentador.ProductoPresentador;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class AgregarProductoActivity extends SuperActivity implements View.OnClickListener, IProducto.vistaAgregarProducto {
    private EditText edtTxtNombre, edtTxtTamanio, edtTxtPrecio, edtTxtStock, edtTxtId;
    private Button btnGuardarProd;
    private Spinner spnEstado;
    private ProductoPresentador presentadorProducto;
    private Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        setupActionBar(getSupportActionBar());
        setContext(this);
        findViewByIdAgregarProducto();
        presentadorProducto = new ProductoPresentador(this, this);
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdAgregarProducto() {
        edtTxtNombre = (EditText) findViewById(R.id.editNomAltaProd);
        edtTxtTamanio = (EditText) findViewById(R.id.editTamAltaProd);
        edtTxtPrecio = (EditText) findViewById(R.id.editPreAltaProd);
        spnEstado = (Spinner) findViewById(R.id.spinnerEstAltaProd);
        edtTxtStock = (EditText) findViewById(R.id.editStoAltaProd);
        btnGuardarProd = (Button) findViewById(R.id.btnGuarAltaProd);
        btnGuardarProd.setOnClickListener(this);
    }

    /**
     * Valida un producto de acuerdo al nombre
     */
    @Override
    public void validarProducto() {
        cargarDatos();
        presentadorProducto.validarProducto(producto);
    }

    /**
     * Valida que el formulario no este vacio
     */
    @Override
    public void validarFormularioVacio() {
        presentadorProducto.validarFormularioVacioAgregar(verificarCamposVacios());
    }

    /**
     * Muestra un mensaje por pantalla si el
     * producto se agrego correctamente
     * @param info
     */
    @Override
    public void showInfoAgregar(String info) {
        mostrarMensaje(info);
    }

    /**
     * Agrega un producto
     */
    @Override
    public void agregarProducto() {
        presentadorProducto.agregarProducto(producto);
        llamarProducto(this);
        finish();
    }

    /**
     * Carga los datos que estan en la vista del layout y
     * los asigna a la clase producto
     */
    private void cargarDatos() {
        producto = new Producto();
        producto.setNombre(edtTxtNombre.getText().toString());
        producto.setTamanio(Integer.parseInt(edtTxtTamanio.getText().toString()));
        producto.setPrecioUnitario(Double.parseDouble(edtTxtPrecio.getText().toString()));
        producto.setEstado(spnEstado.getSelectedItem().toString());
        producto.setStock(Integer.parseInt(edtTxtStock.getText().toString()));
    }

    /**
     * Controla que el formulario no este vacio, si lo esta marca un error y muestra
     * un mensaje por pantalla de acuerdo a los campos de la vista
     * @param info
     */
    @Override
    public void showFormularioVacio(String info) {
        if (edtTxtNombre.getText().toString().isEmpty()) {
            edtTxtNombre.setError(info);
        }
        if (edtTxtTamanio.getText().toString().isEmpty()) {
            edtTxtTamanio.setError(info);
        }
        if (edtTxtPrecio.getText().toString().isEmpty()) {
            edtTxtPrecio.setError(info);
        }
        if (edtTxtStock.getText().toString().isEmpty()) {
            edtTxtStock.setError(info);
        }
    }

    /**
     * Verifica que los campos de la vista del layout no
     * esten vacios
     * @return true si estan vacios o false si tienen datos cargados
     */
    private boolean verificarCamposVacios() {
        return edtTxtNombre.getText().toString().isEmpty() ||
                edtTxtTamanio.getText().toString().isEmpty() ||
                edtTxtPrecio.getText().toString().isEmpty() ||
                edtTxtStock.getText().toString().isEmpty();
    }

    /**
     * Segun el boton que se presione en la vista
     * manda a una determinada actividad
     * @param view
     */
    @Override
    public void onClick(View view) {
        validarFormularioVacio();
    }
}
