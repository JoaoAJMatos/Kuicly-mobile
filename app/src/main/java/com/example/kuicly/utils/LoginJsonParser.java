package com.example.kuicly.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginJsonParser {

    public static String parserJsonLogin(String response) {
        String token = null;
        try {
            JSONObject loginJSON = new JSONObject(response);
            token = loginJSON.getString("token");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

}
