package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/11/2015.
 */

import android.os.AsyncTask;

import java.util.HashMap;

import java.lang.ref.WeakReference;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by Sahithi on 6/23/2015.
 */
public class DownloadDeatilEventAsynTask extends AsyncTask<String, Void, HashMap> {


    private final WeakReference<EventRecyclerView.OnListItemSelectedListener> weakListener;
    int position;
    public  DownloadDeatilEventAsynTask(EventRecyclerView.OnListItemSelectedListener weakListener,int position)
    {
        this.weakListener=new WeakReference<EventRecyclerView.OnListItemSelectedListener>(weakListener);
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

        EventRecyclerView.OnListItemSelectedListener mlistener=weakListener.get();
        mlistener.OnListItemSelected(threadevent, position);

    }


}