package com.lugo.manueln.recetas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class press_Fragment extends android.app.Fragment {

    Button b_delete;
    Button b_editar;
    View miVista;

    public press_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        miVista= inflater.inflate(R.layout.fragment_press_, container, false);

        b_delete=(Button)miVista.findViewById(R.id.Delete_Receta);
        b_editar=(Button)miVista.findViewById(R.id.edit_Receta);


        return miVista;
    }

}
