package com.example.kuicly.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.kuicly.R;
import com.example.kuicly.modelo.Licao;

import java.util.ArrayList;

public class ListaLicoesAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Licao> licoes;


    public ListaLicoesAdaptador(Context context, ArrayList<Licao> licoes) {
        this.context = context;
        this.licoes = licoes;
    }

    @Override
    public int getCount() {
        return licoes.size();
    }

    @Override
    public Object getItem(int i) {
        return licoes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return licoes.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } if(view == null){
            view = inflater.inflate(R.layout.item_lista_licao , null);
        }
        //otimização
        ListaLicoesAdaptador.ViewHolderLista viewHolder = (ListaLicoesAdaptador.ViewHolderLista) view.getTag();
        if(viewHolder == null){
            viewHolder = new ListaLicoesAdaptador.ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(licoes.get(position));
        return view;
    }

    private class ViewHolderLista{
        private TextView tvTitle;

        private VideoView videoLesson;

        public ViewHolderLista(View view){
            tvTitle = view.findViewById(R.id.tvTitle);


        }

        //invoca 1 vez por cada linha da lista
        public void update(Licao licao){
            tvTitle.setText(licao.getTitle());


        }
    }
}
