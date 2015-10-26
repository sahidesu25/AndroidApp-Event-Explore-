package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/10/2015.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sahithi on 6/7/2015.
 */
public class MyImageAdapter extends BaseAdapter {
    private Context context;
    public static LruCache<String,Bitmap> imagecache;

    private final List<Map<String, ?>> movieList;
    //int position;

    public MyImageAdapter(Context context, List<Map<String, ?>> moviesList) {
        this.context = context;
        this.movieList = moviesList;
    }

    public int getCount() {
        return movieList.size();
    }



    public Object getItem(int position) {
        return movieList.get(position);

    }

    private class imageViewClickListene implements View.OnClickListener {
        int position;
        public imageViewClickListene(int pos)
        {
            this.position = pos;
        }

        public void onClick(View view) {

           if (view.getContext() instanceof UnfoldableActivity) {
                UnfoldableActivity activity = (UnfoldableActivity) view.getContext();
                 view.getTag();
                activity.openDetails(view, movieList.get(position));
            }
        }

        // here we  remove the selected item

    }
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = null;
        ViewHolder holder=null;
        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.ticketelement, parent, false);
            holder= new ViewHolder();
            holder.image=(ImageView) rowView.findViewById((R.id.GridImage));
            holder.image.setOnClickListener(new imageViewClickListene(position) );
            holder.rating=(RatingBar) rowView.findViewById(R.id.GridRatings);
            holder.name=(TextView)rowView.findViewById(R.id.name);
            rowView.setTag(holder);
        }
        else
        {
            rowView=view;
            holder=(ViewHolder)view.getTag();
            holder.image.setOnClickListener(new imageViewClickListene(position) );
        }
        if(position%2==0)
        {
            rowView.setBackgroundResource(R.color.Blue_CadetBlue);


        }
        else
        {
            rowView.setBackgroundResource(R.color.dim_foreground_disabled_material_light);
        }


        Map<String,?> item=movieList.get(position);
        String url=(String) item.get("logourl");


            MyDownloadImageAsyncTask task=new MyDownloadImageAsyncTask(holder.image);

            task.execute(new String[] {url});


        Double rating =(Double)item.get("rating");
        holder.rating.setRating((float) (rating / 2));
        holder.name.setText((String) item.get("eventname"));


        return rowView;
    }
}