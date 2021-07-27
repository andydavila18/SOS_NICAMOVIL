package com.example.sos.controllers.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.sos.R;
import com.example.sos.controllers.adapters.adapter_fragmentmanager_usuariocliente;
import com.example.sos.controllers.fragments.fragmento_usuariocliente_carro;
import com.example.sos.controllers.fragments.fragmento_usuariocliente_datospersonales;

import fonts.MyTextView;

public class activity_usuariocliente extends AppCompatActivity implements fragmento_usuariocliente_carro.OnFragmentInteractionListener, fragmento_usuariocliente_datospersonales.OnFragmentInteractionListener {

    // Declaracion de variables de componentes
    ViewPager VP;
    FragmentPagerAdapter AdaptadorViewPager;
    MyTextView BTN_DATOSPERSONALES;
    MyTextView BTN_DATOSCARRO;
    ImageButton Atras;
    public String EsCrearUsuario = "0";
    public String Usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuariocliente);


        // Recogiendo referencias de los controles
        VP = findViewById(R.id.vpcliente);
        BTN_DATOSPERSONALES = findViewById(R.id.DatosPersonales);
        BTN_DATOSCARRO= findViewById(R.id.DatosCarro);
        Atras = findViewById(R.id.Atras);

        EsCrearUsuario = (getIntent().getStringExtra("EsCrearUsuario"));
        Usuario = (getIntent().getStringExtra("Usuario"));

        // Metodo que inicializa eventos
        InicializarEventos();
        // Metodo que configura la ventana para propositos esteticos
        InicializandoVentana();
    }

    private void InicializarEventos(){
        // Configurando fragmentos
        AdaptadorViewPager= new adapter_fragmentmanager_usuariocliente(getSupportFragmentManager());
        VP.setAdapter(AdaptadorViewPager);

        // Boton para volver atras
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                // Si se esta creando el usuario
                if(EsCrearUsuario.equals("1")){
                    Intent Vista = new Intent(activity_usuariocliente.this, activity_loginActivity.class);
                    Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(Vista);
                }
                // Si el usuario es un mecanico
                else{
                    Intent Vista = new Intent(activity_usuariocliente.this, activity_iniciocliente.class);
                    Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(Vista);
                }
            }
        });


        // Evento del ViewPager
        VP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int Estado) {}
            public void onPageScrolled(int Posicion, float Offset, int OffsetPixels) {}

            // Determinamos cual pagina esta seleccionada
            public void onPageSelected(int Posicion) {
                switch (Posicion){
                    case 0:
                        BTN_DATOSPERSONALES.setTextColor(getColor(R.color.ColorSeleccion));
                        BTN_DATOSCARRO.setTextColor(getColor(R.color.ColorCajaTexto));
                        break;
                    case 1:
                        BTN_DATOSPERSONALES.setTextColor(getColor(R.color.ColorCajaTexto));
                        BTN_DATOSCARRO.setTextColor(getColor(R.color.ColorSeleccion));
                        break;
                }
            }
        });
    }


    private  void InicializandoVentana(){
        // Se quita la barra de estatus
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }


    // Regresar al activity anterior
    @Override
    public void onBackPressed() {
        Atras.performClick();
    }
}


