package com.example.kuicly.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CursoBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "yii2kuicly", TABLE_NAME = "course";

    private static final int DB_VERSION = 1;

    private SQLiteDatabase db;

    private static final String ID = "id",DESCRIPTION = "description",  TITLE = "title", PRICE = "price", SKILL_LEVEL = "skill_level";

    public CursoBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    public void removerAllCursosBD(){
        db.delete(TABLE_NAME, null, null);
    }
    public ArrayList<Curso> getAllCursosBD(){
        ArrayList<Curso> cursos = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, DESCRIPTION, TITLE,PRICE,SKILL_LEVEL}, null, null,
                null, null, null);
        if(cursor.moveToFirst()){
            do{
                Curso auxCurso = new Curso(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getInt(4));
                cursos.add(auxCurso);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return cursos;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTableCursos = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY, " +
                DESCRIPTION + " TEXT NOT NULL, " +
                TITLE + " TEXT NOT NULL, " +
                PRICE + " FLOAT NOT NULL, " +
                SKILL_LEVEL + " INTEGER NOT NULL);";
        db.execSQL(sqlTableCursos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
