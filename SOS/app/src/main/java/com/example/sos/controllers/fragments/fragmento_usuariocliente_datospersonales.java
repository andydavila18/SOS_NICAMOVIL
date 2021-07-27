package com.example.sos.controllers.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.sos.R;
import com.example.sos.controllers.activity.activity_usuariocliente;
import com.example.sos.models.entity.E_API;
import com.example.sos.models.ws.ws_usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fonts.MyTextView;
import fonts.TextViewMavenRegular;


public class fragmento_usuariocliente_datospersonales extends Fragment {

    // Variables a usar  para instanciar la vista
    private OnFragmentInteractionListener mListener;
    public fragmento_usuariocliente_datospersonales() { }

    // Declaracion de variables de componentes
    E_API API;
    ws_usuario USAPI;
    TextViewMavenRegular BTN_GUARDAR;
    ImageButton Atras;
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


    public static fragmento_usuariocliente_datospersonales newInstance(String param1, String param2) {
        fragmento_usuariocliente_datospersonales fragment = new fragmento_usuariocliente_datospersonales();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View VistaFragmento = inflater.inflate(R.layout.fragment_usuariocliente_datospersonales, container, false);

        // Recogiendo referencias de los controles
        BTN_GUARDAR = VistaFragmento .findViewById(R.id.btnguardar);
        Usuario = VistaFragmento.findViewById(R.id.Usuario);
        Contraseña = VistaFragmento .findViewById(R.id.Password);
        Nombre= VistaFragmento .findViewById(R.id.Nombre);
        Apellido= VistaFragmento .findViewById(R.id.Apellido);
        Direccion= VistaFragmento .findViewById(R.id.Direccion);
        Telefono = VistaFragmento .findViewById(R.id.Telefono);
        Ciudad = VistaFragmento .findViewById(R.id.Ciudad);
        CorreoElectronico = VistaFragmento .findViewById(R.id.Email);
        Cedula = VistaFragmento .findViewById(R.id.Cedula);
        API = new E_API(getActivity());

        Activity Actividad = (activity_usuariocliente) getActivity();
        UsuarioNombre= ((activity_usuariocliente) Actividad).Usuario;


        // Metodo que inicializa eventos
        InicializarEventos();

        // Carga los datos del usuario
        CargarVista();

        return  VistaFragmento;
    }


    private void InicializarEventos() {

        // Boton para guardar usuario
        BTN_GUARDAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                // Se validan los datos del usuario
                if (!Usuario.getText().toString().equals("") && !Contraseña.getText().toString().equals("") && !Nombre.getText().toString().equals("") && !Apellido.getText().toString().equals("") && !Direccion.getText().toString().equals("") && !Telefono.getText().toString().equals("") && !Ciudad.getText().toString().equals("") && !CorreoElectronico.getText().toString().equals("") && !Cedula.getText().toString().equals("")) {
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

                    final SweetAlertDialog Dialogo = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                    Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                    Dialogo.setTitleText("Espere por favor...");
                    Dialogo.show();

                    // Se envia un Put a la API para actualizar el usuario
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
                                MostrarExito("Exito", "Se actualizo el usuario exitosamente");
                            } else {
                                MostrarError("Error de envio", "Ocurrio un error al enviar los datos, ¿Estas conectado a internet?");
                            }
                        }
                    }));
                } else {
                    MostrarError("Error de validación", "Faltan datos.");
                }
            }
        });
    }

    private  void CargarVista()
    {
        final SweetAlertDialog Dialogo = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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
        SweetAlertDialog Dialogo = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
        Dialogo.setTitleText(Cabezera);
        Dialogo.setContentText(Mensaje);
        Dialogo.show();
    }

    private  void MostrarExito(String Cabezera,String Mensaje){
        SweetAlertDialog Dialogo = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
        Dialogo.setTitleText(Cabezera);
        Dialogo.setContentText(Mensaje);
        Dialogo.show();
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
