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
import com.example.sos.models.ws.ws_contacto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fonts.MyTextView;
import fonts.TextViewMavenRegular;

public class activity_contacto extends AppCompatActivity {

    // Declarando variables
    String EsCliente= "1";
    ImageButton Atras;
    MyTextView Mensaje;
    String Usuario;
    TextViewMavenRegular BTN_SOLICITUD;
    E_API API;
    ws_contacto CRAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        Atras = findViewById(R.id.Atras);
        EsCliente= (getIntent().getStringExtra("EsCliente"));
        Usuario= (getIntent().getStringExtra("Usuario"));
        Mensaje = findViewById(R.id.Mensaje);
        BTN_SOLICITUD = findViewById(R.id.btnsolicitud);
        API = new E_API(this);

        // Configurando ventanas
        InicializandoVentana();
        InicializarEventos();

    }


    private  void InicializarEventos(){

        // Regresa al activity anterior

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                if(EsCliente.equals("1")){
                    Intent Vista = new Intent(activity_contacto.this, activity_iniciocliente.class);
                    Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(Vista);
                }
                else{
                    Intent Vista = new Intent(activity_contacto.this, activity_iniciomecanico.class);
                    Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(Vista);
                }
            }
        });

        // Inserta la solicitud en el servidor
        BTN_SOLICITUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                if(!Mensaje.getText().toString().equals("")) {
                    HashMap<String, String> Parametros = new HashMap<String, String>();
                    Parametros.put("ID_USUARIO", Usuario);
                    Parametros.put("MENSAJE", Mensaje.getText().toString());

                    final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_contacto.this, SweetAlertDialog.PROGRESS_TYPE);
                    Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                    Dialogo.setTitleText("Espere por favor...");
                    Dialogo.show();

                    // Se envia un Post a la API para insertar el mensaje
                    API.GetAPI().add(CRAPI.Post(new JSONObject(Parametros), new E_API.VolleyResponseListener() {
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
                                MostrarExito("Exito","Se envio el mensaje exitosamente");
                            } else {
                                MostrarError("Error de envio", "Ocurrio un problema al mandar el mensaje, ¿Estas conectado a internet?");
                            }
                        }
                    }));
                }
                else{
                    MostrarError("Error de validación", "Faltan datos.");
                }
            }
        });
    }

    // Metodos de ayuda del activity
    private  void MostrarError(String Cabezera,String Mensaje){
        SweetAlertDialog Dialogo = new SweetAlertDialog(activity_contacto.this, SweetAlertDialog.ERROR_TYPE);
        Dialogo.setTitleText(Cabezera);
        Dialogo.setContentText(Mensaje);
        Dialogo.show();
    }

    private  void MostrarExito(String Cabezera,String Mensaje){
        SweetAlertDialog Dialogo = new SweetAlertDialog(activity_contacto.this, SweetAlertDialog.SUCCESS_TYPE);
        Dialogo.setTitleText(Cabezera);
        Dialogo.setContentText(Mensaje);
        Dialogo.show();
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
