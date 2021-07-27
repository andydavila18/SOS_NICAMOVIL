package com.example.sos.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sos.R;
import com.example.sos.controllers.activity.activity_listado_averias;
import com.example.sos.controllers.activity.activity_seguimiento_averia;
import com.example.sos.models.entity.E_Averia;

import java.util.List;

import fonts.MyTextView;
import fonts.TextViewMavenRegular;

public class adaptar_recyclerview_listaaverias extends RecyclerView.Adapter<adaptar_recyclerview_listaaverias.MenuViewHolder> {


    public LayoutInflater Inflater;
    public List<E_Averia> Datos;
    public activity_listado_averias Actividad;



    public adaptar_recyclerview_listaaverias(Context Context, List<E_Averia> Data, activity_listado_averias Referencia ) {
        Inflater = LayoutInflater.from(Context);
        this.Actividad = Referencia;
        this.Datos = Data;
    }


    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup Parent, int ViewType) {
        View View = Inflater.inflate(R.layout.tarjeta_averia, Parent, false);
        MenuViewHolder Holder = new MenuViewHolder(View);
        return Holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder MenuViewHolder, final int I) {

        MenuViewHolder.TipoAveria.setText(Datos.get(I).getTipo());
        MenuViewHolder.Fecha.setText(Datos.get(I).getFecha());
        MenuViewHolder.Estado.setText(Datos.get(I).getEstado());
        MenuViewHolder.BTN_VER.setTag(Datos.get(I).getID());
        if(Datos.get(I).getTipo() == "Arranque")
        {
            MenuViewHolder.ImagenTipo.setImageResource(R.drawable.circuloarranque);
        }
        else if ( Datos.get(I).getTipo().equals("Cambios de llantas")){
            MenuViewHolder.ImagenTipo.setImageResource(R.drawable.circulocambiollanta);
        }
        else if(Datos.get(I).getTipo().equals("Otro"))
        {
            MenuViewHolder.ImagenTipo.setImageResource(R.drawable.circulootro);
        }
        else if(Datos.get(I).getTipo().equals("Falta de gasolina"))
        {
            MenuViewHolder.ImagenTipo.setImageResource(R.drawable.circulofaltagasolina);
        }
        else if(Datos.get(I).getTipo().equals("Grua"))
        {
            MenuViewHolder.ImagenTipo.setImageResource(R.drawable.circulogrua);
        }
        else if(Datos.get(I).getTipo().equals("Falla Mecanica"))
        {
            MenuViewHolder.ImagenTipo.setImageResource(R.drawable.circulofallamecanica );
        }

        MenuViewHolder.BTN_VER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Vista = new Intent(Actividad, activity_seguimiento_averia.class);
                Vista.putExtra("ID_AVERIA",MenuViewHolder.BTN_VER.getTag().toString());
                Vista.putExtra("EsUsuario","1");
                Vista.putExtra("ID_USUARIO",Datos.get(I).getID_USUARIO());
                Vista.putExtra("EsUsuario",Datos.get(I).getMecanico());
                Actividad.startActivity(Vista);
            }
        });
    }

    @Override
    public int getItemCount() { return Datos.size(); }


    class MenuViewHolder extends RecyclerView.ViewHolder {

        MyTextView TipoAveria ;
        MyTextView Fecha ;
        MyTextView Estado;
        TextViewMavenRegular BTN_VER;
        ImageView ImagenTipo;

        public MenuViewHolder(final View Vista) {
            super(Vista);
            TipoAveria = Vista.findViewById(R.id.Tipo);
            Fecha = Vista.findViewById(R.id.Fecha);
            Estado = Vista.findViewById(R.id.Estado);
            BTN_VER = Vista.findViewById(R.id.btn_entrardetalle);
            ImagenTipo = Vista.findViewById(R.id.ImagenTipo);
        }
    }

}


