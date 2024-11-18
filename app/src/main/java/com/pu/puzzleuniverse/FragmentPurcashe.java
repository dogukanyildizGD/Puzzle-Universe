package com.pu.puzzleuniverse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class FragmentPurcashe extends Fragment {

    private ArrayList<PuzzlelarVT> tumPuzzleList;
    private ArrayList<String> alinanPuzzleList;
    private VeritabaniYardimcisi vt;
    private RecyclerView rv_purcashe_tab;
    private PurcasheTabAdapter purcasheTabAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_purcashe_tab,container,false);

        rv_purcashe_tab = view.findViewById(R.id.rv_purcashe_tab);
        rv_purcashe_tab.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rv_purcashe_tab.setHasFixedSize(true);

        vt = new VeritabaniYardimcisi(getActivity());

        tumPuzzleList = new PuzzlelarDao().tumPuzzlelar(vt);

        alinanPuzzleList = new ArrayList<>();

        for (PuzzlelarVT p: tumPuzzleList){

            String gelenDurum = p.getPuzzle_satinal();

            if (gelenDurum.equals("satinalindi")){
                alinanPuzzleList.add(p.getPuzzle_link());
            }

        }

        purcasheTabAdapter = new PurcasheTabAdapter(getActivity(),alinanPuzzleList);
        rv_purcashe_tab.setAdapter(purcasheTabAdapter);

        return view;
    }
}
