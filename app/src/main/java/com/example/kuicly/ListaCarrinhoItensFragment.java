package com.example.kuicly;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.kuicly.adaptadores.ListaCarrinhoItensAdaptador;
import com.example.kuicly.adaptadores.ListaCursosAdaptador;
import com.example.kuicly.listners.CarrinhoItensListener;
import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.Curso;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListaCarrinhoItensFragment extends Fragment implements CarrinhoItensListener {


    private ListView lvCarrinhoItens; //objeto gráfico
    private ArrayList<CarrinhoItens> carrinhoItens; //modelo, lista de livros

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

        //  lvLivros.setAdapter(new ListaLivrosAdaptador(getContext() , livros));
        //click num item da lista
        lvCarrinhoItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext() , DetalhesCursoActivity.class);
                intent.putExtra(CarrinhoActivity.ID_CARRINHO_ITENS,(int) id);
                //startActivity(intent);
                startActivityForResult(intent , MainActivity.EDIT);

            }
        });
        //click no floating btn
        fabLista = view.findViewById(R.id.fabLista);
        fabLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , DetalhesCursoActivity.class);
                //startActivity(intent);
                startActivityForResult(intent , MainActivity.ADD);
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
}