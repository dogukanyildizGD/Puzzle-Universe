package com.pu.puzzleuniverse;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderTwentyPack2 extends RecyclerView.ViewHolder {

    ImageView imageViewPuzzles6;

    public ViewHolderTwentyPack2(@NonNull View itemView) {
        super(itemView);

        imageViewPuzzles6 = itemView.findViewById(R.id.imageViewPuzzles6);

    }
}
