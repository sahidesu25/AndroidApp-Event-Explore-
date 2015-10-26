package com.example.layout.myeventapplication;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class register extends AppCompatActivity implements View.OnClickListener {

    Button register;
    EditText name,mail,usrname,pswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name  = (EditText)findViewById(R.id.etName);
        mail  = (EditText)findViewById(R.id.etMail);
        usrname = (EditText)findViewById(R.id.etUsername);
        pswd = (EditText)findViewById(R.id.etPassword);
        register = (Button)findViewById(R.id.bRegister);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bRegister:
                String name1 = name.getText().toString();
                String username1 = usrname.getText().toString();
                String password = pswd.getText().toString();
                String email = mail.getText().toString();
                User user = new User(name1,username1,password,email);
                registerfunc(user);
                break;
        }

    }

    private void registerfunc(User user)
    {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallBacks() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(register.this,login.class));
            }
        });
    }
}
