package com.example.usodegmaps;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SeekBar.OnSeekBarChangeListener {

    private GoogleMap mMap;
    SeekBar seekwidth,seekred,seekgreen,seekblue;
    Button btdraw,btclear,btclearall,btmover;
    RadioButton rdanimar, rdmover,rdnormal,rdsatelite,rdhibrid,rdterrain;

    Polyline polyline = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();

    int red = 0, green = 0, blue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Asignar variables
        seekwidth = findViewById(R.id.seek_width);
        seekred = findViewById(R.id.seek_red);
        seekgreen = findViewById(R.id.seek_green);
        seekblue = findViewById(R.id.seek_blue);
        btdraw = findViewById(R.id.btdraw);
        btclear = findViewById(R.id.btclear);
        btclearall = findViewById(R.id.btclearall);
        btmover = findViewById(R.id.btngo);
        rdmover = findViewById(R.id.radmover);
        rdanimar = findViewById(R.id.radanimar);
        rdnormal = findViewById(R.id.rdnormal);
        rdsatelite = findViewById(R.id.rdSatelite);
        rdhibrid = findViewById(R.id.rdHibri);
        rdterrain = findViewById(R.id.rdTerrain);
        seekred.setEnabled(false);
        seekgreen.setEnabled(false);
        seekblue.setEnabled(false);
        // añadir línea al mapa
        btdraw.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick(View view){
                     seekred.setEnabled(false);
                     seekgreen.setEnabled(false);
                     seekblue.setEnabled(false);
                     if (polyline != null) polyline.remove();
                     PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngList).clickable(true);
                     polyline = mMap.addPolyline(polylineOptions);
                     seekred.setEnabled(true);
                     seekgreen.setEnabled(true);
                     seekblue.setEnabled(true);
                     polyline.setColor(Color.rgb(red,green,blue));
                     setWidth();

                 }

             }
        );
        // eliminar lineas
        btclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(polyline != null) polyline.remove();
                for (Marker marker : markerList) marker.remove();
                latLngList.clear();
                markerList.clear();
                seekwidth.setProgress(0);
                seekred.setProgress(0);
                seekblue.setProgress(0);
                seekgreen.setProgress(0);
                seekred.setEnabled(false);
                seekgreen.setEnabled(false);
                seekblue.setEnabled(false);
            }
        });
        //eliminar todo
        btclearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                seekwidth.setProgress(0);
                seekred.setProgress(0);
                seekblue.setProgress(0);
                seekgreen.setProgress(0);
                seekred.setEnabled(false);
                seekgreen.setEnabled(false);
                seekblue.setEnabled(false);
            }
        });
        seekred.setOnSeekBarChangeListener(this);
        seekblue.setOnSeekBarChangeListener(this);
        seekgreen.setOnSeekBarChangeListener(this);
        btmover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdmover.isChecked()){
                    LatLng micasa = new LatLng(-0.216079, -78.494246);
                    mMap.addMarker(new MarkerOptions().position(micasa).title("Marcador en mi casa").snippet("Esta es mi casa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(micasa,20);
                    mMap.moveCamera(cameraUpdate);

                }else
                    if (rdanimar.isChecked()){
                        LatLng micasa = new LatLng(-0.216079, -78.494246);
                        mMap.addMarker(new MarkerOptions().position(micasa).title("Marcador en mi casa").snippet("Esta es mi casa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(micasa,20);
                        mMap.animateCamera(cameraUpdate);
                    }
            }
        });
        rdnormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        rdsatelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });
        rdhibrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });
        rdterrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });
    }

    private void setWidth() {
        seekwidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int width = seekwidth.getProgress();
                if(polyline !=null) polyline.setWidth(width);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // crear marcadores con la longitud y latitud
        LatLng micasa = new LatLng(-0.216079, -78.494246);
        LatLng UTEQ = new LatLng(-1.0126002548120738, -79.46951496733342);
        // añadir los marcadores creados
        mMap.addMarker(new MarkerOptions().position(micasa).title("Marcador en mi casa").snippet("Esta es mi casa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.addMarker(new MarkerOptions().position(UTEQ).title("Marcador en la UTEQ").snippet("Esta es mi universidad").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //Añadir control de camara para el mapa, centrandonos en un punto
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(micasa,20);
        // Utilizar moveCamera o AnimateCamera para ir al punto deseado
        if(rdmover.isChecked())
        {
            mMap.moveCamera(cameraUpdate);
        }else
            if (rdanimar.isChecked()){
                mMap.animateCamera(cameraUpdate);
            }
            else{
                mMap.moveCamera(cameraUpdate);
            }
            //
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        // Activamos los controles de Zoom y de movimiento y rotación
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().isMyLocationButtonEnabled();
        mMap.getUiSettings().isZoomGesturesEnabled();
        mMap.getUiSettings().isRotateGesturesEnabled();
        //Añadir puntos en el mapa
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng latLng){
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                Marker marker = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                if(rdmover.isChecked())
                {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                }else
                if (rdanimar.isChecked()){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                }
                else{
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                }
                latLngList.add(latLng);
                markerList.add(marker);

            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()){
            case R.id.seek_red:
                red = i;
                break;
            case R.id.seek_green:
                green = i;
                break;
            case R.id.seek_blue:
                blue = i;
                break;
        }
        //establecer color de la línea
        polyline.setColor(Color.rgb(red,green,blue));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}