package com.lugo.manueln.recetas;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class receta_seleccionadaActivity extends AppCompatActivity implements Comunica_Actividades{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_seleccionada);

      Bundle miBundle=getIntent().getExtras();

        int id=miBundle.getInt("id");


       Toast.makeText(this," "+id,Toast.LENGTH_SHORT).show();

        Bundle miBundleF=new Bundle();

        miBundleF.putInt("idd",id);

        recetaSeleccionadaFragment miFragment=new recetaSeleccionadaFragment();

        miFragment.setArguments(miBundleF);

        FragmentManager miManejador=getFragmentManager();

        FragmentTransaction miTransaccion=miManejador.beginTransaction();

        miTransaccion.replace(R.id.Contenedor,miFragment);

        miTransaccion.commit();







    }


    @Override
    public void menu(int queBoton) {
        if(queBoton==0){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }else if(queBoton==1){
            Intent intent=new Intent(this,actividad_recetas.class);
            startActivity(intent);
        }else if (queBoton==2){
            Intent intent=new Intent(this,info.class);
            startActivity(intent);
        }

    }
}
