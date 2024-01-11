package com.example.kuicly.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.kuicly.modelo.Curso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CursoJsonParser {

    public static ArrayList<Curso> parserJsonCursos(JSONArray response){
        ArrayList<Curso> cursos = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject cursoJSON = (JSONObject) response.get(i);
                int id = cursoJSON.getInt("id");
                String description = cursoJSON.getString("description");
                String title = cursoJSON.getString("title");
                float price = cursoJSON.getInt("price");
                int skill_level = cursoJSON.getInt("skill_level");
                String capa = cursoJSON.getString("file_id");
                Curso curso = new Curso(id, description, title, price, skill_level,capa);
                cursos.add(curso);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return cursos;
    }

    public static Curso parserJsonCurso(String response) {
        Curso curso  = null;
        try {
            JSONObject cursoJSON = new JSONObject(response);
            int id = cursoJSON.getInt("id");
            String description = cursoJSON.getString("description");
            String title = cursoJSON.getString("title");
            float price = cursoJSON.getInt("price");
            int skill_level = cursoJSON.getInt("skill_level");
            String capa = cursoJSON.getString("file_id");
            curso = new Curso(id, description, title, price, skill_level,capa);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return curso;
    }


    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
