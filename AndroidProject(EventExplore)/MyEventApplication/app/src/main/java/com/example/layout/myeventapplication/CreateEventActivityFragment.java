package com.example.layout.myeventapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.xgc1986.ripplebutton.widget.RippleButton;

import java.util.HashMap;


/**
 * A placeholder fragment containing a simple view.
 */
public class CreateEventActivityFragment extends Fragment {

    String Hostname, contactnumber, email;
    EditText hostname;
    EditText email_;
    EditText contactnum;
    TextView Response;
    Button next;
   public  HashMap hostinfo;
    private OnButtonClickListener buttonListener;
    public static final String ARG_OPTION="argument_option";

    public static CreateEventActivityFragment newInstance(int option)
    {
        CreateEventActivityFragment fragment=new CreateEventActivityFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_OPTION,option);
        fragment.setArguments(args);
        return fragment;

    }

    private HashMap createHostInfo(String hostname,  String contactnumber,
                              String email) {
         hostinfo = new HashMap();

        hostinfo.put("hostname", hostname);
        hostinfo.put("contactnumber", contactnumber);
        hostinfo.put("email", email);

        return hostinfo;
    }


    public CreateEventActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.hostinfo, container, false);
        hostname=(EditText)view.findViewById(R.id.HostName);
        email_=(EditText)view.findViewById(R.id.email);
     contactnum=(EditText)view.findViewById(R.id.contact);
        Response=(TextView)view.findViewById(R.id.Response);
      next=(Button)view.findViewById(R.id.next);
        int buttonColor = 0xff33b5e5; //holo_blue_light
        int rippleColor = 0x80ffffff; //transparent white

        final RippleView rippleView = (RippleView) view.findViewById(R.id.more);
        final RippleView rippleView1=(RippleView)view.findViewById(R.id.more2);
        final Button submit=(Button)view.findViewById(R.id.hostnext);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hostname = hostname.getText().toString();
                contactnumber = contactnum.getText().toString();
                email = email_.getText().toString();
                HashMap hostinfo = new HashMap();
                hostinfo = createHostInfo(Hostname, contactnumber, email);
                String url_ = "http://www.example.com/events/insert/hostinfo/post/";
                UploadHostInfoAsyncTask uploadHostInfoAsyncTask = new UploadHostInfoAsyncTask(hostinfo, Response);
                uploadHostInfoAsyncTask.execute(new String[]{url_});

            }
        });
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                submit.setBackgroundColor(submit.getContext().getResources().getColor(R.color.Gray_LightGray));
                next.setBackgroundColor(submit.getContext().getResources().getColor(R.color.Blue_DodgerBlue));

            }

        });
        rippleView1.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                buttonListener.OnButtonClick(rippleView1, hostinfo);

            }

        });
        rippleView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  buttonListener.OnButtonClick(v, hostinfo);

            }
        });





        return view;
    }

    public interface OnButtonClickListener
    {
        public void OnButtonClick(View v,HashMap Hostinfo);

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
