package com.lugo.manueln.recetas;

/**
 * Created by ManuelN on 9/21/2018.
 */

public class receta {

    private Integer id;
    private String nombre,descripcion,imagen,ingredientes,preparacion;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public receta(){


    }


    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public receta(Integer id, String nombre, String descripcion, String imagen, String ingredientes, String preparacion) {
        this.id= id;
        this.nombre = nombre;

        this.descripcion = descripcion;
        this.imagen=imagen;
        this.ingredientes=ingredientes;
        this.preparacion=preparacion;


    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
