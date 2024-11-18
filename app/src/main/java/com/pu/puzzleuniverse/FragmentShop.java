package com.pu.puzzleuniverse;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentShop extends Fragment implements PurchasesUpdatedListener {

    private RecyclerView rv_shop;
    private ArrayList<Puzzlelar> puzzlelarArrayList;
    private ShopAdapter shopAdapter;

    private ImageView imageViewGem1,imageViewGem2,imageViewGem3,imageViewGem4,imageViewGem5,imageViewGem6,imageViewGem7;

    private BillingClient mBillingClient;

    private List<SkuDetails> skuINAPPDetaylistesi = new ArrayList<>();

    private RewardedVideoAd rewardedVideoAd;
    private TextView activityTV;
    DatabaseGemRoom databaseGemRoom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_shop,container,false);

        imageViewGem1 = view.findViewById(R.id.imageViewGem1);
        imageViewGem2 = view.findViewById(R.id.imageViewGem2);
        imageViewGem3 = view.findViewById(R.id.imageViewGem3);
        imageViewGem4 = view.findViewById(R.id.imageViewGem4);
        imageViewGem5 = view.findViewById(R.id.imageViewGem5);
        imageViewGem6 = view.findViewById(R.id.imageViewGem6);
        imageViewGem7 = view.findViewById(R.id.imageViewGem7);
        activityTV = getActivity().findViewById(R.id.textViewGemCount);

        databaseGemRoom = Room.databaseBuilder(getActivity(),DatabaseGemRoom.class,"gemler").allowMainThreadQueries().build();

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());

        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());

        // BILLING
        mBillingClient = BillingClient.newBuilder(getActivity()).enablePendingPurchases().setListener(this).build();

        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    btnDurum(true);

                    List<String> skuListINAPP = new ArrayList<>();

                    skuListINAPP.add("gem2sell");
                    skuListINAPP.add("gem3sell");
                    skuListINAPP.add("gem4sell");
                    skuListINAPP.add("gem5sell");
                    skuListINAPP.add("gem6sell");
                    skuListINAPP.add("gem7sell");

                    SkuDetailsParams.Builder paramsINAPP = SkuDetailsParams.newBuilder();

                    paramsINAPP.setSkusList(skuListINAPP).setType(BillingClient.SkuType.INAPP);

                    mBillingClient.querySkuDetailsAsync(paramsINAPP.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {

                            skuINAPPDetaylistesi = list;

                        }
                    });


                }else {
                    Toast.makeText(getActivity(), "Check Your Google Play Account For Payment", Toast.LENGTH_SHORT).show();
                    btnDurum(false);
                }

            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(getActivity(), "Payment System is Currently Invalid", Toast.LENGTH_SHORT).show();
                btnDurum(false);
            }
        });

        imageViewGem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rewardedVideoAd.isLoaded()){
                    rewardedVideoAd.show();
                }

                rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onRewardedVideoAdLoaded() {

                    }

                    @Override
                    public void onRewardedVideoAdOpened() {

                    }

                    @Override
                    public void onRewardedVideoStarted() {

                    }

                    @Override
                    public void onRewardedVideoAdClosed() {

                    }

                    @Override
                    public void onRewarded(RewardItem rewardItem) {

                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view_5_reward,null);

                        ImageView imageGetButton = view.findViewById(R.id.imageGetButton);

                        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                        final AlertDialog add = ad.create();
                        add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        add.setView(view);
                        add.setCancelable(false);
                        add.show();

                        imageGetButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Gem gem = databaseGemRoom.daoGem().tumGemler().get(0);
                                int rewardGem = gem.getGemSayisi() + 5;

                                gem.setGemSayisi(rewardGem);
                                databaseGemRoom.daoGem().guncelle(gem);
                                activityTV.setText(String.valueOf(rewardGem));

                                rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
                                imageViewGem1.setImageResource(R.drawable.gem1done);
                                imageViewGem1.setClickable(false);

                                add.cancel();

                                new CountDownTimer(600000,1000){

                                    @Override
                                    public void onTick(long l) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
                                        imageViewGem1.setImageResource(R.drawable.gem1_selector);
                                        imageViewGem1.setClickable(true);
                                    }

                                }.start();

                            }
                        });

                    }

                    @Override
                    public void onRewardedVideoAdLeftApplication() {

                    }

                    @Override
                    public void onRewardedVideoAdFailedToLoad(int i) {

                    }

                    @Override
                    public void onRewardedVideoCompleted() {

                    }
                });

            }
        });
        imageViewGem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuINAPPDetaylistesi.get(1))
                        .build();

                mBillingClient.launchBillingFlow(getActivity(),flowParams);

            }
        });
        imageViewGem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuINAPPDetaylistesi.get(2))
                        .build();

                mBillingClient.launchBillingFlow(getActivity(),flowParams);

            }
        });
        imageViewGem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuINAPPDetaylistesi.get(3))
                        .build();

                mBillingClient.launchBillingFlow(getActivity(),flowParams);

            }
        });
        imageViewGem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuINAPPDetaylistesi.get(4))
                        .build();

                mBillingClient.launchBillingFlow(getActivity(),flowParams);

            }
        });
        imageViewGem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuINAPPDetaylistesi.get(5))
                        .build();

                mBillingClient.launchBillingFlow(getActivity(),flowParams);

            }
        });

        imageViewGem7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuINAPPDetaylistesi.get(6))
                        .build();

                mBillingClient.launchBillingFlow(getActivity(),flowParams);

            }
        });

        /*rv_shop = view.findViewById(R.id.rv_shop);
        rv_shop.setHasFixedSize(true);
        rv_shop.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        Puzzlelar c1 = new Puzzlelar("gem1","");
        Puzzlelar c2 = new Puzzlelar("gem2","");
        Puzzlelar c3 = new Puzzlelar("gem3","");
        Puzzlelar c4 = new Puzzlelar("gem4","");
        Puzzlelar c5 = new Puzzlelar("gem5","");
        Puzzlelar c6 = new Puzzlelar("gem6","");
        Puzzlelar c7 = new Puzzlelar("gem7","");

        puzzlelarArrayList = new ArrayList<>();

        puzzlelarArrayList.add(c1);
        puzzlelarArrayList.add(c2);
        puzzlelarArrayList.add(c3);
        puzzlelarArrayList.add(c4);
        puzzlelarArrayList.add(c5);
        puzzlelarArrayList.add(c6);
        puzzlelarArrayList.add(c7);

        shopAdapter = new ShopAdapter(getActivity(),puzzlelarArrayList);

        rv_shop.setAdapter(shopAdapter);*/

        return view;
    }

    private void btnDurum(boolean durum){
        imageViewGem2.setEnabled(durum);
        imageViewGem3.setEnabled(durum);
        imageViewGem4.setEnabled(durum);
        imageViewGem5.setEnabled(durum);
        imageViewGem6.setEnabled(durum);
        imageViewGem7.setEnabled(durum);
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

    }
}
