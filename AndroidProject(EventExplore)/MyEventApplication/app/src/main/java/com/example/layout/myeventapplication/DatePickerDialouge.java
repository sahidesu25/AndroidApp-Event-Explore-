package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/15/2015.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Sahithi on 7/8/2015.
 */
public class DatePickerDialouge extends DialogFragment {
    public static final String ARGS_DATE = "argument_date";
    private Date mDate;
    TextView name;
    TextView email;
    public DatePickerDialouge() {
        // Required empty public constructor
    }


    public Dialog onCreateDialog(Bundle savedInstance)
    {
        mDate = (Date) getArguments().getSerializable(ARGS_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        View v = getActivity().getLayoutInflater().inflate(R.layout.datepickerfragmentdialouge, null);
        DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                mDate = new GregorianCalendar(year, month, day).getTime();
                getArguments().putSerializable(ARGS_DATE, mDate);
            }
        });
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(v)
                .setTitle("Date Picker")
                .setMessage("Please select a date !")
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (getTargetFragment() != null) {
                                    Intent i = new Intent();
                                    i.putExtra(ARGS_DATE, mDate);
                                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                                } else {
                                    Toast.makeText(getActivity(), "no need to return the results", Toast.LENGTH_LONG).show();
                                }
                            }

                        });

        return alertDialogBuilder.create();




    }
    public static DatePickerDialouge newInstance(Date d) {
        DatePickerDialouge fragment = new DatePickerDialouge();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_DATE, d);
        fragment.setArguments(args);
        return fragment;
    }


}