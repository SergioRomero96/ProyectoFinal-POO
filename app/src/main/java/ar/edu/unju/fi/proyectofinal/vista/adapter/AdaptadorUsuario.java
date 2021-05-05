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
import ar.edu.unju.fi.proyectofinal.modelo.dominio.Usuario;

/**
 * adaptador para mostrar un listview personalizado
 */
public class AdaptadorUsuario extends BaseAdapter {
    Context context;
    List<Usuario> listUsuarios;

    /**
     * constructor recibe la lista a mostrar y el contexto de la aplicacion
     * @param context
     * @param listUsuarios
     */
    public AdaptadorUsuario(Context context, List<Usuario> listUsuarios) {
        this.context = context;
        this.listUsuarios = listUsuarios;
    }

    /**
     * Retorna la cantidad de elementos de la lista
     */
    @Override
    public int getCount() {
        return listUsuarios.size();
    }

    /**
     * Retorna el objetivo en la posicion indicada
     */
    @Override
    public Object getItem(int i) {
        return listUsuarios.get(i);
    }

    /**
     * Retorna el id del objetivo de la posicion indicada
     */
    @Override
    public long getItemId(int i) {
        return listUsuarios.get(i).getIdUsuario();
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
        Usuario item = (Usuario) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.estructuralstview, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.idImgUsuario);//obtiene la referencia a la imagen
        TextView txtNombre = (TextView) view.findViewById(R.id.idTxtNombre);
        TextView txtDni = (TextView) view.findViewById(R.id.idTxtDni);
        TextView txtRol = (TextView) view.findViewById(R.id.idTxtRol);

        txtNombre.setText(txtNombre.getText().toString()+" "+item.getNombre());
        txtDni.setText(txtDni.getText().toString()+" "+item.getDni().toString());
        txtRol.setText(txtRol.getText().toString()+" "+item.getRol());

        return view;
    }
}
