package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/4/2015.
 */
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class MyDownloadImageAsyncTask extends AsyncTask<String,Void,Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;
    public MyDownloadImageAsyncTask(ImageView imageView)
    {
        imageViewWeakReference=new WeakReference<ImageView>(imageView);
    }
    protected Bitmap doInBackground(String... urls)
    {
        Bitmap bitmap=null;
        for(String url:urls)
        {
            bitmap=MyUtility.downloadImage(url);
            if(bitmap!=null)
            {
                EventRecyclerView.imagecache.put(url,bitmap);
            }
        }
        return bitmap;


    }
    protected void onPostExecute(Bitmap bitmap)
    {
        if(imageViewWeakReference!=null && bitmap!=null)
        {
            final ImageView imageView=imageViewWeakReference.get();
            if(imageView!=null)
            {
                imageView.setImageBitmap(bitmap);
            }

        }
    }


}