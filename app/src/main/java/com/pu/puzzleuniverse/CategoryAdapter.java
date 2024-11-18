package com.pu.puzzleuniverse;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CardViewHolder>{

    private Context context;
    private List<Puzzlelar> listPuzzlelar;
    FragmentManager fm;
    private MediaPlayer mp;

    public CategoryAdapter(Context context, List<Puzzlelar> listPuzzlelar,FragmentManager fm) {
        this.context = context;
        this.listPuzzlelar = listPuzzlelar;
        this.fm = fm;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewCategory;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_category,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        mp = MediaPlayer.create(context,R.raw.tictocclick);

        final Puzzlelar p = listPuzzlelar.get(position);

        holder.imageViewCategory.setImageResource(context.getResources().getIdentifier(p.getPuzzle_category(),"drawable",context.getPackageName()));

        holder.imageViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();

                FragmentTransaction ft = fm.beginTransaction();
                FragmentCategory2 category = new FragmentCategory2();

                Bundle bundle = new Bundle();
                bundle.putString("puzzleCategory", p.getPuzzle_category());

                category.setArguments(bundle);
                ft.replace(R.id.frameLayoutCategory,category).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return listPuzzlelar.size();
    }
}
