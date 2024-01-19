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

import com.example.kuicly.adaptadores.ListaCarrinhoItensAdaptador;
import com.example.kuicly.adaptadores.ListaCursosAdaptador;
import com.example.kuicly.listners.CarrinhoItensListener;
import com.example.kuicly.modelo.Carrinho;
import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.Curso;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListaCarrinhoItensFragment extends Fragment implements CarrinhoItensListener {


    private ListView lvCarrinhoItens; //objeto gráfico
    private CarrinhoItens carrinhoItens; //modelo

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
        SingletonGestorCursos.getInstance(getContext()).setCarrinhoItensListener(this);
        SingletonGestorCursos.getInstance(getContext()).getAllCarrinhoItensAPI(getContext());


        return view;
    }


    @Override
    public void onRefreshListaCarrinhoItens(ArrayList<CarrinhoItens> listaCarrinhoItens) {
        if(listaCarrinhoItens!=null){
            lvCarrinhoItens.setAdapter(new ListaCarrinhoItensAdaptador(getContext(),listaCarrinhoItens));
        }
    }
}