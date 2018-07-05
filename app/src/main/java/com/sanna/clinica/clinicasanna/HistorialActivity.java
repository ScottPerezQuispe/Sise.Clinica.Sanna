package com.sanna.clinica.clinicasanna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {
    ListView listView;
    private  HistorialListAdapter adpter;
    private List<Historial> mHistorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        listView=(ListView)findViewById(R.id.list_item);

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

        adpter = new HistorialListAdapter(getApplicationContext(),mHistorial);
        listView.setAdapter(adpter);
    }
}
