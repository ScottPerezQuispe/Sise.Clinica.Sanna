package com.sanna.clinica.clinicasanna;


public class Historial {
    private  int  Images;
    private String Especialidad;
    private String Doctor;
    private String Clinica;
    private String FechaRegistro;


    public Historial(int  images, String especialidad, String doctor, String clinica, String fechaRegistro) {
        Images = images;
        Especialidad = especialidad;
        Doctor = doctor;
        Clinica = clinica;
        FechaRegistro = fechaRegistro;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(String especialidad) {
        Especialidad = especialidad;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getClinica() {
        return Clinica;
    }

    public void setClinica(String clinica) {
        Clinica = clinica;
    }

    public String getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        FechaRegistro = fechaRegistro;
    }
}
