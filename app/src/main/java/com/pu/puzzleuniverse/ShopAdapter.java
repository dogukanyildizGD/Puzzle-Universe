package com.pu.puzzleuniverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.CardViewHolder>{

    private Context context;
    private List<Puzzlelar> listPuzzlelar;

    public ShopAdapter(Context context, List<Puzzlelar> listPuzzlelar) {
        this.context = context;
        this.listPuzzlelar = listPuzzlelar;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewShop;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewShop = itemView.findViewById(R.id.imageViewShop);
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_shop,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        Puzzlelar p = listPuzzlelar.get(position);

        holder.imageViewShop.setImageResource(context.getResources().getIdentifier(p.getPuzzle_category(),"drawable",context.getPackageName()));

    }

    @Override
    public int getItemCount() {
        return listPuzzlelar.size();
    }
}
