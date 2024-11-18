package com.pu.puzzleuniverse;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class FragmentSettings extends Fragment {

    private ImageView imageViewSound,imageViewAchievements,imageViewRateUs,imageViewShareUs,imageViewLanguage,imageViewFacebook,imageViewinstagram;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_settings,container,false);

        imageViewAchievements = view.findViewById(R.id.imageViewAchievements);
        imageViewRateUs = view.findViewById(R.id.imageViewRateUs);
        imageViewShareUs = view.findViewById(R.id.imageViewShareUs);
        imageViewLanguage = view.findViewById(R.id.imageViewLanguage);
        imageViewFacebook = view.findViewById(R.id.imageViewFacebook);
        imageViewinstagram = view.findViewById(R.id.imageViewinstagram);

        imageViewAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentAchievements()).commit();

            }
        });

        return view;

    }
}
