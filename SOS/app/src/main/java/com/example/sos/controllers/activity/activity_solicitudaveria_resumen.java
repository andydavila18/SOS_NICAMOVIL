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
import com.example.sos.models.ws.ws_carro;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fonts.MyTextView;
import fonts.TextViewMavenRegular;

public class activity_solicitudaveria_resumen extends AppCompatActivity {

    // Declaracion de variables
    TextViewMavenRegular BTN_SIGUIENTE;
    ImageButton Atras;
    String TIPO_AVERIA;
    String LONGITUD;
    String LATITUD;
    String USUARIO;
    MyTextView Tipo;
    MyTextView Marca;
    MyTextView Año;
    MyTextView Modelo;
    MyTextView Placa;
    MyTextView Latitud;
    MyTextView Longitud;
    MyTextView Calle;
    MyTextView Provincia;
    MyTextView Departamento;
    E_API API;
    ws_carro CRAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudaveria_resumen);

        BTN_SIGUIENTE = findViewById(R.id.Siguiente);
        Tipo = findViewById(R.id.Tipo);
        Atras = findViewById(R.id.Atras);
        Marca = findViewById(R.id.Marca);
        Modelo = findViewById(R.id.Modelo);
        Placa = findViewById(R.id.Placa);
        Año = findViewById(R.id.Año);
        Latitud = findViewById(R.id.Latitud);
        Longitud = findViewById(R.id.Longitud);
        Calle = findViewById(R.id.Calle);
        Provincia = findViewById(R.id.Provincia);
        Departamento = findViewById(R.id.Departamento);

        API = new E_API(activity_solicitudaveria_resumen.this);


        TIPO_AVERIA = getIntent().getExtras().getString("TIPO_AVERIA");
        LONGITUD = getIntent().getExtras().getString("LONGITUD");
        LATITUD = getIntent().getExtras().getString("LATITUD");
        USUARIO = getIntent().getExtras().getString("USUARIO");




        Tipo.setText(GetTipoAveria(TIPO_AVERIA));
        Longitud.setText(LONGITUD);
        Latitud.setText(LATITUD);

        InicializandoVentana();
        InicializarEventos();
        CargarVista();
    }


    private void InicializarEventos() {

        // Continua al siguiente formulario
        BTN_SIGUIENTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_solicitudaveria_resumen.this, activity_solicitudaveria_final.class);
                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                Vista.putExtra("TIPO_AVERIA",TIPO_AVERIA);
                Vista.putExtra("LONGITUD",LONGITUD);
                Vista.putExtra("LATITUD",LATITUD);
                Vista.putExtra("USUARIO",USUARIO);
                Vista.putExtra("CALLE",Calle.getText().toString());
                Vista.putExtra("PROVINCIA",Provincia.getText().toString());
                Vista.putExtra("DEPARTAMENTO",Departamento.getText().toString());
                startActivity(Vista);
            }
        });

        // Retrocede a la actividad anterior
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_solicitudaveria_resumen.this, activity_solicitudaveria_ubicacion.class);
                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Vista);
            }
        });
    }

    private void InicializandoVentana() {
        // Se quita la barra de estatus
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // Regresar al activity anterior
    @Override
    public void onBackPressed() {
        Atras.performClick();
    }

    public String GetTipoAveria(String TIPO_AVERIA) {
        if (this.TIPO_AVERIA.equals("4")) {
            return "Arranque";
        } else if (TIPO_AVERIA.equals("6")) {
            return "Falla Mecanica";
        } else if (TIPO_AVERIA.equals("3")) {
            return "Gasolina";
        } else if (TIPO_AVERIA.equals("1")) {
            return "Cambio de llanta";
        } else if (TIPO_AVERIA.equals("5")) {
            return "Grua";
        } else {
            return "Otros";
        }
    }

    private void CargarVista() {
        final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_solicitudaveria_resumen.this, SweetAlertDialog.PROGRESS_TYPE);
        Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
        Dialogo.setTitleText("Espere por favor...");
        Dialogo.show();

        // Se envia un Get a la API para obtener los datos de el usuario
        API.GetAPI().add(CRAPI.Get(USUARIO, new E_API.VolleyResponseListener() {
            @Override
            public void onError(String Mensaje) {
                Dialogo.dismissWithAnimation();
                MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
            }

            @Override
            public void onResponse(Object Respuesta) throws JSONException {
                Dialogo.dismissWithAnimation();
                JSONObject Objeto = new JSONObject(Respuesta.toString());
                Marca.setText(Objeto.getString("MARCA_CARRO"));
                Año.setText(Objeto.getString("AÑO_CARRO"));
                Modelo.setText(Objeto.getString("MODELO_CARRO"));
                Placa.setText(Objeto.getString("PLACA_CARRO"));
                Dialogo.dismissWithAnimation();
            }
        }));
    }

    // Metodos de ayuda del activity
    private void MostrarError(String Cabezera, String Mensaje) {
        SweetAlertDialog Dialogo = new SweetAlertDialog(activity_solicitudaveria_resumen.this, SweetAlertDialog.ERROR_TYPE);
        Dialogo.setTitleText(Cabezera);
        Dialogo.setContentText(Mensaje);
        Dialogo.show();
    }

    private void MostrarExito(String Cabezera, String Mensaje) {
        SweetAlertDialog Dialogo = new SweetAlertDialog(activity_solicitudaveria_resumen.this, SweetAlertDialog.SUCCESS_TYPE);
        Dialogo.setTitleText(Cabezera);
        Dialogo.setContentText(Mensaje);
        Dialogo.show();
    }



}
