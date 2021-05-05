package ar.edu.unju.fi.proyectofinal.vista.pedido;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.vista.SuperActivity;

/**
 * CONTROLAR SI LA CLASE SE USA
 */
public class ConsultaPedidoActivity extends SuperActivity implements  AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Spinner spVendedor, spCliente;
    private List<String> listaVendedores = new ArrayList<>();
    private Button btnBuscar;
    private EditText txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pedidos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar(getSupportActionBar());
        setContext(this);
        findViewByIdPedidos();
        cargarLista();
    }

    /**
     * hace referencia a los Componentes de la vistas y asigna a los objetos
     * para poder manipularlos
     */
    private void findViewByIdPedidos() {
        txtTotal = (EditText) findViewById(R.id.idTxtTotalListPedido);
        spVendedor = (Spinner) findViewById(R.id.idSpnPedVend);
        spCliente = (Spinner) findViewById(R.id.idSpnPedClien);
        btnBuscar = (Button) findViewById(R.id.idBtnFindPedido);
        btnBuscar.setOnClickListener(this);
    }

    private void cargarLista() {
        for (Usuario usuario: getService().obtenerListaVendedores()) {
            listaVendedores.add(usuario.getNombre());
        }
        cargarSpinner();
    }

    private void cargarSpinner(){
        if (!listaVendedores.isEmpty()) {
            ArrayAdapter<CharSequence> adaptadorUsuario = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaVendedores);
            spVendedor.setAdapter(adaptadorUsuario);
            spVendedor.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Intent intent = new Intent(this, DetallePedidoActivity.class);
        intent.putExtra("pedidoSeleccionado", (Pedido) parent.getItemAtPosition(position));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        startActivity(intent);

    }

    /*private Double obtenerTotal(){
        Double total = 0d;
        for (Pedido pedido: listaPedidos)
            if (pedido.getEstado().equals(Constantes.INICIADO))
                total += pedido.getImporteTotal();
        return total;
    }*/

    @Override
    public void onClick(View view) {
    }
}
