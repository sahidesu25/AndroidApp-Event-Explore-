package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 7/31/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A placeholder fragment containing a simple view.
 */
public class EventRecyclerView extends Fragment {
    EventRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    UserLocalStore userLocalStore;
    LinearLayoutManager layoutManager;
    GridLayoutManager gridlayoutmanager;
    EventData mdata;
    User user;
    PopupMenu popup;
 int  option;
    public static LruCache<String,Bitmap> imagecache;
    private OnListItemSelectedListener mlistener;
    int counter;
    public static final String ARG_OPTION="argument_option";
    public static EventRecyclerView newInstance(int option)
    {
        EventRecyclerView fragment=new EventRecyclerView();
        Bundle args=new Bundle();
        args.putInt(ARG_OPTION,option);
        fragment.setArguments(args);
        return fragment;

    }

    public EventRecyclerView() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mdata = new EventData();
        userLocalStore =  new UserLocalStore(getActivity());
      user = userLocalStore.getLoggedInuser();

        recyclerAdapter=new EventRecyclerAdapter(getActivity(),mdata.getMoviesList());
      option=getArguments().getInt(ARG_OPTION);
        String _option=Integer.toString(option + 1);
        String url=EventData.PHP_SERVER+_option;
        MyDownloadEventAsyncTask mydownload=new MyDownloadEventAsyncTask(recyclerAdapter,mdata);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.eventrecyclerview, container, false);



        recyclerView=(RecyclerView)view.findViewById(R.id.recycler2);
        recyclerView.setHasFixedSize(true);



            layoutManager=new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);



      //  layoutManager=new LinearLayoutManager(getActivity());
      //  recyclerView.setLayoutManager(layoutManager);
      //  recyclerAdapter=new CategoryRecyclerAdapter(getActivity(),mdata.getCategoryList());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new EventRecyclerAdapter.OnItemClickListener() {
            public void OnOverFlowMenuClick(View v, final int position) {
                popup = new PopupMenu(getActivity(), v);
                MenuInflater inflater = popup.getMenuInflater();
                try {
                    Class<?> classPopupMenu = Class.forName(popup
                            .getClass().getName());
                    Field mPopup = classPopupMenu.getDeclaredField("mPopup");
                    mPopup.setAccessible(true);
                    Object menuPopupHelper = mPopup.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.save:
                                HashMap eventmap=new HashMap();
                               eventmap= mdata.getItem(position);
                                int eventid=(Integer)eventmap.get("eventid");
                                String username=user.usrname;
                                HashMap savedmap=new HashMap();
                                savedmap.put("EventId",eventmap.get("eventid"));
                                savedmap.put("username",username);
                                String url_="http://www.example.com/events/saved/insert/post/";
                                UploadSavedEventAsyncTask task =new UploadSavedEventAsyncTask(savedmap);
                                task.execute(new String[]{url_});



                                return true;
                            case R.id.action_share:
                                ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
                                HashMap event = mdata.getItem(position);
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                User user = userLocalStore.getLoggedInuser();
                                String text = "Your friend "+user.name+" shared the event "+ event.get("eventname") +" at "+ event.get("city")+event.get("state")+" on "+event.get("starteventdate")+ " with you";
                                intent.putExtra(Intent.EXTRA_TEXT,text);
                                shareActionProvider.setShareIntent(intent);
                                return true;
                            default:
                                return false;

                        }
                    }
                });
                inflater.inflate(R.menu.contextual_or_popupmenu, popup.getMenu());
                popup.show();

            }

            public void OnItemClick(View v, int position) {
                HashMap event = mdata.getItem(position);
                int id = (Integer) event.get("eventid");
                String url = "http://www.example.com/events/eventinfo/";
                String id_ = Integer.toString(id);
                String url_ = url + id_;
                DownloadDeatilEventAsynTask downloadDeatilEventAsynTask = new DownloadDeatilEventAsynTask(mlistener, position);
                downloadDeatilEventAsynTask.execute((new String[]{url_}));
            }

        });

        return view;
    }

    public interface OnListItemSelectedListener
    {
        public void OnListItemSelected( HashMap event,int position);


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


