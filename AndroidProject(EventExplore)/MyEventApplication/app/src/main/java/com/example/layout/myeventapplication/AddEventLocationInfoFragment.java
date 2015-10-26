package com.example.layout.myeventapplication;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.andexert.library.RippleView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Sahithi on 8/13/2015.
 */
public class AddEventLocationInfoFragment extends Fragment {
    public static final String ARG_OPTION="argument_option";
    String state,city, address, zipcode;
    EditText _state, _city,_address, _zipcode;
    private OnButtonClickListener buttonListener;

    HashMap location=new HashMap();
    public static AddEventLocationInfoFragment newInstance( int option)
    {
        AddEventLocationInfoFragment fragment=new AddEventLocationInfoFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_OPTION,option);
        fragment.setArguments(args);
        return fragment;

    }

    public AddEventLocationInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.eventlocation, container, false);

         _state=(EditText)view.findViewById(R.id.state);
      _city=(EditText) view.findViewById(R.id.city);
       _address=(EditText)view.findViewById(R.id.address);
       _zipcode=(EditText)view.findViewById(R.id.zipcode);
        final RippleView rippleView = (RippleView) view.findViewById(R.id.more);
        Button next=(Button)view.findViewById(R.id.next);
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                state=_state.getText().toString();
                location.put("state",state);

                city=_city.getText().toString();
                location.put("city",city);

                address=_address.getText().toString();
                location.put("address",address);

                zipcode=_zipcode.getText().toString();
                location.put("zipcode",zipcode);
                String url_ = "http://www.example.com/events/insert/location/post/";
                UploadEventLocationAsyncTask uploadHostInfoAsyncTask = new UploadEventLocationAsyncTask(location);
                uploadHostInfoAsyncTask.execute(new String[]{url_});
                buttonListener.OnNextButtonClick(rippleView,location);

            }

        });
     /*   next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                state=_state.getText().toString();
                location.put("state",state);

                city=_city.getText().toString();
                location.put("city",city);

                address=_address.getText().toString();
                location.put("address",address);

                zipcode=_zipcode.getText().toString();
                location.put("zipcode",zipcode);
                String url_ = "http://www.example.com/events/insert/location/post/";
                UploadEventLocationAsyncTask uploadHostInfoAsyncTask = new UploadEventLocationAsyncTask(location);
                uploadHostInfoAsyncTask.execute(new String[]{url_});
                buttonListener.OnNextButtonClick(view,location);




            }
        });*/









        return view;
    }



    public interface OnButtonClickListener
    {
        public void OnNextButtonClick(View v, HashMap location);

    }
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            buttonListener=(OnButtonClickListener)activity;
        }catch(ClassCastException e)
        {

        }
    }
}


