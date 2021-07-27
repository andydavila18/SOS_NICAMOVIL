package com.example.sos.controllers.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.R;
import com.example.sos.models.entity.E_API;
import com.example.sos.models.ws.ws_averia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fonts.MyTextView;
import fonts.TextViewMavenRegular;

public class activity_solicitudaveria_final extends AppCompatActivity {

    // Variables declaradas de los componentes
    ImageButton Atras;
    TextViewMavenRegular BTN_SOLICITUD;
    String TIPO_AVERIA;
    String LONGITUD;
    String LATITUD;
    String USUARIO;
    String CALLE;
    String PROVINCIA;
    String DEPARTAMENTO;
    MyTextView DatosAdicionales;
    E_API API;
    ws_averia AVAPI;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudaveria_final);

        Atras = findViewById(R.id.Atras);
        BTN_SOLICITUD = findViewById(R.id.enviarsolicitud);
        DatosAdicionales = findViewById(R.id.Final);

        TIPO_AVERIA = getIntent().getExtras().getString("TIPO_AVERIA");
        LONGITUD = getIntent().getExtras().getString("LONGITUD");
        LATITUD = getIntent().getExtras().getString("LATITUD");
        USUARIO = getIntent().getExtras().getString("USUARIO");
        CALLE =  getIntent().getExtras().getString("CALLE");
        PROVINCIA= getIntent().getExtras().getString("PROVINCIA");
        DEPARTAMENTO = getIntent().getExtras().getString("DEPARTAMENTO");
        API = new E_API(activity_solicitudaveria_final.this);


        // Inicializando actividad
        InicializandoVentana();
        InicializarEventos();
    }

    private  void InicializarEventos(){

        // Boton atras para retroceder
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Vista = new Intent(activity_solicitudaveria_final.this, activity_solicitudaveria_resumen.class);
                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Vista);
            }
        });

        // Boton atras para meter solicitud
        BTN_SOLICITUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(activity_solicitudaveria_final.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("¿Estas seguro?")
                        .setContentText("¿Ingresar solicitud?")
                        .setConfirmText("Si")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                HashMap<String, String> Parametros = new HashMap<String, String>();
                                Parametros.put("ID_USUARIO",USUARIO);
                                Parametros.put("LATITUD_UBICACION",LATITUD);
                                Parametros.put("LONGITUD_UBICACION",LONGITUD);
                                Parametros.put("CALLE",CALLE);
                                Parametros.put("PROVINCIA",PROVINCIA);
                                Parametros.put("DEPARTAMENTO",DEPARTAMENTO);
                                Parametros.put("DATOS_ADICIONALES",DatosAdicionales.getText().toString());
                                Parametros.put("TIPO_AVERIA",TIPO_AVERIA);
                                Parametros.put("CALIFICACION","");


                                final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_solicitudaveria_final.this, SweetAlertDialog.PROGRESS_TYPE);
                                Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                                Dialogo.setTitleText("Espere por favor...");
                                Dialogo.show();

                                // Se envia un Post a la API para crear el usuario
                                API.GetAPI().add(AVAPI.Post(new JSONObject(Parametros), new E_API.VolleyResponseListener() {
                                    @Override
                                    public void onError(String Mensaje) {
                                        Dialogo.dismissWithAnimation();
                                        MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                                    }
                                    @Override
                                    public void onResponse(Object Respuesta) throws JSONException {
                                        Dialogo.dismissWithAnimation();
                                        JSONObject Objeto = new JSONObject(Respuesta.toString());
                                        if (Objeto.getInt("Codigo") == 5) {
                                           MostrarExito();
                                        } else {
                                           MostrarError("Error","Ocurrio un error intentarlo mas tarde.");
                                        }
                                    }
                                }));

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

    // Metodos de ayuda del activity
    private  void MostrarError(String Cabezera,String Mensaje){
        new SweetAlertDialog(activity_solicitudaveria_final.this,SweetAlertDialog.ERROR_TYPE)
                .setTitleText(Cabezera)
                .setContentText(Mensaje)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent Vista = new Intent(activity_solicitudaveria_final.this, activity_iniciocliente.class);
                        Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(Vista);
                        finish();
                    }
                })
                .show();
    }

    private  void MostrarExito(){
        new SweetAlertDialog(activity_solicitudaveria_final.this,SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Exito")
                .setContentText("Se mandara un tecnico a dicha ubicación")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent Vista = new Intent(activity_solicitudaveria_final.this, activity_iniciocliente.class);
                        Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(Vista);
                        finish();
                    }
                })
                .show();
    }

    private  void InicializandoVentana(){
        // Se quita la barra de estatus
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    // Regresar al activity anterior
    @Override
    public void onBackPressed() {
        Atras.performClick();
    }
}
