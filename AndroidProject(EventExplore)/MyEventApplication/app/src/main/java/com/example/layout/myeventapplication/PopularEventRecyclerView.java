package com.example.layout.myeventapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sahithi on 8/6/2015.
 */
public class PopularEventRecyclerView extends Fragment {

    PopularRecyclerAdapter recyclerAdapter;
    private ViewFlipper mViewFlipper;
    private GestureDetector mGestureDetector;
    private OnListItemSelectedListener mlistener;


    int[] resources = {
            R.drawable.music,
            R.drawable.health,
            R.drawable.business,
            R.drawable.spirituality,
            R.drawable.sports,
            R.drawable.fashion
    };

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    GridLayoutManager gridlayoutmanager;
    EventData mdata;
    PopupMenu popup;
    ArrayList<Integer> option;
    public static LruCache<String,Bitmap> imagecache;
    // private OnListItemSelectedListener mlistener;
    int counter;
    public static final String ARG_OPTION="argument_option";

    public static PopularEventRecyclerView newInstance(int option)
    {
        PopularEventRecyclerView fragment=new PopularEventRecyclerView();
        Bundle args=new Bundle();
        args.putInt(ARG_OPTION,option);
        fragment.setArguments(args);
        return fragment;

    }
    public PopularEventRecyclerView() {
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mdata = new EventData();
        recyclerAdapter=new PopularRecyclerAdapter(getActivity(),mdata.getMoviesList());
//        option=getArguments().getIntegerArrayList(ARG_OPTION);
        //String _option=Integer.toString(option.get(0) + 1);
        String url="http://www.example.com/events/popular/9.0";
        MyDownloadPopularEventAsynTask mydownload=new MyDownloadPopularEventAsynTask(recyclerAdapter,mdata);
        mydownload.execute(new String[]{url});
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
    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                mViewFlipper.setInAnimation(getActivity(), R.anim.abc_slide_in_top);
                mViewFlipper.setOutAnimation(getActivity(), R.anim.abc_slide_in_bottom);

                mViewFlipper.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                mViewFlipper.setInAnimation(getActivity(), R.anim.abc_slide_out_top);
                mViewFlipper.setOutAnimation(getActivity(), R.anim.abc_slide_out_bottom);

                mViewFlipper.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return onTouchEvent(event);

        //return super.onTouchEvent(event);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popularrecyclerview, container, false);

        mViewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);

        // Add all the images to the ViewFlipper
        for (int i = 0; i < resources.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(resources[i]);
            mViewFlipper.addView(imageView);
        }
        mViewFlipper.setInAnimation(getActivity(), android.R.anim.fade_in);
        mViewFlipper.setOutAnimation(getActivity(), android.R.anim.fade_out);

        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(getActivity(), customGestureDetector);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(2000);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler3);
        recyclerView.setHasFixedSize(true);




            gridlayoutmanager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(gridlayoutmanager);

        //  layoutManager=new LinearLayoutManager(getActivity());
        //  recyclerView.setLayoutManager(layoutManager);
        //  recyclerAdapter=new CategoryRecyclerAdapter(getActivity(),mdata.getCategoryList());
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickListener(new PopularRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                HashMap event = mdata.getItem(position);
                int id = (Integer) event.get("eventid");
                String url = "http://www.example.com/events/eventinfo/";
                String id_ = Integer.toString(id);
                String url_ = url + id_;
                DownloadPopularEventDetailAsyncTask downloadDeatilEventAsynTask = new DownloadPopularEventDetailAsyncTask(mlistener, position);
                downloadDeatilEventAsynTask.execute(new String[]{url_});

            }
        });
        return view;
    }

    public interface OnListItemSelectedListener
    {
        public void OnListItemSelectedPopular( HashMap event,int position);


    }
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mlistener=(OnListItemSelectedListener)activity;
        }catch(ClassCastException e)
        {

        }
    }


}
