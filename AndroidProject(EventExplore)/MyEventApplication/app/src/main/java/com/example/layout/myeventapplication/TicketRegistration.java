package com.example.layout.myeventapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;


import java.util.HashMap;


public class TicketRegistration extends ActionBarActivity {
HashMap event=new HashMap();
   boolean machine_changed_edittext=false;
    Double amount_;
    String totalamount;
    UserLocalStore userLocalStore;
    int noofticketstaken;
    HashMap ticketcart=new HashMap();
    String usrname1;
    User user;



    int eventid;
    private static final String TAG = "paymentexample";

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = "AZ3b3Zfi7DcdvFaOVtPvsCVleRKawyFpdCUpc8kaJi3nztzYY6FqPr-UZyU7qWl3ToWR8Yd7fs744252";

    // private static final String CONFIG_CLIENT_ID = "rkochuku@syr.edu";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID);
    PayPalPayment thingToBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_registration);
        userLocalStore = new UserLocalStore(this);
        user=userLocalStore.getLoggedInuser();
        usrname1 = user.usrname;


        overridePendingTransition(R.animator.right_in, R.animator.leftout);
        Intent intent_ = new Intent(this, PayPalService.class);
        intent_.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(intent_);

        Intent intent = getIntent();
        event = (HashMap) intent.getSerializableExtra("event");

        TextView ticketcost = (TextView) findViewById(R.id.ticketcost);
        TextView peoplegoing = (TextView) findViewById(R.id.peoplegoing);
        amount_ = (Double) event.get("eventprice");
        ticketcost.setText(Double.toString(amount_));
         eventid = (Integer) event.get("eventid");
        String eventid_ = Integer.toString(eventid);


        TextView ticketsavalible = (TextView) findViewById(R.id.ticketsavailable);
        int Maxcapcity = (Integer) event.get("maxcapacity");
        String ticketurl_ = "http://www.example.com/events/ticketssold/";
        String ticketurl = ticketurl_ + eventid_;

        MyDownloadTicketsInfoAsyncTask task2 = new MyDownloadTicketsInfoAsyncTask(peoplegoing, ticketsavalible, new EventData(), Maxcapcity);
        task2.execute(new String[]{ticketurl});

        EditText nooftickets = (EditText) findViewById(R.id.noofticketsans);
        nooftickets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView myOutputBox = (TextView) findViewById(R.id.totalticketcostans);
                if (!machine_changed_edittext) {
                    machine_changed_edittext = true;
                    if (!s.toString().equals("")) {
                        double c = Double.parseDouble(s.toString());
                        noofticketstaken=Integer.parseInt(s.toString());
                        double f = c * amount_;
                        totalamount = Double.toString(f);
                        myOutputBox.setText(String.valueOf(f));
                    } else {
                        myOutputBox.setText("");
                    }
                    //Now as it is finished we declare back the Boolean to the correct value
                    machine_changed_edittext = false;
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
         }

    public void onBuyPressed(View view) {
            thingToBuy = new PayPalPayment(new BigDecimal(totalamount), "USD", (String)event.get("eventname"), PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(TicketRegistration.this, PaymentActivity.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));

                        Toast.makeText(
                                getApplicationContext(),
                                "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
                                .show();
                        ticketcart=createticketcartInfo(usrname1,noofticketstaken,eventid);
                        MyUploadTicketCartAsyncTask task3=new MyUploadTicketCartAsyncTask(ticketcart);
                        String url="http://www.example.com/events/insert/ticketscart/post/";
                        task3.execute(new String[] {url});
                       // ReduceSeatAsync reduceSeatAsync = new ReduceSeatAsync(2,1);
                        //reduceSeatAsync.execute();



                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            }
        }
    }
    public HashMap createticketcartInfo(String username, int noofticketstaken,int eventid)
    {
        HashMap ticketcart=new HashMap();
        ticketcart.put("eventid",eventid);
        ticketcart.put("username", username);
        ticketcart.put("noofticketstaken", noofticketstaken);


        return ticketcart;
    }
    protected void onPause()
    {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.animator.activity_open_scale, R.animator.activity_close_translate);
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
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
















