package com.pu.puzzleuniverse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class FragmentCategory extends Fragment {

    private RecyclerView rv_category;
    private ArrayList<Puzzlelar> puzzlelarArrayList;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_category,container,false);

        rv_category = view.findViewById(R.id.rv_category);
        rv_category.setHasFixedSize(true);
        rv_category.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        Puzzlelar c1 = new Puzzlelar("cat","cat");
        Puzzlelar c2 = new Puzzlelar("dog","cat");
        Puzzlelar c3 = new Puzzlelar("space","cat");
        Puzzlelar c4 = new Puzzlelar("nature","cat");
        Puzzlelar c5 = new Puzzlelar("coffee","cat");
        Puzzlelar c6 = new Puzzlelar("tea","cat");
        Puzzlelar c7 = new Puzzlelar("butterfly","cat");
        Puzzlelar c8 = new Puzzlelar("horse","cat");
        Puzzlelar c9 = new Puzzlelar("food","cat");
        Puzzlelar c10 = new Puzzlelar("fruit","cat");
        Puzzlelar c11 = new Puzzlelar("bird","cat");
        Puzzlelar c12 = new Puzzlelar("flower","cat");
        Puzzlelar c13 = new Puzzlelar("season","cat");
        Puzzlelar c14 = new Puzzlelar("spor","cat");
        Puzzlelar c15 = new Puzzlelar("bug","cat");
        Puzzlelar c16 = new Puzzlelar("car","cat");

        puzzlelarArrayList = new ArrayList<>();

        puzzlelarArrayList.add(c1);
        puzzlelarArrayList.add(c2);
        puzzlelarArrayList.add(c3);
        puzzlelarArrayList.add(c4);
        puzzlelarArrayList.add(c5);
        puzzlelarArrayList.add(c6);
        puzzlelarArrayList.add(c7);
        puzzlelarArrayList.add(c8);
        puzzlelarArrayList.add(c9);
        puzzlelarArrayList.add(c10);
        puzzlelarArrayList.add(c11);
        puzzlelarArrayList.add(c12);
        puzzlelarArrayList.add(c13);
        puzzlelarArrayList.add(c14);
        puzzlelarArrayList.add(c15);
        puzzlelarArrayList.add(c16);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        categoryAdapter = new CategoryAdapter(getActivity(),puzzlelarArrayList,fm);

        rv_category.setAdapter(categoryAdapter);

        return view;
    }
}
