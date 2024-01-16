package com.example.kuicly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

        tvTotal = findViewById(R.id.tvTotal);
        btnPagamento = findViewById(R.id.btnPagamento);


        SingletonGestorCursos.getInstance(getApplicationContext()).setCarrinhoTotalListener(this);
        SingletonGestorCursos.getInstance(getApplicationContext()).setCarrinhoListener(this);

       /* int id = getIntent().getIntExtra(ID_CARRINHO,0);
        if (id > 0) {
            carrinho = SingletonGestorCursos.getInstance(getApplicationContext()).getCarrinho(id);//erro getlivrosbd nao faço ideia como é
            if (carrinho != null) {

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

    private void carregarInfoCarrinho() {
        tvTotal.setText("Total: "+carrinho.getTotal()+"€");



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(carrinho!=null){
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
    private void dialogRemover() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Remover Item do Carrinho").setMessage("Tem a certeza que pretende remover o item").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SingletonGestorCursos.getInstance(getApplicationContext()).removerCarrinhoItemAPI(carrinhoItens,getApplicationContext());

                }
            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setIcon(android.R.drawable.ic_delete).show();
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