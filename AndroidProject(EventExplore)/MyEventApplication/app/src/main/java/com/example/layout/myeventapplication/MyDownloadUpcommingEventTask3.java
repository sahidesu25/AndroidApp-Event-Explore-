package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/18/2015.
 */

import android.os.AsyncTask;

import java.lang.ref.WeakReference;


;

/**
 * Created by Sahithi on 8/6/2015.
 */
public class MyDownloadUpcommingEventTask3 extends AsyncTask<String,Void,EventData> {
    private final WeakReference<UpcommingRecyclerAdapter> adapterReference;
    EventData Data;
    // RecyclerAdapter adapter;
    public MyDownloadUpcommingEventTask3(UpcommingRecyclerAdapter adapter,EventData Data)
    {         adapterReference=new WeakReference<UpcommingRecyclerAdapter>(adapter);
        this.Data=Data;

    }

    @Override
    protected EventData doInBackground(String...urls){
        EventData threaddata=new EventData();
        for(String url:urls)
        {
            threaddata.downloadEventDataJson(url);
        }

        return threaddata;
    }
    @Override
    protected void onPostExecute(EventData threaddata)
    {
        Data.eventsList.clear();
        for(int i=0;i<threaddata.getSize();i++)
        {
            Data.eventsList.add(threaddata.eventsList.get(i));
        }
        if(adapterReference!=null)
        {

            UpcommingRecyclerAdapter adapter=adapterReference.get();
            //adapter=RecyclerViewFragment.recyclerAdapter;
            if(adapter!=null)
            {
                adapter.notifyDataSetChanged();
            }
        }
    }
}


