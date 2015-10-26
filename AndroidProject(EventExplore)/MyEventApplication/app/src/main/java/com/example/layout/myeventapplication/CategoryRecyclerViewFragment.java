package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 7/31/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryRecyclerViewFragment extends Fragment {
    CategoryRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    CategoryData mdata;
   private OnListItemSelectedListener mlistener;
    int counter;
    public static final String ARG_OPTION="argument_option";
    public static CategoryRecyclerViewFragment newInstance(int option)
    {
        CategoryRecyclerViewFragment fragment=new CategoryRecyclerViewFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_OPTION,option);
        fragment.setArguments(args);
        return fragment;

    }

    public CategoryRecyclerViewFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mdata = new CategoryData();
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
        View view= inflater.inflate(R.layout.categoryrecyclerview, container, false);
        mdata.loadLocalCategoryDataJson(getActivity());
        List<Map<String,?>> categoryList=mdata.getCategoryList();

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler1);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter=new CategoryRecyclerAdapter(getActivity(),mdata.getCategoryList());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new CategoryRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {

                mlistener.OnListItemSelected(position);
            }
        });
        return view;
    }
    public interface OnListItemSelectedListener
    {
        public void OnListItemSelected( int position);


    }
    @Override
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


