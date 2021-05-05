package ar.edu.unju.fi.proyectofinal.vista.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Producto;

public class AdaptadorProducto extends BaseAdapter {
    Context context;
    List<Producto> listaProductos;

    public AdaptadorProducto(Context context, List<Producto> listaProductos) {
        this.context = context;
        this.listaProductos = listaProductos;
    }

    /**
     * Retorna la cantidad de elementos de la lista
     */
    @Override
    public int getCount() {
        return listaProductos.size();
    }

    /**
     * Retorna el objetivo en la posicion indicada
     */
    @Override
    public Object getItem(int i) {
        return listaProductos.get(i);
    }

    /**
     * Retorna el id del objetivo de la posicion indicada
     */
    @Override
    public long getItemId(int i) {
        return listaProductos.get(i).getIdProducto();
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
        Producto item = (Producto) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.view_productos, null);
        TextView txtNombre = (TextView) view.findViewById(R.id.idProdNombre);
        TextView txtTamanio = (TextView) view.findViewById(R.id.idProdTamanio);
        TextView txtPrecio = (TextView) view.findViewById(R.id.idProdPrecio);
        TextView txtEstado = (TextView) view.findViewById(R.id.idProdEstado);
        TextView txtStock = (TextView) view.findViewById(R.id.idProdStock);

        txtNombre.setText(txtNombre.getText().toString() + " " + item.getNombre().toString());
        txtTamanio.setText(txtTamanio.getText().toString() + " " + item.getTamanio().toString()+"cm3");
        txtPrecio.setText(txtPrecio.getText().toString() + " " + "$" + String.format("%.2f", item.getPrecioUnitario().doubleValue()));
        txtEstado.setText(txtEstado.getText().toString() + " " + item.getEstado().toString());
        txtStock.setText(txtStock.getText().toString() + " " + item.getStock().toString());
        return view;
    }
}
