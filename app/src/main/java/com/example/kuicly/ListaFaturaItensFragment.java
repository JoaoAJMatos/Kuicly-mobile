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
import com.example.kuicly.adaptadores.ListaFaturaItensAdaptador;
import com.example.kuicly.listners.FaturaItensListener;
import com.example.kuicly.modelo.Carrinho;
import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.FaturaItens;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListaFaturaItensFragment extends Fragment implements FaturaItensListener {


    private ListView lvFaturaItens; //objeto gráfico
    private ArrayList<FaturaItens> faturaItens; //modelo

    private FloatingActionButton fabLista;
    private SearchView searchView;


    public ListaFaturaItensFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_fatura_itens , container , false);
        setHasOptionsMenu(true);

        lvFaturaItens = view.findViewById(R.id.lvFaturaItens);
        SingletonGestorCursos.getInstance(getContext()).setFaturaListner(this);
        SingletonGestorCursos.getInstance(getContext()).getAllCursosAPI(getContext());

        //  lvLivros.setAdapter(new ListaLivrosAdaptador(getContext() , livros));
        //click num item da lista
        lvFaturaItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext() , DetalhesCursoActivity.class);
                intent.putExtra(DetalhesCursoActivity.ID_CURSO,(int) id);

                //startActivity(intent);
                startActivityForResult(intent , MainActivity.EDIT);

            }
        });
        //click no floating btn
       /* fabLista = view.findViewById(R.id.fabLista);
        fabLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , DetalhesCursoActivity.class);
                //startActivity(intent);
                startActivityForResult(intent , MainActivity.ADD);
            }
        });*/

        return view;
    }
    @Override
    public void onRefreshListaFaturaItens(ArrayList<FaturaItens> listaFaturaItens) {
        if(listaFaturaItens!=null){
            lvFaturaItens.setAdapter(new ListaFaturaItensAdaptador(getContext(),listaFaturaItens));
        }
    }
}