package com.example.kuicly.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.Curso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarrinhoItensJsonParser {

    public static ArrayList<CarrinhoItens> parserJsonCarrinhoItens(JSONArray response){
        ArrayList<CarrinhoItens> carrinhoitens = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject carrinhoitensJSON = (JSONObject) response.get(i);
                int id = carrinhoitensJSON.getInt("id");
                String title = carrinhoitensJSON.getString("title");
                float price = carrinhoitensJSON.getInt("price");
                String capa = carrinhoitensJSON.getString("file_id");
                int carrinho_id = carrinhoitensJSON.getInt("cart_id");
                int curso_id = carrinhoitensJSON.getInt("course_id");
                CarrinhoItens carrinhoitem = new CarrinhoItens(id,title,price,capa,carrinho_id,curso_id );
                carrinhoitens.add(carrinhoitem);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return carrinhoitens;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
