package com.example.sos.models.ws;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sos.models.entity.E_API;

import org.json.JSONException;
import org.json.JSONObject;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ws_autenticacion {

    final static String Controlador = "/Autenticacion";

    // Post : Permite autenticarse
    public static JsonObjectRequest Post(JSONObject Parametros, final E_API.VolleyResponseListener Listener) {
        JsonObjectRequest Peticion= new JsonObjectRequest
                (Request.Method.POST,E_API.URL+Controlador, Parametros, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject Respuesta) {
                        try {
                            Listener.onResponse(Respuesta);
                        } catch (JSONException e) {
                            Log.e(TAG, "Ocurrio un error al realizar la peticion : " + Respuesta, e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError Error) {
                        Listener.onError(Error.toString());
                    }
                }) {
        };
        return  Peticion;
    }
    // Put : Permite recuperar las credenciales
    public static JsonObjectRequest Put(JSONObject Parametros,final E_API.VolleyResponseListener Listener) {
        JsonObjectRequest Peticion= new JsonObjectRequest
                (Request.Method.PUT,E_API.URL+Controlador, Parametros, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject Respuesta) {
                        try {
                            Listener.onResponse(Respuesta);
                        } catch (JSONException e) {
                            Log.e(TAG, "Ocurrio un error al realizar la peticion : " + Respuesta, e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError Error) {
                        Listener.onError(Error.toString());
                    }
                }) {
        };
        return  Peticion;
    }
}
