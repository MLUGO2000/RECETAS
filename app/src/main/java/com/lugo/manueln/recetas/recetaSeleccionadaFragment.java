package com.lugo.manueln.recetas;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lugo.manueln.recetas.utilidades.utilidades;

import java.io.IOException;


public class recetaSeleccionadaFragment extends android.app.Fragment {


    public recetaSeleccionadaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_receta_seleccionada, container, false);

        id_p =getArguments().getInt("idd");

        miConex=new ConexionBBDDHelper(getActivity().getApplicationContext(),"bd_recetas",null,1);

        consultaReceta(id_p);

        String imageString=Receta.getImagen();
        Uri image_uri=Uri.parse(imageString);

        Bitmap miBit=null;
        try {
            miBit= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),image_uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap definitivo=Bitmap.createScaledBitmap(miBit,1280,720,false);

        nombre_Receta=(TextView) v.findViewById(R.id.receta_name);

        miImage=(ImageView)v.findViewById(R.id.receta_image);

        descrip_Receta=(TextView)v.findViewById(R.id.Descrip);

        preparacion_Receta=(TextView)v.findViewById(R.id.ingre);

        boton_editar=(Button)v.findViewById(R.id.b_edit);
        boton_delete=(Button)v.findViewById(R.id.b_delete);

        id_onclick= "" + id_p;
        boton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarRegistro(id_onclick);
            }
        });

       boton_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarRegistro(id_p);
            }
        });

        nombre_Receta.setText(""+Receta.getNombre());

        miImage.setImageBitmap(definitivo);

        descrip_Receta.setText(Receta.getDescripcion());

        preparacion_Receta.setText(Receta.getIngredientes() + "\n" + Receta.getPreparacion());



        return v;

    }

    public void editarRegistro(int id){

        if(Receta!=null){
            Toast.makeText(getActivity().getApplicationContext(),"Preparando para editar receta " + id,Toast.LENGTH_SHORT).show();

            if(ingredientes!=null){

                Bundle pasa_Datos=new Bundle();
                pasa_Datos.putInt("id",Receta.getId());
                pasa_Datos.putString("name",Receta.getNombre());
                pasa_Datos.putString("desc",Receta.getDescripcion());
                pasa_Datos.putString("image",Receta.getImagen());
                pasa_Datos.putInt("c_ingre",c_ingredientes);
                // pasa_Datos.putStringArray("ingre",ingredientes); //Cuando era un array nomal
                pasa_Datos.putSerializable("ingre",ingredientes2);//cuando es un array bidimensional necesitamos serealizarlo
                pasa_Datos.putString("prepa",Receta.getPreparacion());
                Intent edit_Intent=new Intent(getActivity().getApplicationContext(),edita_receta.class);
                edit_Intent.putExtras(pasa_Datos);
                startActivity(edit_Intent);
            }
        }





    }

    public void eliminarRegistro(String id){
        SQLiteDatabase miBBDD=miConex.getWritableDatabase();
        String[] parametros={id};

        miBBDD.delete(utilidades.TABLA_RECETAS,utilidades.CAMPO_ID + "=?",parametros);



        Toast.makeText(getActivity().getApplicationContext(),"Ya se elimino la receta con id " + id,Toast.LENGTH_SHORT).show();


    }

    public void consultaReceta(int id){//Metodo Que se debe Probar
        SQLiteDatabase miBBDD=miConex.getReadableDatabase();
        String[]ingregientes;
         Receta=null;

        //SELECT * FROM RECETAS WHERE ID='1'
        Cursor miCursor=miBBDD.rawQuery("SELECT * FROM "+ utilidades.TABLA_RECETAS + " WHERE " + utilidades.CAMPO_ID + "=" + "'" + id + "'",null);


        miCursor.moveToNext();

            Receta=new receta();
            String cadena_Ingrediente=miCursor.getString(4);

             c_ingredientes=cadena_Ingrediente.split("-").length;

            ingredientes2=new String[c_ingredientes][3];

            ingredientes=cadena_Ingrediente.split("-");


             for (int i=0;i<ingredientes.length;i++){
                ingredientes2[i]= ingredientes[i].split("_");
            }


            String Completo="";

        for (int i=0;i<c_ingredientes;i++){
            Completo+=ingredientes2[i][0]+ " " + ingredientes2 [i][1]+ " " +ingredientes2 [i][2] + "\n";
        }


            Receta.setId(miCursor.getInt(0));
            Receta.setNombre(miCursor.getString(1));
            Receta.setDescripcion(miCursor.getString(2));
            Receta.setImagen(miCursor.getString(3));
            Receta.setIngredientes(Completo);
            Receta.setPreparacion(miCursor.getString(5));



    }

    private int id_p;
    private String id_onclick;
    private Button boton_delete,boton_editar;
    private TextView nombre_Receta,descrip_Receta,preparacion_Receta;
    private ImageView miImage;
    private ConexionBBDDHelper miConex;
    private receta Receta;
    private String ingredientes[];
    private String ingredientes2[][];
    private int c_ingredientes;










}
