package com.example.layout.myeventapplication;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by Sahithi on 8/16/2015.
 */
public class UploadEventInfoAsyncTask extends AsyncTask<String,Void,String> {

    private HashMap Info=new HashMap();
    // private final WeakReference<TextView> TextViewWeakReference;

    public UploadEventInfoAsyncTask(HashMap Info)
    {
        this.Info=Info;
        // TextViewWeakReference=new WeakReference<TextView>(Response);

    }
    protected String doInBackground(String...urls)
    {
        MyUtility.uploadEventInfoJSON(urls[0],Info);


        return "uploading";
    }

    protected void onPostExecute(String UploadStatus)
    {

        //  TextView UploadText=TextViewWeakReference.get();
        // UploadText.setText(UploadStatus);
    }



}