package com.example.kuicly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String user;
    private FragmentManager fragmentManager;

    public static final String USER="USER";
    public static final int ADD=100, EDIT=200, DELETE=300;
    public static final String OP_CODE="op_detalhes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        carregarCabecalho();

        navigationView.setNavigationItemSelectedListener(this);  // fica à escuta de cliques (fragmentos tipo menu)
        fragmentManager = getSupportFragmentManager();
        //Precisa de ser inicializado fragment manager antes da função
        carregarFragmentoInicial(); //Fica nesta linha poisnão faria sentido forçar um click se ainda não está à escuta dos mesmos
    }

    private boolean carregarFragmentoInicial() {
        Menu menu = navigationView.getMenu();
        //MenuItem item = menu.getItem(0);
        MenuItem item = menu.findItem(R.id.navCurso);
        item.setChecked(true);
        return onNavigationItemSelected(item);
    }

    private void carregarCabecalho() {
        Intent intent = getIntent();
        user=intent.getStringExtra(LoginActivity.USER);
        SharedPreferences sharedPreferencesUser = getSharedPreferences("DADOS", Context.MODE_PRIVATE);

        if(user!=null) {
            SharedPreferences.Editor editorUser = sharedPreferencesUser.edit();
            editorUser.putString(USER, user);
            editorUser.apply();
        } else {
            user = sharedPreferencesUser.getString(USER,"Email não encontrado");

        }
        View hView = navigationView.getHeaderView(0);
        TextView tvEmail = hView.findViewById(R.id.tvUser);
        tvEmail.setText(user);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        if(item.getItemId()==R.id.navCurso) {

            fragment = new ListaCursosFragment();
            setTitle(item.getTitle());
        }else if(item.getItemId()==R.id.navCarrinho) {
            SingletonGestorCursos.getInstance(getApplicationContext()).getCarrinhoAPI(getApplicationContext());
            fragment = new ListaCarrinhoItensFragment();
            setTitle(item.getTitle());


        }else if(item.getItemId()==R.id.navMeusCursos) {
            SingletonGestorCursos.getInstance(getApplicationContext()).getMeusCursosAPI(getApplicationContext());
            fragment = new ListaMeusCursosFragment();
            setTitle(item.getTitle());
        }
        else if(item.getItemId()== R.id.navLogout) {
            SharedPreferences sharedPreferencesEmailUser = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorUser = sharedPreferencesEmailUser.edit();
            editorUser.clear();
            editorUser.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START); //após a ação ele fecha logo o menu/fragmento Timed out after 300seconds waiting for emulator to come online.
        return true;
    }


}