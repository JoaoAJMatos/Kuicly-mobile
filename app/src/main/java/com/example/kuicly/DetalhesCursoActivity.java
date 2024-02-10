package com.example.kuicly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kuicly.listners.CursoListener;
import com.example.kuicly.listners.FavoritoListner;
import com.example.kuicly.listners.TemCursoCarrinhoListener;
import com.example.kuicly.listners.TemCursoListener;
import com.example.kuicly.modelo.Curso;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.example.kuicly.utils.CarrinhoItensJsonParser;
import com.example.kuicly.utils.CursoJsonParser;
import com.google.android.material.navigation.NavigationView;

public class DetalhesCursoActivity extends AppCompatActivity implements CursoListener, TemCursoListener, FavoritoListner, TemCursoCarrinhoListener {

    public static final String ID_CURSO = "id";
    private static final int MIN_CHAR = 3,MIN_NUM = 4;
    private TextView etTitulo, etDescricao, etLicao;
    private ImageView imgCapa;
    private FrameLayout contentFragment ;

    private Button btnAddCarrinho,btnAddFavorito;
    public static final String DEFAULT_IMG =
            "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";

    public static final int ADD=100,DELETE=300;

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
        btnAddFavorito = findViewById(R.id.btnAddFavorito);
        contentFragment = findViewById(R.id.contentFragment);
        etLicao = findViewById(R.id.etLicao);



        SingletonGestorCursos.getInstance(getApplicationContext()).setCursoListner(this);
        SingletonGestorCursos.getInstance(getApplicationContext()).setTemCursoListener(this);
        SingletonGestorCursos.getInstance(getApplicationContext()).setFavoritoListner(this);
        SingletonGestorCursos.getInstance(getApplicationContext()).setTemCursoCarrinhoListener(this);




        int id = getIntent().getIntExtra(ID_CURSO,0);
        if (id > 0) {
            curso = SingletonGestorCursos.getInstance(getApplicationContext()).getCurso(id);
            SingletonGestorCursos.getInstance(getApplicationContext()).temCurso(curso.getId(),getApplicationContext());
            SingletonGestorCursos.getInstance(getApplicationContext()).temFavorito(curso.getId(),getApplicationContext());
            SingletonGestorCursos.getInstance(getApplicationContext()).temCursoCarrinho(curso.getId(),getApplicationContext());
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


        if(!CursoJsonParser.isConnectionInternet(getApplicationContext())){
            btnAddCarrinho.setVisibility(View.GONE);
            btnAddFavorito.setVisibility(View.GONE);
            etLicao.setVisibility(View.GONE);

        }


        btnAddCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingletonGestorCursos.getInstance(getApplicationContext()).adicionarCarrinhoItemAPI(curso,getApplicationContext());


            }
        });

        btnAddFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingletonGestorCursos.getInstance(getApplicationContext()).adicionarFavoritoAPI(curso,getApplicationContext());
                SingletonGestorCursos.getInstance(getApplicationContext()).getAllCursosAPI(getApplicationContext());


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
            etLicao.setVisibility(View.VISIBLE);

        }else{
            btnAddCarrinho.setVisibility(View.VISIBLE);
            contentFragment.setVisibility(View.GONE);
            etLicao.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshFavorito(boolean op) {
        if(op){
            btnAddFavorito.setText("Remover dos favoritos");
        }else{
            btnAddFavorito.setText("Adicionar aos favoritos");
        }

    }

    @Override
    public void onRefreshAddCarrinho(boolean op) {
        if(op){
            btnAddCarrinho.setText("Curso adicionado");
        }else{
            btnAddCarrinho.setText("Adicionar ao carrinho");

        }
    }
}