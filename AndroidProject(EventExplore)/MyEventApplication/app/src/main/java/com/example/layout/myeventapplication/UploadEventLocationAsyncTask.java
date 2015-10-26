package com.example.layout.myeventapplication;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Sahithi on 8/14/2015.
 */
public class UploadEventLocationAsyncTask extends AsyncTask<String,Void,String> {

    private HashMap Info=new HashMap();
   // private final WeakReference<TextView> TextViewWeakReference;

    public UploadEventLocationAsyncTask(HashMap Info)
    {
        this.Info=Info;
       // TextViewWeakReference=new WeakReference<TextView>(Response);

    }
    protected String doInBackground(String...urls)
    {
        MyUtility.uploadLocationinfoJSON(urls[0],Info);


        return "uploading";
    }

    protected void onPostExecute(String UploadStatus)
    {

      //  TextView UploadText=TextViewWeakReference.get();
       // UploadText.setText(UploadStatus);
    }



}