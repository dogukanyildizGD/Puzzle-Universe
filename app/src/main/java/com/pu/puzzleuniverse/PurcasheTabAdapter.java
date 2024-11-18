package com.pu.puzzleuniverse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PurcasheTabAdapter extends RecyclerView.Adapter<PurcasheTabAdapter.CardViewHolder>{

    private Context context;
    private ArrayList<String> alinanPuzzleList;
    private MediaPlayer mp,mp2;

    public PurcasheTabAdapter(Context context, ArrayList<String> alinanPuzzleList) {
        this.context = context;
        this.alinanPuzzleList = alinanPuzzleList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rv_category,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, final int position) {

        Glide.with(context).load(alinanPuzzleList.get(position)).dontAnimate().into(holder.imageViewCategory);

        mp = MediaPlayer.create(context,R.raw.paperflip2);
        mp2 = MediaPlayer.create(context,R.raw.tictocclick);

        holder.imageViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();

                View alertViewPiece = LayoutInflater.from(context).inflate(R.layout.alert_view_pieces,null);

                ImageView imageViewPuzzle = alertViewPiece.findViewById(R.id.imageViewPuzzle);
                final ImageView imageViewPiece9 = alertViewPiece.findViewById(R.id.imageViewPiece9);
                final ImageView imageViewPiece25 = alertViewPiece.findViewById(R.id.imageViewPiece25);
                final ImageView imageViewPiece100 = alertViewPiece.findViewById(R.id.imageViewPiece100);
                final ImageView imageViewPiece225 = alertViewPiece.findViewById(R.id.imageViewPiece225);

                Glide.with(context).load(alinanPuzzleList.get(position)).dontAnimate().into(imageViewPuzzle);

                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                final AlertDialog add = ad.create();
                add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                add.setView(alertViewPiece);
                imageViewPiece9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp2.start();
                        Intent i = new Intent(context,GameActivity.class);
                        i.putExtra("imgLink",alinanPuzzleList.get(position));
                        i.putExtra("puzzlePiece",9);
                        context.startActivity(i);
                        add.cancel();
                    }
                });
                imageViewPiece25.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp2.start();
                        Intent i = new Intent(context,GameActivity.class);
                        i.putExtra("imgLink",alinanPuzzleList.get(position));
                        i.putExtra("puzzlePiece",25);
                        context.startActivity(i);
                        add.cancel();
                    }
                });
                imageViewPiece100.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp2.start();
                        Intent i = new Intent(context,GameActivity.class);
                        i.putExtra("imgLink",alinanPuzzleList.get(position));
                        i.putExtra("puzzlePiece",100);
                        context.startActivity(i);
                        add.cancel();
                    }
                });
                imageViewPiece225.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp2.start();
                        Intent i = new Intent(context,GameActivity.class);
                        i.putExtra("imgLink",alinanPuzzleList.get(position));
                        i.putExtra("puzzlePiece",225);
                        context.startActivity(i);
                        add.cancel();
                    }
                });
                add.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return alinanPuzzleList.size();
    }
}
