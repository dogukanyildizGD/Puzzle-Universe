package com.pu.puzzleuniverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View decorView;

    private ImageView imageViewStartButton;

    DatabaseGemRoom databaseGemRoom;

    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //deleteDatabase("gemler");

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        databaseGemRoom = Room.databaseBuilder(this,DatabaseGemRoom.class,"gemler").allowMainThreadQueries().build();

        isExist(databaseGemRoom);

        final Gem gem = databaseGemRoom.daoGem().tumGemler().get(0);
        int gelenGem = gem.getGemSayisi();
        Log.e("gelenGem",(gelenGem)+" - "+gem.getFireBaseEklenme());
        Log.e("gem.getUserKey()",String.valueOf(gem.getUserKey()));
        Log.e("gem.getFinishedPuzzle()",String.valueOf(gem.getFinishPuzzle9()));
        Log.e("gem.getFinishedPuzzle()",String.valueOf(gem.getFinishPuzzle25()));
        Log.e("gem.getFinishedPuzzle()",String.valueOf(gem.getFinishPuzzle100()));
        Log.e("gem.getFinishedPuzzle()",String.valueOf(gem.getFinishPuzzle225()));

        imageViewStartButton = findViewById(R.id.imageViewStartButton);

        imageViewStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.imageViewStartButton:
                        signIn();
                        break;
                }

            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(MainActivity.this,MenuActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
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

    public boolean isExist(DatabaseGemRoom databaseGemRoom) {
        boolean isExist = false;
        List<Gem> gemList = databaseGemRoom.daoGem().tumGemler();
        ArrayList<Integer> arrayListP = new ArrayList<>();
        for (Gem g : gemList) {
            int gelenGem = g.getGemSayisi();
            arrayListP.add(gelenGem);
        }
        if (arrayListP.size() == 0){
            Gem gem = new Gem();
            gem.setGemSayisi(50000);
            gem.setFireBaseEklenme(0);
            gem.setUserKey("null");
            gem.setFinishPuzzle9(0);
            gem.setFinishPuzzle25(0);
            gem.setFinishPuzzle100(0);
            gem.setFinishPuzzle225(0);
            databaseGemRoom.daoGem().birGemSayisiEkle(gem);
        }
        return isExist;
    }
}
