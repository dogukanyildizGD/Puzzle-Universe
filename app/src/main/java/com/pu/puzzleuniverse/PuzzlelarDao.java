package com.pu.puzzleuniverse;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PuzzlelarDao {

    public void puzzleEkle (VeritabaniYardimcisi vt,String puzzle_category,String puzzle_link,String puzzle_satinal,String piece9,String piece25,String piece100,String piece225){

        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("puzzle_category",puzzle_category);
        values.put("puzzle_link",puzzle_link);
        values.put("puzzle_satinal",puzzle_satinal);
        values.put("piece9",piece9);
        values.put("piece25",piece25);
        values.put("piece100",piece100);
        values.put("piece225",piece225);

        dbx.insertOrThrow("puzzlelar",null,values);
        dbx.close();
    }

    public ArrayList<PuzzlelarVT> tumPuzzlelar(VeritabaniYardimcisi vt){

        ArrayList<PuzzlelarVT> puzzlelarArrayList = new ArrayList<>();
        SQLiteDatabase dbx = vt.getWritableDatabase();
        Cursor c = dbx.rawQuery("SELECT * FROM puzzlelar",null);

        while (c.moveToNext()){

            PuzzlelarVT puzzle = new PuzzlelarVT(c.getInt(c.getColumnIndex("puzzle_id"))
                    ,c.getString(c.getColumnIndex("puzzle_category"))
                    ,c.getString(c.getColumnIndex("puzzle_link"))
                    ,c.getString(c.getColumnIndex("puzzle_satinal"))
                    ,c.getString(c.getColumnIndex("piece9"))
                    ,c.getString(c.getColumnIndex("piece25"))
                    ,c.getString(c.getColumnIndex("piece100"))
                    ,c.getString(c.getColumnIndex("piece225")));

            puzzlelarArrayList.add(puzzle);
        }
        return puzzlelarArrayList;
    }

    public void puzzleUpdate (VeritabaniYardimcisi vt,String puzzle_category,String puzzle_link,String puzzle_satinal,String piece9,String piece25,String piece100,String piece225){

        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("puzzle_category",puzzle_category);
        values.put("puzzle_link",puzzle_link);
        values.put("puzzle_satinal",puzzle_satinal);
        values.put("piece9",piece9);
        values.put("piece25",piece25);
        values.put("piece100",piece100);
        values.put("piece225",piece225);

        dbx.update("puzzlelar",values,"puzzle_link=?",new String[]{puzzle_link});
        dbx.close();
    }

    public void puzzleVTSil(VeritabaniYardimcisi vt,String puzzlekayit){
        SQLiteDatabase dbx = vt.getWritableDatabase();
        dbx.delete(puzzlekayit, null, null);
        dbx.close();
    }

}
