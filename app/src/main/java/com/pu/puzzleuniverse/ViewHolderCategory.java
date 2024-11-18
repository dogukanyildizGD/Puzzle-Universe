package com.pu.puzzleuniverse;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderCategory extends RecyclerView.ViewHolder {

    ImageView imageViewCategory,imageViewLock;

    public ViewHolderCategory(@NonNull View itemView) {
        super(itemView);

        imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
        imageViewLock = itemView.findViewById(R.id.imageViewLock);

    }
}
