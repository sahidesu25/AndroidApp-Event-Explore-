package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/5/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sahithi on 6/29/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    int count;
    int categorynumber;
    public ViewPagerAdapter(FragmentManager fm, int size,int categorynumber)
    {
        super(fm);
        this.count=size;
        this.categorynumber=categorynumber;

    }

    public Fragment getItem(int position)
    {
        if(position==0){


        return EventRecyclerView.newInstance(categorynumber);
        }
        else if(position==1)
        {
            return PopularEventRecyclerView.newInstance(1);
        }
        else if(position==2)
        {
            return UpcommingEventsFragment.newInstance(1);
        }
        else
        {
        return UpcommingEventsFragment.newInstance(1);
        }

    }
    public void setCategoryName(int categorynumber)
    {
        this.categorynumber=categorynumber;
    }

    public int getCount(){return count;}

    public CharSequence getPageTitle(int position)
    {
        Locale l=Locale.getDefault();
        String name;
        switch(position)
        {
            case 0:
                switch (categorynumber)
                {
                    case 0:
                        name="Music";
                        break;
                    case 1:
                        name="Health";
                        break;
                    case 2:
                        name="Business";
                        break;
                    case 3:
                        name="Filim and Media";
                        break;
                    case 4:
                        name="Sports";
                        break;
                    case 5:
                        name="Science and Tech";
                        break;
                    case 6:
                        name="Fashion";
                        break;
                    case 7:
                        name="Spirituality";
                        break;
                    case 8:
                        name="Government";
                        break;
                    case 9:
                        name="Food";
                        break;
                    case 10:
                        name="Others";
                        break;
                    default:
                        name="Others";

                }

                break;
            case 1:
                name="Popular";
                break;
            default:
                name="Upcomming";

        }
        return name.toUpperCase(l);


    }

}