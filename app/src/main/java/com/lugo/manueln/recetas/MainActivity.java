package com.lugo.manueln.recetas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lugo.manueln.recetas.utilidades.imageViewRedondea;
import com.lugo.manueln.recetas.utilidades.utilidades;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Comunica_Actividades{

    private Button boton_Registro,copiar;
    private String pathDB,b;
    private int idUltimaReceta;

    private ImageButton miImageButton;

    private TextView nombreUltimaReceta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ConexionBBDDHelper miConex=new ConexionBBDDHelper(this,"bd_recetas",null,1);

        miImageButton=(ImageButton)findViewById(R.id.imageButton1);
        nombreUltimaReceta=(TextView)findViewById(R.id.text_ultima);


       idUltimaReceta=ultimaReceta(miConex);


        miImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle pasaid_ultima=new Bundle();
                pasaid_ultima.putInt("id",idUltimaReceta);
                Intent verReceta=new Intent(getApplicationContext(),receta_seleccionadaActivity.class);
                verReceta.putExtras(pasaid_ultima);

                startActivity(verReceta);
                //Toast.makeText(getApplicationContext(),"Receta " + idUltimaReceta,Toast.LENGTH_SHORT).show();
            }
        });





        boton_Registro=(Button) findViewById(R.id.boton_registro);

        boton_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                irRegistro();

            }
        });





    }

    public void irRegistro(){
        Intent i=new Intent(this,registro_recetas.class);
        startActivity(i);
    }

    public int ultimaReceta(ConexionBBDDHelper conexion){

        int id;

        String nombreReceta;

        SQLiteDatabase miBBDD=conexion.getReadableDatabase();

        Cursor miCursor=miBBDD.rawQuery("SELECT * from " + utilidades.TABLA_RECETAS + " ORDER BY " + utilidades.CAMPO_ID+ " DESC LIMIT 1" ,null);

        miCursor.moveToNext();

        id=miCursor.getInt(0);

        nombreReceta=miCursor.getString(1);

        nombreUltimaReceta.setText(nombreReceta);

        String imageString=miCursor.getString(3);
        Uri image=Uri.parse(imageString);

        Bitmap miBit=null;
        try {
            miBit= MediaStore.Images.Media.getBitmap(this.getContentResolver(),image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap definitivo=Bitmap.createScaledBitmap(miBit,1280/2,720/2,false);

        nombreUltimaReceta.setWidth(definitivo.getWidth());


        Bitmap def_2=imageViewRedondea.getRoundedCornerBitmap(definitivo,12);

       /* Resources res = getResources();
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, definitivo);
        dr.setCornerRadius(12);

        miImageButton.setImageDrawable(dr);  Tambien Valida a partir de la api21*/

       miImageButton.setImageBitmap(def_2);


        Toast.makeText(this,nombreReceta,Toast.LENGTH_SHORT).show();

        return id;

    }

    @Override
    public void menu(int queBoton) {

        if (queBoton==1){
        Intent i=new Intent(this,actividad_recetas.class);
        startActivity(i);
        }else if (queBoton==2){
            Intent i=new Intent(this,info.class);
            startActivity(i);
        }

    }

    public void ejecutarInfo(View vista){

        Intent objeto_intent=new Intent(this,info.class);

        startActivity(objeto_intent);

    }


   @Override public boolean onCreateOptionsMenu(Menu mimenu){

      getMenuInflater().inflate(R.menu.menu_principal,mimenu);
       return true;
   }

   @Override public boolean onOptionsItemSelected(MenuItem opcion_menu){

       int id=opcion_menu.getItemId();

       if(id==R.id.configuracion){
           return true;
       }

       if (id==R.id.info){

           ejecutarInfo(null);

           return true;
       }

       return  super.onOptionsItemSelected(opcion_menu);

   }




}
