package ar.edu.unju.fi.proyectofinal.vista.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Pedido;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;
import ar.edu.unju.fi.proyectofinal.modelo.services.Service;
import ar.edu.unju.fi.proyectofinal.modelo.util.FechaUtil;

public class AdaptadorPedido extends BaseAdapter {
    private Context context;
    private List<Pedido> listaPedidos;

    public AdaptadorPedido(Context context, List<Pedido> listaPedidos) {
        this.context = context;
        this.listaPedidos = listaPedidos;
    } 

    /**
     * Retorna la cantidad de elementos de la lista
     */
    @Override
    public int getCount() {
        return listaPedidos.size();
    }
    
    /**
     * Retorna el objetivo en la posicion indicada
     */
    @Override
    public Object getItem(int i) {
        return listaPedidos.get(i);
    }

    /**
     * Retorna el id del objetivo de la posicion indicada
     */
    @Override
    public long getItemId(int i) {
        return listaPedidos.get(i).getIdPedido();
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
        Pedido item = (Pedido) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.view_lista_pedidos, null);
        Service service = new Service(context);
        Usuario usuario = service.obtenerUsuario(item.getVendedor().getIdUsuario());
        TextView txtFecha = (TextView) view.findViewById(R.id.idPedLstFecha);
        TextView txtTotal = (TextView) view.findViewById(R.id.idPedLstTotal);
        TextView txtEstado = (TextView) view.findViewById(R.id.idPedLstEstado);
        TextView txtCliente = (TextView) view.findViewById(R.id.idPedLstCliente);
        Cliente cliente = service.obtenerCliente(item.getCliente().getIdCliente());
        txtFecha.setText(txtFecha.getText().toString()+" "+FechaUtil.getFechaAsString(item.getFechaDelPedido()));
        txtTotal.setText(txtTotal.getText().toString()+" "+ "$" + String.format("%.2f",item.getImporteTotal().doubleValue()));
        txtEstado.setText(txtEstado.getText().toString()+" "+item.getEstado());
        txtCliente.setText(txtCliente.getText().toString()+" "+cliente.getNombre());
        return view;
    }
}
