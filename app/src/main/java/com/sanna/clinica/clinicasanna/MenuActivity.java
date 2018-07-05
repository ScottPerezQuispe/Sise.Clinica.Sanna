package com.sanna.clinica.clinicasanna;
import android.location.LocationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanna.clinica.clinicasanna.Entidad.Usuario;
public class MenuActivity extends AppCompatActivity {
    private ImageView btnSalir;
    private ImageView btnHistorial;
    private ImageView btnEmergencia;
    private ImageView btnUbicacion;
    private ImageView btnPaciente;

    private TextView  txtNombre;

     Usuario objUsuario;
     AlertDialog alert = null;
     LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnSalir = (ImageView) findViewById(R.id.btnSalir);
        btnHistorial = (ImageView) findViewById(R.id.btnHistorial);
        btnEmergencia = (ImageView) findViewById(R.id.btnEmergencia);
        btnUbicacion = (ImageView) findViewById(R.id.btnUbicacion);
        btnPaciente = (ImageView) findViewById(R.id.btnPaciente);
        txtNombre = (TextView) findViewById(R.id.txtNombre);

        Intent objIntent = getIntent();
        Bundle objBundle = objIntent.getExtras();
        objUsuario = (Usuario)objBundle.getSerializable("usuario");
        txtNombre.setText(objUsuario.getNombre());


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setMessage("Esta seguro de solicitar una ambulancia?");
                //builder.setTitle("Advertencia");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
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


        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,HistorialActivity.class);
                startActivity(intent);

            }
        });
        btnEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE );

                if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    AlertNoGps();
                }else {

                    Intent intent = new Intent(MenuActivity.this,SolicitudActivity.class);

                    Bundle objBundle = new Bundle();
                    objBundle.putSerializable("usuario", objUsuario);
                    intent.putExtras(objBundle);
                    startActivity(intent);
                }




            }
        });

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,UbicacionActivity.class);
                startActivity(intent);
            }
        });

        btnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,ValidarDatosActivity.class);

                startActivity(intent);
            }
        });


    }

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }
}

