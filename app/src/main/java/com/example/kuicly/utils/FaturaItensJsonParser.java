package com.example.kuicly.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.FaturaItens;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FaturaItensJsonParser {

    public static ArrayList<FaturaItens> parserJsonFaturaItens(JSONArray response){
        ArrayList<FaturaItens> faturaItens = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject faturaItensJSON = (JSONObject) response.get(i);
                int id = faturaItensJSON.getInt("id");
                float price = faturaItensJSON.getInt("price");
                float iva_price = faturaItensJSON.getInt("iva_price");
                int order_id = faturaItensJSON.getInt("order_id");
                int course_id = faturaItensJSON.getInt("course_id");
                String title = faturaItensJSON.getString("course_title");
                FaturaItens faturaitem = new FaturaItens(id,order_id,course_id,price,iva_price,title);
                faturaItens.add(faturaitem);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return faturaItens;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
