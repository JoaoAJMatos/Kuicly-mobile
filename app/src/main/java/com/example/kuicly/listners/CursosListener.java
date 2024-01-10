package com.example.kuicly.listners;

import com.example.kuicly.modelo.Curso;

import java.util.ArrayList;

public interface CursosListener {
    void onRefreshListaCursos(ArrayList<Curso> listaCursos);
}
