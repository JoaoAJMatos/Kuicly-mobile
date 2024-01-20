package com.example.kuicly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kuicly.modelo.SingletonGestorCursos;

public class IPAddressActivity extends AppCompatActivity {

    private EditText etIpAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipaddress);

        etIpAddress = findViewById(R.id.etIpAddress);
    }

    public void onClickAccept(View view) {
        String ipAddress = etIpAddress.getText().toString();
        Intent intent = new Intent(this, LoginActivity.class);

        SharedPreferences sharedIP  = getSharedPreferences("IP", MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedIP.edit();
        editor.putString("ip", ipAddress);
        editor.apply();

        SingletonGestorCursos.getInstance(getApplicationContext()).setIpAddress(ipAddress, getApplicationContext());

        startActivity(intent);

    }
}