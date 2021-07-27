package com.example.sos.models.entity;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public  class E_API {

    // Direccion de la API
    public static String URL =  "https://soswebserver.herokuapp.com/api/v1";
    private RequestQueue ColaDePeticiones;

    // Constructor de la API
    public E_API(Context APP){
         this.ColaDePeticiones = Volley.newRequestQueue(APP);
    }


    public String GetUrlAPI(){ return URL; }
    public RequestQueue GetAPI() { return ColaDePeticiones; }

    // Interfaz de la api
    public interface VolleyResponseListener {
        void onError(String Mensaje);
        void onResponse(Object Respuesta) throws JSONException;
    }


}
