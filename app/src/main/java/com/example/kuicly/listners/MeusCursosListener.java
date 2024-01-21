package com.example.kuicly.listners;

import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.Curso;

import java.util.ArrayList;

public interface MeusCursosListener {
    void onRefreshListaMeusCursos(ArrayList<Curso> listaMeusCursos);
}
