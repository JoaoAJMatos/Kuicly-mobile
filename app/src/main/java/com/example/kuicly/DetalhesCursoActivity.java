package com.example.kuicly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kuicly.listners.CursoListener;
import com.example.kuicly.listners.TemCursoListener;
import com.example.kuicly.modelo.Curso;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class DetalhesCursoActivity extends AppCompatActivity implements CursoListener, TemCursoListener {

    public static final String ID_CURSO = "id";
    private static final int MIN_CHAR = 3,MIN_NUM = 4;
    private TextView etTitulo, etDescricao, etSerie, etAno;
    private ImageView imgCapa;
    private FrameLayout contentFragment ;

    private Button btnAddCarrinho;
    public static final String DEFAULT_IMG =
            "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";

    private Curso curso;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_curso);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTitulo = findViewById(R.id.etTitulo);
        etDescricao = findViewById(R.id.etDescricao);
        imgCapa = findViewById(R.id.imgCapa);
        btnAddCarrinho= findViewById(R.id.btnAddCarrinho);
        contentFragment = findViewById(R.id.contentFragment);



        SingletonGestorCursos.getInstance(getApplicationContext()).setCursoListner(this);
        SingletonGestorCursos.getInstance(getApplicationContext()).setTemCursoListener(this);



        int id = getIntent().getIntExtra(ID_CURSO,0);
        if (id > 0) {
            curso = SingletonGestorCursos.getInstance(getApplicationContext()).getCurso(id);
            SingletonGestorCursos.getInstance(getApplicationContext()).temCurso(curso.getId(),getApplicationContext());
            if (curso != null) {
                SharedPreferences sharedCurso = getSharedPreferences("DADOS_CURSO", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedCurso.edit();
                editor.putInt("cursoid", curso.getId());
                editor.apply();
                carregarInfoCurso();
                //fabGuardar.setImageResource(R.drawable.ic_action_guardar);
            }else {
                finish();
            }
        }else {
            setTitle("Adicionar Livro");

        }



        btnAddCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingletonGestorCursos.getInstance(getApplicationContext()).adicionarCarrinhoItemAPI(curso,getApplicationContext());
                showToast("Produto adicionado ao carrinho!");
            }
        });


        Fragment fragment = new ListaLicoesFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    private void carregarInfoCurso() {
        setTitle("Detalhes:"+curso.getTitle());
        etTitulo.setText(curso.getTitle());
        etDescricao.setText(curso.getDescription());
        Glide.with(getApplicationContext())
                .load(curso.getCapa())
                .placeholder(R.drawable.logoipl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefreshDetalhes(int op) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.OP_CODE,op);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onRefreshTemCurso(boolean op) {
        if(op){
            btnAddCarrinho.setVisibility(View.GONE);
            contentFragment.setVisibility(View.VISIBLE);

        }else{
            btnAddCarrinho.setVisibility(View.VISIBLE);
            contentFragment.setVisibility(View.GONE);
        }
    }
}