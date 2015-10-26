package com.example.layout.myeventapplication;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.util.HashMap;


public class DetailFragmentActivity extends ActionBarActivity {
    HashMap event=new HashMap();
    private BottomFragment m_bottomFragment;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fragment);
        Intent intent = getIntent();
        userLocalStore = new UserLocalStore(this);
        event=(HashMap)intent.getSerializableExtra("event");
        m_bottomFragment = BottomFragment.newInstance(1);
        overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);
       // GoogleMapFragment mapFragment=GoogleMapFragment.newInstance();
       // getSupportFragmentManager().beginTransaction().replace(R.id.googlemapfragment,mapFragment).commit();

        getSupportFragmentManager().beginTransaction().hide(m_bottomFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, DetailEventFragment.newInstance(1)).commit();
        BottomFragmentAsyncTask  task=new BottomFragmentAsyncTask(getSupportFragmentManager(),m_bottomFragment);
        String url=" ";
        task.execute(new String[]{url});




    }
    public HashMap getEvent_()
    {
        return event;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);


        return true;

       }
    protected void onPause()
    {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.abc_slide_out_top, R.anim.abc_slide_in_bottom);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        User user = userLocalStore.getLoggedInuser();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.save)
        {
            HashMap eventmap=new HashMap();
            eventmap= event;
            int eventid=(Integer)eventmap.get("eventid");
            String username=user.usrname;
            HashMap savedmap=new HashMap();
            savedmap.put("EventId",eventmap.get("eventid"));
            savedmap.put("username",username);
            String url_="http://www.example.com/events/saved/insert/post/";
            UploadSavedEventAsyncTask task =new UploadSavedEventAsyncTask(savedmap);
            task.execute(new String[]{url_});
            Toast.makeText(this, "Event is added to your saved list", Toast.LENGTH_SHORT);

            return true;


        }
        if (id==R.id.action_share)
        {
            MenuItem menuItem = item;
            ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");

            String text = "Your frined "+user.name+" shared the event "+ event.get("eventname") +" at "+ event.get("city")+event.get("state")+" on "+event.get("starteventdate")+ " with you";
            intent.putExtra(Intent.EXTRA_TEXT,text);
            mShareActionProvider.setShareIntent(intent);

            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
