package com.lugo.manueln.recetas;

import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.lugo.manueln.recetas.utilidades.utilidades;

import java.io.IOException;

public class edita_receta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_receta);


        edit_miLayout=(LinearLayout) findViewById(R.id.edit_Layout_ingredientes);

        edit_campo_ID =(EditText) findViewById(R.id.edit_R_ID);
        edit_campo_NOMBRE =(EditText)findViewById(R.id.edit_R_NOMBRE);
        edit_campo_DECRIPCION =(EditText)findViewById(R.id.edit_R_DESCRIPCION);
        edit_imagen_receta =(ImageView)findViewById(R.id.edit_image_selec);
        edit_campo_PREPARACION =(EditText)findViewById(R.id.Edit_preparacion);

        edit_cantidad=(EditText) findViewById(R.id.edit_cantidad);
        edit_ingrediente=(EditText)findViewById(R.id.edit_ingrediente);


        Button botonedi=(Button)findViewById(R.id.edit_b_editar);

        final ConexionBBDDHelper conex=new ConexionBBDDHelper(this,"bd_recetas",null,1);

        botonedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_receta(conex);
            }
        });


        Bundle miBundle=getIntent().getExtras();

         rellenaInfo(miBundle);




        //See you Later
    }

    private void rellenaInfo(Bundle miBundle){

        String[][] ingredientes= (String[][]) miBundle.getSerializable("ingre");

        edit_campo_ID.setEnabled(false);
        edit_campo_ID.setText(""+miBundle.getInt("id"));
        edit_campo_NOMBRE.setText(miBundle.getString("name"));
        edit_campo_DECRIPCION.setText(miBundle.getString("desc"));
        edit_campo_PREPARACION.setText(miBundle.getString("prepa"));
        cantidad_ingre=miBundle.getInt("c_ingre");


        edit_cantidad=(EditText) findViewById(R.id.edit_cantidad);
        edit_medida=(Spinner)findViewById(R.id.edit_medida);
        edit_ingrediente=(EditText)findViewById(R.id.edit_ingrediente);


        ArrayAdapter<CharSequence> edit_adapter1 = ArrayAdapter.createFromResource(this, R.array.combo_medidas, android.R.layout.simple_spinner_item);
        edit_medida.setAdapter(edit_adapter1);

        edit_cantidad.setText("" + ingredientes[0][0]);
        edit_medida.setSelection(medidad_selec(ingredientes[0][1]));
        edit_ingrediente.setText("" + ingredientes[0][2]);

        edit_cantidades=new EditText[cantidad_ingre];
        edit_ingredientes=new EditText[cantidad_ingre];
        edit_medidas=new Spinner[cantidad_ingre];
        edit_misIngredientes=new LinearLayout[cantidad_ingre];


        for(int i=0;i<cantidad_ingre-1;i++){

                edit_misIngredientes[i] = new LinearLayout(this);
                edit_misIngredientes[i].setOrientation(LinearLayout.HORIZONTAL);


                edit_cantidades[i] = new EditText(this);
                edit_ingredientes[i] = new EditText(this);
                edit_medidas[i] = new Spinner(this);


                ArrayAdapter<CharSequence> edit_adapter2 = ArrayAdapter.createFromResource(this, R.array.combo_medidas, android.R.layout.simple_spinner_item);

                edit_medidas[i].setAdapter(edit_adapter2);

                edit_cantidades[i].setText("" + ingredientes[i+1][0]);              // ¿por que i+1? esto es debido a que ya que i es 0
                edit_medidas[i].setSelection(medidad_selec(ingredientes[i+1][1]));  //al buscar ingredientes[i]me buscaria el primer valor
                edit_ingredientes[i].setText("" + ingredientes[i+1][2]);            //del array ingredientes y necesito es el 2do

                ViewGroup.LayoutParams edit_misParametrosCantidades = new ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT);

                ViewGroup.LayoutParams edit_misParametrosIngredientes = new ViewGroup.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT);

                edit_cantidades[i].setLayoutParams(edit_misParametrosCantidades);
                edit_ingredientes[i].setLayoutParams(edit_misParametrosIngredientes);

                edit_misIngredientes[i].addView(edit_cantidades[i]);
                edit_misIngredientes[i].addView(edit_medidas[i]);
                edit_misIngredientes[i].addView(edit_ingredientes[i]);

                edit_miLayout.addView(edit_misIngredientes[i]);
        }



        edit_Imagen_Uri=Uri.parse(miBundle.getString("image"));

        Bitmap edit_miBitMap=null;
        try {
            edit_miBitMap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),edit_Imagen_Uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap edit_def=Bitmap.createScaledBitmap(edit_miBitMap,1280,720,false);

        edit_imagen_receta.setImageBitmap(edit_def);

        String msj_def="";
        for (int i=0;i<ingredientes.length;i++){
            msj_def+=ingredientes[i][0]+ " " + ingredientes [i][1]+ " " +ingredientes [i][2] + "\n";
        }






        Toast.makeText(this,msj_def,Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            edit_Imagen_Uri=data.getData();
            Bitmap miBitMap=null;
            try {
                miBitMap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),edit_Imagen_Uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap def=Bitmap.createScaledBitmap(miBitMap,1280,720,false);

            edit_imagen_receta.setImageBitmap(def);
        }
    }
    public void edit_press_image(View view) {
        edit_cargarImagen();
    }



    public void update_receta(ConexionBBDDHelper miConex){

        SQLiteDatabase bbdd=miConex.getWritableDatabase();

        String[]parametros={edit_campo_ID.getText().toString()};

        ContentValues valores=new ContentValues();

        valores.put(utilidades.CAMPO_NOMBRE,edit_campo_NOMBRE.getText().toString());
        valores.put(utilidades.CAMPO_DESCRIPCION,edit_campo_DECRIPCION.getText().toString());
        valores.put(utilidades.CAMPO_PREPARACION,edit_campo_PREPARACION.getText().toString());

        if(edit_Imagen_Uri==null){
            valores.put(utilidades.CAMPO_IMAGEN,"");
        }else{
            valores.put(utilidades.CAMPO_IMAGEN,edit_Imagen_Uri.toString());
        }

        valores.put(utilidades.CAMPO_INGREDIENTES,obtenIngredientes());


        bbdd.update(utilidades.TABLA_RECETAS,valores,utilidades.CAMPO_ID+ "=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizo",Toast.LENGTH_SHORT);

        bbdd.close();

    }

    private String obtenIngredientes( ){

        int num=cantidad_ingre;
        String completo="";
        String Linea="";

        for (int i=0;i<cantidad_ingre;i++) {

            if(i==0) {
                Linea= edit_cantidad.getText().toString() + "_" + edit_medida.getSelectedItem().toString() + "_" +
                        edit_ingrediente.getText().toString();
                completo+=Linea+"-";
            }else {

                Linea=edit_cantidades[i-1].getText().toString()+ "_" + edit_medidas[i-1].getSelectedItem().toString() +
                        "_" + edit_ingredientes[i-1].getText().toString();

                completo+=Linea+"-";


            }
        }


        Toast.makeText(getApplicationContext()," "+ cantidad_ingre,Toast.LENGTH_SHORT);


        return completo;
    }
    private void edit_cargarImagen() {
        Intent miIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        miIntent.setType("image/");
        startActivityForResult(miIntent.createChooser(miIntent,"Seleccione Aplicacion"),10);
    }

    private int medidad_selec(String medida){
        int selec=6;
        if(medida.equalsIgnoreCase("kg")){
            selec=0;
            return selec;
        }else if (medida.equalsIgnoreCase("gr")){
            selec=1;
            return selec;
        }else if(medida.equalsIgnoreCase("l")){
            selec=2;
            return selec;
        }else if (medida.equalsIgnoreCase("ml")){
            selec=3;
            return selec;
        }else if (medida.equalsIgnoreCase("cup")){
            selec=4;
            return selec;
        }

        return selec;
    }


    EditText edit_campo_ID, edit_campo_NOMBRE, edit_campo_DECRIPCION, edit_campo_PREPARACION;
    ImageView edit_imagen_receta;

    LinearLayout edit_miLayout;
    LinearLayout[] edit_misIngredientes;
    EditText edit_cantidad, edit_ingrediente;
    Spinner edit_medida;
    EditText[]edit_cantidades,edit_ingredientes;
    Spinner[]edit_medidas;



    Uri edit_Imagen_Uri;
    private int cantidad_ingre;
}
