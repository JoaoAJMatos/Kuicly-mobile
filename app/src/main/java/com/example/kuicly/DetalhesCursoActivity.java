package com.example.kuicly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
            //fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }
        /*fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (livro != null) {
                    if(isLivroValido()){
                        livro.setTitulo(etTitulo.getText().toString());
                        livro.setSerie(etSerie.getText().toString());
                        livro.setAutor(etAutor.getText().toString());
                        livro.setAno(Integer.parseInt(etAno.getText().toString()));
                        //SingletonGestorLivros.getInstance(getApplicationContext()).editarLivroBD(livro);
                        SingletonGestorCursos.getInstance(getApplicationContext()).editarLivroAPI(livro,getApplicationContext());

                    }


                }else if(isLivroValido()) {
                    livro = new Livro(0,DEFAULT_IMG, Integer.parseInt(etAno.getText().toString()), etTitulo.getText().toString(), etSerie.getText().toString(), etAutor.getText().toString());
                    SingletonGestorCursos.getInstance(getApplicationContext()).adicionarLivroAPI(livro,getApplicationContext());

                }

            }

        });*/


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

    private boolean isLivroValido() {
        String titulo = etTitulo.getText().toString();
        String descricao = etDescricao.getText().toString();

        if(titulo.length() < MIN_CHAR){
            etTitulo.setError("Titulo tem de ter pelo menos "+MIN_CHAR+" caracteres");
            return false;
        }
       /* if(serie.length() < MIN_CHAR){
            etSerie.setError("Serie tem de ter pelo menos "+MIN_CHAR+" caracteres");
            return false;
        }
        if(autor.length() < MIN_CHAR){
            etAutor.setError("Autor tem de ter pelo menos "+MIN_CHAR+" caracteres");
            return false;
        }
        if(ano.length() != MIN_NUM){
            etAno.setError("Ano tem de ter pelo menos "+MIN_NUM+" caracteres");
            return false;
        }*/
        return true;
    }

    private void carregarInfoCurso() {
        setTitle("Detalhes:"+curso.getTitle());
        etTitulo.setText(curso.getTitle());
        etDescricao.setText(curso.getDescription());
        //etAutor.setText(curso.getPrice() + "");
        //imgCapa.setImageResource(livro.getCapa());
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

    /*private void dialogRemover() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover Livro").setMessage("Tem a certeza que pretende remover o livro").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SingletonGestorCursos.getInstance(getApplicationContext()).removerCursoAPI(curso,getApplicationContext());

            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setIcon(android.R.drawable.ic_delete).show();
    }*/






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
        }else{
            btnAddCarrinho.setVisibility(View.VISIBLE);
        }
    }
}