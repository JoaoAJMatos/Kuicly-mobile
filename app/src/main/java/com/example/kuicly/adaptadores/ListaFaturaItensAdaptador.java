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
import com.example.kuicly.modelo.CarrinhoItens;
import com.example.kuicly.modelo.FaturaItens;

import java.util.ArrayList;

public class ListaFaturaItensAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<FaturaItens> faturaItens;

    public ListaFaturaItensAdaptador(Context context, ArrayList<FaturaItens> faturaItens) {
        this.context = context;
        this.faturaItens = faturaItens;
    }

    @Override
    public int getCount() {
        return faturaItens.size();
    }

    @Override
    public Object getItem(int i) {
        return faturaItens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return faturaItens.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } if(view == null){
            view = inflater.inflate(R.layout.item_lista_fatura_itens , null);
        }
        //otimização
        ListaFaturaItensAdaptador.ViewHolderLista viewHolder = (ListaFaturaItensAdaptador.ViewHolderLista) view.getTag();
        if(viewHolder == null){
            viewHolder = new ListaFaturaItensAdaptador.ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(faturaItens.get(position));
        return view;
    }

    private class ViewHolderLista{
        private TextView tvTile , tvPrice , tvIvaPrice;

        public ViewHolderLista(View view){
            tvTile = view.findViewById(R.id.tvTitle);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvIvaPrice = view.findViewById(R.id.tvIvaPrice);

        }

        //invoca 1 vez por cada linha da lista
        public void update(FaturaItens faturaItens){
            tvTile.setText(faturaItens.getTitle());
            tvPrice.setText(faturaItens.getPrice()+"");
            tvIvaPrice.setText(faturaItens.getIva_price()+"");
        }
    }

}
