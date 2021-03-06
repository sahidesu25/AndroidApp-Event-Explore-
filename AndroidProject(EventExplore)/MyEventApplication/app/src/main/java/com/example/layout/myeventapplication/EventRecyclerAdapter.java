package com.example.layout.myeventapplication;

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
public class EventRecyclerAdapter extends  RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    private List<Map<String, ?>> mDataSet;
    private Context mContext;
    OnItemClickListener mlistener;


    public EventRecyclerAdapter(Context context, List<Map<String, ?>> mDataSet) {
        this.mDataSet = mDataSet;
        mContext = context;
    }

    public int getItemCount() {
        return mDataSet.size();
    }
    public interface OnItemClickListener
    {

        public void OnOverFlowMenuClick(View v, int position);
        public void OnItemClick(View v,int position);
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

    public EventRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v;
   switch(ViewType)
   {
       case 0:
           v = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.eventrow, parent, false);
           break;
       case 1:
           v = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.eventrow1, parent, false);
           break;
       case 2:
           v = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.eventrow2, parent, false);
           break;
       case 3:
           v = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.eventrow3, parent, false);
           break;
       default:
           v = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.eventrow, parent, false);

   }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView eventname;
        TextView address;
        TextView dateandtime;
        TextView peoplegoing;
        TextView seatsleft;
        RatingBar rating;
        ImageView logoimage;
        CardView cardView;
        TextView amount;
        ImageView overflow;

        public ViewHolder(View v) {
            super(v);

            cardView = (CardView) v.findViewById(R.id.cardview);
            eventname = (TextView) v.findViewById(R.id.eventextView);
            logoimage = (ImageView) v.findViewById((R.id.logoimage));
            rating = (RatingBar) v.findViewById(R.id.rating);
            address = (TextView) v.findViewById(R.id.locationans);
            dateandtime = (TextView) v.findViewById(R.id.datetime);
            peoplegoing = (TextView) v.findViewById(R.id.peoplegoing);
            seatsleft = (TextView) v.findViewById(R.id.seatsleftans);
            amount = (TextView) v.findViewById(R.id.amount);
            overflow = (ImageView) v.findViewById(R.id.overflow);
            cardView.setCardBackgroundColor(Color.TRANSPARENT);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if (mlistener != null) {
                    mlistener.OnItemClick(v, getPosition());
                     }
                }
            });
            if (overflow != null) {
                overflow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mlistener != null) {
                            mlistener.OnOverFlowMenuClick(v, getPosition());
                        }
                    }
                });


            }
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

            int eventid=(Integer)item.get("eventid");
            String eventid_=Integer.toString(eventid);
            int maxcapacity=(Integer)item.get("maxcapacity");

            String ticketurl_="http://www.example.com/events/ticketssold/";
            String ticketurl=ticketurl_+eventid_;

            MyDownloadTicketsInfoAsyncTask task2=new MyDownloadTicketsInfoAsyncTask(peoplegoing,seatsleft,new EventData(),maxcapacity);
            task2.execute(new String[] {ticketurl});
            eventname.setText((String) item.get("eventname"));
            Double rating_ =(Double)item.get("rating");
            rating.setRating((float)(rating_/ 2));
            String city=(String)item.get("city");
            String State=(String)item.get("state");
            String Address=(String)item.get("address");
            address.setText(city+" "+State+" "+Address);
            dateandtime.setText((String)item.get("starteventdate"));
            Double amount_ =(Double)item.get("eventprice");
            String amt="$"+Double.toString(amount_);
            amount.setText(amt);
        }


    }


}
