package com.example.layout.myeventapplication;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import java.lang.ref.WeakReference;

/**
 * Created by Sahithi on 8/12/2015.
 */
public class BottomFragmentAsyncTask extends AsyncTask<String,Void,String> {
    private final WeakReference<FragmentManager> adapterReference;
    BottomFragment fragment;

    // RecyclerAdapter adapter;
    public BottomFragmentAsyncTask(FragmentManager adapter, BottomFragment fragment)
    {         adapterReference=new WeakReference<FragmentManager>(adapter);


    }

    @Override
    protected String doInBackground(String...urls){

        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String t="timewaste";

return t;
    }
    @Override
    protected void onPostExecute(String text )
    {
        FragmentManager adapter=adapterReference.get();
        adapter.beginTransaction().setCustomAnimations(
                R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom
        ).replace(R.id.fragment_bottom, BottomFragment.newInstance(1)).commit();
    }
}
