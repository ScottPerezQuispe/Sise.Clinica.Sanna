package com.sanna.clinica.clinicasanna.Entidad;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by scott on 25/06/2018.
 */

public class Clinica implements Serializable {
    @Expose
    private int IdClinica;
    private String Clinica;
    private String Direccion;
    private String Telefono;
    private String Longitud;
    private String Latitud;


    public int getIdClinica() {
        return IdClinica;
    }

    public void setIdClinica(int idClinica) {
        IdClinica = idClinica;
    }

    public String getClinica() {
        return Clinica;
    }

    public void setClinica(String clinica) {
        Clinica = clinica;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }
}
