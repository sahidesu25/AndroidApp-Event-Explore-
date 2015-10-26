package com.example.layout.myeventapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Raghavendra on 8/4/2015.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDataStorage;


    public UserLocalStore(Context context)
    {
        userLocalDataStorage  = context.getSharedPreferences(SP_NAME,0);

    }

    public void storeUserData(User user)
    {
        SharedPreferences.Editor speditor = userLocalDataStorage.edit();
        speditor.putString("email",user.email);
        speditor.putString("name",user.name);
        speditor.putString("username",user.usrname);
        speditor.putString("password",user.pswd);
        speditor.commit();
    }

    public User getLoggedInuser()
    {
       // int id = userLocalDataStorage.getInt("id", -1);
        String name = userLocalDataStorage.getString("name", "");
        String usrname = userLocalDataStorage.getString("username","");
        String passwd = userLocalDataStorage.getString("password","");
        String email = userLocalDataStorage.getString("email","");
        User storeduser = new User(name,usrname,passwd,email);
        return storeduser;
    }



    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor speditor = userLocalDataStorage.edit();
        speditor.putBoolean("logged",loggedIn);
        speditor.commit();
    }

    public void clearUserData()
    {

        SharedPreferences.Editor speditor = userLocalDataStorage.edit();
        speditor.clear();
        speditor.commit();

    }

    public boolean getUserLoggedIn()
    {
        if(userLocalDataStorage.getBoolean("logged",false)== true)
            return true;
        else
            return false;
    }
}
