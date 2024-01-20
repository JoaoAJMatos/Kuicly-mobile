package com.example.kuicly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kuicly.listners.LoginListener;
import com.example.kuicly.modelo.SingletonGestorCursos;

public class LoginActivity extends AppCompatActivity implements LoginListener{

    private EditText etUsername, etPassword;
    public static final int MAX_CHAR = 4, MIN_CHAR = 3;
    public static final String USER="USER";
    public static final String TOKEN = "token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        //inicializar
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

    }

    public void onClickLogin(View view) {
        String user = etUsername.getText().toString();
        String pass = etPassword.getText().toString();

       /* if(!isUsernameValido(user)) {
            etUsername.setError("Username inválido");
            return;
        }*/
        if(!isPasswordValida(pass)) {
            etPassword.setError("Password inválida");
            return;
        }

        SingletonGestorCursos singletonGestorCursos = SingletonGestorCursos.getInstance(this);
        singletonGestorCursos.setLoginListener(this);

        singletonGestorCursos.loginAPI(user, pass, getApplicationContext());
    }

    private boolean isUsernameValido(String username) {
        if(username == null)
            return false;

        return username.length() >= MIN_CHAR;
    }

    private boolean isPasswordValida(String pass) {
        if(pass == null)
            return false;
        return pass.length() >= MAX_CHAR;
    }

    @Override
    public void onUpdateLogin(String token) {
        if(token != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(USER, etUsername.getText().toString());

            SharedPreferences sharedToken  = getSharedPreferences("DADOS_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor  = sharedToken.edit();
            editor.putString("username", etUsername.getText().toString());
            editor.apply();
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Token incorreto", Toast.LENGTH_SHORT).show();
        }

    }
}