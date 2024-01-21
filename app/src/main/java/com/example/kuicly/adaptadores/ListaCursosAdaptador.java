package com.example.kuicly.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kuicly.R;
import com.example.kuicly.modelo.Curso;

import java.util.ArrayList;


public class ListaCursosAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Curso> cursos;


    public ListaCursosAdaptador(Context context, ArrayList<Curso> cursos) {
        this.context = context;
        this.cursos = cursos;
    }

    @Override
    public int getCount() {
        return cursos.size();
    }

    @Override
    public Object getItem(int i) {
        return cursos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return cursos.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } if(view == null){
            view = inflater.inflate(R.layout.item_lista_curso , null);
        }
        //otimização
        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null){
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(cursos.get(position));
        return view;
    }

    private class ViewHolderLista{
        private TextView tvTile , tvDescription , tvPrice , tvSkill_level;
        private ImageView imgCapa;

        public ViewHolderLista(View view){
            tvTile = view.findViewById(R.id.tvTitle);
            tvDescription = view.findViewById(R.id.tvDescription);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvSkill_level = view.findViewById(R.id.tvSkill_level);
            imgCapa = view.findViewById(R.id.imgCapa);
        }

        //invoca 1 vez por cada linha da lista
        public void update(Curso curso){
            tvTile.setText(curso.getTitle());
            tvDescription.setText(curso.getDescription());
            tvPrice.setText(curso.getPrice()+"");
            tvSkill_level.setText(curso.getSkill_level()+"");
            //imgCapa.setImageResource(curso.getCapa());
            Glide.with(context)
                    .load(curso.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
        }
    }
}
