package com.example.sos.controllers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.R;

public class activity_iniciocliente extends AppCompatActivity {

    // Declarando variables //
    LinearLayout BTN_ASISTENCIA;
    LinearLayout BTN_PERFIL;
    LinearLayout BTN_HISTORICO;
    LinearLayout BTN_AYUDA;
    ImageButton CerrarSesion;
    String Usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciocliente);

        BTN_ASISTENCIA =  findViewById(R.id.btn_asistencia);
        BTN_PERFIL =  findViewById(R.id.btn_perfil);
        BTN_HISTORICO = findViewById(R.id.Historico);
        BTN_AYUDA = findViewById(R.id.Ayuda);
        CerrarSesion = findViewById(R.id.CerrarSesion);

        // Obtenemos el nombre del usuario
        Usuario = getIntent().getExtras().getString("Usuario");

        // Inicializando elementos
        InicializandoVentana();
        InicializarEventos();
    }


    private  void  InicializarEventos(){

        // Evento Click del boton asistencia para empezar el proceso de una
        BTN_ASISTENCIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_iniciocliente.this, activity_solicitudaveria_tipo.class);
                Vista.putExtra("Usuario",Usuario);
                startActivity(Vista);
            }
        });

        // Evento Click del boton perfil para ver los datos del usuario
        BTN_PERFIL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_iniciocliente.this,activity_usuariocliente.class);
                Vista.putExtra("EsCrearUsuario","0");
                Vista.putExtra("Usuario",Usuario);
                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Vista);
            }
        });

        // Evento Click del boton historico

        BTN_HISTORICO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_iniciocliente.this,activity_listado_averias.class);
                Vista.putExtra("EsCliente","1");
                Vista.putExtra("EsHistoricoMecanico","0");
                Vista.putExtra("USUARIO",Usuario);
                startActivity(Vista);
            }
        });

        // Evento click del boton ayuda
        BTN_AYUDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Vista = new Intent(activity_iniciocliente.this,activity_contacto.class);
                Vista.putExtra("Usuario",Usuario);
                Vista.putExtra("EsCliente","1");
                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Vista);
            }
        });


        //Evento click del boton para cerrar sesion
        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_iniciocliente.this,activity_loginActivity.class);
                startActivity(Vista);
            }
        });

    }
    private  void InicializandoVentana(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // Regresar al activity anterior
    @Override
    public void onBackPressed() {
        CerrarSesion.performClick();
    }

}
