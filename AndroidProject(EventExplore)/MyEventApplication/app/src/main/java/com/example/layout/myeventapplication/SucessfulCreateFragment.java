package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/18/2015.
 */
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class SucessfulCreateFragment extends Fragment {
    public static final String ARG_OPTION="argument_option";
    public static SucessfulCreateFragment newInstance(int option)
    {
        SucessfulCreateFragment fragment=new SucessfulCreateFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_OPTION,option);
        fragment.setArguments(args);
        return fragment;

    }

    public SucessfulCreateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sucessfulcreate, container, false);
    }
}