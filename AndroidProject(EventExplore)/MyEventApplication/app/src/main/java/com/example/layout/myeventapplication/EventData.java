package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/2/2015.
 */
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventData {
    public static String PHP_SERVER="http://www.example.com/events/categoryid/";

    List<Map<String,?>> eventsList;

    public List<Map<String, ?>> getMoviesList() {
        return eventsList;
    }
    public int getSize(){
        return eventsList.size();
    }
    public HashMap getItem(int i){
        if (i >=0 && i < eventsList.size()){
            return (HashMap) eventsList.get(i);
        } else
            return null;
    }

    public EventData() {
        eventsList = new ArrayList<Map<String,?>>();
    }

    public void downloadTicketDataJson(String json_url)
    {
        eventsList.clear(); // clear the list

        String eventsArray = MyUtility.downloadJSON(json_url);
        if (eventsArray == null){
            Log.d("MyDebugMsg", "Having trouble loading URL: "+json_url);
            return;
        }

        try {
            JSONArray eventsJsonArray = new JSONArray(eventsArray);
            for (int i = 0; i < eventsJsonArray.length(); i++) {
                JSONObject eventJsonObj = (JSONObject) eventsJsonArray.get(i);
                if (eventJsonObj != null) {
                    int eventid=eventJsonObj.getInt("EventId");
                    String eventname = (String) eventJsonObj.get("EventName");
                    double rating = Double.parseDouble(eventJsonObj.get("rating").toString());
                    String logourl = (String)eventJsonObj.get("LogoImageUrl");
                    String description = (String) eventJsonObj.get("EventName");
                    double eventprice = Double.parseDouble(eventJsonObj.get("EventPrice").toString());
                    int nooftickets=eventJsonObj.getInt("nooftickets");
                    String state = (String) eventJsonObj.get("State");
                    String city=(String) eventJsonObj.get("City");
                    String address=(String) eventJsonObj.get("Address");
                    String zipcode=(String) eventJsonObj.get("Zipcode");
                    String categoryname=(String) eventJsonObj.get("CategoryName");
                    int categoryid=eventJsonObj.getInt("Categoryid");
                    String starteventdate=(String)eventJsonObj.get("StartDateandTime");
                    String endeventdate=(String)eventJsonObj.get("EndDateandTime");


                    eventsList.add( createTicket( eventid, eventname,  rating, logourl, description,
                     eventprice, state,city, nooftickets,
                     address,  zipcode, categoryname, categoryid,starteventdate, endeventdate));
                }
            }
        } catch (JSONException ex) {
            Log.d("MyDebugMsg", "JSONException in downloadMovieDataJson");
            ex.printStackTrace();
        }
    }





    public void downloadEventDataJson(String json_url) {
        eventsList.clear(); // clear the list

        String eventsArray = MyUtility.downloadJSON(json_url);
        if (eventsArray == null){
            Log.d("MyDebugMsg", "Having trouble loading URL: "+json_url);
            return;
        }

        try {
            JSONArray eventsJsonArray = new JSONArray(eventsArray);
            for (int i = 0; i < eventsJsonArray.length(); i++) {
                JSONObject eventJsonObj = (JSONObject) eventsJsonArray.get(i);
                if (eventJsonObj != null) {
                    int eventid=eventJsonObj.getInt("EventId");
                    String eventname = (String) eventJsonObj.get("EventName");
                    double rating = Double.parseDouble(eventJsonObj.get("rating").toString());
                    String logourl = (String)eventJsonObj.get("LogoImageUrl");
                    String description = (String) eventJsonObj.get("EventName");
                    double eventprice = Double.parseDouble(eventJsonObj.get("EventPrice").toString());
                    int maxcapacity=eventJsonObj.getInt("MaxCapacity");
                    String state = (String) eventJsonObj.get("State");
                    String city=(String) eventJsonObj.get("City");
                    String address=(String) eventJsonObj.get("Address");
                    String zipcode=(String) eventJsonObj.get("Zipcode");
                    String categoryname=(String) eventJsonObj.get("CategoryName");
                    int categoryid=eventJsonObj.getInt("Categoryid");
                    String starteventdate=(String)eventJsonObj.get("StartDateandTime");
                    String endeventdate=(String)eventJsonObj.get("EndDateandTime");


                    eventsList.add( createEvent(eventid, eventname, rating, logourl, description,
                            eventprice, maxcapacity, state, city,
                            address, zipcode, categoryname, categoryid, starteventdate, endeventdate));
                }
            }
        } catch (JSONException ex) {
            Log.d("MyDebugMsg", "JSONException in downloadMovieDataJson");
            ex.printStackTrace();
        }
    }
    public int downloadTicketssold(String json_url)
    {
        int peoplegoing=0;
        String eventsArray = MyUtility.downloadJSON(json_url);
        if (eventsArray == null){
            Log.d("MyDebugMsg", "Having trouble loading URL: "+json_url);
            return peoplegoing;
        }

        try {
            JSONArray eventsJsonArray = new JSONArray(eventsArray);
            for (int i = 0; i < eventsJsonArray.length(); i++) {
                JSONObject eventJsonObj = (JSONObject) eventsJsonArray.get(i);
                if (eventJsonObj != null) {
            peoplegoing=eventJsonObj.getInt("Peoplegoing");
                }
            }
        } catch (JSONException ex) {
            Log.d("MyDebugMsg", "JSONException in downloadMovieDataJson");
            ex.printStackTrace();
        }

        return peoplegoing;
    }


    public HashMap downloadEventDetailDataJson(String json_url) {
     HashMap event=new HashMap();

        String eventsArray = MyUtility.downloadJSON(json_url);
        if (eventsArray == null){
            Log.d("MyDebugMsg", "Having trouble loading URL: "+json_url);
           return event;
        }

        try {
            JSONArray eventsJsonArray = new JSONArray(eventsArray);
            for (int i = 0; i < eventsJsonArray.length(); i++) {
                JSONObject eventJsonObj = (JSONObject) eventsJsonArray.get(i);
                if (eventJsonObj != null) {
                    int eventid=eventJsonObj.getInt("EventId");
                    String eventname = (String) eventJsonObj.get("EventName");
                    double rating = Double.parseDouble(eventJsonObj.get("rating").toString());
                    String logourl = (String)eventJsonObj.get("LogoImageUrl");
                    String description = (String) eventJsonObj.get("EventName");
                    double eventprice = Double.parseDouble(eventJsonObj.get("EventPrice").toString());
                    int maxcapacity=eventJsonObj.getInt("MaxCapacity");
                    String state = (String) eventJsonObj.get("State");
                    String city=(String) eventJsonObj.get("City");
                    String address=(String) eventJsonObj.get("Address");
                    String zipcode=(String) eventJsonObj.get("Zipcode");
                    String categoryname=(String) eventJsonObj.get("CategoryName");
                    int categoryid=eventJsonObj.getInt("Categoryid");
                    String starteventdate=(String)eventJsonObj.get("StartDateandTime");
                    String endeventdate=(String)eventJsonObj.get("EndDateandTime");
                    String hostname=(String)eventJsonObj.get("HostName");
                    String hostemail=(String)eventJsonObj.get("HostEmail");
                    String hostnumber=(String)eventJsonObj.get("ContactNumber");
                    String vedioid=(String)eventJsonObj.get("VedioUrl");


                     event=createDetailEvent( eventid,  eventname,  rating,  logourl, description,
                     eventprice, state, city, maxcapacity,
                     address,  zipcode,
                             categoryname, categoryid,  starteventdate,  endeventdate,  hostname,
                             hostnumber,  hostemail,  vedioid);
                }
            }
        } catch (JSONException ex) {
            Log.d("MyDebugMsg", "JSONException in downloadMovieDataJson");
            ex.printStackTrace();
        }
        return event;
    }

    private static HashMap createDetailEvent(int eventid, String eventname, double rating, String logourl,String description,
                                             double eventprice,String state,String city,int maxcapacity,
                                             String address, String zipcode,
                                             String categoryname,int categoryid, String starteventdate, String endeventdate, String hostname,
                                             String hostnumber, String hostemail, String vedioid)
    {
        HashMap event=new HashMap();
        event.put("eventid",eventid);
        event.put("eventname",eventname);
        event.put("rating",rating);
        event.put("logourl",logourl);
        event.put("description",description);
        event.put("hostname",hostname);
        event.put("hostnumber",hostnumber);
        event.put("hostemail",hostemail);
        event.put("vedioid",vedioid);
        event.put("eventprice",eventprice);
        event.put("maxcapacity",maxcapacity);
        event.put("state",state);
        event.put("city",city);
        event.put("address",address);
        event.put("zipcode",zipcode);
        event.put("categoryname",categoryname);
        event.put("categoryid",categoryid);
        event.put("starteventdate",starteventdate);
        event.put("endeventdate",endeventdate);



        return event;
    }
    private static HashMap createTicket(int eventid, String eventname, double rating, String logourl,String description,
                                        double eventprice,String state,String city,int nooftickets,
                                        String address, String zipcode,
                                        String categoryname,int categoryid, String starteventdate, String endeventdate)
    {
        HashMap ticket=new HashMap();
        ticket.put("eventid", eventid);
        ticket.put("eventname", eventname);
        ticket.put("rating", rating);
        ticket.put("logourl", logourl);
        ticket.put("description", description);
        ticket.put("eventprice",eventprice);
        ticket.put("nooftickets",nooftickets);
        ticket.put("state",state);
        ticket.put("city",city);
        ticket.put("address",address);
        ticket.put("zipcode",zipcode);
        ticket.put("categoryname", categoryname);
        ticket.put("categoryid",categoryid);
        ticket.put("starteventdate", starteventdate);
        ticket.put("endeventdate", endeventdate);





        return ticket;
    }


    private static HashMap createEvent(int eventid, String eventname, double rating, String logourl,String description,
                                       double eventprice,int maxcapacity,String state,String city,
                                       String address, String zipcode,
                                       String categoryname,int categoryid, String starteventdate, String endeventdate)
    {
        HashMap event=new HashMap();
        event.put("eventid",eventid);
        event.put("eventname",eventname);
        event.put("rating",rating);
        event.put("logourl",logourl);
        event.put("description",description);
        event.put("eventprice",eventprice);
        event.put("maxcapacity",maxcapacity);
        event.put("state",state);
        event.put("city",city);
        event.put("address",address);
        event.put("zipcode",zipcode);
        event.put("categoryname",categoryname);
        event.put("categoryid",categoryid);
        event.put("starteventdate",starteventdate);
        event.put("endeventdate",endeventdate);
        return  event;
    }
}
