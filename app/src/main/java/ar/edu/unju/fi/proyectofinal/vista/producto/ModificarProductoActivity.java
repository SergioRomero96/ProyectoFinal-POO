package ar.edu.unju.fi.proyectofinal.vista.producto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.interfaces.IProducto;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.presentador.ProductoPresentador;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

public class ModificarProductoActivity extends SuperActivity implements View.OnClickListener, IProducto.vistaModificarProducto {
    private EditText etNomProd, etTamProd, etPreProd, etStocProd;
    private TextView stEstado;
    private Spinner spnEstado;
    private Button btnModficar, btnEliminar;
    private IProducto.presentadorProducto presentadorProducto;
    private Producto producto, productoMod;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_modificar_producto);
        findViewByModificarProducto();
        obtenerProductoAModificar();
        setContext(this);
        presentadorProducto = new ProductoPresentador(this, this);
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByModificarProducto() {
        etNomProd = (EditText) findViewById(R.id.idEditNomProd);
        etTamProd = (EditText) findViewById(R.id.idEditTamProd);
        etPreProd = (EditText) findViewById(R.id.idEditPrecProd);
        stEstado = (TextView) findViewById(R.id.idSpnEstadPro);
        spnEstado = (Spinner) findViewById(R.id.idEditEstProd);
        etStocProd = (EditText) findViewById(R.id.idEditStoProd);
        btnModficar = (Button) findViewById(R.id.idBtnUpProd);
        btnModficar.setOnClickListener(this);
        btnEliminar = (Button) findViewById(R.id.idBtnDelProd);
        btnEliminar.setOnClickListener(this);
    }

    /**
     * Obtiene el detalle de un producto a modificar
     */
    private void obtenerProductoAModificar() {
        Intent intent = getIntent();
        Bundle extras = ((Intent) intent).getExtras();
        if (extras != null) {
            producto = (Producto) extras.getSerializable("productoSeleccionado");
            etNomProd.setText(producto.getNombre());
            etTamProd.setText(producto.getTamanio().toString());
            etPreProd.setText(producto.getPrecioUnitario().toString());
            etStocProd.setText(producto.getStock().toString());
            int pos = 0;
            for (int i = 0; i < spnEstado.getCount(); i++)
                if (spnEstado.getItemAtPosition(i).toString().equals(producto.getEstado())){
                    pos = i;
                    break;
                }
            spnEstado.setSelection(pos);
        }
    }

    /**
     * muestra el dialogo para eliminar
     */
    private void dialogEliminar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea eliminar el producto: "+producto.getNombre());
        builder.setTitle("Eliminar Producto");
        confirmarEliminar(builder);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * muestra las opciones si esta seguno de eliminar
     * @param builder
     */
    private void confirmarEliminar(AlertDialog.Builder builder){
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presentadorProducto.validarRegistroDePedido(producto.getIdProducto());
                //presentadorProducto.eliminarProducto(producto);
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
     * Carga los datos de la vista del producto a modificar
     */
    private void cargarDatosAModificar() {
        productoMod = new Producto();
        productoMod.setIdProducto(producto.getIdProducto());
        productoMod.setNombre(etNomProd.getText().toString());
        productoMod.setTamanio(Integer.parseInt(etTamProd.getText().toString()));
        productoMod.setPrecioUnitario(Double.parseDouble(etPreProd.getText().toString()));
        productoMod.setStock(Integer.parseInt(etStocProd.getText().toString()));
        productoMod.setEstado(spnEstado.getSelectedItem().toString());
    }

    /**
     * Verifica que los campos de la vista del layout no
     * esten vacios
     * @return true si estan vacios o false si tiene datos cargados
     */
    private boolean verificarFormularioVacio() {
        return etNomProd.getText().toString().isEmpty() ||
                etTamProd.getText().toString().isEmpty() ||
                etPreProd.getText().toString().isEmpty() ||
                etStocProd.getText().toString().isEmpty();
    }

    /**
     * Muestra un mensaje por pantalla si el producto se modifico correctamente
     * @param info
     */
    @Override
    public void showInfoModificar(String info) {
        mostrarMensaje(info);
    }

    /**
     * Muestra un mensaje por pantalla si el producto se elimino correctamente
     * y finaliza la actividad
     * @param info
     */
    @Override
    public void showInfoEliminar(String info) {
        mostrarMensaje(info);
        llamarProducto(this);
        finish();
    }

    /**
     * Valida que el fomulario no este vacio
     */
    @Override
    public void validarFormularioVacio() {
        presentadorProducto.validarFormularioVacioModificar(verificarFormularioVacio());
    }

    /**
     * Controla que el formulario no este vacio, si lo esta marca un error y muestra
     * un mensaje por pantalla de acuerdo a los campos de la vista
     * @param info
     */
    @Override
    public void showFormularioVacio(String info) {
        if (etNomProd.getText().toString().isEmpty()) {
            etNomProd.setError(info);
        }
        if (etTamProd.getText().toString().isEmpty()) {
            etTamProd.setError(info);
        }
        if (etPreProd.getText().toString().isEmpty()) {
            etPreProd.setError(info);
        }
        if (etStocProd.getText().toString().isEmpty()) {
            etStocProd.setError(info);
        }
    }

    /**
     * Valida un producto de acuerdo a contenido de la vista
     */
    @Override
    public void validarProducto() {
        cargarDatosAModificar();
        presentadorProducto.validarProducto(productoIsEqual(), productoMod);
    }

    /**
     * Modifica un producto
     */
    @Override
    public void modificarProducto() {
        presentadorProducto.modificarProducto(productoMod);
        llamarProducto(this);
        finish();
    }

    /**
     * Elimina un producto
     */
    @Override
    public void eliminarProducto() {
        presentadorProducto.eliminarProducto(producto);
    }

    /**
     * Compara si el nombre y tamaño del producto es igual al nombre
     *  y tamaño del producto que esta en la vista del layout
     * @return true si son iguales o false si son distintos
     */
    private Boolean productoIsEqual() {
        return ((producto.getNombre().equals(etNomProd.getText().toString()))
                && (producto.getTamanio().equals(Integer.parseInt(etTamProd.getText().toString()))));
    }

    /**
     * Segun el boton que se presione en la vista
     * manda a una determinada actividad
     * @param view
     */
    @Override
    public void onClick(View view) {
        Integer id = view.getId();
        if (id == R.id.idBtnUpProd) {
            validarFormularioVacio();
        } else if (id == R.id.idBtnDelProd) {
            dialogEliminar();
        }
    }
}