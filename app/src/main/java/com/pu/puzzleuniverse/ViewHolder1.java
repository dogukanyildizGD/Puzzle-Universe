package com.pu.puzzleuniverse;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder1 extends RecyclerView.ViewHolder {

    ImageView imageViewPuzzles;

    public ViewHolder1(@NonNull View itemView) {
        super(itemView);

        imageViewPuzzles = itemView.findViewById(R.id.imageViewPuzzles);

    }
}
