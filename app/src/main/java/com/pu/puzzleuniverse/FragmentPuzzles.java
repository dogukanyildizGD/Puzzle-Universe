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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentPuzzles extends Fragment {

    private RecyclerView rv_dfg,rv_2,rv_3,rv_4;
    private VeritabaniYardimcisi vt;
    private MediaPlayer mp,mp2;
    private int gemCount;
    private Gem gem;

    private TextView activityTV;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef,mRef1,mRef2,mRef3,mReftwentypercent1,mReftwentypercent2,mRefFifteenPack,mReftwentypercentPackSort,mRefBuy,mRefBuy2,mRefBuy3;
    FirebaseRecyclerOptions<ModelPurchase> options;
    FirebaseRecyclerAdapter<ModelPurchase,ViewHolder1> adapter1;
    FirebaseRecyclerAdapter<ModelPurchase,ViewHolder2> adapter2;
    FirebaseRecyclerAdapter<ModelPurchase,ViewHolder3> adapter3;
    FirebaseRecyclerAdapter<ModelPurchase,ViewHolderFifteenPack> adapterFifteenPack;
    FirebaseRecyclerAdapter<ModelPurchase,ViewHolderTwentyPack1> adapterTwenty1;
    FirebaseRecyclerAdapter<ModelPurchase,ViewHolderTwentyPack2> adapterTwenty2;
    FirebaseRecyclerAdapter<ModelPurchase,ViewHolderTwentyPack3> adapterTwenty3;

    DatabaseGemRoom databaseGemRoom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_layout_puzzles,container,false);

        activityTV = getActivity().findViewById(R.id.textViewGemCount);

        rv_dfg = view.findViewById(R.id.rv_dfg);
        rv_2 = view.findViewById(R.id.rv_2);
        rv_3 = view.findViewById(R.id.rv_3);
        rv_4 = view.findViewById(R.id.rv_4);

        rv_dfg.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        rv_2.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        rv_3.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        rv_4.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        vt = new VeritabaniYardimcisi(getActivity());
        mp = MediaPlayer.create(getActivity(),R.raw.paperflip2);
        mp2 = MediaPlayer.create(getActivity(),R.raw.tictocclick);

        databaseGemRoom = Room.databaseBuilder(getActivity(),DatabaseGemRoom.class,"gemler").allowMainThreadQueries().build();

        gem = databaseGemRoom.daoGem().tumGemler().get(0);
        gemCount = gem.getGemSayisi();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("users");

        /*Map<String,Object> values = new HashMap<>();

        values.put("gemCount",String.valueOf(gemCount));

        mRef.child(gem.getUserKey()).updateChildren(values);*/

        ArrayList<PuzzlelarVT> gelenPuzzleListesi = new PuzzlelarDao().tumPuzzlelar(vt);

        for (PuzzlelarVT p : gelenPuzzleListesi){

            Log.e("gelenPuzzleListesi : ", p.getPuzzle_id()+" "+p.getPuzzle_category()+" "+p.getPuzzle_link()
                    +" "+p.getPuzzle_satinal()+" "+p.getPiece9()+" "+p.getPiece25()+" "+p.getPiece100()+" "+p.getPiece225());

        }

        // Slider 1
        mRef1 = mFirebaseDatabase.getReference("purchases").child("dailyfreegifts");

        options = new FirebaseRecyclerOptions.Builder<ModelPurchase>().setQuery(mRef1,ModelPurchase.class).build();

        adapter1 = new FirebaseRecyclerAdapter<ModelPurchase, ViewHolder1>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder1 holder, int position, @NonNull final ModelPurchase model) {

                View alertViewFree = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_free,null);

                isExist(model.image,mRef1);

                ImageView imageViewCancel = alertViewFree.findViewById(R.id.imageViewCancel);
                ImageView imageViewGelen = alertViewFree.findViewById(R.id.imageViewGelen);
                final ImageView imageViewBuyButton = alertViewFree.findViewById(R.id.imageViewBuyButton);
                TextView textViewGemCount = alertViewFree.findViewById(R.id.textViewGemCount);

                textViewGemCount.setText(String.valueOf(gemCount));

                Glide.with(getActivity()).load(model.getImage()).dontAnimate().into(holder.imageViewPuzzles);
                Glide.with(getActivity()).load(model.getImage()).dontAnimate().fitCenter().into(imageViewGelen);

                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                final AlertDialog add = ad.create();
                add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                add.setView(alertViewFree);
                add.setCancelable(false);
                holder.imageViewPuzzles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        add.show();
                        mp.start();

                        imageViewBuyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Gem gem = databaseGemRoom.daoGem().tumGemler().get(0);
                                int kalanGem = gem.getGemSayisi() - 40;

                                if (kalanGem >=  0){
                                    add.cancel();
                                    View alertViewTamamlandi = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_buy_completed,null);

                                    ImageView imageViewCancel2 = alertViewTamamlandi.findViewById(R.id.imageViewCancel);
                                    TextView textViewGemCount2 = alertViewTamamlandi.findViewById(R.id.textViewGemCount);

                                    ifBuyTekil(model.image);

                                    gem.setGemSayisi(kalanGem);
                                    databaseGemRoom.daoGem().guncelle(gem);
                                    activityTV.setText(String.valueOf(kalanGem));
                                    Toast.makeText(getActivity(), "Satın Alındı.", Toast.LENGTH_SHORT).show();

                                    textViewGemCount2.setText(String.valueOf(kalanGem));

                                    AlertDialog.Builder ad2 = new AlertDialog.Builder(getActivity());
                                    final AlertDialog add2 = ad2.create();
                                    add2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    add2.setView(alertViewTamamlandi);
                                    add2.show();

                                    imageViewCancel2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            add2.cancel();
                                            mp2.start();
                                        }
                                    });

                                }else {

                                    View alertViewFailed = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_buy_failed,null);

                                    ImageView imageViewCancel2 = alertViewFailed.findViewById(R.id.imageViewCancel);
                                    TextView textViewGemCount2 = alertViewFailed.findViewById(R.id.textViewGemCount);
                                    textViewGemCount2.setText(String.valueOf(gemCount));

                                    AlertDialog.Builder ad2 = new AlertDialog.Builder(getActivity());
                                    final AlertDialog add2 = ad2.create();
                                    add2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    add2.setView(alertViewFailed);
                                    add2.show();

                                    imageViewCancel2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            add2.cancel();
                                            mp2.start();
                                        }
                                    });

                                }

                            }
                        });


                    }
                });
                imageViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add.cancel();
                        mp2.start();
                    }
                });

            }

            @NonNull
            @Override
            public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rv1,parent,false);
                return new ViewHolder1(view);
            }
        };
        adapter1.startListening();
        rv_dfg.setAdapter(adapter1);

        // Slider 2
        mRef2 = mFirebaseDatabase.getReference("purchases").child("tenpercent");

        options = new FirebaseRecyclerOptions.Builder<ModelPurchase>().setQuery(mRef2,ModelPurchase.class).build();

        adapter2 = new FirebaseRecyclerAdapter<ModelPurchase, ViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder2 holder, int position, @NonNull final ModelPurchase model) {

                View alertViewFree = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_tenpercent,null);

                isExist(model.image,mRef2);

                ImageView imageViewCancel = alertViewFree.findViewById(R.id.imageViewCancel);
                ImageView imageViewGelen = alertViewFree.findViewById(R.id.imageViewGelen);
                final ImageView imageViewBuyButton = alertViewFree.findViewById(R.id.imageViewBuyButton);
                TextView textViewGemCount = alertViewFree.findViewById(R.id.textViewGemCount);

                textViewGemCount.setText(String.valueOf(gemCount));

                Glide.with(getActivity()).load(model.getImage()).dontAnimate().into(holder.imageViewPuzzles2);
                Glide.with(getActivity()).load(model.getImage()).dontAnimate().fitCenter().into(imageViewGelen);

                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                final AlertDialog add = ad.create();
                add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                add.setView(alertViewFree);
                add.setCancelable(false);

                holder.imageViewPuzzles2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add.show();
                        mp.start();

                        Log.e("model.image : ",model.image);

                        imageViewBuyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Gem gem = databaseGemRoom.daoGem().tumGemler().get(0);
                                int kalanGem = gem.getGemSayisi() - 40;

                                if (kalanGem >=  0){
                                    add.cancel();
                                    View alertViewTamamlandi = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_buy_completed,null);

                                    ImageView imageViewCancel2 = alertViewTamamlandi.findViewById(R.id.imageViewCancel);
                                    TextView textViewGemCount2 = alertViewTamamlandi.findViewById(R.id.textViewGemCount);

                                    ifBuyTekil(model.image);

                                    gem.setGemSayisi(kalanGem);
                                    databaseGemRoom.daoGem().guncelle(gem);
                                    activityTV.setText(String.valueOf(kalanGem));
                                    Toast.makeText(getActivity(), "Satın Alındı.", Toast.LENGTH_SHORT).show();

                                    textViewGemCount2.setText(String.valueOf(kalanGem));

                                    AlertDialog.Builder ad2 = new AlertDialog.Builder(getActivity());
                                    final AlertDialog add2 = ad2.create();
                                    add2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    add2.setView(alertViewTamamlandi);
                                    add2.show();

                                    imageViewCancel2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            add2.cancel();
                                            mp2.start();
                                        }
                                    });

                                }else {

                                    View alertViewFailed = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_buy_failed,null);

                                    ImageView imageViewCancel2 = alertViewFailed.findViewById(R.id.imageViewCancel);
                                    TextView textViewGemCount2 = alertViewFailed.findViewById(R.id.textViewGemCount);
                                    textViewGemCount2.setText(String.valueOf(gemCount));

                                    AlertDialog.Builder ad2 = new AlertDialog.Builder(getActivity());
                                    final AlertDialog add2 = ad2.create();
                                    add2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    add2.setView(alertViewFailed);
                                    add2.show();

                                    imageViewCancel2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            add2.cancel();
                                            mp2.start();
                                        }
                                    });

                                }

                            }
                        });

                    }
                });
                imageViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add.cancel();
                        mp2.start();
                    }
                });

            }

            @NonNull
            @Override
            public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rv2,parent,false);
                return new ViewHolder2(view);
            }
        };
        adapter2.startListening();
        rv_2.setAdapter(adapter2);

        // Slider 3
        mRef3 = mFirebaseDatabase.getReference("purchases").child("fifteenpercent");

        options = new FirebaseRecyclerOptions.Builder<ModelPurchase>().setQuery(mRef3,ModelPurchase.class).build();

        adapter3 = new FirebaseRecyclerAdapter<ModelPurchase, ViewHolder3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder3 holder, final int position, @NonNull final ModelPurchase model) {

                View alertViewFree = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_fifteenpercent,null);

                isExist(model.image,mRef3);

                ImageView imageViewCancel = alertViewFree.findViewById(R.id.imageViewCancel);
                final ImageView imageViewBuyButton = alertViewFree.findViewById(R.id.imageViewBuyButton);
                TextView textViewGemCount = alertViewFree.findViewById(R.id.textViewGemCount);

                textViewGemCount.setText(String.valueOf(gemCount));

                final RecyclerView rv_fifteenPack = alertViewFree.findViewById(R.id.rv_fifteenPack);
                rv_fifteenPack.setHasFixedSize(true);
                rv_fifteenPack.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

                Glide.with(getActivity()).load(model.getImage()).dontAnimate().into(holder.imageViewPuzzles3);

                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                final AlertDialog add = ad.create();
                add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                add.setView(alertViewFree);
                add.setCancelable(false);
                holder.imageViewPuzzles3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        add.show();
                        mp.start();

                        mRefFifteenPack = mFirebaseDatabase.getReference("purchases").child("fifteenpercent"+(position+1)+"pack");

                        options = new FirebaseRecyclerOptions.Builder<ModelPurchase>().setQuery(mRefFifteenPack,ModelPurchase.class).build();

                        adapterFifteenPack = new FirebaseRecyclerAdapter<ModelPurchase, ViewHolderFifteenPack>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull ViewHolderFifteenPack holder, int position, @NonNull ModelPurchase model) {
                                Glide.with(getActivity()).load(model.getImage()).dontAnimate().into(holder.imageViewFifteenPack);
                                isExist(model.getImage(),mRefFifteenPack);

                                imageViewBuyButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Gem gem = databaseGemRoom.daoGem().tumGemler().get(0);
                                        int kalanGem = gem.getGemSayisi() - 320;

                                        if (kalanGem >=  0){
                                            add.cancel();
                                            View alertViewTamamlandi = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_buy_completed,null);

                                            ifBuy(mRefFifteenPack);

                                            ImageView imageViewCancel2 = alertViewTamamlandi.findViewById(R.id.imageViewCancel);
                                            TextView textViewGemCount2 = alertViewTamamlandi.findViewById(R.id.textViewGemCount);

                                            gem.setGemSayisi(kalanGem);
                                            databaseGemRoom.daoGem().guncelle(gem);
                                            activityTV.setText(String.valueOf(kalanGem));
                                            Toast.makeText(getActivity(), "Satın Alındı.", Toast.LENGTH_SHORT).show();

                                            textViewGemCount2.setText(String.valueOf(kalanGem));

                                            AlertDialog.Builder ad2 = new AlertDialog.Builder(getActivity());
                                            final AlertDialog add2 = ad2.create();
                                            add2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                            add2.setView(alertViewTamamlandi);
                                            add2.show();

                                            imageViewCancel2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    add2.cancel();
                                                    mp2.start();
                                                }
                                            });

                                        }else {

                                            View alertViewFailed = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_buy_failed,null);

                                            ImageView imageViewCancel2 = alertViewFailed.findViewById(R.id.imageViewCancel);
                                            TextView textViewGemCount2 = alertViewFailed.findViewById(R.id.textViewGemCount);
                                            textViewGemCount2.setText(String.valueOf(gemCount));

                                            AlertDialog.Builder ad2 = new AlertDialog.Builder(getActivity());
                                            final AlertDialog add2 = ad2.create();
                                            add2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                            add2.setView(alertViewFailed);
                                            add2.show();

                                            imageViewCancel2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    add2.cancel();
                                                    mp2.start();
                                                }
                                            });

                                        }

                                    }
                                });
                            }

                            @NonNull
                            @Override
                            public ViewHolderFifteenPack onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rv_fifteen_pack,parent,false);
                                return new ViewHolderFifteenPack(view);
                            }
                        };
                        adapterFifteenPack.startListening();
                        rv_fifteenPack.setAdapter(adapterFifteenPack);

                    }
                });
                imageViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add.cancel();
                        mp2.start();
                    }
                });

            }

            @NonNull
            @Override
            public ViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rv3,parent,false);
                return new ViewHolder3(view);
            }
        };
        adapter3.startListening();
        rv_3.setAdapter(adapter3);

        mReftwentypercent1 = mFirebaseDatabase.getReference("purchases").child("twentypercent");

        options = new FirebaseRecyclerOptions.Builder<ModelPurchase>().setQuery(mReftwentypercent1,ModelPurchase.class).build();

        adapterTwenty1 = new FirebaseRecyclerAdapter<ModelPurchase, ViewHolderTwentyPack1>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderTwentyPack1 holder, final int positionX, @NonNull final ModelPurchase model) {

                final View alertViewFree = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_twentypercent,null);

                isExist(model.image,mReftwentypercent1);

                ImageView imageViewCancel = alertViewFree.findViewById(R.id.imageViewCancel);
                ImageView imageViewBuyButton = alertViewFree.findViewById(R.id.imageViewBuyButton);
                TextView textViewGemCount = alertViewFree.findViewById(R.id.textViewGemCount);

                textViewGemCount.setText(String.valueOf(gemCount));

                RecyclerView rv_twentypack = alertViewFree.findViewById(R.id.rv_twentypack);
                rv_twentypack.setHasFixedSize(true);
                rv_twentypack.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

                // Twenty Percent Pack Part
                mReftwentypercent2 = mFirebaseDatabase.getReference("purchases").child("twentypercent"+(positionX+1)+"pack");

                options = new FirebaseRecyclerOptions.Builder<ModelPurchase>().setQuery(mReftwentypercent2,ModelPurchase.class).build();

                adapterTwenty2 = new FirebaseRecyclerAdapter<ModelPurchase, ViewHolderTwentyPack2>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolderTwentyPack2 holder, final int position, @NonNull ModelPurchase model) {

                        View alertViewFree = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_twentypercent_pack_parts,null);

                        isExist(model.image,mReftwentypercent2);

                        ImageView imageViewCancel = alertViewFree.findViewById(R.id.imageViewCancel);
                        RecyclerView rv_twenty_pack_part = alertViewFree.findViewById(R.id.rv_twenty_pack_part);
                        rv_twenty_pack_part.setHasFixedSize(true);
                        rv_twenty_pack_part.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

                        Glide.with(getActivity()).load(model.getImage()).dontAnimate().into(holder.imageViewPuzzles6);

                        // Twenty Percent Pack Part Sort
                        mReftwentypercentPackSort = mFirebaseDatabase.getReference("purchases").child("twentypercent"+(positionX+1)+"packsort"+(position+1));

                        options = new FirebaseRecyclerOptions.Builder<ModelPurchase>().setQuery(mReftwentypercentPackSort,ModelPurchase.class).build();

                        adapterTwenty3 = new FirebaseRecyclerAdapter<ModelPurchase, ViewHolderTwentyPack3>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull ViewHolderTwentyPack3 holder, int position, @NonNull ModelPurchase model) {
                                isExist(model.image,mReftwentypercentPackSort);

                                Glide.with(getActivity()).load(model.getImage()).dontAnimate().into(holder.imageViewTwentyPack2);
                            }

                            @NonNull
                            @Override
                            public ViewHolderTwentyPack3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rv_twenty_packs_2,parent,false);
                                return new ViewHolderTwentyPack3(view);
                            }
                        };
                        adapterTwenty3.startListening();
                        rv_twenty_pack_part.setAdapter(adapterTwenty3);

                        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                        final AlertDialog add = ad.create();
                        add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        add.setView(alertViewFree);
                        add.setCancelable(false);
                        holder.imageViewPuzzles6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                add.show();
                                mp.start();

                            }
                        });
                        imageViewCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                add.cancel();
                                mp2.start();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ViewHolderTwentyPack2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rv_twenty_packs,parent,false);
                        return new ViewHolderTwentyPack2(view);
                    }
                };
                adapterTwenty2.startListening();
                rv_twentypack.setAdapter(adapterTwenty2);

                Glide.with(getActivity()).load(model.getImage()).dontAnimate().into(holder.imageViewPuzzles4);

                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                final AlertDialog add = ad.create();
                add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                add.setView(alertViewFree);
                add.setCancelable(false);
                holder.imageViewPuzzles4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        add.show();
                        mp.start();

                    }
                });
                imageViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add.cancel();
                        mp2.start();
                    }
                });

                imageViewBuyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRefBuy = mFirebaseDatabase.getReference("purchases").child("twentypercent"+(positionX+1)+"packsort"+(1));
                        mRefBuy2 = mFirebaseDatabase.getReference("purchases").child("twentypercent"+(positionX+1)+"packsort"+(2));

                        Gem gem = databaseGemRoom.daoGem().tumGemler().get(0);
                        int kalanGem = gem.getGemSayisi() - 600;

                        if (kalanGem >=  0){
                            add.cancel();
                            View alertViewTamamlandi = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_buy_completed,null);

                            ImageView imageViewCancel = alertViewTamamlandi.findViewById(R.id.imageViewCancel);
                            TextView textViewGemCount = alertViewTamamlandi.findViewById(R.id.textViewGemCount);

                            gem.setGemSayisi(kalanGem);
                            databaseGemRoom.daoGem().guncelle(gem);
                            activityTV.setText(String.valueOf(kalanGem));
                            Toast.makeText(getActivity(), "Satın Alındı.", Toast.LENGTH_SHORT).show();

                            ifBuy(mRefBuy);
                            ifBuy(mRefBuy2);

                            textViewGemCount.setText(String.valueOf(kalanGem));

                            AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                            final AlertDialog add = ad.create();
                            add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            add.setView(alertViewTamamlandi);
                            add.show();

                            imageViewCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    add.cancel();
                                    mp2.start();
                                }
                            });

                        }else {

                            View alertViewFailed = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_buy_failed,null);

                            ImageView imageViewCancel = alertViewFailed.findViewById(R.id.imageViewCancel);
                            TextView textViewGemCount = alertViewFailed.findViewById(R.id.textViewGemCount);
                            textViewGemCount.setText(String.valueOf(gemCount));

                            AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                            final AlertDialog add = ad.create();
                            add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            add.setView(alertViewFailed);
                            add.show();

                            imageViewCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    add.cancel();
                                    mp2.start();
                                }
                            });

                        }

                    }
                });

            }

            @NonNull
            @Override
            public ViewHolderTwentyPack1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rv4,parent,false);
                return new ViewHolderTwentyPack1(view);
            }
        };
        adapterTwenty1.startListening();
        rv_4.setAdapter(adapterTwenty1);

        return view;
    }

    public void ifBuyTekil(String imageLink){

        PuzzlelarDao puzzlelarDao = new PuzzlelarDao();
        ArrayList<PuzzlelarVT> gelenPuzzleListesi = new PuzzlelarDao().tumPuzzlelar(vt);

        for (PuzzlelarVT p : gelenPuzzleListesi){

            if (p.getPuzzle_link().equals(imageLink)){

                puzzlelarDao.puzzleUpdate(vt,p.getPuzzle_category(),p.getPuzzle_link(),"satinalindi",p.getPiece9(),p.getPiece25(),p.getPiece100(),p.getPiece225());

            }

        }

    }

    public void ifBuy(final DatabaseReference mRef){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                PuzzlelarDao puzzlelarDao = new PuzzlelarDao();

                for (DataSnapshot d:dataSnapshot.getChildren()){

                    String imgLink = (String) d.child("image").getValue();

                    ArrayList<PuzzlelarVT> gelenPuzzleListesi = new PuzzlelarDao().tumPuzzlelar(vt);

                    for (PuzzlelarVT p : gelenPuzzleListesi){

                        if (p.getPuzzle_link().equals(imgLink)){

                            puzzlelarDao.puzzleUpdate(vt,p.getPuzzle_category(),p.getPuzzle_link(),"satinalindi",p.getPiece9(),p.getPiece25(),p.getPiece100(),p.getPiece225());

                        }

                    }

                }

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentPuzzles puzzles = new FragmentPuzzles();
                ft.replace(R.id.frameLayout,puzzles).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public boolean isExist(String puzzleLink,DatabaseReference mRef) {
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

    @Override
    public void onStart() {
        super.onStart();
        if (adapter1 != null){
            adapter1.startListening();
        }
        if (adapter2 != null){
            adapter2.startListening();
        }
        if (adapter3 != null){
            adapter3.startListening();
        }
        if (adapterTwenty1 != null){
            adapterTwenty1.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter1 != null){
            adapter1.stopListening();
        }
        if (adapter2 != null){
            adapter2.stopListening();
        }
        if (adapter3 != null){
            adapter3.stopListening();
        }
        if (adapterTwenty1 != null){
            adapterTwenty1.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter1 != null){
            adapter1.startListening();
        }
        if (adapter2 != null){
            adapter2.startListening();
        }
        if (adapter3 != null){
            adapter3.startListening();
        }
        if (adapterTwenty1 != null){
            adapterTwenty1.startListening();
        }
    }
}
