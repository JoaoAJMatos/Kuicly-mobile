package com.example.kuicly.listners;

import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.FaturaItens;

import java.util.ArrayList;

public interface FaturaItensListener {
    void onRefreshListaFaturaItens(ArrayList<FaturaItens> listaFaturaItens);
}
