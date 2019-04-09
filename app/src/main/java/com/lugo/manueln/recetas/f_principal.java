package com.lugo.manueln.recetas;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class f_principal extends Fragment {

    private final int[] misBotonesMenu={R.id.b_Principal,R.id.b_Recetas,R.id.b_Otros};


    int a=misBotonesMenu[1];

    public f_principal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vistaFragment=inflater.inflate(R.layout.fragment_f_principal, container, false);

        Button boton_menu;

        for(int i=0;i<misBotonesMenu.length;i++){
            boton_menu=(Button) vistaFragment.findViewById(misBotonesMenu[i]);

            final int queboton=i;

            boton_menu.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Activity actividadActual=getActivity();

                    ((Comunica_Actividades)actividadActual).menu(queboton);
                }
            });
        }
        return vistaFragment;
    }



}
