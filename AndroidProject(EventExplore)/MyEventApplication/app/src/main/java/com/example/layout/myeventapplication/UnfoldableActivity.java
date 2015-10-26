package com.example.layout.myeventapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.foldablelayout.UnfoldableView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
public class UnfoldableActivity extends ActionBarActivity {
    private ListView mListView;
    public  EventData movieData ;
    private View mListTouchInterceptor;
    private View mDetailsLayout;
    UserLocalStore userLocalStore;
    private UnfoldableView mUnfoldableView;
    MyImageAdapter myImageAdapter;
    public static LruCache<String,Bitmap> imagecache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unfoldable);
        overridePendingTransition(R.animator.activity_open_scale,R.animator.activity_close_translate);
        movieData=new EventData();
        userLocalStore =  new UserLocalStore(this);
       User user = userLocalStore.getLoggedInuser();
        String user_=user.usrname;
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

      myImageAdapter=new MyImageAdapter(this,movieData.getMoviesList());
        mListView=(ListView)findViewById(R.id.list_view);

        String url_="http://www.example.com/events/tickets/";
        String url=url_+user_;
        MyDownloadTicketAsyncTask mydownload=new MyDownloadTicketAsyncTask(myImageAdapter,movieData);
        mydownload.execute(new String[]{url});
        mListView.setAdapter(myImageAdapter);
        mListTouchInterceptor = findViewById(R.id.touch_interceptor_view);
        mDetailsLayout = findViewById(R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);
        mUnfoldableView = (UnfoldableView)findViewById( R.id.unfoldable_view);

        mListTouchInterceptor.setClickable(false);
       /* mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, ?> item = movieData.getItem(position);
                openDetails(view, item);
            }
        });*/
        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
                mDetailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
            mUnfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
        overridePendingTransition(R.anim.abc_slide_out_top,R.anim.abc_slide_in_bottom);
    }
    public class MyDownloadTicketAsyncTask extends AsyncTask<String,Void,EventData> {
        private final WeakReference<MyImageAdapter> adapterReference;
        EventData Data;
        // RecyclerAdapter adapter;
        public MyDownloadTicketAsyncTask(MyImageAdapter adapter,EventData Data)
        {         adapterReference=new WeakReference<MyImageAdapter>(adapter);
            this.Data=Data;

        }

        @Override
        protected EventData doInBackground(String...urls){
            EventData threaddata=new EventData();
            for(String url:urls)
            {
                threaddata.downloadTicketDataJson(url);
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

                MyImageAdapter adapter=adapterReference.get();
                //adapter=RecyclerViewFragment.recyclerAdapter;
                if(adapter!=null)
                {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_unfoldable, menu);
        return true;
    }
    public void openDetails(View view,Map<String,?> movie)
    {
        ImageView _image = (ImageView)findViewById(R.id.details_image);
        TextView title =(TextView)findViewById(R.id.details_title);
        TextView location=(TextView)findViewById(R.id.locationans);
        TextView dateandtime=(TextView)findViewById(R.id.datetime);
        TextView nooftickets=(TextView)findViewById(R.id.noofticketsans);
        String url=(String) movie.get("logourl");
        Bitmap bitmap=imagecache.get(url);


            MyDownloadImageAsyncTask task=new MyDownloadImageAsyncTask(_image);

            task.execute(new String[] {url});

       // _image.setImageResource((Integer) movie.get("image"));
        title.setText((String) movie.get("eventname"));
        String city=(String)movie.get("city");
        String State=(String)movie.get("state");
        String Address=(String)movie.get("address");
        location.setText(city+" "+State+" "+Address);
        dateandtime.setText((String) movie.get("starteventdate"));
        int tickets=(Integer)movie.get("nooftickets");
        String tic_=Integer.toString(tickets)+"(No of tickets)";
        nooftickets.setText(tic_);


        // mUnfoldableView.changeCoverView(view);

        mUnfoldableView.unfold(view, mDetailsLayout);



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