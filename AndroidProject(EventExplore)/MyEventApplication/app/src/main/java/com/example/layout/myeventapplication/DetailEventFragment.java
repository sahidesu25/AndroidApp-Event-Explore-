package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/11/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailEventFragment extends Fragment {
    public static final String ARG_OPTION="argument_option";
    EventData data=new EventData();
    public static DetailEventFragment newInstance(int option)
    {
        DetailEventFragment fragment2=new DetailEventFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_OPTION,option);
        fragment2.setArguments(args);



        return fragment2;

    }

    public DetailEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.detailevent, container, false);
        ImageView image1=(ImageView)view.findViewById(R.id.image1);
        ImageView image2=(ImageView)view.findViewById(R.id.image2);
        TextView peoplegoing=(TextView)view.findViewById(R.id.peoplegoing);
        TextView dateandtime=(TextView)view.findViewById(R.id.datetime);
        TextView enddateandtime=(TextView)view.findViewById(R.id.enddatetime);
        TextView Description=(TextView)view.findViewById(R.id.desans);
        TextView Hostname=(TextView)view.findViewById(R.id.hostname);
        TextView hostnumber=(TextView)view.findViewById(R.id.phonenumber);
        TextView hostemail=(TextView)view.findViewById(R.id.email);
        TextView location=(TextView)view.findViewById(R.id.locationans);
        DetailFragmentActivity activity = (DetailFragmentActivity) getActivity();
        HashMap<String,?> Item=activity.getEvent_();

        String url=(String)Item.get("logourl");
        MyDownloadImageAsyncTask task=new MyDownloadImageAsyncTask(image1);
        task.execute(new String[] {url});
        MyDownloadImageAsyncTask task2=new MyDownloadImageAsyncTask(image2);
        task2.execute(new String[]{url});
        String vedioid=(String)Item.get("vedioid");
        int eventid=(Integer)Item.get("eventid");
        String eventid_=Integer.toString(eventid);
        String ticketurl_="http://www.example.com/events/ticketssold/";
        String ticketurl=ticketurl_+eventid_;
        MyDownloadTicketsSoldAsyncTask task3=new MyDownloadTicketsSoldAsyncTask(peoplegoing,data);
        task3.execute(new String[]{ticketurl});


        PlayerYoutubeFragment myFragment = PlayerYoutubeFragment.newInstance(vedioid);
        getFragmentManager().beginTransaction().replace(R.id.youtubecontainer, myFragment).commit();
        dateandtime.setText((String) Item.get("starteventdate"));
        enddateandtime.setText((String)Item.get("endeventdate"));
        String host="By"+" " +(String )Item.get("hostname");
        Hostname.setText(host);
        hostnumber.setText((String)Item.get("hostnumber"));
        hostemail.setText((String)Item.get("hostemail"));
        Description.setText((String)Item.get("eventname"));
        String city=(String)Item.get("city");
        String State=(String)Item.get("state");
        String Address=(String)Item.get("address");
        String add=city + " " + State + " " + Address;
        GoogleMapFragment mapFragment=GoogleMapFragment.newInstance(add);
        getFragmentManager().beginTransaction().replace(R.id.googlemapfragment,mapFragment).commit();

        location.setText(city + " " + State + " " + Address);













        return view;
    }


    public void onDestroyView()
    {
        FragmentManager mFragmentMgr= getFragmentManager();
        FragmentTransaction mTransaction = mFragmentMgr.beginTransaction();
        Fragment childFragment =mFragmentMgr.findFragmentByTag("unique_tag");
        mTransaction.remove(childFragment);
       // Fragment childFragment1 =mFragmentMgr.findFragmentById(R.id.googlemapfragment);
       // mTransaction.remove(childFragment1);


        // mTransaction.commit();
        super.onDestroyView();
    }
}