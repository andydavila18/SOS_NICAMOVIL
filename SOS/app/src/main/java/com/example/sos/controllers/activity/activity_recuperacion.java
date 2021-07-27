package com.example.sos.controllers.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.R;
import com.example.sos.models.entity.E_API;
import com.example.sos.models.ws.ws_autenticacion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fonts.MyTextView;
import fonts.TextViewMavenRegular;

public class activity_recuperacion extends AppCompatActivity {

    // Declarando variables
    TextViewMavenRegular BTN_CREDENCIALES;
    TextViewMavenRegular BTN_REGRESARLOGIN;
    MyTextView Usuario;
    MyTextView Correo;
    E_API API;
    ws_autenticacion Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);

        // Inicializando campos
        BTN_CREDENCIALES = (TextViewMavenRegular)findViewById(R.id.btn_enviarcredenciales);
        BTN_REGRESARLOGIN = (TextViewMavenRegular) findViewById(R.id.RegresarLogin);
        Usuario = (MyTextView)findViewById(R.id.Usuario);
        Correo = (MyTextView)findViewById(R.id.Email);

        // Inicializando API
        API = new E_API(this);

        // Metodo que inicializa eventos
        InicializarEventos();
        // Metodo que configura la ventana para propositos esteticos
        InicializandoVentana();

    }


    private void InicializarEventos(){

        // Metodo que se dispara con el boton de crendenciales
        BTN_CREDENCIALES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                if(!Usuario.getText().toString().equals("") && !Correo.getText().toString().equals("")) {
                    HashMap<String, String> Parametros = new HashMap<String, String>();
                    Parametros.put("Usuario", Usuario.getText().toString());
                    Parametros.put("Email", Correo.getText().toString());

                    final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_recuperacion.this, SweetAlertDialog.PROGRESS_TYPE);
                    Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                    Dialogo.setTitleText("Espere por favor...");
                    Dialogo.show();

                    // Se envia un Post a la API para verificar si el usuario existe
                    API.GetAPI().add(Auth.Put(new JSONObject(Parametros), new E_API.VolleyResponseListener() {
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
                                MostrarExito("Exito","Se enviaron las credenciales al correo dado.");
                            } else {
                                MostrarError("Error de busqueda", "No existe ningun usuario con las credenciales dadas");
                            }
                        }
                    }));
                }
                else{
                    MostrarError("Error de validación", "Faltan datos.");
                }
            }
        });

        // Metodo que se dispara con el boton de regresar al login

        BTN_REGRESARLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Vista = new Intent(activity_recuperacion.this, activity_loginActivity.class);
                startActivity(Vista);
            }
        });
    }


    // Metodos de ayuda

    private  void InicializandoVentana(){
        // Se quita la barra de estatus
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private  void MostrarError(String Cabezera,String Mensaje){
        SweetAlertDialog Dialogo = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        Dialogo.setTitleText(Cabezera);
        Dialogo.setContentText(Mensaje);
        Dialogo.show();
    }

    private  void MostrarExito(String Cabezera,String Mensaje){
        SweetAlertDialog Dialogo = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        Dialogo.setTitleText(Cabezera);
        Dialogo.setContentText(Mensaje);
        Dialogo.show();
    }

    // Regresar al login
    @Override
    public void onBackPressed() {
        Intent Vista = new Intent(activity_recuperacion.this, activity_loginActivity.class);
        startActivity(Vista);
    }
}
