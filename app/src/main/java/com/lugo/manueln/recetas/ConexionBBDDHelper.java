package com.lugo.manueln.recetas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lugo.manueln.recetas.utilidades.utilidades;

/**
 * Created by ManuelN on 9/22/2018.
 */

public class ConexionBBDDHelper extends SQLiteOpenHelper {


    public ConexionBBDDHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(utilidades.CREAR_TABLA_RECETA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {

        db.execSQL("DROP TABLE IF EXISTS " + utilidades.TABLA_RECETAS);
        onCreate(db);
    }


}
