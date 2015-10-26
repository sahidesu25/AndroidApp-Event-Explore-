package com.example.layout.myeventapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CreateEventActivity extends ActionBarActivity implements CreateEventActivityFragment.OnButtonClickListener,AddEventLocationInfoFragment.OnButtonClickListener,AddEventInfoFragment.OnButtonClickListener {
    List<HashMap<String,?>> eventlist =new ArrayList<HashMap<String, ?>>();
    HashMap hostinfo=new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        getFragmentManager().beginTransaction().replace(R.id.fragment,CreateEventActivityFragment.newInstance(1)).commit();
        overridePendingTransition(R.animator.activity_open_scale,R.animator.activity_close_scale);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }
    public void OnNextButtonClick(View v,HashMap location)
    {
        eventlist.add(location);
        eventlist.add(hostinfo);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction,  AddEventLocationInfoFragment.newInstance(1),AddEventInfoFragment.newInstance(eventlist), R.id.fragment);
        fragmentTransactionExtended.addTransition(FragmentTransactionExtended.GLIDE
        );
        fragmentTransactionExtended.commit();
        getFragmentManager().beginTransaction().replace(R.id.fragment, AddEventInfoFragment.newInstance(eventlist)).commit();

    }
    public void OnSubmitButtonClick(View v)
    {
        v.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Event is sucessfully created", Toast.LENGTH_SHORT);

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
       /* FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction, AddEventInfoFragment.newInstance(eventlist), SucessfulCreateFragment.newInstance(1), R.id.fragment);
        fragmentTransactionExtended.addTransition(FragmentTransactionExtended.STACK
        );
        fragmentTransactionExtended.commit();
        getFragmentManager().beginTransaction().replace(R.id.fragment,SucessfulCreateFragment.newInstance(1)).commit();*/

    }
    public void OnButtonClick(View v,HashMap hostinfo)
    {
        this.hostinfo=hostinfo;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction, CreateEventActivityFragment.newInstance(1), AddEventLocationInfoFragment.newInstance(1), R.id.fragment);
        fragmentTransactionExtended.addTransition(FragmentTransactionExtended.CUBE
       );
        fragmentTransactionExtended.commit();
        getFragmentManager().beginTransaction().replace(R.id.fragment,AddEventLocationInfoFragment.newInstance(1)).commit();
    }
    protected void onPause()
    {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.animator.leftin, R.animator.rightout);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
