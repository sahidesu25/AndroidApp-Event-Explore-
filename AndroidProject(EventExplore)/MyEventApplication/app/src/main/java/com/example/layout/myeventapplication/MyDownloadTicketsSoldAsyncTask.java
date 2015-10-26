package com.example.layout.myeventapplication;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by Sahithi on 8/12/2015.
 */
public class MyDownloadTicketsSoldAsyncTask extends AsyncTask<String,Void,String> {
    private final WeakReference<TextView> textViewWeakReference;
    EventData data;
    public MyDownloadTicketsSoldAsyncTask(TextView textView,EventData data)
    {
        textViewWeakReference=new WeakReference<TextView>(textView);
        this.data=data;
    }
    protected String doInBackground(String... urls)
    {
        String text=null;
        for(String url:urls)
        {
            int value=data.downloadTicketssold(url);
            text=Integer.toString(value);

        }
        return text;


    }
    protected void onPostExecute(String text)
    {
        if(textViewWeakReference!=null && text!=null)
        {
            final TextView textView=textViewWeakReference.get();
            if(textView!=null)
            {String text_=text+" "+"People going";
                textView.setText(text_);
            }

        }
    }


}