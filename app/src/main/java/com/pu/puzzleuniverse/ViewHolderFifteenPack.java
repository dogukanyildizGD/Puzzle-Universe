package com.pu.puzzleuniverse;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderFifteenPack extends RecyclerView.ViewHolder {

    ImageView imageViewFifteenPack;

    public ViewHolderFifteenPack(@NonNull View itemView) {
        super(itemView);

        imageViewFifteenPack = itemView.findViewById(R.id.imageViewFifteenPack);

    }
}
