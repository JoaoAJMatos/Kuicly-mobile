package com.example.kuicly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kuicly.adaptadores.ListaLicoesAdaptador;
import com.example.kuicly.listners.LicoesListener;
import com.example.kuicly.modelo.Licao;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListaLicoesFragment extends Fragment implements LicoesListener {

    private ListView lvLicoes; //objeto gráfico
    private ArrayList<Licao> licoes; //modelo, lista de livros

    private FloatingActionButton fabLista;

    public ListaLicoesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_licoes , container , false);
        setHasOptionsMenu(true);

        SharedPreferences sharedCurso = getActivity().getSharedPreferences("DADOS_CURSO", Context.MODE_PRIVATE);
        int cursoid = sharedCurso.getInt("cursoid", 0);

        lvLicoes = view.findViewById(R.id.lvLicoes);
        SingletonGestorCursos.getInstance(getContext()).setLicoesListner(this);
        SingletonGestorCursos.getInstance(getContext()).getAllLicoesAPI(cursoid,getContext());

        lvLicoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext() , LicaoActivity.class);
                intent.putExtra(LicaoActivity.ID_LICAO,(int) id);
                //startActivity(intent);
                startActivityForResult(intent , MainActivity.EDIT);

            }
        });


        return view;
    }




    @Override
    public void onRefreshListaLicoes(ArrayList<Licao> listaLicoes) {
        if(listaLicoes!=null){
            lvLicoes.setAdapter(new ListaLicoesAdaptador(getContext(),listaLicoes));
        }
    }
}