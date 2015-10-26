package com.example.layout.myeventapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Raghavendra on 7/11/2015.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {



    private static task3handler task3h;
    private String mNavTitles[];
    private int mIcons[];
    Context context;
    private static final ArrayList<Integer> selected = new ArrayList<>();





    class ViewHolder extends RecyclerView.ViewHolder  {
        int Holderid;

        TextView textView;
        ImageView imageView;
        private View parent;

        Context contxt;


        public ViewHolder(View itemView,int ViewType,Context c) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            contxt = c;
            this.parent = itemView;
            itemView.setClickable(true);


            textView = (TextView) itemView.findViewById(R.id.item1text); // Creating TextView object with the id of textView from item_row.xml
            imageView = (ImageView) itemView.findViewById(R.id.item1img);// Creating ImageView object with the id of ImageView from item_row.xml
            Holderid = 0;                                               // setting holder id as 1 as the object being populated are of type item row


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundColor(Color.CYAN);

                    // (*1)
                    // forcing single selection here
                    if (selected.isEmpty()) {
                        selected.add(getPosition());
                    } else {
                        int oldSelected = selected.get(0);
                        selected.clear();
                        selected.add(getPosition());
                        // we do not notify that an item has been selected
                        // because that work is done here.  we instead send
                        // notifications for items to be deselected
                        notifyItemChanged(oldSelected);
                    }

                    task3h.itemclick(v,getPosition());
                }
            });


        }

    }

    public void  setlistener1(task3handler task3h){

        this.task3h = task3h;
    }

    public DrawerAdapter(String Titles[], int Icons[], Context passedContext){
        mNavTitles = Titles;
        mIcons = Icons;
        this.context = passedContext;

    }



    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1,parent,false); //Inflating the layout

        ViewHolder vhItem = new ViewHolder(v,viewType,context);

        return vhItem;




    }




    @Override
    public void onBindViewHolder(final DrawerAdapter.ViewHolder holder, final int position) {

        if (!selected.contains(position)){
            // view not selected
            holder.parent.setBackgroundColor(Color.LTGRAY);
        }
        else
            // view is selected
            holder.parent.setBackgroundColor(Color.CYAN);


        holder.textView.setText(mNavTitles[position]); // Setting the Text with the array of our Titles
        holder.imageView.setImageResource(mIcons[position]);// Settimg the image with array of our icons




    }



    @Override
    public int getItemCount() {
        return mNavTitles.length;
    }

    public interface task3handler{
        public void itemclick(View view, int position);
    }


}
