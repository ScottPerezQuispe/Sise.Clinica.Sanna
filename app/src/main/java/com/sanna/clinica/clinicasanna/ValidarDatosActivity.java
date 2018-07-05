package com.sanna.clinica.clinicasanna;

import android.app.Notification;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.sanna.clinica.clinicasanna.Entidad.NotificacionHandler;

public class ValidarDatosActivity extends AppCompatActivity {

    private NotificacionHandler  notificacionHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_datos);
        notificacionHandler = new NotificacionHandler(this);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ValidarDatosActivity.this);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setMessage("Espere un momento estamos validando la disponibilidad de una ambulancia");
        progressDoalog.setCancelable(false);
        progressDoalog.setCanceledOnTouchOutside(false);

        // show it
        progressDoalog.show();


        sendNotification();
        progressDoalog.dismiss();
       /* Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progressDoalog.dismiss();
            }
        };*/

       // Handler pdCanceller = new Handler();
       // pdCanceller.postDelayed(progressRunnable, 5000);


    }

    private void sendNotification(){

        Notification.Builder nb =notificacionHandler.createNotification("Ambulancia","Su ambulancia ya llego", true);

        notificacionHandler.getManager().notify(1,nb.build());
    }
}
