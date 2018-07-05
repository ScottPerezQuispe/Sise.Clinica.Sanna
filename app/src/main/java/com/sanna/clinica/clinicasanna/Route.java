package com.sanna.clinica.clinicasanna;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by scott on 20/05/2018.
 */

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
