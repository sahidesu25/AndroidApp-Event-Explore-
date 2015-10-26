package com.example.layout.myeventapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sahithi on 8/6/2015.
 */
public class UpcommingEventsFragment extends Fragment {
    UpcommingRecyclerAdapter recyclerAdapter,recyclerAdapter1,recyclerAdapter2;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;

    LinearLayoutManager layoutManager;
    GridLayoutManager gridlayoutmanager;
    EventData mdata,mdata1,mdata2;
    public static LruCache<String,Bitmap> imagecache;
    // private OnListItemSelectedListener mlistener;
    int counter;
    public static final String ARG_OPTION="argument_option";

    public static UpcommingEventsFragment newInstance(int option)
    {
        UpcommingEventsFragment fragment=new UpcommingEventsFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_OPTION,option);
        fragment.setArguments(args);
        return fragment;

    }
    public UpcommingEventsFragment() {
    }
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mdata = new EventData();
        mdata1=new EventData();
        mdata2=new EventData();
        recyclerAdapter=new UpcommingRecyclerAdapter(getActivity(),mdata.getMoviesList());
        recyclerAdapter1=new UpcommingRecyclerAdapter(getActivity(),mdata1.getMoviesList());
        recyclerAdapter2=new UpcommingRecyclerAdapter(getActivity(),mdata2.getMoviesList());


        //String _option=Integer.toString(option.get(0) + 1);
        String url="http://www.example.com/events/weekend/";
        MyDownloadUpcommingEventAsynTask mydownload=new MyDownloadUpcommingEventAsynTask(recyclerAdapter,mdata);
        mydownload.execute(new String[]{url});

        String url1="http://www.example.com/events/month/";
        MyDownloadUpcommingEventTask2 mydownload1=new MyDownloadUpcommingEventTask2(recyclerAdapter1,mdata1);
        mydownload1.execute(new String[]{url1});

        String url2="http://www.example.com/events/nextmonth/";
        MyDownloadUpcommingEventTask3 mydownload2=new MyDownloadUpcommingEventTask3(recyclerAdapter2,mdata2);
        mydownload2.execute(new String[]{url2});


        if(imagecache==null)
        {
            final int maxMemory=(int) (Runtime.getRuntime().maxMemory()/1024);
            //use 1/8th of the memory
            final int cachesize=maxMemory/8;
            imagecache=new LruCache<String,Bitmap>(cachesize) {
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount()/1024;
                }
            };
        }
        //Log.d(TAG, this + ": onCreate()");
        if(savedInstanceState==null)
        {
            counter=0;
        }
        else
        {
            counter=savedInstanceState.getInt("counter");
        }
        setRetainInstance(true);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.upcommingrecyclerview, container, false);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler1);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(recyclerAdapter);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler2);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager1);
        recyclerView2.setAdapter(recyclerAdapter1);

        recyclerView3 = (RecyclerView) view.findViewById(R.id.recycler3);
        recyclerView3.setHasFixedSize(true);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(layoutManager2);
        recyclerView3.setAdapter(recyclerAdapter2);
        return view;
    }




}
