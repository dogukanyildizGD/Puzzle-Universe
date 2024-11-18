package com.pu.puzzleuniverse;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderTwentyPack1 extends RecyclerView.ViewHolder {

    ImageView imageViewPuzzles4;

    public ViewHolderTwentyPack1(@NonNull View itemView) {
        super(itemView);

        imageViewPuzzles4 = itemView.findViewById(R.id.imageViewPuzzles4);

    }
}
