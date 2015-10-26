package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/12/2015.
 *
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public
class BottomFragment extends Fragment
{ public static BottomFragment newInstance(int option)
{
    BottomFragment fragment2=new BottomFragment();
    Bundle args=new Bundle();
   // args.putInt(ARG_OPTION,option);
    fragment2.setArguments(args);



    return fragment2;

}

    public
    BottomFragment()
    {
    }

    @Override
    public
    View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    )
    {
        View rootView = inflater.inflate( R.layout.top_fragment, container, false );
        DetailFragmentActivity activity = (DetailFragmentActivity) getActivity();
        final HashMap<String,?> Item=activity.getEvent_();
        Double amount_ =(Double)Item.get("eventprice");
        String amount=Double.toString(amount_);
        String eventprice="$"+amount;
        TextView price=(TextView)rootView.findViewById(R.id.price);
        final RippleView rippleView = (RippleView) rootView.findViewById(R.id.more);
        price.setText(eventprice);
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                Intent intent=new Intent(getActivity(),TicketRegistration.class);
                intent.putExtra("event",Item);
                startActivity(intent);


            }

        });

        Button register=(Button)rootView.findViewById(R.id.register);
      /*  register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),TicketRegistration.class);
                intent.putExtra("event",Item);
                startActivity(intent);


            }
        });*/

        return rootView;
    }
}