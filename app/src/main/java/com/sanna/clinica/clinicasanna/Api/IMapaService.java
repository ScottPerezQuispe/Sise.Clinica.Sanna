package com.sanna.clinica.clinicasanna.Api;

import com.sanna.clinica.clinicasanna.Entidad.Clinica;
import com.sanna.clinica.clinicasanna.Entidad.SolicitudAmbulancia;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by scott on 25/06/2018.
 */

public interface IMapaService {

    @GET("Mapa/ObtenerClinicas")
    Call<List<Clinica> > ListarClinica();

    @POST("Mapa/RegistrarSolicitudAmbulancia")
    Call<Integer> RegistrarSolicitudAmbulancia(@Body SolicitudAmbulancia oSolicitudAmbulancia);

    @GET("SolicitudAmbulancia/ObtenerEstadoSolicitudAmbulancia")
    Call<Integer> ObtenerEstadoSolicitudAmbulancia(@Query("IdSolicitudAmbulancia") Integer IdSolicitudAmbulancia);


}
