package com.sanna.clinica.clinicasanna;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class HistorialFragment extends Fragment {

   ListView listView;
   private  HistorialListAdapter adpter;
   private List<Historial>mHistorial;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_historial, container, false);

        listView=(ListView)rootView.findViewById(R.id.list_item);



        mHistorial = new ArrayList<>();
        mHistorial.add(new Historial(R.drawable.ambulancia,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.user_info_icon,"Especialidad: Odontologia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.ambulancia,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.user_info_icon,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.ambulancia,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.user_info_icon,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.user_info_icon,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.ambulancia,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.ambulancia,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.ambulancia,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.ambulancia,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));
        mHistorial.add(new Historial(R.drawable.ambulancia,"Emergencia","Dr. Delgado Coz Oscar","Centro de atención: Clínica Sanna el Golf","08/03/2018"));

        adpter = new HistorialListAdapter(rootView.getContext(),mHistorial);
        listView.setAdapter(adpter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > adapter, View view,int position, long arg){
                // TODO Auto-generated method stub
                TextView v = (TextView) view.findViewById(R.id.txtDireccion);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Descripcion "+v.getText());
                builder.setTitle("Advertencia");
                builder.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
                }
            }
        );

        return rootView;
    }


}
