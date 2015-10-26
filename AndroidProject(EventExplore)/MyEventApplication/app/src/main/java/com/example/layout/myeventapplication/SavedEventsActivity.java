package com.example.layout.myeventapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SavedEventsActivity extends ActionBarActivity {
    private  MyBaseAdapter mAdapter;
    private SwipeMenuListView mListView;
    UserLocalStore userLocalStore;
    EventData mdata;
    public static LruCache<String,Bitmap> imagecache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.leftin,R.animator.rightout);
        setContentView(R.layout.swiplist_save);
        mdata=new EventData();
        mAdapter=new MyBaseAdapter(this,mdata.getMoviesList());
        mListView = (SwipeMenuListView) findViewById(R.id.listView);
        userLocalStore = new UserLocalStore(this);
        User user = userLocalStore.getLoggedInuser();
        String user_=user.usrname;


        String url_="http://www.example.com/events/saved/";
        String url=url_+"sahi.desu25";
        MyDownloadSavedEventAsyncTask mydownload=new MyDownloadSavedEventAsyncTask(mAdapter,mdata);
        mydownload.execute(new String[]{url});
        if(imagecache==null)
        {
            final int maxMemory=(int) (Runtime.getRuntime().maxMemory()/1024);
            //use 1/8th of the memory
            final int cachesize=maxMemory/8;
            imagecache=new LruCache<String,Bitmap>(cachesize) {
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount()/1024;
                }
            };
        }
        mListView.setAdapter(mAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);

                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0xF9, 0xF9)));
                // set item width
                deleteItem.setWidth(dp2px(90));

                // set a icon
                deleteItem.setIcon(R.drawable.deletesmall);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);
        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                 Map<String,?> item = mdata.getItem(position);
                switch (index) {
                    case 0:

                        int id = (Integer) item.get("eventid");
                        String url = "http://www.example.com/events/eventinfo/";
                        String id_ = Integer.toString(id);
                        String _url = url + id_;
                        DownloadSavedEventAsyncTask asyncTask=new DownloadSavedEventAsyncTask(position,getApplicationContext());
                        asyncTask.execute((new String[]{_url}));

                        // open
                        // open(item);
                        break;
                    case 1:
                     //   Toast.makeText(getBaseContext(),(String)item.get("eventid"),
                          //      Toast.LENGTH_SHORT).show();
                        HashMap delete_item = new HashMap();
                        delete_item.put("EventId",item.get("eventid"));
                        delete_item.put("username", "sahi.desu25");
                        String url_="http://www.example.com/events/saved/delete/post/";
                        UploadSavedEventAsyncTask uploadMovieAsyncTask = new UploadSavedEventAsyncTask(delete_item);
                        uploadMovieAsyncTask.execute(new String[]{url_});
                        List<Integer> deletionIndexList = new ArrayList<Integer>();
                        deletionIndexList.add(position);

                        mAdapter.removeItemsFromList(deletionIndexList);


                        // delete
//					delete(item);
                        // mAppList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });





    }
    protected void onPause()
    {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.animator.activity_open_scale,R.animator.activity_close_translate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_events, menu);
        return true;
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_left) {
           mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            return true;
        }
        if (id == R.id.action_right) {
           mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
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



    public class MyDownloadSavedEventAsyncTask extends AsyncTask<String,Void,EventData> {
        private final WeakReference<MyBaseAdapter> adapterReference;
        EventData Data;
        // RecyclerAdapter adapter;
        public MyDownloadSavedEventAsyncTask(MyBaseAdapter adapter,EventData Data)
        {         adapterReference=new WeakReference<MyBaseAdapter>(adapter);
            this.Data=Data;

        }

        @Override
        protected EventData doInBackground(String...urls){
            EventData threaddata=new EventData();
            for(String url:urls)
            {
                threaddata.downloadEventDataJson(url);
            }

            return threaddata;
        }
        @Override
        protected void onPostExecute(EventData threaddata)
        {
            Data.eventsList.clear();
            for(int i=0;i<threaddata.getSize();i++)
            {
                Data.eventsList.add(threaddata.eventsList.get(i));
            }
            if(adapterReference!=null)
            {

                MyBaseAdapter adapter=adapterReference.get();
                //adapter=RecyclerViewFragment.recyclerAdapter;
                if(adapter!=null)
                {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    public class MyBaseAdapter extends BaseAdapter {
        private final Context context;
        private final List<Map<String,?>> eventlist;
        public MyBaseAdapter(Context context,List<Map<String,?>> eventlist)
        {
            this.context=context;
            this.eventlist=eventlist;
        }

        public int getCount()
        {
            return eventlist.size();
        }
        public Object getItem(int position)
        {
            return eventlist.get(position);
        }
        public long getItemId(int position)
        {
            return 0;
        }
        public void  removeItemsFromList(List<Integer> selectedList)
        {int count=0;
            if(selectedList.size()>=eventlist.size())
            {
                this.eventlist.clear();
            }
            else
            {
                for(int position:selectedList)
                {
                    if(this.isEnabled(position))
                    {
                        this.eventlist.remove(position-(count++));



                    }
                }
            }
        }

        public View getView(int position, View view, ViewGroup parent) {
            View rowView;
            ViewHolder holder=null;
            if(view==null)
            {

                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if(position%2==0)


                    rowView = inflater.inflate(R.layout.savedeventsrow, parent, false);
                else
                    rowView = inflater.inflate(R.layout.savedeventsrow2, parent, false);



                holder= new ViewHolder();
                holder.name=(TextView) rowView.findViewById(R.id.name);
                holder.image=(ImageView) rowView.findViewById((R.id.image));
                holder.location=(TextView) rowView.findViewById((R.id.locationans));
                holder.dateandtime=(TextView)rowView.findViewById(R.id.datetime);
                holder.rating=(RatingBar) rowView.findViewById(R.id.rating);

               holder.image2 = (ImageView) rowView.findViewById(R.id.swipe);
                if(position==0)
                {
                    holder.image2.setImageResource(R.drawable.swipeeee);
                    TranslateAnimation animation = new TranslateAnimation(400.0f, 0.0f,
                            0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                    animation.setDuration(2000);  // animation duration
                    animation.setRepeatCount(5);  // animation repeat count
                    animation.setRepeatMode(3);   // repeat animation (left to right, right to left )
                    //animation.setFillAfter(true);

                    holder.image2.startAnimation(animation);  // start animation
                    holder.image2.setVisibility(View.INVISIBLE);

                }

              //  holder.selector=(CheckBox) rowView.findViewById(R.id.checkbox1);
                rowView.setTag(holder);
            }
            else {
                rowView = view;
                holder=(ViewHolder)view.getTag();
            }


               // rowView.setBackgroundResource(R.color.dim_foreground_disabled_material_light);

            Map<String,?> item=eventlist.get(position);
            String url=(String) item.get("logourl");
            Bitmap bitmap=imagecache.get(url);
            if(bitmap!=null)
            {
                holder.image.setImageBitmap(bitmap);
            }
            else
            {
                MyDownloadImageAsyncTask task=new MyDownloadImageAsyncTask(holder.image);

                task.execute(new String[] {url});
            }
          //  holder.image.setImageResource((Integer) item.get("image"));
            holder.name.setText((String) item.get("eventname"));

            Double rating =(Double)item.get("rating");
            holder.rating.setRating((float) (rating / 2));
            String city=(String)item.get("city");
            String State=(String)item.get("state");

            holder.location.setText(city + " " + State);
            holder.dateandtime.setText((String) item.get("starteventdate"));
            return rowView;
        }
    }


}
