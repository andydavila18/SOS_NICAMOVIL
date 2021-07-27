package com.example.sos.controllers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.R;

public class activity_iniciomecanico extends AppCompatActivity {

    // Declarando variables //
    LinearLayout BTN_AVERIASPENDIENTES;
    LinearLayout BTN_PERFIL;
    LinearLayout BTN_HISTORICO;
    LinearLayout BTN_AYUDA;
    ImageButton CerrarSesion;
    String Usuario;
    String ID_USUARIO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciomecanico);

        BTN_AVERIASPENDIENTES =  findViewById(R.id.BusquedaAveria);
        BTN_PERFIL =  findViewById(R.id.MiPerfil);
        BTN_HISTORICO = findViewById(R.id.AveriasAtentidas);
        BTN_AYUDA = findViewById(R.id.Ayuda);
        CerrarSesion = findViewById(R.id.CerrarSesion);
        Usuario = getIntent().getExtras().getString("USUARIO");
        ID_USUARIO = getIntent().getExtras().getString("ID_USUARIO");


        // Inicializando elementos
        InicializandoVentana();
        InicializarEventos();
    }

    private  void  InicializarEventos(){

        // Evento Click del boton asistencia para empezar el proceso de una
        BTN_AVERIASPENDIENTES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_iniciomecanico.this, activity_listado_averias.class);
                Vista.putExtra("EsCliente","0");
                Vista.putExtra("USUARIO",Usuario);
                Vista.putExtra("ID_USUARIO",ID_USUARIO);
                Vista.putExtra("EsHistoricoMecanico","0");
                startActivity(Vista);
            }
        });

        // Evento Click del boton perfil para ver los datos del usuario
        BTN_PERFIL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_iniciomecanico.this, activity_usuariogeneral.class);
                Vista.putExtra("EsCrearUsuario","0");
                Vista.putExtra("Usuario",Usuario);
                Vista.putExtra("Tipo_Usuario","2");

                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Vista);
            }
        });

        // Evento Click del boton historico

        BTN_HISTORICO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_iniciomecanico.this,activity_listado_averias.class);
                Vista.putExtra("EsCliente","0");
                Vista.putExtra("EsHistoricoMecanico","1");
                Vista.putExtra("USUARIO",Usuario);
                Vista.putExtra("ID_USUARIO",ID_USUARIO);
                startActivity(Vista);
            }
        });

        // Evento click del boton ayuda
        BTN_AYUDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Vista = new Intent(activity_iniciomecanico.this,activity_contacto.class);
                Vista.putExtra("EsCliente","0");
                startActivity(Vista);
            }
        });


        //Evento click del boton para cerrar sesion
        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_iniciomecanico.this,activity_loginActivity.class);
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
