package com.example.kuicly.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.kuicly.modelo.Carrinho;
import com.example.kuicly.modelo.Curso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarrinhoJsonParser {

        /*public static ArrayList<Carrinho> parserJsonCarrinho(JSONArray response){
        ArrayList<Curso> carrinhos = new ArrayList<>();
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
    }*/

    public static Carrinho parserJsonCarrinho(String response) {
        Carrinho carrinho  = null;
        try {
            JSONObject carrinhoJSON = new JSONObject(response);
            int id = carrinhoJSON.getInt("id");
            int user_id = carrinhoJSON.getInt("user_id");
            float total = carrinhoJSON.getInt("total");

            carrinho = new Carrinho(id, total,user_id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return carrinho;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
