package com.example.layout.myeventapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xgc1986.ripplebutton.utils.RippleDrawableHelper;

import android.app.Fragment;

/**
 * Created by Sahithi on 8/15/2015.
 */
public class AddEventInfoFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    public static final String ARG_OPTION="argument_option";
    public static List<HashMap<String,?>> option_;
    public static final int REQUEST_DATE=0;
    CategoryData categoryData;
    List<Map<String,?>> categorylist;
    EditText eventname_;
    EditText eventprice_;
    EditText maxcapacity_;
    EditText logourl_;
    EditText vediourl_;
    EditText rating_;
    TextView startdate;
    TextView starttime;
    TextView Endtime;
    TextView Enddate;
    String shour,ehour;
    String smin,emin;
    private OnButtonClickListener buttonListener;
    String syear,eyear;
    String smonth,emonth;
    String sday,eday;
    String eventname;
    String logourl,vediourl;
    Double eventprice,rating;
    Integer maxcapacity;
    HashMap addeventmap=new HashMap();
    Spinner spinner2;


    boolean srttime=false,srtdate=false;
    boolean endtime=false,enddate=false;
    public static AddEventInfoFragment fragment;


    public static AddEventInfoFragment newInstance(List<HashMap<String,?>> option)
    {
       fragment=new AddEventInfoFragment();
        Bundle args=new Bundle();
       // args.putInt(ARG_OPTION,option);
        fragment.setArguments(args);
        option_=option;
        return fragment;

    }

    public AddEventInfoFragment() {
         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.eventinfo, container, false);
       eventname_=(EditText)view.findViewById(R.id.eventname);
       eventprice_=(EditText)view.findViewById(R.id.eventprice);
      maxcapacity_=(EditText)view.findViewById(R.id.maximmumcapacity);
       logourl_=(EditText)view.findViewById(R.id.logourl);
         vediourl_=(EditText)view.findViewById(R.id.vediourl);
         rating_=(EditText)view.findViewById(R.id.rating);
       spinner2 = (Spinner) view.findViewById(R.id.spinner2);
       startdate=(TextView)view.findViewById(R.id.startdayandtime);
        starttime=(TextView)view.findViewById(R.id.enddayandtime);
Enddate=(TextView)view.findViewById(R.id.endday);
        Endtime=(TextView)view.findViewById(R.id.endtime);
        categoryData=new CategoryData();
        final RippleView rippleView = (RippleView) view.findViewById(R.id.more);
        categoryData.loadLocalCategoryDataJson(getActivity());
categorylist=categoryData.getCategoryList();
        List list = new ArrayList();
        for(int i=0;i<categoryData.getSize();i++)
        {
            HashMap categorymap=categoryData.getItem(i);
            list.add(categorymap.get("categoryname"));
        }
        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        String categoryselected=spinner2.getSelectedItem().toString();
        Toast.makeText(getActivity(), categoryselected, Toast.LENGTH_LONG);
        Button datepickerbuuton1=(Button)view.findViewById(R.id.button1);
        datepickerbuuton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srtdate=true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                       fragment,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(true);
                dpd.vibrate(true);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        Button timepickerbutton1=(Button)view.findViewById(R.id.button2);
        timepickerbutton1.setBackground(RippleDrawableHelper.createRippleDrawable(timepickerbutton1, 0x80ffffff, R.drawable.timebutton));

        timepickerbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srttime = true;
                Calendar now = Calendar.getInstance();

                TimePickerDialog tpd = TimePickerDialog.newInstance(fragment,

                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                tpd.setThemeDark(true);
                tpd.vibrate(true);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });

        Button DatePickerbutton2=(Button)view.findViewById(R.id.button3);
        DatePickerbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enddate=true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        fragment,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(true);
                dpd.vibrate(true);
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });
        Button TimePickerbutton2=(Button)view.findViewById(R.id.button4);
        TimePickerbutton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                endtime=true;
                Calendar now = Calendar.getInstance();

                TimePickerDialog tpd = TimePickerDialog.newInstance(fragment,

                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                tpd.setThemeDark(true);
                tpd.vibrate(true);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");


            }
        });
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                Date sdate=new Date();
                Date edate=new Date();
                String StartDateandTime;
                String EndDateandTime;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startDateandTime=syear+"-"+smonth+"-"+sday+" "+shour+":"+smin+":"+"00";
                String endDateandTime=eyear+"-"+emonth+"-"+eday+" "+ehour+":"+emin+":"+"00";
                try
                {
                    sdate=formatter.parse(startDateandTime);
                    StartDateandTime=formatter.format(sdate);
                    addeventmap.put("startdateandtime",StartDateandTime);
                    edate= formatter.parse(endDateandTime);
                    EndDateandTime=formatter.format(edate);
                    addeventmap.put("enddateandtime",EndDateandTime);

                }
                catch (Exception e)
                {

                }
                eventname=eventname_.getText().toString();
                String _eventprice=eventprice_.getText().toString();
                eventprice=Double.parseDouble(_eventprice);
                String   _maxcapacity=maxcapacity_.getText().toString();
                maxcapacity=Integer.parseInt(_maxcapacity);
                logourl=logourl_.getText().toString();
                vediourl=vediourl_.getText().toString();
                String _rating=rating_.getText().toString();
                rating=Double.parseDouble(_rating);
                addeventmap.put("eventname",eventname);
                addeventmap.put("eventprice",eventprice);
                addeventmap.put("maxcapacity",maxcapacity);
                addeventmap.put("logourl",logourl);
                addeventmap.put("vediourl",vediourl);
                String categoryselected=spinner2.getSelectedItem().toString();
                addeventmap.put("category",categoryselected);
                addeventmap.put("rating",rating);
                HashMap locationinfo=option_.get(0);
                addeventmap.put("city",locationinfo.get("city"));
                addeventmap.put("state",locationinfo.get("state"));
                addeventmap.put("address",locationinfo.get("address"));
                addeventmap.put("zipcode",locationinfo.get("zipcode"));
                HashMap hostinfo=option_.get(1);
                addeventmap.put("hostname",hostinfo.get("hostname"));
                addeventmap.put("contactnumber",hostinfo.get("contactnumber"));
                addeventmap.put("email",hostinfo.get("email"));
                String url_ = "http://www.example.com/events/eventinfo/post/";
                UploadEventInfoAsyncTask uploadInfoAsyncTask = new UploadEventInfoAsyncTask(addeventmap);
                uploadInfoAsyncTask.execute(new String[]{url_});
                buttonListener.OnSubmitButtonClick(rippleView);



            }

        });

       /* Button submit=(Button)view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date sdate=new Date();
                Date edate=new Date();
                String StartDateandTime;
                String EndDateandTime;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startDateandTime=syear+"-"+smonth+"-"+sday+" "+shour+":"+smin+":"+"00";
                String endDateandTime=eyear+"-"+emonth+"-"+eday+" "+ehour+":"+emin+":"+"00";
                try
                {
                    sdate=formatter.parse(startDateandTime);
                   StartDateandTime=formatter.format(sdate);
                    addeventmap.put("startdateandtime",StartDateandTime);
                    edate= formatter.parse(endDateandTime);
                    EndDateandTime=formatter.format(edate);
                    addeventmap.put("enddateandtime",EndDateandTime);

                }
                catch (Exception e)
                {

                }
                eventname=eventname_.getText().toString();
               String _eventprice=eventprice_.getText().toString();
               eventprice=Double.parseDouble(_eventprice);
             String   _maxcapacity=maxcapacity_.getText().toString();
                maxcapacity=Integer.parseInt(_maxcapacity);
                logourl=logourl_.getText().toString();
                vediourl=vediourl_.getText().toString();
                String _rating=rating_.getText().toString();
                rating=Double.parseDouble(_rating);
                addeventmap.put("eventname",eventname);
                addeventmap.put("eventprice",eventprice);
                addeventmap.put("maxcapacity",maxcapacity);
                addeventmap.put("logourl",logourl);
                addeventmap.put("vediourl",vediourl);
                String categoryselected=spinner2.getSelectedItem().toString();
                addeventmap.put("category",categoryselected);
                addeventmap.put("rating",rating);
                HashMap locationinfo=option_.get(0);
                addeventmap.put("city",locationinfo.get("city"));
                addeventmap.put("state",locationinfo.get("state"));
                addeventmap.put("address",locationinfo.get("address"));
                addeventmap.put("zipcode",locationinfo.get("zipcode"));
                HashMap hostinfo=option_.get(1);
                addeventmap.put("hostname",hostinfo.get("hostname"));
                addeventmap.put("contactnumber",hostinfo.get("contactnumber"));
                addeventmap.put("email",hostinfo.get("email"));
                String url_ = "http://www.example.com/events/eventinfo/post/";
                UploadEventInfoAsyncTask uploadInfoAsyncTask = new UploadEventInfoAsyncTask(addeventmap);
                uploadInfoAsyncTask.execute(new String[]{url_});







            }
        });*/


        return view;
    }





    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String time = hourString+"h"+minuteString;
        if(srttime)
        {
            this.shour=Integer.toString(hourOfDay);
            this.smin=Integer.toString(minute);
            starttime.setText(time);
            srttime=false;
        }
        if(endtime)
        {
            this.ehour=Integer.toString(hourOfDay);
            this.emin=Integer.toString(minute);

            Endtime.setText(time);
            endtime=false;
        }

    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        if(srtdate)
        {
            this.syear=Integer.toString(year);
            this.smonth=Integer.toString(monthOfYear);
            this.sday=Integer.toString(dayOfMonth);

            startdate.setText(date);
            srtdate=false;
        }
        if(enddate)
        {
            this.eyear=Integer.toString(year);
            this.emonth=Integer.toString(monthOfYear);
            this.eday=Integer.toString(dayOfMonth);

            Enddate.setText(date);
            enddate=false;
        }


    }
    public interface OnButtonClickListener
    {
        public void OnSubmitButtonClick(View v);

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
