package com.pu.puzzleuniverse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends BaseAdapter {

    private Context context;
    private GridView gridView;
    private int puzzleCount;
    private ArrayList<Bitmap> arrayList;
    private ArrayList<Integer> listPosition;

    public GameAdapter(Context context, GridView gridView, int puzzleCount, ArrayList<Bitmap> arrayList, ArrayList<Integer> listPosition) {
        this.context = context;
        this.gridView = gridView;
        this.puzzleCount = puzzleCount;
        this.arrayList = arrayList;
        this.listPosition = listPosition;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageView;
        imageView = new ImageView(this.context);
        imageView.setLayoutParams(new GridView.LayoutParams(gridView.getWidth()/(int) Math.sqrt(puzzleCount),gridView.getHeight()/(int) Math.sqrt(puzzleCount)));
        imageView.setImageBitmap(arrayList.get(listPosition.get(i)));
        imageView.setPadding(1,1,1,1);
        imageView.setBackgroundColor(Color.rgb(112, 112, 112));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return imageView;
    }
}
