package com.example.sos.controllers.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sos.R;
import com.example.sos.controllers.activity.activity_usuariocliente;
import com.example.sos.models.entity.E_API;
import com.example.sos.models.ws.ws_carro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fonts.MyTextView;
import fonts.TextViewMavenRegular;

public class fragmento_usuariocliente_carro extends Fragment {

    // Variables de las vistas
    private OnFragmentInteractionListener mListener;
    TextViewMavenRegular BTN_GUARDAR;
    private String UsuarioNombre;
    MyTextView Usuario;
    MyTextView Marca;
    MyTextView Año;
    MyTextView Modelo;
    MyTextView Placa;
    E_API API;
    ws_carro CRAPI;



    public fragmento_usuariocliente_carro() { }

    public static fragmento_usuariocliente_carro newInstance(String param1, String param2) {
        fragmento_usuariocliente_carro fragment = new fragmento_usuariocliente_carro();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Obtenemos la vista
        View VistaFragmento = inflater.inflate(R.layout.fragment_usuariocliente_carro, container, false);

        // Recopilando meta datos de la actividad para ver si se crea el usuario o se va leer.
        Activity Actividad = (activity_usuariocliente) getActivity();
        UsuarioNombre = ((activity_usuariocliente) Actividad).Usuario;

        BTN_GUARDAR = VistaFragmento .findViewById(R.id.btnguardar);
        Usuario = VistaFragmento.findViewById(R.id.Usuario);
        Marca = VistaFragmento.findViewById(R.id.Tipo);
        Año= VistaFragmento.findViewById(R.id.Año);
        Modelo = VistaFragmento.findViewById(R.id.Modelo);
        Placa = VistaFragmento.findViewById(R.id.Placa);
        Usuario.setText(UsuarioNombre);
        API = new E_API(getActivity());


        InicializarEventos();
        CargarVista();

        return VistaFragmento;
    }


    private void InicializarEventos() {
        // Boton para guardar usuario
        BTN_GUARDAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                // Se validan los datos del usuario
                if (!Usuario.getText().toString().equals("") && !Marca.getText().toString().equals("") && !Año.getText().toString().equals("") && !Modelo.getText().toString().equals("") && !Placa.getText().toString().equals("")) {
                    final SweetAlertDialog Dialogo = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                    Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
                    Dialogo.setTitleText("Espere por favor...");
                    Dialogo.show();

                    // Se elimina e inserta el nuevo carro
                    API.GetAPI().add(CRAPI.Delete(UsuarioNombre, new E_API.VolleyResponseListener() {
                        @Override
                        public void onError(String Mensaje) {
                            Dialogo.dismissWithAnimation();
                            MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                        }

                        @Override
                        public void onResponse(Object Respuesta) {

                            HashMap<String, String> Parametros = new HashMap<String, String>();
                            Parametros.put("ID_USUARIO", Usuario.getText().toString());
                            Parametros.put("MARCA_CARRO", Marca.getText().toString());
                            Parametros.put("AÑO_CARRO", Año.getText().toString());
                            Parametros.put("COLOR_CARRO", "");
                            Parametros.put("MODELO_CARRO", Modelo.getText().toString());
                            Parametros.put("PLACA_CARRO", Placa.getText().toString());
                            Parametros.put("IMAGEN_CARRO", "");

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
                                        MostrarExito("Exito", "Se inserto el carro exitosamente");
                                    } else {
                                        MostrarError("Error de envio", "Ocurrio un error al enviar los datos, ¿Estas conectado a internet?");                                    }
                                }
                            }));
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
        API.GetAPI().add(CRAPI.Get(UsuarioNombre, new E_API.VolleyResponseListener() {
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        void onAttachFragment(Fragment fragment);
    }
}
