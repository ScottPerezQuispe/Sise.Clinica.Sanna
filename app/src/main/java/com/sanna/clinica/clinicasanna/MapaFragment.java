package com.sanna.clinica.clinicasanna;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends Fragment {

    private GoogleMap mMap;

    private Marker markerMap;

    private static View view;

    private double lat = -12.102469, lng = -77.022072;

    private boolean isFirst;


    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }


        try {



            view = inflater.inflate(R.layout.fragment_mapa, container, false);

            MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map_fragm);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @SuppressLint("MissingPermission")
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    UiSettings uiSettings =mMap.getUiSettings();
                    uiSettings.setZoomControlsEnabled(true);

                    mMap.setMyLocationEnabled(true);


                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                    addMarket(lat, lng);
                }
            });

        } catch (InflateException e) {

        }
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event


    public void addMarket(double lat, double lng) {
        if (!isFirst) {
            isFirst = true;
            LatLng coordenada = new LatLng(lat, lng);
            LatLng sydney1 = new LatLng(-12.070217, -77.036187);

            CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenada, 16);
            CameraUpdate ubication1 = CameraUpdateFactory.newLatLngZoom(sydney1, 16);

            if (mMap == null) {
            } else {
                markerMap = mMap.addMarker(new MarkerOptions().position(coordenada)
                        .draggable(false)
                        .title("SANNA|Clínica San Borja ")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                markerMap = mMap.addMarker(new MarkerOptions().position(sydney1)
                        .draggable(false)
                        .title("SANNA|Clínica El Golf")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


                mMap.animateCamera(ubication);

                mMap.animateCamera(ubication1);

            }
        } else {
            markerMap.setPosition(new LatLng(lat, lng));
        }
    }

}
