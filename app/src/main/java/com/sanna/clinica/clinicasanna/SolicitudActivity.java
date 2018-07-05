package com.sanna.clinica.clinicasanna;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.MotionEvent;

import com.sanna.clinica.clinicasanna.Entidad.Usuario;

public class SolicitudActivity extends AppCompatActivity {
    private ImageView btnExtrema;
    private LinearLayout linearLayout;
    Usuario objUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);

        btnExtrema = (ImageView) findViewById(R.id.btnExtrema);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutExterna);

        Intent objIntent = getIntent();
        Bundle objBundle = objIntent.getExtras();
        objUsuario = (Usuario)objBundle.getSerializable("usuario");

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a toast message.
                AlertDialog.Builder builder = new AlertDialog.Builder(SolicitudActivity.this);
                builder.setMessage("Esta seguro de solicitar una Ambulancia?");
                builder.setTitle("Advertencia");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SolicitudActivity.this,MapsActivity.class);

                        Bundle objBundle = new Bundle();
                        objUsuario.setIdTipo(1);//Extrema
                        objBundle.putSerializable("usuario", objUsuario);
                        intent.putExtras(objBundle);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });





/*
        btnExtrema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SolicitudActivity.this);
                builder.setMessage("Esta seguro de solicitar una Ambulancia?");
                builder.setMessage("Advertencia");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SolicitudActivity.this,MapaActivity.class);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog= builder.create();
                dialog.show();

            }
        });*/
    }
}
