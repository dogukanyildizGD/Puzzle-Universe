package com.pu.puzzleuniverse;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.util.ArrayList;


public class FragmentCollectionsTab extends Fragment {

    private RecyclerView rv_collection_tab;
    private ArrayList<String> nameList;
    private CollectionTabAdapter collectionTabAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_collection_tab,container,false);

        rv_collection_tab = view.findViewById(R.id.rv_collection_tab);
        rv_collection_tab.setHasFixedSize(true);
        rv_collection_tab.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        collectionTabAdapter = new CollectionTabAdapter(getActivity(),imageNames());
        rv_collection_tab.setAdapter(collectionTabAdapter);

        return view;
    }

    public ArrayList<String> imageNames(){  // Benim Kodum.

        nameList = new ArrayList<>();

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PuzzleSave";
        File dir = new File(dirPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File[] imageList = dir.listFiles();

        for (File imagePath : imageList) {
            String path= imagePath.getAbsolutePath();
            String[] separated = path.split("PuzzleSave/"); // string ayırma
            String[] separated2 = separated[1].split(".txt"); // string ayırma
            nameList.add(separated2[0]);
        }

        return nameList;
    }

    /*public ArrayList<String> getFilePaths(){ // Örnek Kod.

        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<>();
        ArrayList<String> resultIAV = new ArrayList<>();

        String[] directories = null;
        if (u != null)
        {
            c = getActivity().managedQuery(u, projection, null, null, null);
        }

        if ((c != null) && (c.moveToFirst()))
        {
            do
            {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try{
                    dirList.add(tempDir);
                }
                catch(Exception e)
                {

                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);

        }

        for(int i=0;i<dirList.size();i++)
        {
            File imageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PuzzleSave");
            File[] imageList = imageDir.listFiles();
            if(imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if(imagePath.isDirectory())
                    {
                        imageList = imagePath.listFiles();

                    }
                    if ( imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg")|| imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                    )
                    {

                        String path= imagePath.getAbsolutePath();
                        if (path.contains("PuzzleSave/")){
                            String[] separated = path.split("PuzzleSave/"); // string ayırma
                            Log.e("separated[1] :",separated[1]);
                            resultIAV.add(separated[1]);
                        }

                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return resultIAV;

    }*/
}
