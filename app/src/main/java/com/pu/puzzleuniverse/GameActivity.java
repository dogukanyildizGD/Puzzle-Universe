package com.pu.puzzleuniverse;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.WHITE;

public class GameActivity extends AppCompatActivity {

    private View decorView;

    private GridView gridView;
    private GameAdapter gameAdapter;
    private ImageView imageViewLamb,imagePuzzlePause;
    private TextView textViewMove;
    private int puzzleSayisi;
    int currentPos = -1;
    private int sayacMove = 0;
    private String gelenPuzzleLink,collectiontab;

    private ArrayList<Bitmap> chunkedImagesArrayList,arrayListEsleme;
    private ArrayList<Integer> listPosition;
    private ArrayList<String> xList;

    private VeritabaniYardimcisi vt;

    private Chronometer chronometer;

    private String[] separated,pauseName;
    private String atilanAd,dosyaYolu;
    private Boolean puzzelinkBool;
    private File dosya;

    private Gem gem;
    private DatabaseGemRoom databaseGemRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridView = findViewById(R.id.gridView);
        imageViewLamb = findViewById(R.id.imageViewLamb);
        imagePuzzlePause = findViewById(R.id.imagePuzzlePause);
        textViewMove = findViewById(R.id.textViewMove);
        chronometer = findViewById(R.id.chronometer);
        chronometer.start();

        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_GRANTED);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        databaseGemRoom = Room.databaseBuilder(this,DatabaseGemRoom.class,"gemler").allowMainThreadQueries().build();

        gem = databaseGemRoom.daoGem().tumGemler().get(0);
        Log.e("gem.getFinishedPuzzle()",String.valueOf(gem.getFinishPuzzle9()));
        Log.e("gem.getFinishedPuzzle()",String.valueOf(gem.getFinishPuzzle25()));
        Log.e("gem.getFinishedPuzzle()",String.valueOf(gem.getFinishPuzzle100()));
        Log.e("gem.getFinishedPuzzle()",String.valueOf(gem.getFinishPuzzle225()));

        vt = new VeritabaniYardimcisi(this);

        gelenPuzzleLink = GameActivity.this.getIntent().getExtras().getString("imgLink");
        puzzleSayisi = GameActivity.this.getIntent().getExtras().getInt("puzzlePiece");

        if (gelenPuzzleLink.contains("com/")){
            puzzelinkBool = true;
            separated = gelenPuzzleLink.split("com/"); // string ayırma
            atilanAd = separated[1];
        }else {
            puzzelinkBool = false;
            atilanAd = gelenPuzzleLink;
        }

        dosyaYolu = Environment.getExternalStorageDirectory()+"/PuzzleSave/";
        dosya = new File(dosyaYolu,atilanAd+".txt");

        gridView.setNumColumns((int) Math.sqrt(puzzleSayisi)); // karekök alma

        listPosition = new ArrayList<>();

        for (int i=0;i<225;i++){
            listPosition.add(i);
        }

        imageViewLamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View alertViewFullimage = LayoutInflater.from(GameActivity.this).inflate(R.layout.alert_view_full_image,null);

                ImageView imageViewFullimage = alertViewFullimage.findViewById(R.id.imageViewFullimage);

                Glide.with(GameActivity.this).load(gelenPuzzleLink).dontAnimate().fitCenter().into(imageViewFullimage);

                AlertDialog.Builder ad = new AlertDialog.Builder(GameActivity.this);
                final AlertDialog add = ad.create();
                add.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
                add.setView(alertViewFullimage);
                add.show();

            }
        });

        itemLocationChange();
        drawableShuffle();

        imagePuzzlePause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puzzleKayit();
            }
        });

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

    public void puzzleKayit(){
        try {
            if (!dosya.exists()){
                dosya.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(dosya);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int i=0;i<puzzleSayisi;i++){

                for (int j=0;j<puzzleSayisi;j++){

                    if (arrayListEsleme.get(j).equals(chunkedImagesArrayList.get(i))){
                        bufferedWriter.write(j+"/");
                    }

                }

            }

            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void puzzleKayıtGetir(){
        try {
            FileReader fileReader = new FileReader(dosya);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();

            String satir = "";

            while ((satir = bufferedReader.readLine()) != null){
                stringBuilder.append(satir);
            }

            String[] sbBölme = stringBuilder.toString().split("/");

            for (int a=0;a<puzzleSayisi;a++){
                chunkedImagesArrayList.add(arrayListEsleme.get(Integer.parseInt(sbBölme[a])));
            }

            for (int i=0;i<puzzleSayisi;i++){

                for (int j=0;j<puzzleSayisi;j++){

                    if (arrayListEsleme.get(i).equals(chunkedImagesArrayList.get(j))){
                        Log.e("esleme-chunked : ",i+". "+j);
                        Log.e("++++++++++++","++++++++++++");
                    }

                }

            }
            Log.e("chunkedImages :  ", String.valueOf(chunkedImagesArrayList));

            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void PuzzleGame(){
        chunkedImagesArrayList.addAll(arrayListEsleme);
        Collections.shuffle(chunkedImagesArrayList);
        for (int i=0;i<puzzleSayisi;i++){

            for (int j=0;j<puzzleSayisi;j++){

                if (arrayListEsleme.get(i).equals(chunkedImagesArrayList.get(j))){
                    Log.e("esleme-chunked : ",i+". "+j);
                    Log.e("---------","---------");
                }

            }

        }
        Log.e("arrayListEsleme", String.valueOf(arrayListEsleme));
        Log.e("chunkedImagesAr", String.valueOf(chunkedImagesArrayList));
    }

    public void puzzlePlayControl(){

        PuzzlelarDao puzzlelarDao = new PuzzlelarDao();

        ArrayList<PuzzlelarVT> gelenPuzzleListesi = new PuzzlelarDao().tumPuzzlelar(vt);

        for (PuzzlelarVT p : gelenPuzzleListesi){

            if (p.getPuzzle_link().equals(gelenPuzzleLink) && puzzleSayisi == 9){
                puzzlelarDao.puzzleUpdate(vt,p.getPuzzle_category(),p.getPuzzle_link(),p.getPuzzle_satinal(),"played",p.getPiece25(),p.getPiece100(),p.getPiece225());
                gem.setFinishPuzzle9(gem.getFinishPuzzle9()+1);
                databaseGemRoom.daoGem().guncelle(gem);
            }
            if (p.getPuzzle_link().equals(gelenPuzzleLink) && puzzleSayisi == 25){
                puzzlelarDao.puzzleUpdate(vt,p.getPuzzle_category(),p.getPuzzle_link(),p.getPuzzle_satinal(),p.getPiece9(),"played",p.getPiece100(),p.getPiece225());
                gem.setFinishPuzzle25(gem.getFinishPuzzle25()+1);
                databaseGemRoom.daoGem().guncelle(gem);
            }
            if (p.getPuzzle_link().equals(gelenPuzzleLink) && puzzleSayisi == 100){
                puzzlelarDao.puzzleUpdate(vt,p.getPuzzle_category(),p.getPuzzle_link(),p.getPuzzle_satinal(),p.getPiece9(),p.getPiece25(),"played",p.getPiece225());
                gem.setFinishPuzzle100(gem.getFinishPuzzle100()+1);
                databaseGemRoom.daoGem().guncelle(gem);
            }
            if (p.getPuzzle_link().equals(gelenPuzzleLink) && puzzleSayisi == 225){
                puzzlelarDao.puzzleUpdate(vt,p.getPuzzle_category(),p.getPuzzle_link(),p.getPuzzle_satinal(),p.getPiece9(),p.getPiece25(),p.getPiece100(),"played");
                gem.setFinishPuzzle225(gem.getFinishPuzzle225()+1);
                databaseGemRoom.daoGem().guncelle(gem);
            }

        }

    }

    private void puzzleCompleted(){

        if (chunkedImagesArrayList.equals(arrayListEsleme)){
            chronometer.stop();

            puzzlePlayControl();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            FragmentEndScreen fragmentEndScreen = new FragmentEndScreen();

            Bundle b = new Bundle();
            b.putString("gelenPuzzleLink",gelenPuzzleLink);
            b.putString("textViewMove",textViewMove.getText().toString());
            b.putString("chronometer",chronometer.getText().toString());
            b.putInt("puzzleSayisi",puzzleSayisi);
            fragmentEndScreen.setArguments(b);

            ft.add(R.id.frameLayoutEndScreen,fragmentEndScreen);
            ft.commit();

        }

    }

    private void itemLocationChange() {

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sayacMove++;
                textViewMove.setText(String.valueOf(sayacMove));

                if (currentPos < 0){

                    currentPos = i;

                }else {

                    if (listPosition.get(currentPos) != listPosition.get(i)){

                        Collections.swap(chunkedImagesArrayList,listPosition.get(currentPos),listPosition.get(i));
                        puzzleCompleted();

                        /*if (chunkedImagesArrayList.get(currentPos).equals(arrayListEsleme.get(i)) || chunkedImagesArrayList.get(i).equals(arrayListEsleme.get(currentPos))){
                        }*/

                    }
                    currentPos = -1;

                }

                gameAdapter = new GameAdapter(GameActivity.this,gridView,puzzleSayisi,chunkedImagesArrayList,listPosition);
                gridView.setAdapter(gameAdapter);

            }
        });

    }

    private void drawableShuffle(){

        if (!gelenPuzzleLink.contains("https://i.imgur.com/")){
            gelenPuzzleLink = "https://i.imgur.com/"+gelenPuzzleLink;
        }

        Glide.with(GameActivity.this)
                .asBitmap()
                .load(gelenPuzzleLink)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ImageView imageView = new ImageView(GameActivity.this);
                        imageView.setImageBitmap(resource);

                        int sayiKok= (int) Math.sqrt(puzzleSayisi);

                        splitImage(imageView,sayiKok,sayiKok);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

    }

    private void splitImage(ImageView imageView, int rows, int cols) {

        //For height and width of the small image chunks
        int chunkHeight,chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        chunkedImagesArrayList = new ArrayList<>();
        arrayListEsleme = new ArrayList<>();

        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<cols; y++){
                arrayListEsleme.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }

        if (puzzelinkBool){
            PuzzleGame();
        }else {
            puzzleKayıtGetir();
        }

        gameAdapter = new GameAdapter(GameActivity.this,gridView,puzzleSayisi,chunkedImagesArrayList,listPosition);
        gridView.setAdapter(gameAdapter);

    }

    public static Bitmap getScreenShot(View view){
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static Bitmap loadBitmapFromView(View v) { // view screenshot alma.
        Bitmap b = Bitmap.createBitmap(v.getWidth() , v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    private void splitImagePause(ImageView imageView, int rows, int cols) {

        //For height and width of the small image chunks
        int chunkHeight,chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        chunkedImagesArrayList = new ArrayList<>();
        arrayListEsleme = new ArrayList<>();
        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;

        if (collectiontab != null){
            //xCoord and yCoord are the pixel positions of the image chunks
            int yCoord = 0;
            for(int x=0; x<rows; x++){
                int xCoord = 0;
                for(int y=0; y<cols; y++){
                    arrayListEsleme.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                    xCoord += chunkWidth;
                }
                yCoord += chunkHeight;
            }

            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PuzzleSave/"+separated[1];
            File dir = new File(dirPath);
            for (int i=0;i<puzzleSayisi;i++){
                Bitmap bmp = BitmapFactory.decodeFile(dir+"/"+i+".png");
                chunkedImagesArrayList.add(bmp);
            }
        }else {
            //xCoord and yCoord are the pixel positions of the image chunks
            int yCoord = 0;
            for(int x=0; x<rows; x++){
                int xCoord = 0;
                for(int y=0; y<cols; y++){
                    chunkedImagesArrayList.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                    xCoord += chunkWidth;
                }
                yCoord += chunkHeight;
            }

            arrayListEsleme.addAll(chunkedImagesArrayList);
            Collections.shuffle(chunkedImagesArrayList);
        }
        gameAdapter = new GameAdapter(GameActivity.this,gridView,puzzleSayisi,chunkedImagesArrayList,listPosition);
        gridView.setAdapter(gameAdapter);

    }

    public void store(ArrayList<Bitmap> b,String fileName){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PuzzleSave/"+fileName;
        File dir = new File(dirPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        for (int i=0;i<puzzleSayisi;i++){
            File file = new File(dirPath, i+".png");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                b.get(i).compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getStore(ArrayList<Bitmap> b,String fileName){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PuzzleSave/"+fileName;
        File dir = new File(dirPath);
        for (int i=0;i<puzzleSayisi;i++){
            Bitmap bmp = BitmapFactory.decodeFile(dir+"/"+i+".png");
            b.add(bmp);
        }
        gameAdapter = new GameAdapter(GameActivity.this,gridView,puzzleSayisi,b,listPosition);
        gridView.setAdapter(gameAdapter);
    }

    /*public static void shuffleList(ArrayList<Bitmap> a) {
        int n = a.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            Collections.swap(a, i, change);
        }
    }*/ // --> Shuffle Fonksiyon

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GameActivity.this,MenuActivity.class));
        finish();
    }
}
