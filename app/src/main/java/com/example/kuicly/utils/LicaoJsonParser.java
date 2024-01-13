package com.example.kuicly.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.kuicly.modelo.Licao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LicaoJsonParser {
    public static ArrayList<Licao> parserJsonLicoes(JSONArray response){
        ArrayList<Licao> licoes = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject licaoJSON = (JSONObject) response.get(i);
                int id = licaoJSON.getInt("id");
                String title = licaoJSON.getString("title");
                String context = licaoJSON.getString("context");
                String video = licaoJSON.getString("file_id");
                Licao licao = new Licao(id,title,context,video);
                licoes.add(licao);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return licoes;
    }

    public static Licao parserJsonLicao(String response) {
        Licao licao  = null;
        try {
            JSONObject licaoJSON = new JSONObject(response);
            int id = licaoJSON.getInt("id");
            String title = licaoJSON.getString("title");
            String context = licaoJSON.getString("context");

            licao = new Licao(id,title,context,"");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return licao;
    }


    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
