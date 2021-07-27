package com.example.sos.controllers.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sos.R;
import com.example.sos.models.entity.E_API;
import com.example.sos.models.ws.ws_averia;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.SupportMapFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fonts.MyTextView;
import fonts.TextViewMavenRegular;

public class activity_seguimiento_averia extends AppCompatActivity {

    String ID_AVERIA;
    String EsUsuario;
    MyTextView Nombre_Cliente;
    MyTextView Vehiculo;
    MyTextView Placa;
    MyTextView Estado;
    MyTextView Telefono;
    MyTextView Latitud;
    MyTextView Longitud;
    MyTextView Mecanico;
    TextViewMavenRegular BTN_ESTADO;
    SupportMapFragment MapaFragmento = null;
    E_API API;
    ws_averia AVAPI;
    Map Mapa = null;
    MapMarker Marcador;
    ImageButton Atras;
    String ID_USUARIO;
    String TIPO_OPERACION;
    ImageButton Calificar;
    RatingBar Estrellas;
    TextViewMavenRegular BTN_CALIFICAR;

    static final int PERMISO_GPS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_averia);
        ID_AVERIA = getIntent().getExtras().getString("ID_AVERIA");
        EsUsuario =     getIntent().getExtras().getString("EsUsuario");
        Atras = findViewById(R.id.Atras);
        Nombre_Cliente = findViewById(R.id.NombreCliente);
        Vehiculo = findViewById(R.id.Vehiculo);
        Placa= findViewById(R.id.Placa);
        Estado= findViewById(R.id.Estado);
        Telefono= findViewById(R.id.Telefono);
        Latitud= findViewById(R.id.Latitud);
        Longitud= findViewById(R.id.Longitud);
        Mecanico = findViewById(R.id.Mecanico);
        BTN_ESTADO = findViewById(R.id.btnestado);
        API = new E_API(activity_seguimiento_averia.this);
        Calificar = findViewById(R.id.Calificar);
        ID_USUARIO= (getIntent().getStringExtra("ID_USUARIO"));
        if(EsUsuario.equals("1"))
        {
            BTN_ESTADO.setVisibility(View.GONE);
        }
        else
        {
            Calificar.setVisibility(View.GONE);
            BTN_ESTADO.setVisibility(View.VISIBLE);
        }

        InicializandoVentana();
        InicializarEventos();
        CargarVista();
    }


    public void InicializarEventos(){

        // Boton atras para retroceder
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Vista = new Intent(activity_seguimiento_averia.this, activity_listado_averias.class);
                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Vista);
            }
        });

        Calificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Estado.getText().toString().equals("Finalizada") )
                {
                    MostrarCalificacionEstrellas(getWindow().getDecorView().getRootView());
                }
                else{
                    new SweetAlertDialog(activity_seguimiento_averia.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Aviso")
                            .setContentText("Aun no puedes calificar esta averia porque no esta en estado de finalizado aun.")
                            .show();
                }
            }
        });

        BTN_ESTADO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pasa el estado a atendido
                final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_seguimiento_averia.this, SweetAlertDialog.PROGRESS_TYPE);
                Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                Dialogo.setTitleText("Espere por favor...");
                Dialogo.show();

                if(TIPO_OPERACION.equals("2"))
                {
                    HashMap<String, String> Parametros = new HashMap<String, String>();
                    Parametros.put("ID_MECANICO", ID_USUARIO);
                    Parametros.put("ID_ESTADO", "2");
                    Parametros.put("ID_AVERIA", ID_AVERIA);
                    // Se envia un Get a la API para obtener los datos de el usuario
                    API.GetAPI().add(AVAPI.Put(new JSONObject(Parametros), new E_API.VolleyResponseListener() {
                        @Override
                        public void onError(String Mensaje) {
                            Dialogo.dismissWithAnimation();
                            MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                        }

                        @Override
                        public void onResponse(Object Respuesta) throws JSONException {
                            Dialogo.dismissWithAnimation();
                            JSONObject Objeto = new JSONObject(Respuesta.toString());
                            if(Objeto.getInt("Codigo") == 5)
                            {
                                new SweetAlertDialog(activity_seguimiento_averia.this,SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Exito")
                                        .setContentText("Se actualizo correctamente la averia")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent Vista = new Intent(activity_seguimiento_averia.this, activity_iniciomecanico.class);
                                                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                startActivity(Vista);
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                            else {
                                MostrarError("Error","Ocurrio un error al comunicar con el servidor");
                            }
                        }
                    }));
                }
                // Pasa el estado a finalizado
                else{
                    HashMap<String, String> Parametros = new HashMap<String, String>();
                    Parametros.put("ID_MECANICO", ID_USUARIO);
                    Parametros.put("ID_ESTADO", "3");
                    Parametros.put("ID_AVERIA", ID_AVERIA);
                    // Se envia un Get a la API para obtener los datos de el usuario
                    API.GetAPI().add(AVAPI.Put(new JSONObject(Parametros), new E_API.VolleyResponseListener() {
                        @Override
                        public void onError(String Mensaje) {
                            Dialogo.dismissWithAnimation();
                            MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                        }

                        @Override
                        public void onResponse(Object Respuesta) throws JSONException {
                            Dialogo.dismissWithAnimation();
                            JSONObject Objeto = new JSONObject(Respuesta.toString());

                            if(Objeto.getInt("Codigo") == 5)
                            {
                                new SweetAlertDialog(activity_seguimiento_averia.this,SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Exito")
                                        .setContentText("Se actualizo correctamente la averia")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent Vista = new Intent(activity_seguimiento_averia.this, activity_iniciomecanico.class);
                                                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                startActivity(Vista);
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                            else {
                                MostrarError("Error","Ocurrio un error al comunicar con el servidor");
                            }
                        }
                    }));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISO_GPS: {
                // Si tenemos permiso de usar el gps
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Este metodo incrusta el mapa en la vista
                    InicializarMapa(Double.parseDouble(Latitud.getText().toString()),Double.parseDouble(Longitud.getText().toString()));

                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("No podemos continuar, si no sabemos tu ubicación.")
                            .show();
                    Intent Vista = new Intent(activity_seguimiento_averia.this, activity_iniciocliente.class);
                    startActivity(Vista);
                }
                return;
            }
        }
        return;
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
                    new SweetAlertDialog(activity_seguimiento_averia.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Ocurrio un problema al cargar el mapa.")
                            .show();
                }
            }
        });
    }


    private void CargarVista() {
        final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_seguimiento_averia.this, SweetAlertDialog.PROGRESS_TYPE);
        Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
        Dialogo.setTitleText("Espere por favor...");
        Dialogo.show();

        // Se envia un Get a la API para obtener los datos de el usuario
        API.GetAPI().add(AVAPI.GetByID(ID_AVERIA, new E_API.VolleyResponseListener() {
            @Override
            public void onError(String Mensaje) {
                Dialogo.dismissWithAnimation();
                MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
            }

            @Override
            public void onResponse(Object Respuesta) throws JSONException {
                Dialogo.dismissWithAnimation();
                JSONObject Objeto = new JSONObject(Respuesta.toString());
                Nombre_Cliente.setText(Objeto.getString("NOMBRE_USUARIO")+" "+Objeto.getString("APELLIDO_USUARIO"));
                Vehiculo.setText(Objeto.getString("MARCA_CARRO")+" - "+Objeto.getString("MODELO_CARRO"));
                Placa.setText(Objeto.getString("PLACA_CARRO"));
                Estado.setText(Objeto.getString("DESCRIPCION_ESTADO"));
                Telefono.setText(Objeto.getString("TELEFONO_USUARIO"));
                Latitud.setText(Objeto.getString("LATITUD_UBICACION"));
                Longitud.setText(Objeto.getString("LONGITUD_UBICACION"));
                if(Estado.getText().toString().equals("Sin Atender"))
                {
                    TIPO_OPERACION = "2"  ;
                    BTN_ESTADO.setText("Atender");
                }
                else if(Estado.getText().toString().equals("En progreso")){
                    TIPO_OPERACION = "3"  ;
                    BTN_ESTADO.setText("Finalizar");
                }
                else
                {
                    BTN_ESTADO.setVisibility(View.GONE);
                }
                if(Objeto.getString("ATENDER").equals("null")){
                    Mecanico.setText("Aun no asignado");
                }
                else{
                    Mecanico.setText(Objeto.getString("ATENDER"));
                }
                // Obtenemos permiso de usar el gps
                ActivityCompat.requestPermissions(activity_seguimiento_averia.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISO_GPS);
                Dialogo.dismissWithAnimation();
            }
        }));
    }

    private  void InicializandoVentana(){
        // Se quita la barra de estatus
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // Metodos de ayuda del activity
    private  void MostrarError(String Cabezera,String Mensaje){
        new SweetAlertDialog(activity_seguimiento_averia.this,SweetAlertDialog.ERROR_TYPE)
                .setTitleText(Cabezera)
                .setContentText(Mensaje)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent Vista = new Intent(activity_seguimiento_averia.this, activity_iniciocliente.class);
                        Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(Vista);
                        finish();
                    }
                })
                .show();
    }

    // Invoca al layout para calificar
    public void MostrarCalificacionEstrellas(View view) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.modal_calificacion, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Estrellas = popupView.findViewById(R.id.ratingbar);
        BTN_CALIFICAR = popupView.findViewById(R.id.btncalificar);

        BTN_CALIFICAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                HashMap<String, String> Parametros = new HashMap<String, String>();
                Parametros.put("CALIFICACION", String.valueOf(Estrellas.getNumStars()) );
                Parametros.put("ID_AVERIA", ID_AVERIA);

                final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_seguimiento_averia.this, SweetAlertDialog.PROGRESS_TYPE);
                Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                Dialogo.setTitleText("Espere por favor...");
                Dialogo.show();

                API.GetAPI().add(AVAPI.Calificar(new JSONObject( Parametros), new E_API.VolleyResponseListener() {
                    @Override
                    public void onError(String Mensaje) {
                        Dialogo.dismissWithAnimation();
                        MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                    }

                    @Override
                    public void onResponse(Object Respuesta) throws JSONException {
                        Dialogo.dismissWithAnimation();
                        new SweetAlertDialog(activity_seguimiento_averia.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Exito")
                                .setContentText("Se califico la averia con exito")
                                .show();
                    }
                }));
            }
        });
    }


    // Regresar al activity anterior
    @Override
    public void onBackPressed() {
        Atras.performClick();
    }

}

