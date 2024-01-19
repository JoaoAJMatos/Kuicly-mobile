package com.example.kuicly;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kuicly.listners.FaturaListener;
import com.example.kuicly.modelo.Carrinho;
import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.Fatura;
import com.example.kuicly.modelo.FaturaItens;
import com.example.kuicly.modelo.SingletonGestorCursos;

public class FaturaActivity extends AppCompatActivity implements FaturaListener {
    public static final String ID_FATURA_ITENS = "id";

    public static final String ID_FATURA = "id";
    private static final int MIN_CHAR = 3,MIN_NUM = 4;
    private TextView tvTotal,tvIvaTotal,tvSubTotal;

    private Fatura fatura;

    private FaturaItens faturaItens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatura);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTotal = findViewById(R.id.tvTotal);
        tvIvaTotal = findViewById(R.id.tvIvaTotal);
        tvSubTotal = findViewById(R.id.tvSubTotal);



        SingletonGestorCursos.getInstance(getApplicationContext()).setFaturaListener(this);

       /* int id = getIntent().getIntExtra(ID_FATURA,0);
        if (id > 0) {
            fatura = SingletonGestorCursos.getInstance(getApplicationContext()).getFatura(id);//erro getlivrosbd nao faço ideia como é
            if (fatura != null) {
                carregarInfoFatura();
                //fabGuardar.setImageResource(R.drawable.ic_action_guardar);
            }else {
                finish();
            }
        }else {
            setTitle("Adicionar Livro");
            //fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }*/
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


        Fragment fragment = new ListaFaturaItensFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
    }
    /*private void carregarInfoFatura() {

        tvTotal.setText("Total: "+fatura.getTotal_price()+"€");
       tvIvaTotal.setText("Iva Total: "+fatura.getTotaliva()+"€");
        tvSubTotal.setText("SubTotal: "+fatura.getSubtotal()+"€");


    }*/
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
    public void onRefreshDetalhes(float total,float subtotal,float ivatotal) {
        tvTotal.setText("Total: "+total+"€");
        tvIvaTotal.setText("Iva Total: "+ivatotal+"€");
        tvSubTotal.setText("SubTotal: "+subtotal+"€");
    }
}