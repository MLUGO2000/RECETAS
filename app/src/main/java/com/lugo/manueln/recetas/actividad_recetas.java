package com.lugo.manueln.recetas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lugo.manueln.recetas.utilidades.utilidades;

import java.util.ArrayList;

public class actividad_recetas extends AppCompatActivity implements Comunica_Actividades{

    ArrayList<receta> lista_recetas;
    RecyclerView recyclerRecetas;
    press_Fragment miFragment;
    Adaptador_datos adapter;

    ConexionBBDDHelper conex;

    int item_selec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recetas);

        conex=new ConexionBBDDHelper(getApplicationContext(),"bd_recetas",null,1);

        lista_recetas=new ArrayList<>();
        recyclerRecetas=(RecyclerView) findViewById(R.id.recycler);
        recyclerRecetas.setLayoutManager(new LinearLayoutManager(this));

        //llenar_recetas(); //En caso de que se llene sin BBDD SQLITE

        consultarListaRecetas();

        adapter=new Adaptador_datos(lista_recetas,this);

        Toast.makeText(this,"dddada",Toast.LENGTH_SHORT);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=new Bundle();

                bundle.putInt("id",lista_recetas.get(recyclerRecetas.getChildAdapterPosition(view)).getId());
                Intent i=new Intent(getApplicationContext(),receta_seleccionadaActivity.class);
                i.putExtras(bundle);
               startActivity(i);

                Toast.makeText(getApplicationContext(),"Seleccionado receta n: " +
                        lista_recetas.get(recyclerRecetas.getChildAdapterPosition(view)).getNombre(),Toast.LENGTH_SHORT).show();

            }
        });

        recyclerRecetas.setAdapter(adapter);

    }

    private void consultarListaRecetas() {

        SQLiteDatabase db=conex.getReadableDatabase();

        receta miReceta=null;

        Cursor miCursor=db.rawQuery("SELECT * FROM " + utilidades.TABLA_RECETAS,null);

        while(miCursor.moveToNext()){

            miReceta=new receta();


            miReceta.setId(miCursor.getInt(0));
            miReceta.setNombre(miCursor.getString(1));
            miReceta.setDescripcion(miCursor.getString(2));
            miReceta.setImagen(miCursor.getString(3));


            lista_recetas.add(miReceta);
        }
    }

   /* public void llenar_recetas(){// En caso de que lo rellenaramos sin una BBDD SQlite
        lista_recetas.add(new receta(1,"Espagueti De Pollo En Salsa Bechamel","Esta receta de espaguetis de pollo en salsa bechamel es, además de exquisita, muy sana.",null,null,null));
        lista_recetas.add(new receta(2,"Pizza Pepperoni","La pizza pepperoni es un clásico italiano difícil de resistir, especialmente si eres amante de los sabores picantes y la intensidad. ",null,null,null));
        lista_recetas.add(new receta(3,"Pasticho","Pasticho es un tipo de pasta que se sirve en láminas",null,null,null));
        lista_recetas.add(new receta(4,"Pasticho","Pasticho es un tipo de pasta que se sirve en láminas",null,null,null));
        lista_recetas.add(new receta(5,"Pasticho","Pasticho es un tipo de pasta que se sirve en láminas",null,null,null));
        lista_recetas.add(new receta(6,"Pasticho","Pasticho es un tipo de pasta que se sirve en láminas",null,null,null));
        lista_recetas.add(new receta(7,"Pasticho","Pasticho es un tipo de pasta que se sirve en láminas",null,null,null));
        lista_recetas.add(new receta(8,"Pasticho","Pasticho es un tipo de pasta que se sirve en láminas",null,null,null));
        lista_recetas.add(new receta(9,"Pasticho","Pasticho es un tipo de pasta que se sirve en láminas",null,null,null));
        lista_recetas.add(new receta(10,"Pasticho","Pasticho es un tipo de pasta que se sirve en láminas",null,null,null));
    }*/


    @Override
    public void menu(int queBoton) {
        if(queBoton==0){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        }else if (queBoton==2){
            Intent intent=new Intent(this,info.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_busqueda,menu);

        MenuItem searchItem=menu.findItem(R.id.buscar);
       SearchView searchView= (SearchView) searchItem.getActionView();



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
