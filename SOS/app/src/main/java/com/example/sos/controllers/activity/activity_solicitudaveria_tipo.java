package com.example.sos.controllers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.R;

import fonts.TextViewMavenRegular;

public class activity_solicitudaveria_tipo extends AppCompatActivity {

    // Declaracion de variables
    TextViewMavenRegular BTN_SIGUIENTE;
    ImageButton Atras;
    RadioButton Arranque;
    RadioButton FallaMecanica;
    RadioButton Gasolina;
    RadioButton CambioLlanta;
    RadioButton Grua;
    RadioButton Otros;
    String Usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudaveria_tipo);

        BTN_SIGUIENTE = findViewById(R.id.btnguardar);

        Arranque= findViewById(R.id.rb1);
        FallaMecanica= findViewById(R.id.rb2);
        Gasolina= findViewById(R.id.rb3);
        CambioLlanta= findViewById(R.id.rb4);
        Grua= findViewById(R.id.rb5);
        Otros= findViewById(R.id.rb6);
        Atras = findViewById(R.id.Atras);

        Usuario = getIntent().getExtras().getString("Usuario");

        // Metodo que configura la ventana para propositos esteticos
        InicializandoVentana();
        InicializarEventos();

    }

    private void InicializarEventos(){

        // Evento click del boton siguiente
        BTN_SIGUIENTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Vista = new Intent(activity_solicitudaveria_tipo.this, activity_solicitudaveria_ubicacion.class);
                Vista.putExtra("TIPO_AVERIA",GetTipoAveria());
                Vista.putExtra("USUARIO",Usuario);
                startActivity(Vista);
            }
        });

        // Evento Radiobutton Arranque
        Arranque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FallaMecanica.setChecked(false);
                Gasolina.setChecked(false);
                CambioLlanta.setChecked(false);
                Grua.setChecked(false);
                Otros.setChecked(false);
            }
        });

        // Evento Radiobutton FallaMecanica
        FallaMecanica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arranque.setChecked(false);
                Gasolina.setChecked(false);
                CambioLlanta.setChecked(false);
                Grua.setChecked(false);
                Otros.setChecked(false);
            }
        });

        // Evento Radiobutton Gasolina
        Gasolina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arranque.setChecked(false);
                FallaMecanica.setChecked(false);
                CambioLlanta.setChecked(false);
                Grua.setChecked(false);
                Otros.setChecked(false);
            }
        });

        // Evento Radiobutton CambioLlanta
        CambioLlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arranque.setChecked(false);
                Gasolina.setChecked(false);
                FallaMecanica.setChecked(false);
                Grua.setChecked(false);
                Otros.setChecked(false);
            }
        });

        // Evento Radiobutton Grua
        Grua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arranque.setChecked(false);
                Gasolina.setChecked(false);
                FallaMecanica.setChecked(false);
                CambioLlanta.setChecked(false);
                Otros.setChecked(false);
            }
        });

        // Evento Radiobutton Otros
        Otros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arranque.setChecked(false);
                Gasolina.setChecked(false);
                FallaMecanica.setChecked(false);
                CambioLlanta.setChecked(false);
                Grua.setChecked(false);
            }
        });

        // Evento atras
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_solicitudaveria_tipo.this, activity_iniciocliente.class);
                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Vista);
            }
        });
    }


    private  void InicializandoVentana(){
        // Se quita la barra de estatus
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // Regresar al activity anterior
    @Override
    public void onBackPressed()
    {
        Atras.performClick();
    }

    public String GetTipoAveria() {
        if(Arranque.isChecked()) {
            return  "4";
        }
        else if(FallaMecanica.isChecked()){
            return "6";
        }
        else if(Gasolina.isChecked())
        {
            return "3";
        }
        else if(CambioLlanta.isChecked()){
            return "1";
        }
        else if(Grua.isChecked()) {
            return  "5";
        }
        else {
            return "2";
        }
    }

}
