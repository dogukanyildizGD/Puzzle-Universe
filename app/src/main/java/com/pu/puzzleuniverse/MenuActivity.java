package com.pu.puzzleuniverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.Manifest;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment tempFragment;
    private ImageView imageViewAchievementIcon,imageViewSound,imageViewBonus,imageViewCancelMenu;
    private TextView textViewGemCount,textViewCM;
    private ArrayList<String> arrayListUserMail;

    private View decorView;

    private MediaPlayer mp,mp2;
    private Animation anim_ach,anim_video,anim_ach_alert;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    DatabaseGemRoom databaseGemRoom;
    private int gemSayisi;
    private Gem gem;

    private RequestPermissionHandler mRequestPermissionHandler;

    private Boolean flag = true;

    GoogleSignInClient mGoogleSignInClient;

    private AdView adViewBanner;

    private RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        imageViewAchievementIcon = findViewById(R.id.imageViewAchievementIcon);
        imageViewSound = findViewById(R.id.imageViewSound);
        imageViewBonus = findViewById(R.id.imageViewBonus);
        imageViewCancelMenu = findViewById(R.id.imageViewCancelMenu);
        textViewGemCount = findViewById(R.id.textViewGemCount);
        textViewCM = findViewById(R.id.textViewCM);
        adViewBanner = findViewById(R.id.adViewBanner);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        //ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_GRANTED);
        mRequestPermissionHandler = new RequestPermissionHandler();
        handleButtonClicked();

        MobileAds.initialize(MenuActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);

        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("users");

        databaseGemRoom = Room.databaseBuilder(this,DatabaseGemRoom.class,"gemler").allowMainThreadQueries().build();

        gem = databaseGemRoom.daoGem().tumGemler().get(0);
        gemSayisi = gem.getGemSayisi();
        textViewGemCount.setText(String.valueOf(gemSayisi));

        googleSignFonk();

        achFonk();

        mp = MediaPlayer.create(this,R.raw.acousticbreeze);
        mp.start();

        mp2 = MediaPlayer.create(this,R.raw.tictocclick);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,new FragmentPuzzles()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mp2.start();
                switch (item.getItemId()) {
                    case R.id.action_shop:
                        tempFragment = new FragmentShop();
                        break;
                    case R.id.action_category:
                        tempFragment = new FragmentCategory();
                        break;
                    case R.id.action_puzzle:
                        tempFragment = new FragmentPuzzles();
                        break;
                    case R.id.action_collections:
                        tempFragment = new FragmentCollection();
                        break;
                    /*case R.id.action_settings:
                        tempFragment = new FragmentSettings();
                        break;*/
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,tempFragment).commit();

                return true;
            }
        });

        imageViewCancelMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewBonus.setVisibility(View.GONE);
                textViewCM.setVisibility(View.GONE);
            }
        });

        imageViewAchievementIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentAchievements()).commit();

            }
        });
        anim_video = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_video);
        imageViewBonus.startAnimation(anim_video);

        imageViewBonus.setOnClickListener(new View.OnClickListener() {
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

                        View view = LayoutInflater.from(MenuActivity.this).inflate(R.layout.alert_view_5_reward,null);

                        ImageView imageGetButton = view.findViewById(R.id.imageGetButton);

                        AlertDialog.Builder ad = new AlertDialog.Builder(MenuActivity.this);
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
                                textViewGemCount.setText(String.valueOf(rewardGem));
                                imageViewBonus.setClickable(false);

                                add.cancel();

                                new CountDownTimer(600000,1000){

                                    @Override
                                    public void onTick(long l) {
                                        textViewCM.setText(String.valueOf((l/1000)-1));
                                    }

                                    @Override
                                    public void onFinish() {
                                        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
                                        imageViewBonus.setVisibility(View.VISIBLE);
                                        textViewCM.setVisibility(View.VISIBLE);
                                        imageViewBonus.setClickable(true);
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

        imageViewSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.imageViewSound:{
                        if(flag)
                        {
                            imageViewSound.setImageResource(R.drawable.soundoff);
                            mp.pause();
                            flag=false;
                        }
                        else
                        {
                            imageViewSound.setImageResource(R.drawable.soundon);
                            mp.start();
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.start();
                                }
                            });
                            flag=true;
                        }
                        return;
                    }
                }
            }
        });

        if (!gem.getUserKey().equals("null")){
            // Firebaseten gem degistirme
            mRef.child(gem.getUserKey()).child("gemCount").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    gem.setGemSayisi(Integer.parseInt(String.valueOf(dataSnapshot.getValue())));
                    databaseGemRoom.daoGem().guncelle(gem);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //Firebasteki gem'i degistirme
            Map<String,Object> values = new HashMap<>();

            values.put("gemCount",String.valueOf(gem.getGemSayisi()));

            mRef.child(gem.getUserKey()).updateChildren(values);
        }
    }

    private void achFonk() {

        View alertViewAch = LayoutInflater.from(MenuActivity.this).inflate(R.layout.alert_view_ach_reward,null);

        ImageView imageViewShine = alertViewAch.findViewById(R.id.imageViewShine);
        ImageView imageViewAchVideo = alertViewAch.findViewById(R.id.imageViewAchVideo);
        TextView textViewAchReward = alertViewAch.findViewById(R.id.textViewAchReward);
        TextView textViewContent = alertViewAch.findViewById(R.id.textViewContent);

        anim_ach = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_ach_shine);
        anim_video = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_video);
        anim_ach_alert = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_ach_alert);

        alertViewAch.setAnimation(anim_ach_alert);
        imageViewAchVideo.setAnimation(anim_video);
        imageViewShine.setAnimation(anim_ach);

        AlertDialog.Builder ad = new AlertDialog.Builder(MenuActivity.this);
        AlertDialog add = ad.create();
        add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        add.setView(alertViewAch);
        //add.setCancelable(false);

        if (gem.getFinishPuzzle9() == 1 || gem.getFinishPuzzle25() == 1 || gem.getFinishPuzzle100() == 1 || gem.getFinishPuzzle225() == 1){
            gem.setGemSayisi(gem.getGemSayisi()+5);
            textViewContent.setText("You have completed the\nfirst puzzle.");
            textViewAchReward.setText("5");
            if (gem.getFinishPuzzle9() == 1){gem.setFinishPuzzle9(2);}
            else if (gem.getFinishPuzzle25() == 1){gem.setFinishPuzzle25(2);}
            else if (gem.getFinishPuzzle100() == 1){gem.setFinishPuzzle100(2);}
            else if (gem.getFinishPuzzle225() == 1){gem.setFinishPuzzle225(2);}
            add.show();
        }else if (gem.getFinishPuzzle9() == 6 || gem.getFinishPuzzle25() == 6 || gem.getFinishPuzzle100() == 6 || gem.getFinishPuzzle225() == 6){
            gem.setGemSayisi(gem.getGemSayisi()+5);
            textViewContent.setText("You have completed five\nseries of puzzles.");
            textViewAchReward.setText("5");
            if (gem.getFinishPuzzle9() == 6){gem.setFinishPuzzle9(7);}
            else if (gem.getFinishPuzzle25() == 6){gem.setFinishPuzzle25(7);}
            else if (gem.getFinishPuzzle100() == 6){gem.setFinishPuzzle100(7);}
            else if (gem.getFinishPuzzle225() == 6){gem.setFinishPuzzle225(7);}
            add.show();
        }else if (gem.getFinishPuzzle9() == 12 || gem.getFinishPuzzle25() == 12 || gem.getFinishPuzzle100() == 12 || gem.getFinishPuzzle225() == 12){
            gem.setGemSayisi(gem.getGemSayisi()+10);
            textViewContent.setText("You have completed ten\nseries of puzzles.");
            textViewAchReward.setText("10");
            if (gem.getFinishPuzzle9() == 12){gem.setFinishPuzzle9(13);}
            else if (gem.getFinishPuzzle25() == 12){gem.setFinishPuzzle25(13);}
            else if (gem.getFinishPuzzle100() == 12){gem.setFinishPuzzle100(13);}
            else if (gem.getFinishPuzzle225() == 12){gem.setFinishPuzzle225(13);}
            add.show();
        }else if (gem.getFinishPuzzle9() == 18 || gem.getFinishPuzzle25() == 18 || gem.getFinishPuzzle100() == 18 || gem.getFinishPuzzle225() == 18){
            gem.setGemSayisi(gem.getGemSayisi()+15);
            textViewContent.setText("You have completed fifteen\nseries of puzzles.");
            textViewAchReward.setText("15");
            if (gem.getFinishPuzzle9() == 18){gem.setFinishPuzzle9(19);}
            else if (gem.getFinishPuzzle25() == 18){gem.setFinishPuzzle25(19);}
            else if (gem.getFinishPuzzle100() == 18){gem.setFinishPuzzle100(19);}
            else if (gem.getFinishPuzzle225() == 18){gem.setFinishPuzzle225(19);}
            add.show();
        }else if (gem.getFinishPuzzle9() == 24 || gem.getFinishPuzzle25() == 24 || gem.getFinishPuzzle100() == 24 || gem.getFinishPuzzle225() == 24){
            gem.setGemSayisi(gem.getGemSayisi()+20);
            textViewContent.setText("You have completed twelve\nseries of puzzles.");
            textViewAchReward.setText("20");
            if (gem.getFinishPuzzle9() == 24){gem.setFinishPuzzle9(25);}
            else if (gem.getFinishPuzzle25() == 24){gem.setFinishPuzzle25(25);}
            else if (gem.getFinishPuzzle100() == 24){gem.setFinishPuzzle100(25);}
            else if (gem.getFinishPuzzle225() == 24){gem.setFinishPuzzle225(25);}
            add.show();
        }
        databaseGemRoom.daoGem().guncelle(gem);
        textViewGemCount.setText(String.valueOf(gem.getGemSayisi()));

    }

    private void googleSignFonk() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null && gem.getFireBaseEklenme() == 0 ) {
            final String personName = acct.getDisplayName();
            final String personEmail = acct.getEmail();
            final String personId = acct.getId();
            //Uri personPhoto = acct.getPhotoUrl();

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    arrayListUserMail = new ArrayList<>();

                    for (final DataSnapshot d: dataSnapshot.getChildren()){

                        String st = String.valueOf(d.child("personEmail").getValue());

                        arrayListUserMail.add(st);

                        Users u = d.getValue(Users.class);

                        // Key'i database'e kayıt etme.
                        if (st.equals(personEmail)){
                            gem.setUserKey(u.getUserKey());
                            gem.setGemSayisi(Integer.parseInt(u.getGemCount()));
                            gem.setFireBaseEklenme(Integer.parseInt(u.getFireBaseEklenme()));
                            databaseGemRoom.daoGem().guncelle(gem);
                        }
                    }

                    if (!arrayListUserMail.contains(personEmail)){
                        gem.setFireBaseEklenme(1);
                        databaseGemRoom.daoGem().guncelle(gem);
                        Users user = new Users("",personName,personEmail,personId,String.valueOf(gemSayisi),String.valueOf(gem.getFireBaseEklenme()),"0");
                        mRef.push().setValue(user, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                // Key'i database'e kayıt etme.

                                Map<String,Object> values = new HashMap<>();

                                values.put("userKey",databaseReference.getKey());

                                mRef.child(databaseReference.getKey()).updateChildren(values);

                                gem.setUserKey(String.valueOf(databaseReference.getKey()));
                            }
                        });

                    }
                    databaseGemRoom.daoGem().guncelle(gem);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    private void handleButtonClicked(){
        mRequestPermissionHandler.requestPermission(this, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MenuActivity.this, "request permission success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(MenuActivity.this, "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
