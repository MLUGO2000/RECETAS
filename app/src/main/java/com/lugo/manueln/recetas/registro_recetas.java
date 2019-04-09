package com.lugo.manueln.recetas;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.lugo.manueln.recetas.utilidades.utilidades;

import java.io.IOException;

public class registro_recetas extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_recetas);

        campo_ID=(EditText) findViewById(R.id.R_ID);
        campo_NOMBRE=(EditText)findViewById(R.id.R_NOMBRE);
        campo_DECRIPCION=(EditText)findViewById(R.id.R_DESCRIPCION);
        imagen_receta=(ImageView)findViewById(R.id.image_selec);
        campo_PREPARACION=(EditText)findViewById(R.id.preparacion);


        cantidad=(EditText) findViewById(R.id.cantidad);
        ingrediente=(EditText)findViewById(R.id.ingrediente);
        medida=(Spinner)findViewById(R.id.medida);

        elementos=0;

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource
                (this,R.array.combo_medidas,android.R.layout.simple_spinner_item);

        medida.setAdapter(adapter);

        miLayout=(LinearLayout) findViewById(R.id.Layout_ingredientes);
        misIngredientes=new LinearLayout[10];
        cantidades=new EditText[10];
        ingredientes=new EditText[10];
        medidas=new Spinner[10];



    }

    public void boton_press(View vista){

        registrar_Receta();
       // registrar_Recetasql();
    }

    private void registrar_Recetasql() {
        ConexionBBDDHelper miConexion=new ConexionBBDDHelper(this,"bd_recetas",null,1);

        SQLiteDatabase db=miConexion.getWritableDatabase();

        //insert into recetas (id,nombre,descripcion) values(123,pasta,descripcion de la receta);


        String insert="INSERT INTO" + utilidades.TABLA_RECETAS + " (" + utilidades.CAMPO_ID + "," + utilidades.CAMPO_NOMBRE +
                "," + utilidades.CAMPO_DESCRIPCION + ") " + "values ("+ campo_ID.getText().toString() + ", " + campo_NOMBRE.getText().toString()
                + ", "+ campo_DECRIPCION.getText().toString() + ")";

        db.execSQL(insert);
        db.close();
    }

    private void registrar_Receta(){

        String c,ing,Spi;
        elementos=0;
        String total="";
        int longitud=cantidades.length;

            for(int i=0;i<(longitud);i++){


                if(elementos==0){

                    c=cantidad.getText().toString();

                    Spi=medida.getSelectedItem().toString();
                    ing=ingrediente.getText().toString();

                    String def= c +"_" + Spi + "_" + ing ;

                    total = total + def + "-";

                    elementos++;

                }else if(cantidades[elementos-1]==null) {break;}
                else{


                    c = cantidades[elementos-1].getText().toString();
                    Spi=medidas[elementos-1].getSelectedItem().toString();
                    ing = ingredientes[elementos-1].getText().toString();

                    String def = c + "_" + Spi + "_" + ing;

                    total = total + def + "-";

                    elementos++;
                }

            }




        Context miContexto=this;
        ConexionBBDDHelper conn=new ConexionBBDDHelper(miContexto,"bd_recetas",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put(utilidades.CAMPO_ID,campo_ID.getText().toString());
        values.put(utilidades.CAMPO_NOMBRE,campo_NOMBRE.getText().toString());
        values.put(utilidades.CAMPO_DESCRIPCION,campo_DECRIPCION.getText().toString());
        if(miPath==null){
            values.put(utilidades.CAMPO_IMAGEN,"");
        }else{
            values.put(utilidades.CAMPO_IMAGEN,miPath.toString());
        }
        values.put(utilidades.CAMPO_INGREDIENTES,total);
        values.put(utilidades.CAMPO_PREPARACION,campo_PREPARACION.getText().toString());



        Long id_resultante=db.insert(utilidades.TABLA_RECETAS,utilidades.CAMPO_ID,values);

        Toast.makeText(getApplicationContext(),"ID REGISTRO: " + id_resultante,Toast.LENGTH_SHORT).show();

        db.close();
    }


    public void press_image(View view) {
        cargarImagen();
    }

    private void cargarImagen() {
        Intent miIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        miIntent.setType("image/");
        startActivityForResult(miIntent.createChooser(miIntent,"Seleccione Aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
             miPath=data.getData();
            Bitmap miBitMap=null;
            try {
                miBitMap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),miPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap def=Bitmap.createScaledBitmap(miBitMap,1280,720,false);

            imagen_receta.setImageBitmap(def);
        }
    }

    public void crear_vista(View vista){

        misIngredientes[elementos]=new LinearLayout(this);
        cantidades[elementos]=new EditText(this);
        ingredientes[elementos]=new EditText(this);
        medidas[elementos]=new Spinner(this);

        misIngredientes[elementos].setOrientation
                (LinearLayout.HORIZONTAL);

        cantidades[elementos].setHint("1");
        ingredientes[elementos].setHint("Tomate");


        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource
                (this,R.array.combo_medidas,android.R.layout.simple_spinner_item);

        medidas[elementos].setAdapter(adapter2);

        ViewGroup.LayoutParams misParametrosCantidades=new ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT);

        ViewGroup.LayoutParams misParametrosIngredientes=new ViewGroup.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT);

       cantidades[elementos].setLayoutParams(misParametrosCantidades);

        ingredientes[elementos].setLayoutParams(misParametrosIngredientes);

        misIngredientes[elementos].addView(cantidades[elementos]);
        misIngredientes[elementos].addView(medidas[elementos]);
        misIngredientes[elementos].addView(ingredientes[elementos]);

        miLayout.addView(misIngredientes[elementos]);

        elementos++;

    }

    EditText campo_ID,campo_NOMBRE,campo_DECRIPCION,campo_PREPARACION;
    ImageView imagen_receta;
    Uri miPath;


    LinearLayout miLayout;

    LinearLayout[] misIngredientes;
    EditText[]cantidades,ingredientes;
    Spinner[]medidas;

    EditText cantidad,ingrediente;
    Spinner medida;



    private int elementos;


}
