package com.sanna.clinica.clinicasanna;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.SquareCap;
import com.sanna.clinica.clinicasanna.Api.ApiUtils;
import com.sanna.clinica.clinicasanna.Api.Common;
import com.sanna.clinica.clinicasanna.Api.IGoogleApi;
import com.sanna.clinica.clinicasanna.Api.IMapaService;
import com.sanna.clinica.clinicasanna.Entidad.Clinica;
import com.sanna.clinica.clinicasanna.Entidad.Localizacion;
import com.sanna.clinica.clinicasanna.Entidad.SolicitudAmbulancia;
import com.sanna.clinica.clinicasanna.Entidad.Usuario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
     ProgressDialog progressDoalog;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private List<LatLng> polylineList;
    private Marker marker;
    private  float v;
    private double lat,lng;
    private Handler handler;
    private LatLng startPosition,endPosition;
    private  int index,next;
    private Button btnGo;
    private EditText edtPlace;
    private String destination;
    private PolylineOptions polylineOptions,blackPolylineOptions;
    private Polyline blackPolyline,greyPolyline;
    private LatLng mylocation;
    IGoogleApi mService;
    private IMapaService mapaService;
    Usuario objUsuario;
    double longitudeGPS, latitudeGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapaService = ApiUtils.getMapaService();
        polylineList = new ArrayList<>();
        btnGo = (Button)findViewById(R.id.btnSearch);
        edtPlace = (EditText)findViewById(R.id.edtPlace);

        Intent objIntent = getIntent();
        Bundle objBundle = objIntent.getExtras();
        objUsuario = (Usuario)objBundle.getSerializable("usuario");
        mapFragment.getMapAsync(MapsActivity.this);
        mService = Common.getGoogleApi();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {

            locationStart();
        }
      //  ejecutar();
    }

    private void locationStart() {
        final Location olocation=null;
        LatLng la =null ;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener(){

            @Override
            public void onLocationChanged(Location location) {
                longitudeGPS= location.getLongitude();
                latitudeGPS =location.getLatitude();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(longitudeGPS + "");
                        System.out.println(latitudeGPS + "");
                       // Toast.makeText(MapsActivity.this, "GPS Provider update", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER,0,0,locationListener);

    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings uiSettings =mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        /*Evento al dar Click los marcadores */
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final SolicitudAmbulancia solicitudAmbulancia= new SolicitudAmbulancia();
                solicitudAmbulancia.setIdPaciente(objUsuario.getIdUsuario());
                solicitudAmbulancia.setIdTipoEmergencia(objUsuario.getIdTipo());
                solicitudAmbulancia.setEstadoSolicitudAmbulancia(1);

                Geocoder geocoder;
                List<Address> addresses;
                String direcion="";
                geocoder = new Geocoder(MapsActivity.this,Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitudeGPS,longitudeGPS, 1);
                    Address adres = addresses.get(0);
                     direcion= adres.getAddressLine(0);
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Bloqueando pantalla

                progressDoalog = new ProgressDialog(MapsActivity.this);
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.setMessage("Registrando Solicitud...");
                progressDoalog.setCanceledOnTouchOutside(false);
                // show it
                progressDoalog.show();

                solicitudAmbulancia.setDistrito(direcion);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RegistrarSolicitud (solicitudAmbulancia);
                    }
                },4000);


                return false;
            }
        });


        mMap.setMyLocationEnabled(true);
        ListarCLinica();

    }

//Registra la solicitud de la ambulancia
 public void RegistrarSolicitud(SolicitudAmbulancia solicitudAmbulancia){



     retrofit2.Call<Integer> call =mapaService.RegistrarSolicitudAmbulancia(solicitudAmbulancia);
     call.enqueue(new Callback<Integer>() {
         @Override
         public void onResponse(Call<Integer> call, Response<Integer> response) {
             if(response.isSuccessful()){
                 int respuesta= response.body();

                 progressDoalog.dismiss();
                 ObtenerEstadoSolicitudAmbulancia(respuesta);
             }else{
                 //Toast.makeText(MapsActivity.this,"Error al solicitar ambulancia",Toast.LENGTH_SHORT).show();
                 //progressDoalog.dismiss();
             }
         }

         @Override
         public void onFailure(Call<Integer> call, Throwable t) {
             Toast.makeText(MapsActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
         }
     });
 }

 public void Hilo(){
     try {
        Thread.sleep(1000);
     }catch (InterruptedException e){
            e.printStackTrace();
     }
 }
public void ejecutar(){
     time time = new time();
     time.execute(1);
}
 public class  time extends AsyncTask<Integer,Integer,Boolean>{



     @Override
     protected Boolean doInBackground(Integer... integers) {
         Hilo();
         //}

         return false ;
     }

     @Override
     protected void onPostExecute(Boolean aBoolean) {
         if(aBoolean){
             ejecutar();
             Toast.makeText(MapsActivity.this,"Cada 2 segundos",Toast.LENGTH_SHORT).show();
         }

     }
 }

 public int ObtenerEstadoSolicitudAmbulancia (Integer IdSolicitud){
     final int[] respueta = {0};
    retrofit2.Call<Integer>call = mapaService.ObtenerEstadoSolicitudAmbulancia(IdSolicitud);
    call.enqueue(new Callback<Integer>() {
        @Override
        public void onResponse(Call<Integer> call, Response<Integer> response) {
            if(response.isSuccessful()){

                int   IdSolicitud= (int) response.body();
                respueta[0] =IdSolicitud;
            }
        }

        @Override
        public void onFailure(Call<Integer> call, Throwable t) {
            Toast.makeText(MapsActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
        }
    });
    return respueta[0];
 }

 public void ListarCLinica(){
        retrofit2.Call<List<Clinica> > call =mapaService.ListarClinica();

        call.enqueue(new Callback<List<Clinica>>() {
            @Override
            public void onResponse(Call<List<Clinica>> call, Response<List<Clinica>> response) {

                if(response.isSuccessful()){
                    List<Clinica>lstclinica= response.body();

                    for (Clinica clinica : lstclinica){
                        double lat  = Double.parseDouble(clinica.getLatitud());
                        double Lon  = Double.parseDouble(clinica.getLongitud());
                        LatLng _cooMarker = new LatLng( lat,Lon);



                        marker = mMap.addMarker(new MarkerOptions()

                                .title(clinica.getClinica())
                                .position(_cooMarker)
                                        .snippet("Extra info")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                                );



                        // LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        //builder.include(marker.getPosition());
                        /*Faltaba esoto*/
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(_cooMarker).zoom(16).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }
                    marker.showInfoWindow();
                    marker.isInfoWindowShown();
                }else{
                    Toast.makeText(MapsActivity.this,"Incorrecto",Toast.LENGTH_SHORT).show();
                    //progressDoalog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<Clinica>> call, Throwable t) {
                Toast.makeText(MapsActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MapsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
