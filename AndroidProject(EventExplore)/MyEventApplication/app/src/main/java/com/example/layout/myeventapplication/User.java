package com.example.layout.myeventapplication;
/**
 * Created by Raghavendra on 8/4/2015.
 */
public class User {

    String name,usrname,pswd,email;

    public User(String usrname,String pswd)
    {
        this.usrname = usrname;
        this.pswd = pswd;
        this.name = "";
        this.email = "";
    }

    public User (String name,String usrname,String pswd,String email)
    {

        this.name = name;
        this.usrname = usrname;
        this.pswd =pswd;
        this.email =email;
    }
}
