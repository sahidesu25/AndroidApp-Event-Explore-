package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/14/2015.
 */

import android.os.AsyncTask;

import java.util.HashMap;



import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Sahithi on 8/7/2015.
 */
public class MyUploadTicketCartAsyncTask extends AsyncTask<String,Void,String> {

    private HashMap Info=new HashMap();

    public MyUploadTicketCartAsyncTask(HashMap Info)
    {
        this.Info=Info;

    }
    protected String doInBackground(String...urls)
    {
        MyUtility.uploadticketcartJSON(urls[0],Info);
        String uploadstatus="UploadComplete";

        return uploadstatus;
    }

    protected void onPostExecute(String UploadStatus)
    {


    }



}