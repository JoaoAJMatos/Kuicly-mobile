package com.example.kuicly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kuicly.listners.CarrinhoItensListener;
import com.example.kuicly.listners.CarrinhoListener;
import com.example.kuicly.listners.CarrinhoTotalListener;
import com.example.kuicly.modelo.Carrinho;
import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CarrinhoActivity extends AppCompatActivity implements CarrinhoListener, CarrinhoTotalListener {

    public static final String ID_CARRINHO_ITENS = "id";

    public static final String ID_CARRINHO = "id";
    private static final int MIN_CHAR = 3,MIN_NUM = 4;
    private TextView tvTotal;
    private Button btnPagamento;
    public static final int ADD=100,DELETE=300;


    private Carrinho carrinho;

    private CarrinhoItens carrinhoItens;

    public static final String DEFAULT_IMG =
            "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTotal = findViewById(R.id.tvTotal);
        btnPagamento = findViewById(R.id.btnPagamento);


        SingletonGestorCursos.getInstance(getApplicationContext()).setCarrinhoTotalListener(this);
        SingletonGestorCursos.getInstance(getApplicationContext()).setCarrinhoListener(this);



        btnPagamento.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SingletonGestorCursos.getInstance(getApplicationContext()).getPagamentoAPI(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(),FaturaActivity.class);
                startActivity(intent);
            }


        });


        Fragment fragment = new ListaCarrinhoItensFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
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
    public void onRefreshTotal(float total) {
        tvTotal.setText("Total: "+total+"€");
    }
}