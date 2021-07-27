package com.example.sos.controllers.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sos.R;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.SupportMapFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class activity_solicitudaveria_ubicacion extends AppCompatActivity {

    // Declaración de variables
    Map Mapa = null;
    MapMarker Marcador;
    Location Coordenada;
    // Fragmento del mapa incrustado en la actividad
    SupportMapFragment MapaFragmento = null;
    ImageButton Atras;
    ImageButton Siguiente;
    String TIPO_AVERIA;
    String USUARIO;
    static final int PERMISO_GPS = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudaveria_ubicacion);

        Atras = findViewById(R.id.Atras);
        Siguiente = findViewById(R.id.Siguiente);
        TIPO_AVERIA = getIntent().getExtras().getString("TIPO_AVERIA");
        USUARIO = getIntent().getExtras().getString("USUARIO");

        InicializandoVentana();
        InicializandoEventos();

        // Obtenemos permiso de usar el gps
        ActivityCompat.requestPermissions(activity_solicitudaveria_ubicacion.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISO_GPS);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISO_GPS: {
                // Si tenemos permiso de usar el gps
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Este metodo incrusta el mapa en la vista
                    Coordenada = ObtenerCoordenadas();
                    InicializarMapa(Coordenada.getLatitude(),Coordenada.getLongitude());

                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("No podemos continuar, si no sabemos tu ubicación.")
                            .show();
                    Intent Vista = new Intent(activity_solicitudaveria_ubicacion.this, activity_iniciocliente.class);
                    startActivity(Vista);
                }
                return;
            }
        }
        return;
    }

    private void InicializandoEventos(){

        // Regresa al formulario anterior
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_solicitudaveria_ubicacion.this, activity_solicitudaveria_tipo.class);
                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Vista);
            }
        });

        // Avanza al siguiente activity
        Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                new SweetAlertDialog(activity_solicitudaveria_ubicacion.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("¿Estas seguro?")
                        .setContentText("Se enviara a un tecnico a esta ubicación.")
                        .setConfirmText("Si")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog Dialog) {
                                Intent Vista = new Intent(activity_solicitudaveria_ubicacion.this, activity_solicitudaveria_resumen.class);
                                Vista.putExtra("TIPO_AVERIA",TIPO_AVERIA);
                                Vista.putExtra("LATITUD",Double.toString(Coordenada.getLatitude()));
                                Vista.putExtra("LONGITUD",Double.toString(Coordenada.getLongitude()));
                                Vista.putExtra("USUARIO",USUARIO);
                                startActivity(Vista);
                            }
                        })
                        .setCancelButton("Cancelar", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog Dialogo) {
                                Dialogo.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
    }

    private void InicializarMapa(final Double Latitud, final Double Longitud) {

        MapaFragmento = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Mapa);
        MapaFragmento.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(OnEngineInitListener.Error Error) {
                if (Error == OnEngineInitListener.Error.NONE) {
                    Mapa = MapaFragmento.getMap();
                    Mapa.setCenter(new GeoCoordinate(Latitud, Longitud, 0.0), Map.Animation.NONE);
                    Mapa.setZoomLevel(17);
                    Marcador  = new MapMarker();
                    Marcador.setCoordinate(new GeoCoordinate(Latitud, Longitud, 0.0));
                    Mapa.addMapObject(Marcador);
                    Mapa.setMapScheme( Map.Scheme.HYBRID_DAY);

                } else {
                    new SweetAlertDialog(activity_solicitudaveria_ubicacion.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Ocurrio un problema al cargar el mapa.")
                            .show();
                }
            }
        });
    }

    private void InicializandoVentana() {
        // Se quita la barra de estatus
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private Location ObtenerCoordenadas() {
        LocationManager LocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Verificamos si la api soporta la geolocalizacion
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Verificamos si tenemos acceso a la ubicacion
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location Ubicacion = LocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                return Ubicacion;
            } else {
                return  null;
            }
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("Tu telefono no soporta la geolocalizacion.")
                    .show();
            Intent Vista = new Intent(activity_solicitudaveria_ubicacion.this, activity_iniciocliente.class);
            startActivity(Vista);
        }
        return  null;
    }

    // Regresar al activity anterior
    @Override
    public void onBackPressed() {
        Atras.performClick();
    }

}