package com.example.layout.myeventapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


public class login extends AppCompatActivity implements View.OnClickListener {


    Button login;
    EditText usrname,pswd;
    TextView register;
    UserLocalStore userLocalStore;
    private VideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (mediaControls == null) {
            mediaControls = new MediaController(login.this);
        }
        myVideoView = (VideoView) findViewById(R.id.video_view);
        try {
            //set the media controller in the VideoView
            myVideoView.setMediaController(mediaControls);
            mediaControls.setAnchorView(myVideoView);
            mediaControls.setMediaPlayer(myVideoView);

            //set the uri of the video to be played
            myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.concert));

        } catch (Exception e) {
            //  Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        myVideoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                //  progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                myVideoView.seekTo(position);
                if (position == 0) {
                    myVideoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    myVideoView.pause();
                }
            }
        });


        login = (Button)findViewById(R.id.bLogin);
        usrname = (EditText)findViewById(R.id.etUsername);
        pswd = (EditText)findViewById(R.id.etPassword);
        register = (TextView)findViewById(R.id.tvRegisterLink);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:

                String usr = usrname.getText().toString();
                String psw = pswd.getText().toString();
                User user = new User(usr,psw);
                authenticateUser(user);


                break;
            case R.id.tvRegisterLink:
                startActivity(new Intent(this,register.class));
                break;
        }
    }

    private void authenticateUser(User user)
    {

        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserdataInBackground(user, new GetUserCallBacks() {
            @Override
            public void done(User returnedUser) {
                if(returnedUser == null)
                    showErrorMessage();
                else
                {
                    logUserIn(returnedUser);
                }

            }
        });
    }

    private void showErrorMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
        builder.setMessage("IncorrectUser deatils");
        builder.setPositiveButton("Ok", null);
        builder.show();

    }

    private void  logUserIn(User returnedUser)
    {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(login.this, MainActivity.class));
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }
}
