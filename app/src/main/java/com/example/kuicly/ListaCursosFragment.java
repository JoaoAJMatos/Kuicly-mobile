package com.example.kuicly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kuicly.adaptadores.ListaCursosAdaptador;
import com.example.kuicly.listners.CursosListener;
import com.example.kuicly.modelo.Curso;
import com.example.kuicly.modelo.SingletonGestorCursos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaCursosFragment extends Fragment implements CursosListener {

    private ListView lvCursos; //objeto gráfico
    private ArrayList<Curso> cursos; //modelo, lista de livros

    private FloatingActionButton fabLista;
    private SearchView searchView;

    public ListaCursosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_cursos , container , false);
        setHasOptionsMenu(true);

        lvCursos = view.findViewById(R.id.lvCursos);
        SingletonGestorCursos.getInstance(getContext()).setCursosListner(this);
        SingletonGestorCursos.getInstance(getContext()).getAllCursosAPI(getContext());

        //  lvLivros.setAdapter(new ListaLivrosAdaptador(getContext() , livros));
        //click num item da lista
        lvCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    @Override
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
                ArrayList<Curso> tempListaCursos = new ArrayList<>();
                for(Curso l:SingletonGestorCursos.getInstance(getContext()).getCursosBD()){
                    if(l.getTitle().toLowerCase().contains(newText.toLowerCase()))
                        tempListaCursos.add(l);
                }
                lvCursos.setAdapter(new ListaCursosAdaptador(getContext() , tempListaCursos));
                return true;
            }
        });
        super.onCreateOptionsMenu(menu , inflater);
    }

    @Override
    public void onRefreshListaCursos(ArrayList<Curso> listaCursos) {
        if(listaCursos!=null){
            lvCursos.setAdapter(new ListaCursosAdaptador(getContext(),listaCursos));
        }
    }
}