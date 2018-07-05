package com.sanna.clinica.clinicasanna.Entidad;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by scott on 29/06/2018.
 */

public class SolicitudAmbulancia implements Serializable {

    @Expose
    private int IdSolicitudAmbulancia;
    private int IdAutorizante;
    private int IdTipoEmergencia;
    private int IdPaciente;

    public int getEstadoSolicitudAmbulancia() {
        return EstadoSolicitudAmbulancia;
    }

    public void setEstadoSolicitudAmbulancia(int estadoSolicitudAmbulancia) {
        EstadoSolicitudAmbulancia = estadoSolicitudAmbulancia;
    }

    private int EstadoSolicitudAmbulancia;



    public int getIdSolicitudAmbulancia() {
        return IdSolicitudAmbulancia;
    }

    public void setIdSolicitudAmbulancia(int idSolicitudAmbulancia) {
        IdSolicitudAmbulancia = idSolicitudAmbulancia;
    }

    public int getIdAutorizante() {
        return IdAutorizante;
    }

    public void setIdAutorizante(int idAutorizante) {
        IdAutorizante = idAutorizante;
    }

    public int getIdTipoEmergencia() {
        return IdTipoEmergencia;
    }

    public void setIdTipoEmergencia(int idTipoEmergencia) {
        IdTipoEmergencia = idTipoEmergencia;
    }

    public int getIdPaciente() {
        return IdPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        IdPaciente = idPaciente;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    private String Distrito;

}
