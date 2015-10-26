package com.example.layout.myeventapplication;



import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.layout.myeventapplication.R;
import com.example.layout.myeventapplication.UserLocalStore;


public class Main_Activity extends ActionBarActivity implements View.OnClickListener{

    Button logout;
    EditText usrname,name,email;
    UserLocalStore userLocalStore;
    private VideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        logout = (Button)findViewById(R.id.bLogout);
        usrname = (EditText)findViewById(R.id.etUsername);
        name = (EditText)findViewById(R.id.etUserid);
        email = (EditText)findViewById(R.id.etMail);
        userLocalStore = new UserLocalStore(this);
        logout.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate()){
            displayuserdetails();
        }
        else
            startActivity(new Intent(Main_Activity.this,login.class));

    }
    public void displayuserdetails()
    {
       User user = userLocalStore.getLoggedInuser();
       usrname.setText(user.usrname);
       email.setText(user.email);
        name.setText(user.name);
      Intent intent=new Intent(this,MainActivity.class);//this is category ac
       startActivity(intent);

    }
    public boolean authenticate()
    {
        return  userLocalStore.getUserLoggedIn();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this,login.class));
                break;
        }

    }



}
