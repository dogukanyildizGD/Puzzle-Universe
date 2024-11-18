package com.pu.puzzleuniverse;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoGem {

    @Insert
    void birGemSayisiEkle(Gem gem);

    @Query("SELECT * FROM gemler")
    List<Gem> tumGemler();

    @Query("SELECT * FROM gemler where gemsayisi LIKE  :gemsayisi")
    Gem gemBul(int gemsayisi);

    @Update
    void guncelle(Gem gem);

    @Delete
    void sil(Gem gem);

}
