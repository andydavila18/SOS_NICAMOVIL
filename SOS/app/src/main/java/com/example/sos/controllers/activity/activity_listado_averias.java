package com.example.sos.controllers.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sos.R;
import com.example.sos.controllers.adapters.adaptar_recyclerview_listaaverias;
import com.example.sos.models.entity.E_API;
import com.example.sos.models.entity.E_Averia;
import com.example.sos.models.ws.ws_averia;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class activity_listado_averias extends AppCompatActivity {

    // Declarando variables
    String EsCliente= "1";
    String VerHistoricoMecanico = "1";
    String USUARIO;
    String ID_USUARIO;
    ImageButton Atras;
    RecyclerView Lista;
    List<E_Averia> Items = new ArrayList<E_Averia>();
    adaptar_recyclerview_listaaverias Adaptador;
    E_API API;
    ws_averia AVEAPI;
    SwipeRefreshLayout Swipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_averias);

        Atras = findViewById(R.id.Atras);
        Lista = findViewById(R.id.RecyclerLista);

        EsCliente = (getIntent().getStringExtra("EsCliente"));
        API = new E_API(activity_listado_averias.this);
        VerHistoricoMecanico = (getIntent().getStringExtra("EsHistoricoMecanico"));
        USUARIO= (getIntent().getStringExtra("USUARIO"));
        ID_USUARIO= (getIntent().getStringExtra("ID_USUARIO"));
        Swipe = findViewById(R.id.Swipe);

        // Configurando ventanas
        InicializandoVentana();
        InicializarEventos();
    }

    private  void InicializarEventos(){

        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                // Se envia un Get a la API para obtener los datos de el usuario

                // Jalar datos del cliente
                if(EsCliente.equals("1")) {
                    API.GetAPI().add(AVEAPI.GetAllByUser(USUARIO, new E_API.VolleyResponseListener() {
                        @Override
                        public void onError(String Mensaje) {
                            MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                        }

                        @Override
                        public void onResponse(Object Respuesta) throws JSONException {
                            Items.clear();
                            JSONArray Objeto = new JSONArray(Respuesta.toString());

                            for (int I = 0; I < Objeto.length(); I++) {
                                Items.add(new E_Averia(Objeto.getJSONObject(I).getInt("ID_AVERIA"), Objeto.getJSONObject(I).getString("NOMBRE_ESPECIALIDAD"), Objeto.getJSONObject(I).getString("FORMATEADA"), Objeto.getJSONObject(I).getString("DESCRIPCION_ESTADO"),"1",""));
                            }
                            Adaptador = new adaptar_recyclerview_listaaverias(activity_listado_averias.this, Items, activity_listado_averias.this);
                            Lista.setAdapter(Adaptador);
                            Lista.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            Lista.setHasFixedSize(true);
                            Swipe.setRefreshing(false);
                        }
                    }));
                }
                // Jalar datos del historico del mecanico
                else if(VerHistoricoMecanico.equals("1"))
                {
                    API.GetAPI().add(AVEAPI.GetAllHistorico(USUARIO, new E_API.VolleyResponseListener() {
                        @Override
                        public void onError(String Mensaje) {
                            MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                        }

                        @Override
                        public void onResponse(Object Respuesta) throws JSONException {
                            Items.clear();
                            JSONArray Objeto = new JSONArray(Respuesta.toString());

                            for (int I = 0; I < Objeto.length(); I++) {
                                Items.add(new E_Averia(Objeto.getJSONObject(I).getInt("ID_AVERIA"), Objeto.getJSONObject(I).getString("NOMBRE_ESPECIALIDAD"), Objeto.getJSONObject(I).getString("FORMATEADA"), Objeto.getJSONObject(I).getString("DESCRIPCION_ESTADO"),"0",ID_USUARIO));
                            }
                            Adaptador = new adaptar_recyclerview_listaaverias(activity_listado_averias.this, Items, activity_listado_averias.this);
                            Lista.setAdapter(Adaptador);
                            Lista.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            Lista.setHasFixedSize(true);
                            Swipe.setRefreshing(false);
                        }
                    }));
                }
                // Jalar datos del historico de averias pendientes
                else
                {
                    API.GetAPI().add(AVEAPI.GetAllPendientes(USUARIO, new E_API.VolleyResponseListener() {
                        @Override
                        public void onError(String Mensaje) {
                            MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                        }

                        @Override
                        public void onResponse(Object Respuesta) throws JSONException {
                            Items.clear();
                            JSONArray Objeto = new JSONArray(Respuesta.toString());

                            for (int I = 0; I < Objeto.length(); I++) {
                                Items.add(new E_Averia(Objeto.getJSONObject(I).getInt("ID_AVERIA"), Objeto.getJSONObject(I).getString("NOMBRE_ESPECIALIDAD"), Objeto.getJSONObject(I).getString("FORMATEADA"), Objeto.getJSONObject(I).getString("DESCRIPCION_ESTADO"),"0",ID_USUARIO));
                            }
                            Adaptador = new adaptar_recyclerview_listaaverias(activity_listado_averias.this, Items, activity_listado_averias.this);
                            Lista.setAdapter(Adaptador);
                            Lista.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            Lista.setHasFixedSize(true);
                            Swipe.setRefreshing(false);
                        }
                    }));
                }
            }
        });

        // Regresa al activity anterior
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                if(EsCliente.equals("1")){
                    Intent Vista = new Intent(activity_listado_averias.this, activity_iniciocliente.class);
                    Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(Vista);
                }
                else{
                    Intent Vista = new Intent(activity_listado_averias.this, activity_iniciomecanico.class);
                    Vista.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(Vista);
                }
            }
        });

        if(EsCliente.equals("1")) {
            final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_listado_averias.this, SweetAlertDialog.PROGRESS_TYPE);
            Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
            Dialogo.setTitleText("Espere por favor...");
            Dialogo.show();

            // Se envia un Get a la API para obtener los datos de el usuario
            API.GetAPI().add(AVEAPI.GetAllByUser(USUARIO, new E_API.VolleyResponseListener() {
                @Override
                public void onError(String Mensaje) {
                    Dialogo.dismissWithAnimation();
                    MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                }

                @Override
                public void onResponse(Object Respuesta) throws JSONException {
                    Dialogo.dismissWithAnimation();
                    JSONArray Objeto = new JSONArray(Respuesta.toString());

                    for (int I = 0  ; I< Objeto.length(); I++) {
                        Items.add(new E_Averia(Objeto.getJSONObject(I).getInt("ID_AVERIA"),Objeto.getJSONObject(I).getString("NOMBRE_ESPECIALIDAD"),Objeto.getJSONObject(I).getString("FORMATEADA"),Objeto.getJSONObject(I).getString("DESCRIPCION_ESTADO"),"1",""));
                    }
                    Adaptador = new adaptar_recyclerview_listaaverias(activity_listado_averias.this,Items,activity_listado_averias.this);
                    Lista.setAdapter(Adaptador);
                    Lista.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    Lista.setHasFixedSize(true);
                    Dialogo.dismissWithAnimation();
                }
            }));
        }
        // Es mecanico
        else{
            // Ver historial
           if(VerHistoricoMecanico.equals("1"))
           {
               final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_listado_averias.this, SweetAlertDialog.PROGRESS_TYPE);
               Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
               Dialogo.setTitleText("Espere por favor...");
               Dialogo.show();

               API.GetAPI().add(AVEAPI.GetAllHistorico(ID_USUARIO, new E_API.VolleyResponseListener() {
                   @Override
                   public void onError(String Mensaje) {
                       Dialogo.dismissWithAnimation();
                       MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                   }

                   @Override
                   public void onResponse(Object Respuesta) throws JSONException {
                       Items.clear();

                       JSONArray Objeto = new JSONArray(Respuesta.toString());

                       for (int I = 0; I < Objeto.length(); I++) {
                           Items.add(new E_Averia(Objeto.getJSONObject(I).getInt("ID_AVERIA"), Objeto.getJSONObject(I).getString("NOMBRE_ESPECIALIDAD"), Objeto.getJSONObject(I).getString("FORMATEADA"), Objeto.getJSONObject(I).getString("DESCRIPCION_ESTADO"),"0",ID_USUARIO));
                       }
                       Adaptador = new adaptar_recyclerview_listaaverias(activity_listado_averias.this, Items, activity_listado_averias.this);
                       Lista.setAdapter(Adaptador);
                       Lista.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                       Lista.setHasFixedSize(true);
                       Swipe.setRefreshing(false);
                       Dialogo.dismissWithAnimation();
                   }
               }));
           }
           // Ver activas solamente
           else{
               final SweetAlertDialog Dialogo = new SweetAlertDialog(activity_listado_averias.this, SweetAlertDialog.PROGRESS_TYPE);
               Dialogo.getProgressHelper().setBarColor(Color.parseColor("#FF4B75"));
               Dialogo.setTitleText("Espere por favor...");
               Dialogo.show();
               API.GetAPI().add(AVEAPI.GetAllPendientes(USUARIO, new E_API.VolleyResponseListener() {
                   @Override
                   public void onError(String Mensaje) {
                       Dialogo.dismissWithAnimation();
                       MostrarError("Error", "Ocurrio un error de comunicación con el servidor, intentarlo mas tarde.");
                   }

                   @Override
                   public void onResponse(Object Respuesta) throws JSONException {
                       Items.clear();
                       JSONArray Objeto = new JSONArray(Respuesta.toString());

                       for (int I = 0; I < Objeto.length(); I++) {
                           Items.add(new E_Averia(Objeto.getJSONObject(I).getInt("ID_AVERIA"), Objeto.getJSONObject(I).getString("NOMBRE_ESPECIALIDAD"), Objeto.getJSONObject(I).getString("FORMATEADA"), Objeto.getJSONObject(I).getString("DESCRIPCION_ESTADO"),"0",ID_USUARIO));
                       }
                       Adaptador = new adaptar_recyclerview_listaaverias(activity_listado_averias.this, Items, activity_listado_averias.this);
                       Lista.setAdapter(Adaptador);
                       Lista.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                       Lista.setHasFixedSize(true);
                       Swipe.setRefreshing(false);
                       Dialogo.dismissWithAnimation();
                   }
               }));
           }
        }
    }

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
