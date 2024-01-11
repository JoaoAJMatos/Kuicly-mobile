package com.example.kuicly;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kuicly.listners.CursoListener;
import com.example.kuicly.modelo.Curso;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesCursoActivity extends AppCompatActivity implements CursoListener {

    public static final String ID_CURSO = "id";
    private static final int MIN_CHAR = 3,MIN_NUM = 4;
    private TextView etTitulo, etDescricao, etSerie, etAno;
    private FloatingActionButton fabGuardar;
    private ImageView imgCapa;
    public static final String DEFAULT_IMG =
            "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";

    private Curso curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_curso);

        etTitulo = findViewById(R.id.etTitulo);
        etDescricao = findViewById(R.id.etDescricao);
        imgCapa = findViewById(R.id.imgCapa);
        fabGuardar = findViewById(R.id.fabGuardar);

        SingletonGestorCursos.getInstance(getApplicationContext()).setCursoListner(this);

        int id = getIntent().getIntExtra(ID_CURSO,0);
        if (id > 0) {
            curso = SingletonGestorCursos.getInstance(getApplicationContext()).getCurso(id);//erro getlivrosbd nao faço ideia como é
            if (curso != null) {
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


        Fragment fragment = new ListaLicoesFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        if(curso!=null){
            getMenuInflater().inflate(R.menu.menu_remover,menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.itemRemover){
            //dialogRemover();
            //SingletonGestorLivros.getInstance().removerLivro(livro.getId());
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}