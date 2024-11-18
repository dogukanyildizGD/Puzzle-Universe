package com.pu.puzzleuniverse;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder2 extends RecyclerView.ViewHolder {

    ImageView imageViewPuzzles2;

    public ViewHolder2(@NonNull View itemView) {
        super(itemView);

        imageViewPuzzles2 = itemView.findViewById(R.id.imageViewPuzzles2);

    }
}
