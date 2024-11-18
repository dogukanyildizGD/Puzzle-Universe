package com.pu.puzzleuniverse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class FragmentAchievements extends Fragment {

    private RecyclerView rv_achievements;
    private ImageView imageViewBackIcon;
    private AchievementAdapter achievementAdapter;
    private ArrayList<Achievements> achievementsArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_achievements,container,false);

        rv_achievements = view.findViewById(R.id.rv_achievements);
        rv_achievements.setHasFixedSize(true);
        rv_achievements.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        Achievements a1 = new Achievements("5","Login Reward","Every day login and\n" +
                "get reward.","defaultach");
        // 9 - Piece
        Achievements a2 = new Achievements("5","First 9","Complete the first nine\n" +
                "and get your reward","defaultach");
        Achievements a3 = new Achievements("5","Five 9","Complete the 5X nine-piece\n" +
                "and get your reward","bronze");
        Achievements a4 = new Achievements("10","Ten 9","Complete the 10X nine-piece\n" +
                "and get your reward","silver");
        Achievements a5 = new Achievements("15","Fifteen 9","Complete the 15X nine-piece\n" +
                "and get your reward","gold");
        Achievements a6 = new Achievements("20","Twenty 9","Complete the 20X nine-piece\n" +
                "and get your reward","defaultach");

        // 25 - Piece
        Achievements a7 = new Achievements("5","First 25","Complete the first twenty\n" +
                "and get your reward","defaultach");
        Achievements a8 = new Achievements("5","Five 25","Complete the 5X twenty-piece\n" +
                "and get your reward","bronze");
        Achievements a9 = new Achievements("10","Ten 25","Complete the 10X twenty-piece\n" +
                "and get your reward","silver");
        Achievements a10 = new Achievements("15","Fifteen 25","Complete the 15X twenty-piece\n" +
                "and get your reward","gold");
        Achievements a11 = new Achievements("20","Twenty 25","Complete the 20X twenty-piece\n" +
                "and get your reward","defaultach");

        // 100 - Piece
        Achievements a12 = new Achievements("5","First 100","Complete the first hundred\n" +
                "and get your reward","defaultach");
        Achievements a13 = new Achievements("5","Five 100","Complete the 5X hundred-piece\n" +
                "and get your reward","bronze");
        Achievements a14 = new Achievements("10","Ten 100","Complete the 10X hundred-piece\n" +
                "and get your reward","silver");
        Achievements a15 = new Achievements("15","Fifteen 100","Complete the 15X hundred-piece\n" +
                "and get your reward","gold");
        Achievements a16 = new Achievements("20","Twenty 100","Complete the 20X hundred-piece\n" +
                "and get your reward","defaultach");

        // 225 - Piece
        Achievements a17 = new Achievements("5","First 225","Complete the first 225\n" +
                "and get your reward","defaultach");
        Achievements a18 = new Achievements("5","Five 225","Complete the 5X 225-piece\n" +
                "and get your reward","bronze");
        Achievements a19 = new Achievements("10","Ten 225","Complete the 10X 225-piece\n" +
                "and get your reward","silver");
        Achievements a20 = new Achievements("15","Fifteen 225","Complete the 15X 225-piece\n" +
                "and get your reward","gold");
        Achievements a21 = new Achievements("20","Twenty 225","Complete the 20X 225-piece\n" +
                "and get your reward","defaultach");

        achievementsArrayList = new ArrayList<>();

        achievementsArrayList.add(a1);
        achievementsArrayList.add(a2);
        achievementsArrayList.add(a3);
        achievementsArrayList.add(a4);
        achievementsArrayList.add(a5);
        achievementsArrayList.add(a6);
        achievementsArrayList.add(a7);
        achievementsArrayList.add(a8);
        achievementsArrayList.add(a9);
        achievementsArrayList.add(a10);
        achievementsArrayList.add(a11);
        achievementsArrayList.add(a12);
        achievementsArrayList.add(a13);
        achievementsArrayList.add(a14);
        achievementsArrayList.add(a15);
        achievementsArrayList.add(a16);
        achievementsArrayList.add(a17);
        achievementsArrayList.add(a18);
        achievementsArrayList.add(a19);
        achievementsArrayList.add(a20);
        achievementsArrayList.add(a21);

        achievementAdapter = new AchievementAdapter(getActivity(),achievementsArrayList);
        rv_achievements.setAdapter(achievementAdapter);

        return view;
    }
}
