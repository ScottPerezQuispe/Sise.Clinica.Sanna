package com.sanna.clinica.clinicasanna.Entidad;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;

import com.sanna.clinica.clinicasanna.MapaActivity;
import com.sanna.clinica.clinicasanna.MapsActivity;

/**
 * Created by scott on 21/05/2018.
 */

public class Localizacion implements LocationListener{
    MapsActivity mainActivity;

    public MapsActivity getMainActivity() {
        return mainActivity;
    }
    public void setMainActivity(MapsActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    public void onLocationChanged(Location loc) {
        loc.getLatitude();
        loc.getLongitude();

        String Text = "Mi ubicacion actual es: " + "\n Lat = "
                + loc.getLatitude() + "\n Long = " + loc.getLongitude();

        //this.mainActivity.setLocation(loc);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                System.out.println( "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                System.out.println( "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                System.out.println( "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es desactivado
        System.out.println("GPS Activado");
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("GPS Desactivado");
    }
}
