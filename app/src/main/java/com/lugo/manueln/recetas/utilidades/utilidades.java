package com.lugo.manueln.recetas.utilidades;

/**
 * Created by ManuelN on 9/22/2018.
 */

public class utilidades {

    //Constantes campos tabla recetas

    public static final String TABLA_RECETAS="recetas";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_DESCRIPCION="descripcion";
    public static final String CAMPO_IMAGEN="imagen";
    public static final String CAMPO_INGREDIENTES="ingredientes";
    public static final String CAMPO_PREPARACION="preparacion";



    public static final String CREAR_TABLA_RECETA="CREATE TABLE "
            + TABLA_RECETAS + " ("+ CAMPO_ID + " INTEGER, " + CAMPO_NOMBRE + " TEXT, " + CAMPO_DESCRIPCION + " TEXT,"
            + CAMPO_IMAGEN + " TEXT, " + CAMPO_INGREDIENTES + " TEXT, " + CAMPO_PREPARACION + " TEXT)"  ;




}
