package com.example.kuicly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
        SingletonGestorCursos.getInstance(getApplicationContext()).setLoginListener(this);
    }

    public void onClickLogin(View view) {
        String user = etUsername.getText().toString();
        String pass = etPassword.getText().toString();

        if(!isUsernameValido(user)) {
            etUsername.setError("Username inválido");
            return;
        }
        if(!isPasswordValida(pass)) {
            etPassword.setError("Password inválida");
            return;
        }
        //Toast.makeText(this,"Auth bem sucedida", Toast.LENGTH_LONG).show();

        //Ex 3.2
        /*Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(EMAIL,email);
        intent.putExtra("NOME","diana");
        startActivity(intent);
        finish();*/
        SingletonGestorCursos.getInstance(this).loginAPI(user, pass,getApplicationContext());
    }

    private boolean isEmailValido(String email){
        if(email == null)
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isUsernameValido(String username) {
        if(username == null)
            return false;
        // Adapte a validação de acordo com os requisitos do seu sistema
        // Por exemplo, verificando o tamanho mínimo ou a presença de caracteres especiais
        return username.length() >= MIN_CHAR; // Defina MIN_CHAR conforme necessário
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
            intent.putExtra(TOKEN, token);
            intent.putExtra(USER, etUsername.getText().toString());
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Token incorreto", Toast.LENGTH_SHORT).show();
        }

    }
}