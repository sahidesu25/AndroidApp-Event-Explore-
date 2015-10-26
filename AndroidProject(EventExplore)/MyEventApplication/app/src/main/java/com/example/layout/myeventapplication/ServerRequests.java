package com.example.layout.myeventapplication;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Raghavendra on 8/5/2015.
 */
public class ServerRequests {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000*15;
    //public static final String SERVER_ADDR = "http://www.example.com/";
    public static final String SERVER_ADDR = "http://10.0.3.2/";

    public ServerRequests(Context context)
    {

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait .....");

    }

    public void storeUserDataInBackground(User user,GetUserCallBacks userCallBacks)
    {
        progressDialog.show();
        new storeUserdatAsyncTask(user,userCallBacks).execute();

    }

    public void fetchUserdataInBackground(User user,GetUserCallBacks userCallBacks)
    {
        progressDialog.show();
        new fetchUserdatAsyncTask(user,userCallBacks).execute();

    }

    public class storeUserdatAsyncTask extends AsyncTask<Void,Void,Void>
    {
        User user;
        GetUserCallBacks userCallBacks;

        public storeUserdatAsyncTask(User user,GetUserCallBacks userCallBacks)
        {
            this.user = user;
            this.userCallBacks =userCallBacks;
        }
        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<NameValuePair> datatosend = new ArrayList<>();
            datatosend.add(new BasicNameValuePair("name",user.name));
            datatosend.add(new BasicNameValuePair("email",user.email));
            datatosend.add(new BasicNameValuePair("username",user.usrname));
            datatosend.add(new BasicNameValuePair("password", user.pswd));


            HttpParams httpRequestParam = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpClient client =  new DefaultHttpClient(httpRequestParam);
            HttpPost post = new HttpPost(SERVER_ADDR + "register.php");

            try{
                post.setEntity(new UrlEncodedFormEntity(datatosend));
                client.execute(post);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.dismiss();
            userCallBacks.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class fetchUserdatAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        GetUserCallBacks userCallBacks;

        public fetchUserdatAsyncTask(User user, GetUserCallBacks userCallBacks) {
            this.user = user;
            this.userCallBacks = userCallBacks;
        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> datatosend = new ArrayList<>();

            datatosend.add(new BasicNameValuePair("username",user.usrname));
            datatosend.add(new BasicNameValuePair("password", user.pswd));


            HttpParams httpRequestParam = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpClient client =  new DefaultHttpClient(httpRequestParam);
            HttpPost post = new HttpPost(SERVER_ADDR + "FetchUserData.php");

            User returneduser = null;


            try{


                post.setEntity(new UrlEncodedFormEntity(datatosend));
                HttpResponse httpResponse =  client.execute(post);






                HttpEntity entity =  httpResponse.getEntity();


                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(result);
               // JSONObject user1 = jsonObject.getJSONObject("user");
                if(jsonObject.length()==0)
                returneduser = null;
                else
                {
                    String name = jsonObject.getString("name");
                    String email =  jsonObject.getString("email");
                    returneduser = new User(name,user.usrname,user.pswd,email);

                }

            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error parsing data " + e.toString());

                e.printStackTrace();
            }


            return returneduser;
        }


        @Override
        protected void onPostExecute(User returneduser) {

            progressDialog.dismiss();
            userCallBacks.done(returneduser);
            super.onPostExecute(returneduser);
        }
    }
}

