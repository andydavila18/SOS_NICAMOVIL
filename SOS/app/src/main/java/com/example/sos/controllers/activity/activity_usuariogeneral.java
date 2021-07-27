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
import com.example.sos.models.ws.ws_usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fonts.MyTextView;
import fonts.TextViewMavenRegular;

public class activity_usuariogeneral extends AppCompatActivity {

    // Declaracion de variables de componentes
    E_API API;
    ws_usuario USAPI;
    TextViewMavenRegular BTN_GUARDAR;
    ImageButton Atras;
    String EsCrearUsuario;
    MyTextView Usuario;
    MyTextView Contraseña;
    MyTextView Nombre;
    MyTextView Apellido;
    MyTextView Direccion;
    MyTextView Telefono;
    MyTextView Ciudad;
    MyTextView CorreoElectronico;
    MyTextView Cedula;
    String UsuarioNombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuariogeneral);

        // Recogiendo referencias de los controles
        BTN_GUARDAR = findViewById(R.id.btnguardar);
        Atras = findViewById(R.id.Atras);

        EsCrearUsuario = getIntent().getExtras().getString("EsCrearUsuario");
        UsuarioNombre = getIntent().getExtras().getString("Usuario");

        Usuario = findViewById(R.id.Usuario);
        Contraseña = findViewById(R.id.Password);
        Nombre= findViewById(R.id.Nombre);
        Apellido= findViewById(R.id.Apellido);
        Direccion= findViewById(R.id.Direccion);
        Telefono = findViewById(R.id.Telefono);
        Ciudad = findViewById(R.id.Ciudad);
        CorreoElectronico = findViewById(R.id.Email);
        Cedula = findViewById(R.id.Cedula);
        API = new E_API(this);



        // Metodo que inicializa eventos
        InicializarEventos();
        // Metodo que configura la ventana para propositos esteticos
        InicializandoVentana();

        if(EsCrearUsuario .equals("0")) {
            CargarVista();
        }

    }

    private void InicializarEventos(){
        // Boton para guardar usuario
        BTN_GUARDAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                // Si se va crear Usuario;
                if(EsCrearUsuario.equals("1")) {
                    if(!Usuario.getText().toString().equals("") && !Contraseña.getText().toString().equals("") && !Nombre.getText().toString().equals("") && !Apellido.getText().toString().equals("") && !Direccion.getText().toString().equals("") && !Telefono.getText().toString().equals("") && !Ciudad.getText().toString().equals("") && !CorreoElectronico.getText().toString().equals("") && !Cedula.getText().toString().equals("") ) {
                        HashMap<String, String> Parametros = new HashMap<String, String>();
                        Parametros.put("USUARIO", Usuario.getText().toString());
                        Parametros.put("CONTRASEÑA", Contraseña.getText().toString());
                        Parametros.put("ID_TIPO", "1");
                        Parametros.put("NOMBRE_USUARIO", Nombre.getText().toString());
                        Parametros.put("APELLIDO_USUARIO", Apellido.getText().toString());
                        Parametros.put("TELEFONO_USUARIO", Telefono.getText().toString());
                        Parametros.put("EMAIL_USUARIO", CorreoElectronico.getText().toString());
                        Parametros.put("DIRECCION", Direccion.getText().toString());
                        Parametros.put("CIUDAD", Ciudad.getText().toString());
                        Parametros.put("PAIS", "Nicaragua");
                        Parametros.put("CEDULA", Cedula.getText().toString());

                        final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_usuariogeneral.this, SweetAlertDialog.PROGRESS_TYPE);
                        Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                        Dialogo.setTitleText("Espere por favor...");
                        Dialogo.show();

                        // Se envia un Post a la API para crear el usuario
                        API.GetAPI().add(USAPI.Post(new JSONObject(Parametros), new E_API.VolleyResponseListener() {
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
                                    MostrarExito("Exito","Se añadio el usuario exitosamente");
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
                // Si se va actualizar el usuario mecanico
                else{
                    if(!Usuario.getText().toString().equals("") && !Contraseña.getText().toString().equals("") && !Nombre.getText().toString().equals("") && !Apellido.getText().toString().equals("") && !Direccion.getText().toString().equals("") && !Telefono.getText().toString().equals("") && !Ciudad.getText().toString().equals("") && !CorreoElectronico.getText().toString().equals("") && !Cedula.getText().toString().equals("") ) {
                        HashMap<String, String> Parametros = new HashMap<String, String>();
                        Parametros.put("USUARIO", Usuario.getText().toString());
                        Parametros.put("CONTRASEÑA", Contraseña.getText().toString());
                        Parametros.put("ID_TIPO", "2");
                        Parametros.put("NOMBRE_USUARIO", Nombre.getText().toString());
                        Parametros.put("APELLIDO_USUARIO", Apellido.getText().toString());
                        Parametros.put("TELEFONO_USUARIO", Telefono.getText().toString());
                        Parametros.put("EMAIL_USUARIO", CorreoElectronico.getText().toString());
                        Parametros.put("DIRECCION", Direccion.getText().toString());
                        Parametros.put("CIUDAD", Ciudad.getText().toString());
                        Parametros.put("PAIS", "Nicaragua");
                        Parametros.put("CEDULA", Cedula.getText().toString());

                        final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_usuariogeneral.this, SweetAlertDialog.PROGRESS_TYPE);
                        Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                        Dialogo.setTitleText("Espere por favor...");
                        Dialogo.show();

                        // Se envia un Post a la API para verificar si el usuario existe
                        API.GetAPI().add(USAPI.Put(new JSONObject(Parametros), new E_API.VolleyResponseListener() {
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
                                    MostrarExito("Exito","Se actualizo el usuario exitosamente");
                                } else {
                                    MostrarError("Error de envio", "Ocurrio un error al enviar los datos, ¿Estas conectado a internet?");                                }
                            }
                        }));
                    }
                    else{
                        MostrarError("Error de validación", "Faltan datos.");
                    }
                }
            }
        });

        // Boton para volver atras
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                    if(EsCrearUsuario.equals("1")){
                        Intent Vista = new Intent(activity_usuariogeneral.this, activity_loginActivity.class);
                        startActivity(Vista);
                    }
                    else{
                        Intent Vista = new Intent(activity_usuariogeneral.this, activity_iniciomecanico.class);
                        Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(Vista);
                    }
            }
        });

    }

    // Rellena los datos del formulario
    private  void CargarVista()
    {
        final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_usuariogeneral.this, SweetAlertDialog.PROGRESS_TYPE);
        Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
        Dialogo.setTitleText("Espere por favor...");
        Dialogo.show();

        // Se envia un Get a la API para obtener los datos de el usuario
        API.GetAPI().add(USAPI.Get(UsuarioNombre, new E_API.VolleyResponseListener() {
            @Override
            public void onError(String Mensaje) {
                Dialogo.dismissWithAnimation();
                MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
            }

            @Override
            public void onResponse(Object Respuesta) throws JSONException {
                Dialogo.dismissWithAnimation();
                JSONObject Objeto = new JSONObject(Respuesta.toString());
                Usuario.setText(Objeto.getString("USUARIO"));
                Contraseña.setText(Objeto.getString("CONTRASEÑA"));
                Nombre.setText(Objeto.getString("NOMBRE_USUARIO"));
                Apellido.setText(Objeto.getString("APELLIDO_USUARIO"));
                Direccion.setText(Objeto.getString("DIRECCION"));
                Telefono.setText(Objeto.getString("TELEFONO_USUARIO"));
                Ciudad.setText(Objeto.getString("CIUDAD"));
                CorreoElectronico.setText(Objeto.getString("EMAIL_USUARIO"));
                Cedula.setText(Objeto.getString("CEDULA"));
                Dialogo.dismissWithAnimation();
            }
        }));


    }

    // Metodos de ayuda del activity
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
        Dialogo.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
                Atras.performClick();
            }
        });
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


