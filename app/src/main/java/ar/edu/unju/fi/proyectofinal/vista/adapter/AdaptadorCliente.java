package ar.edu.unju.fi.proyectofinal.vista.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.unju.fi.proyectofinal.R;
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Cliente;
import ar.edu.unju.fi.proyectofinal.modelo.util.UsuarioUtil;


public class AdaptadorCliente extends BaseAdapter {
    Context context;
    List<Cliente> listaCliente;

    public AdaptadorCliente(Context context, List<Cliente> listaCliente) {
        this.context = context;
        this.listaCliente = listaCliente;
    }

    /**
     * Retorna la cantidad de elementos de la lista
     */
    @Override
    public int getCount() {
        return listaCliente.size();
    }

    /**
     * Retorna el objetivo en la posicion indicada
     */
    @Override
    public Object getItem(int i) {
        return listaCliente.get(i);
    }

    /**
     * Retorna el id del objetivo de la posicion indicada
     */
    @Override
    public long getItemId(int i) {
        return listaCliente.get(i).getIdCliente();
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
        Cliente item = (Cliente) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.estructuralstviewcliente, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.idImgUsuario);//obtiene la referencia a la imagen
        TextView txtCuit = (TextView) view.findViewById(R.id.idTxtCuit);
        TextView txtNombre = (TextView) view.findViewById(R.id.idTxtNombre);
        TextView txtDomicilio = (TextView) view.findViewById(R.id.idTxtDomicilio);

        txtCuit.setText(txtCuit.getText().toString() +" "+ UsuarioUtil.formatCuit(item.getCuit()));
        txtNombre.setText(txtNombre.getText().toString() + " " + item.getNombre());
        txtDomicilio.setText(txtDomicilio.getText().toString() +" "+ item.getDomicilio());
        return view;
    }
}
