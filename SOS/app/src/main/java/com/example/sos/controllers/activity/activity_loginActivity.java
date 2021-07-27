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


public class activity_loginActivity extends AppCompatActivity {

    // Declarando variables
    E_API API;
    ws_autenticacion Auth;
    int TipoUsuario = 1;
    TextViewMavenRegular BTN_LOGIN;
    TextViewMavenRegular BTN_CREDENCIALES;
    TextViewMavenRegular BTN_USUARIO;
    MyTextView Usuario;
    MyTextView Contraseña;
    MyTextView OpcionUsuario;
    MyTextView OpcionMecanico;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializando campos

        BTN_LOGIN = findViewById(R.id.btniniciar_sesion);
        BTN_CREDENCIALES = findViewById(R.id.btn_enviarcredenciales);
        BTN_USUARIO = findViewById(R.id.btn_crearusuario);
        Usuario = findViewById(R.id.Usuario);
        Contraseña = findViewById(R.id.Password);
        OpcionUsuario = findViewById(R.id.ClienteOpcion);
        OpcionMecanico= findViewById(R.id.MecanicoOpcion);

        // Inicializando API
        API = new E_API(this);

        // Metodo que inicializa eventos
        InicializarEventos();
        // Metodo que configura la ventana para propositos esteticos
        InicializandoVentana();

    }

    private void InicializarEventos(){

        // Evento Click del boton login para autenticar al usuario
        BTN_LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                if(!Usuario.getText().toString().equals("") && !Contraseña.getText().toString().equals("")) {
                    HashMap<String, String> Parametros = new HashMap<String, String>();
                    Parametros.put("usuario", Usuario.getText().toString());
                    Parametros.put("password", Contraseña.getText().toString());

                    final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_loginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                    Dialogo.setTitleText("Espere por favor...");
                    Dialogo.show();

                    // Se envia un Post a la API para verificar si el usuario existe
                    API.GetAPI().add(Auth.Post(new JSONObject(Parametros), new E_API.VolleyResponseListener() {
                        @Override
                        public void onError(String Mensaje) {
                            Dialogo.dismissWithAnimation();
                            MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                        }

                        @Override
                        public void onResponse(Object Respuesta) throws JSONException {
                            Dialogo.dismissWithAnimation();
                            JSONObject Objeto = new JSONObject(Respuesta.toString());
                            if (Objeto.getBoolean("Existe")) {
                                if (Objeto.getInt("Tipo_Usuario") == 1 && TipoUsuario == 1) {
                                    Intent Vista = new Intent(activity_loginActivity.this, activity_iniciocliente.class);
                                    Vista.putExtra("Usuario",Usuario.getText().toString());
                                    startActivity(Vista);
                                } else if (Objeto.getInt("Tipo_Usuario") == 2 && TipoUsuario == 2) {
                                    Intent Vista = new Intent(activity_loginActivity.this, activity_iniciomecanico.class);
                                    Vista.putExtra("USUARIO",Usuario.getText().toString());
                                    Vista.putExtra("ID_USUARIO",Objeto.getString("ID_USUARIO"));
                                    startActivity(Vista);
                                } else {
                                    MostrarError("Error de autenticación", "Tipo de usuario incorrecto.");
                                }
                            } else {
                                MostrarError("Error de autenticación", "No existe ningun usuario con las credenciales dadas");
                            }
                        }
                    }));
                }
                else{
                    MostrarError("Error de validación", "Faltan datos.");
                }
            }

        });

        // Evento Click del boton login para mostrar el activity de credenciales
        BTN_CREDENCIALES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_loginActivity.this, activity_recuperacion.class);
                Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Vista);
            }
        });

        // Evento Click del boton usuario que muestra el activity para crear usuarios
        BTN_USUARIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent Vista = new Intent(activity_loginActivity.this, activity_usuariogeneral.class);
                Vista.putExtra("EsCrearUsuario","1");
                Vista.putExtra("Tipo_Usuario","1");
                startActivity(Vista);
            }
        });

        // Evento click del boton para seleccionar el rol de usuario
        OpcionUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                OpcionUsuario.setTextColor(getColor(R.color.ColorSeleccion));
                OpcionMecanico.setTextColor(getColor(R.color.ColorCajaTexto));
                TipoUsuario = 1;
            }
        });

        // Evento click del boton para seleccionar el rol de mecanico
        OpcionMecanico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                OpcionMecanico.setTextColor(getColor(R.color.ColorSeleccion));
                OpcionUsuario.setTextColor(getColor(R.color.ColorCajaTexto));
                TipoUsuario = 2;
            }
        });

    }

    // Metodos de ayuda del activity

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

    // No hacer nada
    @Override
    public void onBackPressed() {
    }
}

