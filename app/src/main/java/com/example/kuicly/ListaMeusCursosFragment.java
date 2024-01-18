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

import com.example.kuicly.adaptadores.ListaCursosAdaptador;
import com.example.kuicly.adaptadores.ListaMeusCursosAdaptador;
import com.example.kuicly.listners.MeusCursosListener;
import com.example.kuicly.modelo.Curso;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListaMeusCursosFragment extends Fragment implements MeusCursosListener {


    private ListView lvMeusCursos; //objeto gráfico
    private ArrayList<Curso> cursos; //modelo, lista de livros

    private FloatingActionButton fabLista;
    private SearchView searchView;
    public ListaMeusCursosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_meus_cursos , container , false);
        setHasOptionsMenu(true);

        lvMeusCursos = view.findViewById(R.id.lvMeusCursos);
        SingletonGestorCursos.getInstance(getContext()).setMeusCursosListener(this);
        SingletonGestorCursos.getInstance(getContext()).getMeusCursosAPI(getContext());

        //  lvLivros.setAdapter(new ListaLivrosAdaptador(getContext() , livros));
        //click num item da lista
        lvMeusCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext() , DetalhesCursoActivity.class);
                intent.putExtra(DetalhesCursoActivity.ID_CURSO,(int) id);


                //startActivity(intent);
                startActivityForResult(intent , MainActivity.EDIT);

            }
        });

        return view;
    }

    @Override
    public void onRefreshListaMeusCursos(ArrayList<Curso> listaMeusCursos) {
        if(listaMeusCursos!=null){
            lvMeusCursos.setAdapter(new ListaMeusCursosAdaptador(getContext(),listaMeusCursos));
        }
    }
}