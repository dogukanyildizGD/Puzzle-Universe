package com.pu.puzzleuniverse;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Gem.class}, version = 1)
public abstract class DatabaseGemRoom extends RoomDatabase {
    public abstract DaoGem daoGem();
}
