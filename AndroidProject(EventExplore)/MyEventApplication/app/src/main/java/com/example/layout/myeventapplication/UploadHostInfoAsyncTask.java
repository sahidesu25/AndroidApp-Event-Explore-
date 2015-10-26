package com.example.layout.myeventapplication;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Sahithi on 8/13/2015.
 */
public class UploadHostInfoAsyncTask extends AsyncTask<String,Void,String> {

    private HashMap Info=new HashMap();
    private final WeakReference<TextView> TextViewWeakReference;

    public UploadHostInfoAsyncTask(HashMap Info,TextView Response)
    {
        this.Info=Info;
        TextViewWeakReference=new WeakReference<TextView>(Response);

    }
    protected String doInBackground(String...urls)
    {
       String res= MyUtility.uploadHostinfoJSON(urls[0],Info);
        String uploadstatus="UploadComplete";

        return res;
    }

    protected void onPostExecute(String UploadStatus)
    {

        TextView UploadText=TextViewWeakReference.get();
        UploadText.setText(UploadStatus);
    }



}