package com.sanna.clinica.clinicasanna;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by scott on 8/05/2018.
 */

public class HistorialListAdapter extends BaseAdapter {

    private Context mcontext;
    private List<Historial> mProducList;


    public HistorialListAdapter(Context mcontext ,List<Historial>mProducList){
        this.mcontext=mcontext;
        this.mProducList=mProducList;

    }
    @Override
    public int getCount() {
        return mProducList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProducList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= View.inflate(mcontext,R.layout.item_historial_list,null);

        TextView  txtEspecialidad = (TextView) v.findViewById(R.id.txtEspecialidad);
        ImageView imageView = (ImageView) v.findViewById(R.id.icon);
        TextView txtDoctor = (TextView) v.findViewById(R.id.txtDoctor);
        TextView txtDireccion = (TextView) v.findViewById(R.id.txtDireccion);
        TextView txtFecha = (TextView) v.findViewById(R.id.txtFecha);

        txtEspecialidad.setText(mProducList.get(position).getEspecialidad());
        imageView.setImageResource(mProducList.get(position).getImages());
        txtDoctor.setText(mProducList.get(position).getDoctor());
        txtDireccion.setText(mProducList.get(position).getClinica());
        txtFecha.setText(mProducList.get(position).getFechaRegistro());

        v.setTag(mProducList.get(position).getImages());

        return v;
    }
}
