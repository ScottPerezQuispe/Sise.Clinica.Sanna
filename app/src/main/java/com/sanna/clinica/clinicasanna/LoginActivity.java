package com.sanna.clinica.clinicasanna;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sanna.clinica.clinicasanna.Api.ApiUtils;
import com.sanna.clinica.clinicasanna.Api.UserService;
import com.sanna.clinica.clinicasanna.Entidad.Usuario;

import java.net.URL;

import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private boolean bandera = false;
    private Button btnIngreso;
    private EditText txt_usuario, txt_clave;
    private  UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnIngreso = (Button) findViewById(R.id.btnIngreso);
        txt_usuario = (EditText) findViewById(R.id.txt_usuario);
        txt_clave = (EditText) findViewById(R.id.txt_clave);
        userService = ApiUtils.getUserService();

        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String username = txt_usuario.getText().toString();
                String password = txt_clave.getText().toString();

                if(ValidateLogin(username,password)){
                    Login( username, password);
                }

            }
        });
    }



    public boolean ValidateLogin(String username,String password){
        if(username==null||username.trim().length()==0||password==null||password.trim().length()==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

            builder.setMessage("Ingrese Usuario y/o contraseña");
            builder.setTitle("Advertencia");
            builder.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog=builder.create();
            builder.setCancelable(false);
            dialog.show();
            txt_usuario.setFocusable(true);
            return  false;

        }
        return true;
    }

    private void Login(String username,String password){
        retrofit2.Call<Usuario> call =userService.ValidarUsuario(username,password);



        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(LoginActivity.this);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setMessage("Iniciando Sesión...");
        progressDoalog.setCanceledOnTouchOutside(false);

        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(retrofit2.Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Usuario usuario= response.body();


                    if (usuario!= null){


                        Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                        Bundle objBundle = new Bundle();
                        objBundle.putSerializable("usuario", usuario);
                        intent.putExtras(objBundle);
                        startActivity(intent);
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                        builder.setMessage("Usuario y/o contraseña son incorrecto");
                        builder.setTitle("Advertencia");
                        builder.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog=builder.create();
                        builder.setCancelable(false);
                        dialog.show();

                    }
                    progressDoalog.dismiss();
                }else{
                    Toast.makeText(LoginActivity.this,"Incorrecto",Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<Usuario> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }
}
