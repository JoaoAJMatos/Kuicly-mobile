package com.example.kuicly.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.kuicly.modelo.Carrinho;
import com.example.kuicly.modelo.Fatura;

import org.json.JSONException;
import org.json.JSONObject;

public class FaturaJsonParser {

    public static Fatura parserJsonFatura(String response) {
        Fatura fatura  = null;
        try {
            JSONObject faturaJSON = new JSONObject(response);
            int id = faturaJSON.getInt("id");
            String date = faturaJSON.getString("date");
            float total = faturaJSON.getInt("total_price");
            int user_id = faturaJSON.getInt("user_id");
            int iva_id = faturaJSON.getInt("iva_id");

            fatura = new Fatura(id,date,total,user_id,iva_id);
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
