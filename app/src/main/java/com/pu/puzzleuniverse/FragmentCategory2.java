package com.pu.puzzleuniverse;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class FragmentCategory2 extends Fragment {

    private RecyclerView rv_category2;
    private String category;
    private ImageView imageViewBackIcon;
    private VeritabaniYardimcisi vt;
    private MediaPlayer mp,mp2;
    private int gemCount;

    private TextView activityTV;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    FirebaseRecyclerOptions<ModelPurchase> options;
    FirebaseRecyclerAdapter<ModelPurchase,ViewHolderCategory> adapterCategory;

    DatabaseGemRoom databaseGemRoom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_category2,container,false);

        activityTV = getActivity().findViewById(R.id.textViewGemCount);

        vt = new VeritabaniYardimcisi(getActivity());
        mp = MediaPlayer.create(getActivity(),R.raw.paperflip2);
        mp2 = MediaPlayer.create(getActivity(),R.raw.tictocclick);

        databaseGemRoom = Room.databaseBuilder(getActivity(),DatabaseGemRoom.class,"gemler").allowMainThreadQueries().build();

        final List<Gem> gemList = databaseGemRoom.daoGem().tumGemler();

        gemCount = gemList.get(0).getGemSayisi();

        Bundle bundle = getArguments();
        category = bundle.getString("puzzleCategory");

        imageViewBackIcon = view.findViewById(R.id.imageViewBackIcon);

        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp2.start();
                getFragmentManager().beginTransaction().remove(FragmentCategory2.this).commit();
            }
        });

        rv_category2 = view.findViewById(R.id.rv_category2);
        rv_category2.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mRef = mFirebaseDatabase.getReference("categories").child(category);

        Query query = mRef.orderByChild("ordernum");

        options = new FirebaseRecyclerOptions.Builder<ModelPurchase>().setQuery(query,ModelPurchase.class).build();

        adapterCategory = new FirebaseRecyclerAdapter<ModelPurchase, ViewHolderCategory>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderCategory holder, int position, @NonNull final ModelPurchase model) {

                isExist(model.image,mRef,holder.imageViewLock);
                holder.imageViewCategory.setEnabled(false);

                if (isVisible(model.image,holder.imageViewLock,holder.imageViewCategory)){
                    holder.imageViewLock.setVisibility(View.VISIBLE);
                }

                holder.imageViewLock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View alertViewCategoryBuy = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_category_buy,null);

                        TextView textViewGemCount = alertViewCategoryBuy.findViewById(R.id.textViewGemCount);
                        ImageView imageViewGelen = alertViewCategoryBuy.findViewById(R.id.imageViewGelen);
                        ImageView imageViewCancel = alertViewCategoryBuy.findViewById(R.id.imageViewCancel);
                        ImageView imageViewBuyButton = alertViewCategoryBuy.findViewById(R.id.imageViewBuyButton);

                        Glide.with(getActivity()).load(model.getImage()).into(imageViewGelen);
                        textViewGemCount.setText(String.valueOf(gemCount));

                        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                        final AlertDialog add = ad.create();
                        add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        add.setView(alertViewCategoryBuy);
                        add.show();

                        imageViewBuyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Gem gem = databaseGemRoom.daoGem().tumGemler().get(0);
                                int kalanGem = gemCount - 50;

                                if (kalanGem >= 0){

                                    gem.setGemSayisi(kalanGem);
                                    databaseGemRoom.daoGem().guncelle(gem);
                                    activityTV.setText(String.valueOf(kalanGem));

                                    PuzzlelarDao puzzlelarDao = new PuzzlelarDao();

                                    ArrayList<PuzzlelarVT> gelenPuzzleListesi = new PuzzlelarDao().tumPuzzlelar(vt);

                                    for (PuzzlelarVT p : gelenPuzzleListesi){

                                        if (p.getPuzzle_link().equals(model.getImage())){

                                            puzzlelarDao.puzzleUpdate(vt,p.getPuzzle_category(),p.getPuzzle_link(),"categoryalindi",p.getPiece9(),p.getPiece25(),p.getPiece100(),p.getPiece225());

                                            //startActivity(new Intent(getActivity(),MenuActivity.class));
                                            FragmentManager fm = getActivity().getSupportFragmentManager();
                                            FragmentTransaction ft = fm.beginTransaction();
                                            FragmentCategory2 category = new FragmentCategory2();

                                            Bundle bundle = new Bundle();
                                            bundle.putString("puzzleCategory", p.getPuzzle_category());

                                            category.setArguments(bundle);
                                            ft.replace(R.id.frameLayoutCategory,category).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                            ft.commit();
                                            add.cancel();

                                        }

                                    }

                                }

                            }
                        });

                        imageViewCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                add.cancel();
                            }
                        });
                    }
                });

                Glide.with(getActivity()).load(model.getImage()).into(holder.imageViewCategory);

                holder.imageViewCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp.start();

                        View alertViewPiece = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_pieces,null);

                        ImageView imageViewPuzzle = alertViewPiece.findViewById(R.id.imageViewPuzzle);
                        final ImageView imageViewPiece9 = alertViewPiece.findViewById(R.id.imageViewPiece9);
                        final ImageView imageViewPiece25 = alertViewPiece.findViewById(R.id.imageViewPiece25);
                        final ImageView imageViewPiece100 = alertViewPiece.findViewById(R.id.imageViewPiece100);
                        final ImageView imageViewPiece225 = alertViewPiece.findViewById(R.id.imageViewPiece225);

                        Glide.with(getActivity()).load(model.getImage()).dontAnimate().into(imageViewPuzzle);

                        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                        final AlertDialog add = ad.create();
                        add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        add.setView(alertViewPiece);
                        imageViewPiece9.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mp2.start();
                                Intent i = new Intent(getActivity(),GameActivity.class);
                                i.putExtra("imgLink",model.image);
                                i.putExtra("puzzlePiece",9);
                                getActivity().finish();
                                startActivity(i);
                                add.cancel();
                            }
                        });
                        imageViewPiece25.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mp2.start();
                                Intent i = new Intent(getActivity(),GameActivity.class);
                                i.putExtra("imgLink",model.image);
                                i.putExtra("puzzlePiece",25);
                                startActivity(i);
                                add.cancel();
                            }
                        });
                        imageViewPiece100.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mp2.start();
                                Intent i = new Intent(getActivity(),GameActivity.class);
                                i.putExtra("imgLink",model.image);
                                i.putExtra("puzzlePiece",100);
                                startActivity(i);
                                add.cancel();
                            }
                        });
                        imageViewPiece225.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mp2.start();
                                Intent i = new Intent(getActivity(),GameActivity.class);
                                i.putExtra("imgLink",model.image);
                                i.putExtra("puzzlePiece",225);
                                startActivity(i);
                                add.cancel();
                            }
                        });
                        add.show();
                    }
                });


            }

            @NonNull
            @Override
            public ViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rv_category,parent,false);
                return new ViewHolderCategory(view);
            }
        };
        adapterCategory.startListening();
        rv_category2.setAdapter(adapterCategory);

        return view;
    }

    public boolean isVisible(String puzzleLink,ImageView imageView,ImageView imageView2) {
        boolean isvisible = false;
        imageView.setVisibility(View.GONE);
        imageView2.setEnabled(true);
        ArrayList<PuzzlelarVT> gelenPuzzleListesi = new PuzzlelarDao().tumPuzzlelar(vt);
        for (PuzzlelarVT p : gelenPuzzleListesi) {
            if (p.getPuzzle_satinal().equals("satinalinmadi") && p.getPuzzle_link().equals(puzzleLink)) {
                isvisible = true;
            }
        }
        return isvisible;
    }

    public boolean isExist(String puzzleLink,DatabaseReference mRef,ImageView imageView) {
        boolean isExist = false;
        PuzzlelarDao puzzlelarDao = new PuzzlelarDao();
        ArrayList<PuzzlelarVT> gelenPuzzleListesi = new PuzzlelarDao().tumPuzzlelar(vt);
        ArrayList<String> arrayListP = new ArrayList<>();
        for (PuzzlelarVT p : gelenPuzzleListesi) {
            String puzzleName = p.getPuzzle_link();
            arrayListP.add(puzzleName);
        }
        if (!arrayListP.contains(puzzleLink)){
            puzzlelarDao.puzzleEkle(vt,mRef.getKey(),puzzleLink,"satinalinmadi","oynanmadi","oynanmadi","oynanmadi","oynanmadi");
            isExist = true;
        }
        return isExist;
    }

}
