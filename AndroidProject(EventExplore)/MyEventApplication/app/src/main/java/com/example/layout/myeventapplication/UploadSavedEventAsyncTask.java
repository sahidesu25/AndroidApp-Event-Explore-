package com.example.layout.myeventapplication;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Sahithi on 8/7/2015.
 */
public class UploadSavedEventAsyncTask extends AsyncTask<String,Void,String> {

    private HashMap Info=new HashMap();

    public UploadSavedEventAsyncTask(HashMap Info)
    {
        this.Info=Info;

    }
    protected String doInBackground(String...urls)
    {
        MyUtility.uploadJSON(urls[0],Info);
        String uploadstatus="UploadComplete";

        return uploadstatus;
    }

    protected void onPostExecute(String UploadStatus)
    {


    }



}