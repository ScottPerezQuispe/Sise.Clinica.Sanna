package com.sanna.clinica.clinicasanna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Detalle extends AppCompatActivity {

    ImageView foto;
    TextView titulo;
    TextView detalle;
    TextView vCoordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        //foto                = (ImageView) findViewById(R.id.imgFoto);
        //titulo              = (TextView) findViewById(R.id.txtTitulo);
        //detalle             = (TextView) findViewById(R.id.txtDetalle);
        vCoordenadas        = (TextView) findViewById(R.id.txtcoordenas);

        Datos obj = (Datos) getIntent().getExtras().getSerializable("objeto");

        //titulo.setText(obj.getTitulo());
        //detalle.setText(obj.getDetalle());
        //foto.setImageResource(obj.getImagen());
        vCoordenadas.setText(obj.getCoordenas());
    }
}
