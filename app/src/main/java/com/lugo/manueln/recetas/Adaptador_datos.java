package com.lugo.manueln.recetas;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManuelN on 9/21/2018.
 */

public class Adaptador_datos extends RecyclerView.Adapter<Adaptador_datos.ViewHolderDatos> implements View.OnClickListener,Filterable {

    ArrayList<receta>lista_receta;
    ArrayList<receta>lista_recetaFull;
    Context miContexto;
    private View.OnClickListener listener;
    private View.OnLongClickListener longListener;

    public Adaptador_datos(ArrayList<receta> lista_receta,Context c) {
        this.lista_receta = lista_receta;
        lista_recetaFull=new ArrayList<>(lista_receta);
        miContexto=c;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_objetos_recycler, null,false);

        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {

        //Rellenamos los datos de las variables con los metodos set y get de la clase receta
        
        boolean imageNull=false;
        String fotoString=null;

        fotoString=lista_receta.get(position).getImagen();

        Uri foto_Uri=Uri.parse(fotoString);
        Bitmap bit=null;

        try {
            bit = MediaStore.Images.Media.getBitmap(miContexto.getContentResolver(), foto_Uri);
        } catch (IOException e) {
                e.printStackTrace();
            imageNull=true;
        }

        if (imageNull==false){
        Bitmap defi = Bitmap.createScaledBitmap(bit, 1280, 720, false);
        holder.foto.setImageBitmap(defi);
        }else {
            holder.foto.setImageResource(R.drawable.contorno_imagen);
        }





        holder.Yd.setText(lista_receta.get(position).getId().toString());
        holder.nombre.setText(lista_receta.get(position).getNombre());
        holder.descripcion.setText(lista_receta.get(position).getDescripcion());


    }

    @Override
    public int getItemCount() {
        return lista_receta.size();
    }

    public void setOnClickListener(View.OnClickListener Listener){
        listener=Listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener Listenere){
        longListener=Listenere;
    }

    @Override
    public void onClick(View view) {

        if (listener!=null){
            listener.onClick(view);
        }
    }

    @Override
    public Filter getFilter() {
        return filtro_receta;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView nombre,descripcion,Yd;
        ImageView foto;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            //Enlazamos los elementos con su respectivo en la vista

            Yd=(TextView)itemView.findViewById(R.id.id_rece);
            nombre=(TextView) itemView.findViewById(R.id.nombre_receta);
            descripcion=(TextView)itemView.findViewById(R.id.des_receta);
            foto=(ImageView) itemView.findViewById(R.id.foto_receta);

        }

    }

    private Filter filtro_receta=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<receta> lista_receta_filtro=new ArrayList<>();

            if (charSequence==null||charSequence.length()==0){
                lista_receta_filtro.addAll(lista_recetaFull);
            }else {
                String filtroPatron=charSequence.toString().toLowerCase().trim();

                for (receta miItem:lista_recetaFull){
                    if(miItem.getNombre().toLowerCase().contains(filtroPatron)){
                        lista_receta_filtro.add(miItem);
                    }

                }
            }

            FilterResults resultado_receta=new FilterResults();
            resultado_receta.values=lista_receta_filtro;

            return resultado_receta;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            lista_receta.clear();
            lista_receta.addAll((List)filterResults.values);
            notifyDataSetChanged();


        }
    };


}
