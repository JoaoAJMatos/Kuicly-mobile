package com.example.kuicly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.kuicly.adaptadores.ListaCarrinhoItensAdaptador;
import com.example.kuicly.adaptadores.ListaCursosAdaptador;
import com.example.kuicly.listners.CarrinhoItensListener;
import com.example.kuicly.listners.CarrinhoTotalListener;
import com.example.kuicly.modelo.Carrinho;
import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.Curso;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.example.kuicly.utils.CursoJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListaCarrinhoItensFragment extends Fragment implements CarrinhoItensListener, CarrinhoTotalListener {


    private ListView lvCarrinhoItens; //objeto gráfico
    private CarrinhoItens carrinhoItens; //modelo
    private Button btnPagamento;
    private TextView tvTotal;
    private FloatingActionButton fabLista;
    private SearchView searchView;




    public ListaCarrinhoItensFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_carrinho_itens , container , false);
        setHasOptionsMenu(true);



        lvCarrinhoItens = view.findViewById(R.id.lvCarrinhoItens);
        btnPagamento = view.findViewById(R.id.btnPagamento);
        tvTotal = view.findViewById(R.id.tvTotal);
        SingletonGestorCursos.getInstance(getContext()).setCarrinhoItensListener(this);
        SingletonGestorCursos.getInstance(getContext()).getAllCarrinhoItensAPI(getContext());
        SingletonGestorCursos.getInstance(getContext()).setCarrinhoTotalListener(this);

        if(!CursoJsonParser.isConnectionInternet(getContext())){
            btnPagamento.setVisibility(View.GONE);

        }
        btnPagamento.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SingletonGestorCursos.getInstance(getContext()).getPagamentoAPI(getContext());
                Intent intent = new Intent(getContext(),FaturaActivity.class);
                startActivity(intent);
            }


        });

        return view;
    }


    @Override
    public void onRefreshListaCarrinhoItens(ArrayList<CarrinhoItens> listaCarrinhoItens) {
        if(listaCarrinhoItens!=null){
            lvCarrinhoItens.setAdapter(new ListaCarrinhoItensAdaptador(getContext(),listaCarrinhoItens));
        }
    }

    @Override
    public void onRefreshTotal(float total) {
        tvTotal.setText("Total: "+total+"€");
    }
}