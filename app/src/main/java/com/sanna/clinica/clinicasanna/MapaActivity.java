package com.sanna.clinica.clinicasanna;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.model.SquareCap;
import com.sanna.clinica.clinicasanna.Api.ApiUtils;
import com.sanna.clinica.clinicasanna.Api.IMapaService;
import com.sanna.clinica.clinicasanna.Entidad.Clinica;
import com.sanna.clinica.clinicasanna.Entidad.Localizacion;
import com.sanna.clinica.clinicasanna.Api.IGoogleApi;
import com.sanna.clinica.clinicasanna.Api.Common;
import com.sanna.clinica.clinicasanna.Entidad.Usuario;

import org.json.JSONArray;
import org.json.JSONObject;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapaActivity extends FragmentActivity  implements OnMapReadyCallback  {
    private Polyline blackPolyline,greyPolyline;
    private PolylineOptions polylineOptions,blackPolylineOptions;
    private GoogleMap mMap;
    private Marker markerMap;
    private static View view;
    private double lat = -12.102469, lng = -77.022072;
    private boolean isFirst;
    private boolean bandera=true;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Marker marker;
    IGoogleApi mService;
    private Handler handler;
    private List<LatLng> polylineList;
    private  int index,next;
    private  float v;
    private LatLng startPosition,endPosition;
    SupportMapFragment mapFragment;
    private IMapaService mapaService;
    //ArrayList<Clinica> lstClinica= new ArrayList<Clinica>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        polylineList = new ArrayList<>();
        if (status == ConnectionResult.SUCCESS) {
             mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mService = Common.getGoogleApi();
            mapaService = ApiUtils.getMapaService();
           // lstClinica= new ArrayList<Clinica>();
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {

            //locationStart();
        }
        ListarCLinica();
    }



    private final LocationListener locationListenerGPS = new LocationListener() {
        double longitudeBest, latitudeBest;
        double longitudeGPS, latitudeGPS;
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(longitudeGPS + "");
                    System.out.println(latitudeGPS + "");
                    Toast.makeText(MapaActivity.this, "GPS Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



     if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);



    }

    public void ListarCLinica(){
        retrofit2.Call<List<Clinica> > call =mapaService.ListarClinica();

        call.enqueue(new Callback<List<Clinica>>() {
            @Override
            public void onResponse(Call<List<Clinica>> call, Response<List<Clinica>> response) {

                if(response.isSuccessful()){
                    List<Clinica> lstclinica= response.body();
                    for (Clinica clinica : lstclinica){
                        System.out.println(clinica.getClinica());
                    }

                }else{
                    Toast.makeText(MapaActivity.this,"Incorrecto",Toast.LENGTH_SHORT).show();
                    //progressDoalog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<Clinica>> call, Throwable t) {
                Toast.makeText(MapaActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ListarRecorrido(){

        final LatLng sydney = new LatLng(-12.220559, -76.970818);
        final LatLng sydney1 = new LatLng(-12.219672,  -76.971556);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Sise Santa Beatriz"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(mMap.getCameraPosition().target)
                .zoom(17)
                .bearing(30)
                .tilt(45)
                .build()));
        String requestUrl =null;
        try {
            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&"+
                    "transit_routing_preference=less_driving&"+
                    "origin="+sydney.latitude+","+sydney.longitude+"&"+
                    "destination="+ "-12.219672" + "," + " -76.971556"+"&"+
                    "key=" +getResources().getString(R.string.google_maps_key);
            Log.d("URL",requestUrl);
            mService.getDataFromGoogleApi(requestUrl)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                JSONArray jsonArray =  jsonObject.getJSONArray("routes");
                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject route = jsonArray.getJSONObject(i);
                                    JSONObject poly = route.getJSONObject("overview_polyline");
                                    String polyline = poly.getString("points");
                                    polylineList = decodePoly(polyline);
                                }

                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for(LatLng latLng:polylineList)
                                    builder.include(latLng);
                                LatLngBounds bounds = builder.build();
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,2);
                                mMap.animateCamera(mCameraUpdate);


                                polylineOptions = new PolylineOptions();
                                polylineOptions.color(Color.GRAY);
                                polylineOptions.width(5);
                                polylineOptions.startCap(new SquareCap());
                                polylineOptions.endCap(new SquareCap());
                                polylineOptions.jointType(JointType.ROUND);
                                polylineOptions.add(sydney);
                                polylineOptions.addAll(polylineList);


                                greyPolyline = mMap.addPolyline(polylineOptions);
                                // greyPolyline = mMap.addPolyline(new PolylineOptions().add(sydney).width(5).color(Color.RED));


                                blackPolylineOptions = new PolylineOptions();
                                blackPolylineOptions.color(Color.BLACK);
                                blackPolylineOptions.width(5);
                                blackPolylineOptions.startCap(new SquareCap());
                                blackPolylineOptions.endCap(new SquareCap());
                                blackPolylineOptions.jointType(JointType.ROUND);
                                blackPolylineOptions.addAll(polylineList);
                                blackPolylineOptions.add(sydney1);
                                greyPolyline = mMap.addPolyline(blackPolylineOptions);

                                //greyPolyline = mMap.addPolyline(new PolylineOptions().add(sydney1).width(5).color(Color.RED));

                                mMap.addMarker(new MarkerOptions().position(polylineList.get(polylineList.size()-1)));

                                final ValueAnimator polylineAnimator = ValueAnimator.ofInt(0,100);
                                polylineAnimator.setDuration(2000);
                                polylineAnimator.setInterpolator(new LinearInterpolator());
                                polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        List<LatLng> points = greyPolyline.getPoints();
                                        int percentValue = (int)valueAnimator.getAnimatedValue();
                                        int size = points.size();
                                        int newPoints = (int) (size * (percentValue / 100.0f));
                                        List<LatLng> p = points.subList(0,newPoints);
                                        //   blackPolyline.setPoints(points);
                                    }
                                });

                                polylineAnimator.start();
                                marker = mMap.addMarker(new MarkerOptions().position(sydney)
                                        .flat(true)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));


                                handler = new Handler();
                                index = -1;
                                next = -1;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(index < polylineList.size()-1){
                                            index++;
                                            next = index -1;
                                        }
                                        if (index <polylineList.size()-1){
                                            startPosition = polylineList.get(index);
                                            endPosition = polylineList.get(index+1);
                                        }

                                        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,1);
                                        valueAnimator.setDuration(1000);
                                        valueAnimator.setInterpolator(new LinearInterpolator());
                                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                                v = valueAnimator.getAnimatedFraction();
                                                lng = v* endPosition.longitude+(1-v)
                                                        *startPosition.longitude;

                                                lat = v* endPosition.latitude+(1-v)
                                                        *startPosition.latitude;

                                                LatLng newPos = new LatLng(lat,lng);
                                                marker.setPosition(newPos);
                                                marker.setAnchor(0.5f,0.5f);
                                                marker.setRotation(getBearing(startPosition,newPos));
                                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                                        .target(newPos)
                                                        .zoom(15.5f)
                                                        .build()));
                                            }
                                        });

                                        valueAnimator.start();
                                        handler.postDelayed(this,1000);
                                        // List<LatLng> points = greyPolyline.getPoints();
                                        //int percentValue = (int)valueAnimator.getAnimatedValue();
                                        //int size = points.size();
                                        //int newPoints = (int) (size * (percentValue / 100.0f));
                                        //List<LatLng> p = points.subList(0,newPoints);
                                        //blackPolyline.setPoints(p);








                                    }
                                },3000);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                System.out.println("Erro :" +e.getMessage());

                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(MapaActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println("Erro :" +t.getMessage());
                        }
                    });

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private float getBearing(LatLng startPosition, LatLng newPos) {
        double lat = Math.abs(startPosition.latitude - newPos.latitude);
        double lng = Math.abs(startPosition.longitude - newPos.longitude);

        if(startPosition.latitude < newPos.latitude && startPosition.longitude < newPos.longitude)
            return  (float) (Math.toDegrees(Math.atan(lng/lat)));
        else if (startPosition.latitude >= newPos.latitude && startPosition.longitude < newPos.longitude)
            return  (float) ((90-Math.toDegrees(Math.atan(lng/lat)))+90);
        else if (startPosition.latitude >= newPos.latitude && startPosition.longitude >= newPos.longitude)
            return  (float) (Math.toDegrees(Math.atan(lng/lat))*180);
        else if (startPosition.latitude < newPos.latitude && startPosition.longitude >= newPos.longitude)
            return  (float) ((90-Math.toDegrees(Math.atan(lng/lat)))+270);

        return -1;
    }
    private List<LatLng> decodePoly(String encoded) {
        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
