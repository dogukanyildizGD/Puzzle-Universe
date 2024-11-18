package com.pu.puzzleuniverse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VeritabaniYardimcisi extends SQLiteOpenHelper {

    public VeritabaniYardimcisi(Context context) {
        super(context, "puzzlekayit", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE puzzlelar (puzzle_id INTEGER PRIMARY KEY AUTOINCREMENT, puzzle_category TEXT, puzzle_link TEXT, puzzle_satinal TEXT, piece9 TEXT, piece25 TEXT, piece100 TEXT, piece225 TEXT);");
        db.execSQL("CREATE TABLE gems (gem_id INTEGER PRIMARY KEY AUTOINCREMENT, gem_count TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS puzzlelar");
        db.execSQL("DROP TABLE IF EXISTS gems");
        onCreate(db);
    }
}
