package com.example.kuicly.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kuicly.ListaCarrinhoItensFragment;
import com.example.kuicly.R;
import com.example.kuicly.listners.CarrinhoItemApagadoListener;
import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.Curso;
import com.example.kuicly.modelo.SingletonGestorCursos;

import java.util.ArrayList;

public class ListaCarrinhoItensAdaptador extends BaseAdapter implements CarrinhoItemApagadoListener {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<CarrinhoItens> carrinhoItens;

    private Button btnEliminar;

    public ListaCarrinhoItensAdaptador(Context context, ArrayList<CarrinhoItens> carrinhoItens) {
        this.context = context;
        this.carrinhoItens = carrinhoItens;
    }

    @Override
    public int getCount() {
        return carrinhoItens.size();
    }

    @Override
    public Object getItem(int i) {
        return carrinhoItens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return carrinhoItens.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {



        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } if(view == null){
            view = inflater.inflate(R.layout.item_lista_carrinho_itens , null);
            btnEliminar = view.findViewById(R.id.btnEliminar);
            SingletonGestorCursos.getInstance(context.getApplicationContext()).setCarrinhoItemApagadoListener(this);
        }
        //otimização
        ListaCarrinhoItensAdaptador.ViewHolderLista viewHolder = (ListaCarrinhoItensAdaptador.ViewHolderLista) view.getTag();
        if(viewHolder == null){
            viewHolder = new ListaCarrinhoItensAdaptador.ViewHolderLista(view);
            view.setTag(viewHolder);
        }


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingletonGestorCursos.getInstance(context.getApplicationContext()).removerCarrinhoItemAPI(carrinhoItens.get(position).getCurso_id(),context.getApplicationContext());
                carrinhoItens.remove(position);
                notifyDataSetChanged();


            }
        });

        viewHolder.update(carrinhoItens.get(position));
        return view;
    }
    private void showToast(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private class ViewHolderLista{
        private TextView tvTile , tvPrice ;
        private ImageView imgCapa;

        public ViewHolderLista(View view){
            tvTile = view.findViewById(R.id.tvTitle);
            tvPrice = view.findViewById(R.id.tvPrice);
            imgCapa = view.findViewById(R.id.imgCapa);
        }

        //invoca 1 vez por cada linha da lista
        public void update(CarrinhoItens carrinhoItens){
            tvTile.setText(carrinhoItens.getTitle());
            tvPrice.setText(carrinhoItens.getPrice()+"");
            //imgCapa.setImageResource(curso.getCapa());
            Glide.with(context)
                    .load(carrinhoItens.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
        }
    }

    @Override
    public void onRefreshCarrinhoItemApagado(boolean apagado) {
        if(apagado){
            showToast("Carrrinho Item apagado com sucesso");

        }else {
            showToast("Erro ao apagar");
        }
    }

}
