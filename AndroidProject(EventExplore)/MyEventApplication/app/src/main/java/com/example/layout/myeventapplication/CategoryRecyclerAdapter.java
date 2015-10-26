package com.example.layout.myeventapplication;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sahithi on 7/31/2015.
 */
public class CategoryRecyclerAdapter extends  RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {


    private List<Map<String, ?>> mDataSet;
    private Context mContext;
    OnItemClickListener mlistener;


    public CategoryRecyclerAdapter(Context context, List<Map<String, ?>> mDataSet) {
        this.mDataSet = mDataSet;
        mContext = context;
    }

    public int getItemCount() {
        return mDataSet.size();
    }

    public HashMap getItemAtPosition(int position) {
        if (position >= 0 && position < mDataSet.size()) {
            return (HashMap) mDataSet.get(position);
        } else return null;
    }
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        Map<String,?> category=mDataSet.get(position);
        holder.bindCategoryData(category);
    }

    public CategoryRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v;


        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categoryrow, parent, false);

    ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public interface OnItemClickListener
    {
        public void OnItemClick(View v, int position);

    }
    public void setOnItemClickListener(final OnItemClickListener mlistener )
    {
        this.mlistener=mlistener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView categoryname;
        ImageView image;

        CardView cardView;

        public ViewHolder(View v) {
            super(v);


            categoryname = (TextView) v.findViewById(R.id.categoryname);
            image = (ImageView) v.findViewById((R.id.categoryimage));


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if (mlistener != null) {
                     mlistener.OnItemClick(v, getPosition());
                     }
                }
            });


        }
        public void bindCategoryData(Map<String,?> item)
        {
            image.setImageResource((Integer) item.get("image"));
            categoryname.setText((String) item.get("categoryname"));
        }


    }
}


