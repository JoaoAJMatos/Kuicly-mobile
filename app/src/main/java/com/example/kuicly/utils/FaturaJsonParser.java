package com.example.kuicly.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.kuicly.modelo.Carrinho;
import com.example.kuicly.modelo.Fatura;
import com.example.kuicly.modelo.FaturaItens;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FaturaJsonParser {

    public static ArrayList<Fatura> parserJsonFaturas(JSONArray response){
        ArrayList<Fatura> faturas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject faturaJSON = (JSONObject) response.get(i);
                int id = faturaJSON.getInt("id");
                String date = faturaJSON.getString("date");
                float total = faturaJSON.getInt("total_price");
                int user_id = faturaJSON.getInt("user_id");
                int iva = faturaJSON.getInt("iva");
                float totaliva = faturaJSON.getInt("totaliva");
                float subtotal = faturaJSON.getInt("subtotal");
                Fatura fatura = new Fatura(id,date,total,user_id,iva,totaliva,subtotal);
                faturas.add(fatura);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return faturas;
    }

    public static Fatura parserJsonFatura(String response) {
        Fatura fatura  = null;
        try {
            JSONObject faturaJSON = new JSONObject(response);
            int id = faturaJSON.getInt("id");
            String date = faturaJSON.getString("date");
            float total = faturaJSON.getInt("total_price");
            int user_id = faturaJSON.getInt("user_id");
            int iva = faturaJSON.getInt("iva");
            float totaliva = faturaJSON.getInt("totaliva");
            float subtotal = faturaJSON.getInt("subtotal");


            fatura = new Fatura(id,date,total,user_id,iva,totaliva,subtotal);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return fatura;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
