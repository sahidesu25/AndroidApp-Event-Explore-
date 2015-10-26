package com.example.layout.myeventapplication;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by Sahithi on 8/12/2015.
 */
public class MyDownloadTicketsInfoAsyncTask extends AsyncTask<String,Void,String> {
    private final WeakReference<TextView> textViewWeakReference;
    private final WeakReference<TextView> textViewWeakReference2;
    int maxcapcity;
    EventData data;
    int ticketssold;

    public MyDownloadTicketsInfoAsyncTask(TextView textView,TextView textView1, EventData data,int maxcapacity) {
        textViewWeakReference = new WeakReference<TextView>(textView);
        textViewWeakReference2 = new WeakReference<TextView>(textView1);
        this.maxcapcity=maxcapacity;
        this.data = data;
    }

    protected String doInBackground(String... urls) {
        String text = null;
        for (String url : urls) {
         ticketssold = data.downloadTicketssold(url);
            text = Integer.toString(ticketssold);

        }
        return text;


    }

    protected void onPostExecute(String text) {
        if (textViewWeakReference != null && text != null) {
            final TextView textView = textViewWeakReference.get();
            if (textView != null) {
                String text_ = text + " " + "People going";
                textView.setText(text_);
            }

        }
        if (textViewWeakReference2 != null && text != null) {
            final TextView textView = textViewWeakReference2.get();
            if (textView != null) {
                int tickets_left=maxcapcity-ticketssold;
                String seatsleft=Integer.toString(tickets_left);
                String text_ = seatsleft + " " + "Seats Left";
                textView.setText(text_);
            }

        }


    }


}