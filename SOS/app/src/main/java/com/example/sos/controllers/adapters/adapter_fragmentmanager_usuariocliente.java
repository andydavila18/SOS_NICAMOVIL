package com.example.sos.controllers.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sos.controllers.fragments.fragmento_usuariocliente_carro;
import com.example.sos.controllers.fragments.fragmento_usuariocliente_datospersonales;

public class adapter_fragmentmanager_usuariocliente extends FragmentPagerAdapter {

    // Total de fragmentos
    private static int NFragmentos = 2;

    public adapter_fragmentmanager_usuariocliente(FragmentManager FM) {
        super(FM);
    }

    // Obtiene un fragmento del adaptador
    @Override
    public Fragment getItem(int Posicion) {
        switch (Posicion) {
            case 0:
                return new fragmento_usuariocliente_datospersonales();
            case 1:
                return new fragmento_usuariocliente_carro();
            default:
                return null;
        }

    }

    // Devuelve el numero de fragmentos asociados al adaptador
    @Override
    public int getCount() {
        return NFragmentos;
    }
}