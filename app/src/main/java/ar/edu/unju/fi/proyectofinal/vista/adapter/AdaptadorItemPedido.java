package ar.edu.unju.fi.proyectofinal.vista.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.ItemPedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;

public class AdaptadorItemPedido extends BaseAdapter {
    private Context context;
    private List<ItemPedido> listItemsPedidos;

    public AdaptadorItemPedido(Context context, List<ItemPedido> listItemsPedidos) {
        this.context = context;
        this.listItemsPedidos = listItemsPedidos;
    }

    /**
     * Retorna la cantidad de elementos de la lista
     */
    @Override
    public int getCount() {
        return listItemsPedidos.size();
    }

    /**
     * Retorna el objetivo en la posicion indicada
     */
    @Override
    public Object getItem(int i) {
        return listItemsPedidos.get(i);
    }

    /**
     * Retorna el id del objetivo de la posicion indicada
     */
    @Override
    public long getItemId(int i) {
        return listItemsPedidos.get(i).getIdPedido();
    }

    /**
     * metodo donde se crea la vista o cada item y le asignamos los valores para cada uno
     * este metodo se va a ejecutar tantas veces como lo indique el getCount
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemPedido item = (ItemPedido) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.view_pedidos, null);
        TextView txtProducto = (TextView) view.findViewById(R.id.idPedProducto);
        TextView txtTamanio = (TextView) view.findViewById(R.id.idPedTamanio);
        TextView txtUnidades = (TextView) view.findViewById(R.id.idPedUnidades);
        TextView txtPrecioUnitario = (TextView) view.findViewById(R.id.idPedValorUnidad);
        TextView txtSubTotal = (TextView) view.findViewById(R.id.idPedSubTotal);

        Service service = new Service(context);
        Producto producto = service.obtenerProducto(item.getIdProducto());

        txtProducto.setText(txtProducto.getText().toString() + " " + producto.getNombre());
        txtTamanio.setText(txtTamanio.getText().toString() + " " + producto.getTamanio() + "cm3");
        txtUnidades.setText(txtUnidades.getText().toString() + " " + item.getCantidad());
        txtPrecioUnitario.setText(txtPrecioUnitario.getText().toString() + " " + "$" + String.format("%.2f", producto.getPrecioUnitario()));
        txtSubTotal.setText(txtSubTotal.getText().toString() + " " + "$" + String.format("%.2f", item.getSubTotal()));
        return view;
    }
}
