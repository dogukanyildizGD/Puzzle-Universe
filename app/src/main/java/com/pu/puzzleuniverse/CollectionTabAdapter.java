package com.pu.puzzleuniverse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CollectionTabAdapter extends RecyclerView.Adapter<CollectionTabAdapter.CardViewHolder>{

    private Context context;
    private ArrayList<String> alinanPuzzleList;
    private MediaPlayer mp,mp2;

    private ArrayList<Integer> arrayListInt;

    public CollectionTabAdapter(Context context, ArrayList<String> alinanPuzzleList) {
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

        final String imgurlink = "https://i.imgur.com/"+alinanPuzzleList.get(position);

        // final Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PuzzleSave/"+alinanPuzzleList.get(position));

        Glide.with(context).load(imgurlink).dontAnimate().into(holder.imageViewCategory);
        mp = MediaPlayer.create(context,R.raw.paperflip2);
        mp2 = MediaPlayer.create(context,R.raw.tictocclick);

        holder.imageViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                arrayListInt = new ArrayList<>();
                puzzleKayıtGetir(position);
                Intent i = new Intent(context,GameActivity.class);
                i.putExtra("imgLink",alinanPuzzleList.get(position));
                i.putExtra("puzzlePiece",arrayListInt.get(0));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return alinanPuzzleList.size();
    }

    public void puzzleKayıtGetir(int position){
        try {
            String dosyaYolu = Environment.getExternalStorageDirectory()+"/PuzzleSave/";
            File dosya = new File(dosyaYolu,alinanPuzzleList.get(position)+".txt");
            FileReader fileReader = new FileReader(dosya);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();

            String satir = "";

            while ((satir = bufferedReader.readLine()) != null){
                stringBuilder.append(satir);
            }

            String[] sbBölme = stringBuilder.toString().split("/");

            arrayListInt.add(sbBölme.length);

            Log.e("sbBölmeSize : ", String.valueOf(sbBölme.length));

            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
