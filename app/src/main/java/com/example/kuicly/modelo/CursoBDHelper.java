package com.example.kuicly.modelo;

import android.content.ContentValues;
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

    private static final String ID = "id",DESCRIPTION = "description",  TITLE = "title", PRICE = "price", SKILL_LEVEL = "skill_level", CAPA = "file_id";

    public CursoBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    public void removerAllCursosBD(){
        db.delete(TABLE_NAME, null, null);
    }
    public ArrayList<Curso> getAllCursosBD(){
        ArrayList<Curso> cursos = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, DESCRIPTION, TITLE,PRICE,SKILL_LEVEL,CAPA}, null, null,
                null, null, null);
        if(cursor.moveToFirst()){
            do{
                Curso auxCurso = new Curso(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getInt(4),cursor.getString(5));
                cursos.add(auxCurso);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return cursos;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlTableCursos = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY, " +
                DESCRIPTION + " TEXT NOT NULL, " +
                TITLE + " TEXT NOT NULL, " +
                PRICE + " FLOAT NOT NULL, " +
                SKILL_LEVEL + " INTEGER NOT NULL," +
                CAPA + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(sqlTableCursos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Curso adicionarCursoBD(Curso livro){
        ContentValues values = new ContentValues();
        values.put(ID, livro.getId());
        values.put(DESCRIPTION, livro.getDescription());
        values.put(TITLE, livro.getTitle());
        values.put(PRICE, livro.getPrice());
        values.put(SKILL_LEVEL, livro.getSkill_level());
        values.put(CAPA, livro.getCapa());
        db.insert(TABLE_NAME, null, values);
        return livro;
    }
}
