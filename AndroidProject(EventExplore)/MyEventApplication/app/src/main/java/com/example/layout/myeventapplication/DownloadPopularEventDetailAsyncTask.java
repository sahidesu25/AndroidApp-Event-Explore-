package com.example.layout.myeventapplication;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Sahithi on 8/12/2015.
 */
public class DownloadPopularEventDetailAsyncTask extends AsyncTask<String, Void, HashMap> {


    private final WeakReference<PopularEventRecyclerView.OnListItemSelectedListener> weakListener;
    int position;
    public  DownloadPopularEventDetailAsyncTask(PopularEventRecyclerView.OnListItemSelectedListener weakListener,int position)
    {
        this.weakListener=new WeakReference<PopularEventRecyclerView.OnListItemSelectedListener>(weakListener);
        this.position=position;

    }
    protected HashMap doInBackground(String...urls){

        HashMap Map = new HashMap();
        EventData threadEventDataJson = new EventData();
        for(String url : urls){
            //   Log.i("Url passed for MyDownloadMoviesDetailTask is :",url);
            Map = threadEventDataJson.downloadEventDetailDataJson(url);
        }
        return Map;
    }

    protected void onPostExecute(HashMap threadevent)
    {

        PopularEventRecyclerView.OnListItemSelectedListener mlistener=weakListener.get();
        mlistener.OnListItemSelectedPopular(threadevent, position);

    }


}