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
    //private SearchView searchView;
    public ListaLicoesFragment() {
        // Required empty public constructor
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
        //SingletonGestorCursos.getInstance(getContext()).temCurso(cursoid,getContext());

        //  lvLivros.setAdapter(new ListaLivrosAdaptador(getContext() , livros));
        //click num item da lista
        lvLicoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext() , LicaoActivity.class);
                intent.putExtra(LicaoActivity.ID_LICAO,(int) id);
                //startActivity(intent);
                startActivityForResult(intent , MainActivity.EDIT);

            }
        });
        //click no floating btn

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==MainActivity.ADD || requestCode==MainActivity.EDIT){
                SingletonGestorCursos.getInstance(getContext()).getAllCursosAPI(getContext());
               /* livros=SingletonGestorLivros.getInstance(getContext()).getLivrosBD();
                lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(),livros));*/
                switch (requestCode){
                    case MainActivity.ADD: Toast.makeText(getContext(),"Livro adicionado com sucesso",Toast.LENGTH_LONG).show();
                        break;
                    case MainActivity.EDIT:
                        if(data.getIntExtra(MainActivity.OP_CODE,0)==MainActivity.DELETE){
                            Toast.makeText(getContext(),"Livro eliminado com sucesso",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(),"Livro modificado com sucesso",Toast.LENGTH_LONG).show();
                        }

                        break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa , menu);
        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //quando o utilizador submete a pesquisa
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }
            //quando o utilizador escreve
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Curso> tempListaLicoes = new ArrayList<>();
                for(Curso l:SingletonGestorCursos.getInstance(getContext()).getCursosBD()){
                    if(l.getTitle().toLowerCase().contains(newText.toLowerCase()))
                        tempListaLicoes.add(l);
                }
                lvLicoes.setAdapter(new ListaCursosAdaptador(getContext() , tempListaLicoes));
                return true;
            }
        });
        super.onCreateOptionsMenu(menu , inflater);
    }*/

    @Override
    public void onRefreshListaLicoes(ArrayList<Licao> listaLicoes) {
        if(listaLicoes!=null){
            lvLicoes.setAdapter(new ListaLicoesAdaptador(getContext(),listaLicoes));
        }
    }
}