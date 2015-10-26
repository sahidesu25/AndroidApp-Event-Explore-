package com.example.layout.myeventapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Sahithi on 8/17/2015.
 */
public class DownloadSavedEventAsyncTask extends AsyncTask<String, Void, HashMap> {

    //private final WeakReference<SavedEventsActivity> activityWeakReference;
Context context;
    int position;
    public  DownloadSavedEventAsyncTask(int position,Context context)
    {
       // this.activityWeakReference=new WeakReference<SavedEventsActivity>(activity);
        this.position=position;
        this.context=context;


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
      //  SavedEventsActivity activity=activityWeakReference.get();

        Intent intent=new Intent(context,DetailFragmentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("event", threadevent);
        context.startActivity(intent);



    }


}