package com.example.kuicly;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kuicly.listners.FaturaListener;
import com.example.kuicly.listners.PagamentoListener;
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
        SingletonGestorCursos.getInstance(getApplicationContext()).getPagamentoAPI(getApplicationContext(), new PagamentoListener() {
            @Override
            public void onPagamentoSuccess() {
                // Este código será executado após o pagamento ser bem-sucedido
                // Aqui você pode realizar a próxima operação, como carregar o fragmento

                Fragment fragment = new ListaFaturaItensFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
            }

            @Override
            public void onPagamentoFailure(String errorMessage) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SingletonGestorCursos.getInstance(getApplicationContext()).getAllCarrinhoItensAPI(getApplicationContext());
                SingletonGestorCursos.getInstance(getApplicationContext()).getCarrinhoAPI(getApplicationContext());
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