package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/6/2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sahithi on 8/3/2015.
 */
public class UpcommingRecyclerAdapter extends  RecyclerView.Adapter<UpcommingRecyclerAdapter.ViewHolder> {
    private List<Map<String, ?>> mDataSet;
    private Context mContext;
    OnItemClickListener mlistener;


    public UpcommingRecyclerAdapter(Context context, List<Map<String, ?>> mDataSet) {
        this.mDataSet = mDataSet;
        mContext = context;
    }

    public int getItemCount() {
        return mDataSet.size();
    }
    public interface OnItemClickListener
    {


    }
    public void setOnItemClickListener(final OnItemClickListener mlistener )
    {
        this.mlistener=mlistener;
    }

    public HashMap getItemAtPosition(int position) {
        if (position >= 0 && position < mDataSet.size()) {
            return (HashMap) mDataSet.get(position);
        } else return null;
    }
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        Map<String,?> event=mDataSet.get(position);
        holder.bindData(event);
    }
    public int getItemViewType(int position){
        Map<String,?> map=mDataSet.get(position);
        if(position%4==0)
        {
            return 0;
        }
        else if(position%4==1)
        {
            return 1;
        }
        else if(position%4==2)
        {
            return 2;
        }
        else if(position%4==3) {
            return 3;
        }
        else
        {
            return 0;
        }

    }

    public UpcommingRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v;
        switch(ViewType)
        {
            case 0:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.populareventsrow, parent, false);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.populareventsrow1, parent, false);
                break;
            case 2:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.populareventsrow2, parent, false);
                break;
            case 3:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.poulareventsrow3, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.populareventsrow, parent, false);

        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView address;
        TextView dateandtime;

        ImageView logoimage;


        public ViewHolder(View v) {
            super(v);


            logoimage = (ImageView) v.findViewById((R.id.logoimage));

            address = (TextView) v.findViewById(R.id.locationans);
            dateandtime = (TextView) v.findViewById(R.id.datetime);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // if (mlistener != null) {
                    // mlistener.OnItemClick(v, getPosition());
                    // }
                }
            });

        }
        public void bindData(Map<String,?> item)
        {
            // image.setImageResource((Integer) item.get("image"));
            String url=(String) item.get("logourl");
            Bitmap bitmap=EventRecyclerView.imagecache.get(url);
            if(bitmap!=null)
            {
                logoimage.setImageBitmap(bitmap);
            }
            else
            {
                MyDownloadImageAsyncTask task=new MyDownloadImageAsyncTask(logoimage);

                task.execute(new String[] {url});
            }

            String city=(String)item.get("city");
            String State=(String)item.get("state");

            address.setText(city+" "+State);
            dateandtime.setText((String)item.get("starteventdate"));


        }


    }


}
