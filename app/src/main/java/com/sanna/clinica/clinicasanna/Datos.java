package com.sanna.clinica.clinicasanna;

import java.io.Serializable;

/**
 * Created by scott on 9/06/2018.
 */

public class Datos implements Serializable {
    private int Id;
    private String Titulo;
    private String Detalle;
    private int Imagen;
    private String Coordenas;

    public Datos(String detalle ,int id, int imagen, String titulo, String  coordenas) {
        Detalle = detalle;
        Id = id;
        Imagen = imagen;
        Titulo = titulo;
        Coordenas = coordenas;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String detalle) {
        Detalle = detalle;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public String getCoordenas() {
        return Coordenas;
    }

    public void setCoordenas(String coordenas) {
        Coordenas = coordenas;
    }
}
