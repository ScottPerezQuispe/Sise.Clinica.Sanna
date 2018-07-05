package com.sanna.clinica.clinicasanna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by scott on 9/06/2018.
 */

public class Adaptador extends BaseAdapter {


    Context contexto;
    List<Datos> ListaObjectos;

    public Adaptador(Context contexto, List<Datos> listaObjectos) {
        this.contexto = contexto;
        ListaObjectos = listaObjectos;
    }

    @Override
    public int getCount() {
        return ListaObjectos.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaObjectos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ListaObjectos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista= convertView;
        LayoutInflater inflater = LayoutInflater.from(contexto);
        vista = inflater.inflate(R.layout.itemlistview,null);

        ImageView imagen          = (ImageView) vista.findViewById(R.id.imageView);
        TextView titulo          = (TextView) vista.findViewById(R.id.textTitulo);
        TextView    detalle         = (TextView) vista.findViewById(R.id.textDescripcion);
        TextView    coordenas       = (TextView) vista.findViewById(R.id.txtcoordenas);

        titulo.setText(ListaObjectos.get(position).getTitulo().toString());
        detalle.setText(ListaObjectos.get(position).getDetalle().toString());
        imagen.setImageResource(ListaObjectos.get(position).getImagen());
        coordenas.setText(ListaObjectos.get(position).getCoordenas().toString());

        return  vista;
    }
}
