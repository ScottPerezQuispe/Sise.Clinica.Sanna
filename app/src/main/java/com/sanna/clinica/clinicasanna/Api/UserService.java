package com.sanna.clinica.clinicasanna.Api;

import com.sanna.clinica.clinicasanna.Entidad.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by scott on 9/06/2018.
 */

public interface UserService {

    @GET("Seguridad/ObtenerUsuario")
    Call<Usuario> ValidarUsuario(@Query("DNI") String DNI, @Query("Clave") String Clave);
}
