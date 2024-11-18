package com.pu.puzzleuniverse;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gemler")
public class Gem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "gemsayisi")
    public int gemSayisi;

    @ColumnInfo(name = "firebaseeklenme")
    public int fireBaseEklenme;

    @ColumnInfo(name = "userkey")
    public String userKey;

    @ColumnInfo(name = "finishpuzzle9")
    public int finishPuzzle9;

    @ColumnInfo(name = "finishpuzzle25")
    public int finishPuzzle25;

    @ColumnInfo(name = "finishpuzzle100")
    public int finishPuzzle100;

    @ColumnInfo(name = "finishpuzzle225")
    public int finishPuzzle225;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGemSayisi() {
        return gemSayisi;
    }

    public void setGemSayisi(int gemSayisi) {
        this.gemSayisi = gemSayisi;
    }

    public int getFireBaseEklenme() {
        return fireBaseEklenme;
    }

    public void setFireBaseEklenme(int fireBaseEklenme) {
        this.fireBaseEklenme = fireBaseEklenme;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public int getFinishPuzzle9() {
        return finishPuzzle9;
    }

    public void setFinishPuzzle9(int finishPuzzle9) {
        this.finishPuzzle9 = finishPuzzle9;
    }

    public int getFinishPuzzle25() {
        return finishPuzzle25;
    }

    public void setFinishPuzzle25(int finishPuzzle25) {
        this.finishPuzzle25 = finishPuzzle25;
    }

    public int getFinishPuzzle100() {
        return finishPuzzle100;
    }

    public void setFinishPuzzle100(int finishPuzzle100) {
        this.finishPuzzle100 = finishPuzzle100;
    }

    public int getFinishPuzzle225() {
        return finishPuzzle225;
    }

    public void setFinishPuzzle225(int finishPuzzle225) {
        this.finishPuzzle225 = finishPuzzle225;
    }
}
