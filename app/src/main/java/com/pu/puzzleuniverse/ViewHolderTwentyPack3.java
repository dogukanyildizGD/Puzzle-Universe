package com.pu.puzzleuniverse;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderTwentyPack3 extends RecyclerView.ViewHolder {

    ImageView imageViewTwentyPack2;

    public ViewHolderTwentyPack3(@NonNull View itemView) {
        super(itemView);

        imageViewTwentyPack2 = itemView.findViewById(R.id.imageViewSound);

    }
}
