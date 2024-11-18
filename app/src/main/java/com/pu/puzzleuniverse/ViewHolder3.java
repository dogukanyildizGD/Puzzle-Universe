package com.pu.puzzleuniverse;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder3 extends RecyclerView.ViewHolder {

    ImageView imageViewPuzzles3;

    public ViewHolder3(@NonNull View itemView) {
        super(itemView);

        imageViewPuzzles3 = itemView.findViewById(R.id.imageViewPuzzles3);

    }
}
