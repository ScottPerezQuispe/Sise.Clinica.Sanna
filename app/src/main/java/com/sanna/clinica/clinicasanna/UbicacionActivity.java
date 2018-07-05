package com.sanna.clinica.clinicasanna;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class UbicacionActivity extends AppCompatActivity {
    ListView listaDatos;
    ArrayList<Datos> Lista;
    String coordenadas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);
        listaDatos = (ListView) findViewById(R.id.listDatos);

        Lista = new ArrayList<Datos>();

        Lista.add(new Datos("Av. Aurelio Miró Quesada 1030, San Isidro",1,R.drawable.clinicaelgolf,"Clinica Sanna - El Golf", "geo: -12.098635, -77.050810"));
        Lista.add(new Datos("Av. Guardia Civil 337, San Borja - Lima",2,R.drawable.clinicasanborja,"Clinica Sanna - San Borja","geo: -12.091561, -77.008226"));
        Lista.add(new Datos("Av. Raúl Ferrero Rebagliati N° 1256 - La Molina.",3,R.drawable.clinicalamolina,"Clinica Sanna - La Molina","geo: -12.090098, -76.950377"));
        Lista.add(new Datos("Av. Primavera 336, urb. Chacarilla del Estanque",4,R.drawable.clinicachacarrilla,"Clinica Sanna - Chacarrilla", "geo: -12.111245, -76.989827"));

        Adaptador miadaptador = new Adaptador(getApplicationContext(),Lista);

        listaDatos.setAdapter(miadaptador);

        listaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        coordenadas =  "geo: -12.098635, -77.050810";
                        break;
                    case 1:
                        coordenadas = "geo: -12.091561, -77.008226";
                        break;
                    case 2:
                        coordenadas = "geo: -12.090098, -76.950377";
                        break;
                    case 3:
                        coordenadas = "geo: -12.111245, -76.989827";
                        break;

                }
                //String uri = "geo: -12.077512, -77.035549 ";

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(coordenadas)));

                //Datos obj = (Datos)parent.getItemAtPosition(position);
                //Intent paso = new Intent(getApplicationContext(),Detalle.class);
                //paso.putExtra("objeto",(Serializable)obj);
                //startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(String.valueOf(paso))));
            }
        });
    }
}
