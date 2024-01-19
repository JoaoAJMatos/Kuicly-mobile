package com.example.kuicly;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kuicly.listners.LicaoListener;
import com.example.kuicly.modelo.Licao;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LicaoActivity extends AppCompatActivity implements LicaoListener {

    public static final String ID_LICAO = "id";
    private static final int MIN_CHAR = 3,MIN_NUM = 4;
    private TextView etTitulo, etContexto;
    private FloatingActionButton fabGuardar;
    private VideoView videoLicao;


    public static final String DEFAULT_IMG =
            "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";

    private Licao licao;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licao);

        mediaController = new MediaController(this);

        etTitulo = findViewById(R.id.etTitulo);
        etContexto = findViewById(R.id.etContexto);
        videoLicao = findViewById(R.id.videoLicao);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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



        Fragment fragment = new ListaLicoesFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
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

        String videoUrl = licao.getVideo(); // Substitua isso pela URL do seu vídeo
        Uri videoUri = Uri.parse(videoUrl);

        videoLicao.setVideoURI(videoUri);

        videoLicao.setMediaController(mediaController);
        mediaController.setAnchorView(videoLicao);


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
}