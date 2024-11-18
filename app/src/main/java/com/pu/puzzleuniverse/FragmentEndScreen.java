package com.pu.puzzleuniverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class FragmentEndScreen extends Fragment {

    ImageView imageViewFullimage,imageViewHomeicon;
    TextView textViewChroGet,textViewMoveGet;
    String gelenPuzzleLink,chronometer,textViewMove;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.alert_view_game_finish,container,false);

        imageViewFullimage= view.findViewById(R.id.imageViewFullimage);
        imageViewHomeicon = view.findViewById(R.id.imageViewHomeicon);
        textViewChroGet = view.findViewById(R.id.textViewChroGet);
        textViewMoveGet = view.findViewById(R.id.textViewMoveGet);

        Bundle b = getArguments();
        gelenPuzzleLink = b.getString("gelenPuzzleLink");
        textViewMove = b.getString("textViewMove");
        chronometer = b.getString("chronometer");

        textViewChroGet.setText(chronometer);
        textViewMoveGet.setText(textViewMove);

        imageViewHomeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MenuActivity.class));
            }
        });

        Glide.with(getActivity()).load(gelenPuzzleLink).dontAnimate().fitCenter().into(imageViewFullimage);

        return view;
    }
}
