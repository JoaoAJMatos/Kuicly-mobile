package com.example.kuicly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kuicly.listners.LicaoListener;
import com.example.kuicly.modelo.Licao;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LessonActivity extends AppCompatActivity implements LicaoListener {

    public static final String ID_LICAO = "id";
    private static final int MIN_CHAR = 3,MIN_NUM = 4;
    private TextView etTitulo, etContexto;
    private FloatingActionButton fabGuardar;
    private VideoView videoLicao;
    public static final String DEFAULT_IMG =
            "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";

    private Licao licao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        etTitulo = findViewById(R.id.etTitulo);
        etContexto = findViewById(R.id.etContexto);
        //videoLicao = findViewById(R.id.videoLicao);


        SingletonGestorCursos.getInstance(getApplicationContext()).setLicaoListner(this);

        int id = getIntent().getIntExtra(ID_LICAO,0);
        if (id > 0) {
            licao = SingletonGestorCursos.getInstance(getApplicationContext()).getLicao(id);//erro getlivrosbd nao faço ideia como é
            if (licao != null) {
                carregarInfoLicao();
                //fabGuardar.setImageResource(R.drawable.ic_action_guardar);
            }else {
                finish();
            }
        }else {
            setTitle("Adicionar Livro");
            //fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }
    }

    private void carregarInfoLicao() {
        setTitle("Detalhes:"+licao.getTitle());
        etTitulo.setText(licao.getTitle());
        etContexto.setText(licao.getContext());
        //etAutor.setText(curso.getPrice() + "");
        //imgCapa.setImageResource(livro.getCapa());
       /* Glide.with(getApplicationContext())
                .load(licao.getVideo())
                .placeholder(R.drawable.logoipl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(videoLicao);*/

    }

    @Override
    public void onRefreshDetalhes(int op) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.OP_CODE,op);
        setResult(RESULT_OK,intent);
        finish();
    }
}