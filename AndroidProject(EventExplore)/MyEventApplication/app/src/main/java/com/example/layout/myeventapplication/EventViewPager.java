package com.example.layout.myeventapplication;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.Map;


public class EventViewPager extends ActionBarActivity implements EventRecyclerView.OnListItemSelectedListener,PopularEventRecyclerView.OnListItemSelectedListener {
    ViewPager mViewPager;
    ViewPagerAdapter pagerAdapter;
    HashMap event_;
    UserLocalStore userLocalStore;
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_pager);
        Intent intent = getIntent();
        userLocalStore = new UserLocalStore(this);
        int categoryPosition=intent.getIntExtra("category", 0);
       overridePendingTransition(R.animator.activity_open_translate,R.animator.activity_close_scale);

        mViewPager=(ViewPager)findViewById(R.id.viewPagerActivity);
        pagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),3,categoryPosition);
        mViewPager.setAdapter(pagerAdapter);
        animations();
    }
    public void animations()
    {
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            public void transformPage(View view, float position) {
                //scaling effect
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    } else {
                        view.setTranslationX(-horzMargin + vertMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }
        });

    }
    protected void onPause()
    {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.animator.activity_open_scale, R.animator.activity_close_translate);
    }
    public void OnListItemSelectedPopular(HashMap event,int position)
    {
        event_=event;
        Intent intent=new Intent(this,DetailFragmentActivity.class);
        intent.putExtra("event",event_);
        startActivity(intent);


    }
    public void OnListItemSelected(HashMap event,int position)
    {

       // getSupportFragmentManager()
              //  .beginTransaction()
              //  .replace(R.id.fragment, DetailEventFragment.newInstance(7)).addToBackStack("null").commit();
        event_=event;
        Intent intent=new Intent(this,DetailFragmentActivity.class);
        intent.putExtra("event",event_);
        startActivity(intent);
    }
    public HashMap getEvent_()
    {
        return event_;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_view_pager, menu);
        return true;
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
        if (id == R.id.savedevents) {
            Intent intent = new Intent(this, SavedEventsActivity.class);
            startActivity(intent);


        }
        if (id == R.id.mytickets) {
            Intent intent = new Intent(this, UnfoldableActivity.class);
            startActivity(intent);
            return true;

        }
        if (id == R.id.CreateEvent) {
            Intent intent = new Intent(this, CreateEventActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.logout) {
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            startActivity(new Intent(this, login.class));
            return true;

        }
        if(id==R.id.Home)
        {
            startActivity(new Intent(this, MainActivity.class));
            return true;

        }




        return super.onOptionsItemSelected(item);
    }
}
