package com.pu.puzzleuniverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.CardViewHolder>{

    private Context context;
    private List<Achievements> achievementsList;

    DatabaseGemRoom databaseGemRoom;
    private Gem gem;
    private  Achievements ach;

    public AchievementAdapter(Context context, List<Achievements> achievementsList) {
        this.context = context;
        this.achievementsList = achievementsList;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewGemCount,textViewGemTitle,textViewAchContent;
        private ImageView imageViewAchWindow,imageViewClosed,imageViewDone;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewGemCount = itemView.findViewById(R.id.textViewGemCount);
            textViewGemTitle = itemView.findViewById(R.id.textViewGemTitle);
            textViewAchContent = itemView.findViewById(R.id.textViewAchContent);
            imageViewAchWindow = itemView.findViewById(R.id.imageViewAchWindow);
            imageViewClosed = itemView.findViewById(R.id.imageViewClosed);
            imageViewDone = itemView.findViewById(R.id.imageViewDone);

        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_achievements,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, int position) {

        databaseGemRoom = Room.databaseBuilder(context,DatabaseGemRoom.class,"gemler").allowMainThreadQueries().build();
        gem = databaseGemRoom.daoGem().tumGemler().get(0);

        ach = achievementsList.get(position);

        holder.textViewGemCount.setText(ach.getRewardCount());
        holder.textViewGemTitle.setText(ach.getAchTitle());
        holder.textViewAchContent.setText(ach.getAchContent());

        if (isVisible(position)){
            holder.imageViewDone.setVisibility(View.VISIBLE);
            holder.imageViewClosed.setVisibility(View.INVISIBLE);
        }else {
            holder.imageViewDone.setVisibility(View.INVISIBLE);
            holder.imageViewClosed.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return achievementsList.size();
    }

    public boolean isVisible(int position) {
        boolean isvisible = false;
        if (gem.getFinishPuzzle9() >= 1 && position == 1){
            isvisible = true;
        }
        return isvisible;
    }
}
