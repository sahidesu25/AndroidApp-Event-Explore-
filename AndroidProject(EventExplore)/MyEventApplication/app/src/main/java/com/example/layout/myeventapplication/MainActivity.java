package com.example.layout.myeventapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements CategoryRecyclerViewFragment.OnListItemSelectedListener {
    UserLocalStore userLocalStore;
    private String mNavTitles[] = {"Saved Events", "My Tickets", "Create Event", "Popular Events", "Upcoming Events", "Logout"};
    private int mIcons[] = {R.drawable.saved, R.drawable.tickets, R.drawable.create, R.drawable.popular, R.drawable.upcoming, R.drawable.logout};
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    DrawerAdapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    private Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        userLocalStore = new UserLocalStore(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, CategoryRecyclerViewFragment.newInstance(1)).commit();


    /*   toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.drawer_list);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new DrawerAdapter(mNavTitles, mIcons, this);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setlistener1(new DrawerAdapter.task3handler() {
            @Override
            public void itemclick(View view, int position) {


                //selectitem(position);
            }
        });

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        // Drawer object Assigned to the view

        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();*/
    }




    public void OnListItemSelected(int position) {
        Intent intent = new Intent(this, EventViewPager.class);
        intent.putExtra("category", position);
        startActivity(intent);

        // getSupportFragmentManager()
        //  .beginTransaction()
        // .replace(R.id.fragment,EventRecyclerView.newInstance(position)).addToBackStack(null).commit();

    }



    public void selectitem(int k) {
        switch (k) {
            case 0:
                Intent intent=new Intent(this,SavedEventsActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1=new Intent(this,UnfoldableActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2=new Intent(this,CreateEventActivity.class);
                startActivity(intent2);
                break;
            case 3:
                //popular
                break;
            case 4:
                //upcomming
                break;
            case 5:
                //logout

                break;


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


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
        return super.onOptionsItemSelected(item);
    }

  /*  @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }*/


}







