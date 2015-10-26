package com.example.layout.myeventapplication;

/**
 * Created by Sahithi on 8/2/2015.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Created by Sahithi on 6/29/2014.
 */
public class MyUtility {

    // Download an image from online
    public static Bitmap downloadImage(String url) {
        Bitmap bitmap = null;

        InputStream stream = getHttpConnection(url);
        if(stream!=null) {
            bitmap = BitmapFactory.decodeStream(stream);
            try {
                stream.close();
            }catch (IOException e1) {
                Log.d("MyDebugMsg", "IOException in downloadImage()");
                e1.printStackTrace();
            }
        }

        return bitmap;
    }

    // Download a Json file from online
    public static String downloadJSON(String url) {
        String json=null, line;

        InputStream stream = getHttpConnection(url);
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder out = new StringBuilder();
            try {
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                json = out.toString();
            } catch (IOException ex) {
                Log.d("MyDebugMsg", "IOException in downloadJSON()");
                ex.printStackTrace();
            }
        }
        return json;
    }
//uploading host info to server
    public static String uploadHostinfoJSON(String url,HashMap item) {
        String line;
        String Response;
        try {
            URL urlObj = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) urlObj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.connect();
            // Send the JSON data
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("hostname",item.get("hostname"));
            jsonParam.put("contactnumber", item.get("contactnumber"));
            jsonParam.put("email", item.get("email"));



            OutputStreamWriter out = new   OutputStreamWriter(httpConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();
            // Read the response, if needed
            int number=httpConnection.getResponseCode();
           // HttpEntity entity = response.getEntity();
           // InputStream is = entity.getContent();
            String result = "";
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null) {
                    Log.d("Uploading Finished", line);
                    if(line.equals("\t\t\t\t\t\t\tThis Information already exsists, Press Next to Continue"))
                    {
                        return line;
                    }
                    // for debugging purpose
                }


                reader.close();
                return line;
            }

        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "Unknown Host exception in uploadJSON()");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in uploadJSON()");
            ex.printStackTrace();
        }
        return " ";

    }




    public static void uploadticketcartJSON(String url,HashMap item) {
        String line;
        try {
            URL urlObj = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) urlObj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.connect();
            // Send the JSON data
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("eventid",item.get("eventid"));
            jsonParam.put("username", item.get("username"));
            jsonParam.put("noofticketstaken",item.get("noofticketstaken"));

            OutputStreamWriter out = new   OutputStreamWriter(httpConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();
            // Read the response, if needed
            int number=httpConnection.getResponseCode();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null) {
                    Log.d("Uploading Finished", line); // for debugging purpose
                }
                reader.close();
            }
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "Unknown Host exception in uploadJSON()");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in uploadJSON()");
            ex.printStackTrace();
        }
    }

    public static void uploadEventInfoJSON(String url,HashMap item) {
        String line;
        try {
            URL urlObj = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) urlObj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.connect();
            // Send the JSON data
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("city",item.get("city"));
            jsonParam.put("state", item.get("state"));
            jsonParam.put("address",item.get("address"));
            jsonParam.put("zipcode",item.get("zipcode"));
            jsonParam.put("hostname",item.get("hostname"));
            jsonParam.put("contactnumber", item.get("contactnumber"));
            jsonParam.put("email", item.get("email"));
            jsonParam.put("eventname",item.get("eventname"));
            jsonParam.put("eventprice",item.get("eventprice"));
            jsonParam.put("maxcapacity",item.get("maxcapacity"));
            jsonParam.put("logourl",item.get("logourl"));
            jsonParam.put("vediourl",item.get("vediourl"));
            jsonParam.put("category",item.get("category"));
            jsonParam.put("rating",item.get("rating"));
            jsonParam.put("startdateandtime",item.get("startdateandtime"));
            jsonParam.put("enddateandtime",item.get("enddateandtime"));



            OutputStreamWriter out = new   OutputStreamWriter(httpConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();
            // Read the response, if needed
            int number=httpConnection.getResponseCode();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null) {
                    Log.d("Uploading Finished", line); // for debugging purpose
                }
                reader.close();
            }
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "Unknown Host exception in uploadJSON()");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in uploadJSON()");
            ex.printStackTrace();
        }
    }
    public static void uploadLocationinfoJSON(String url,HashMap item) {
        String line;
        try {
            URL urlObj = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) urlObj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.connect();
            // Send the JSON data
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("city",item.get("city"));
            jsonParam.put("state", item.get("state"));
            jsonParam.put("address",item.get("address"));
            jsonParam.put("zipcode",item.get("zipcode"));

            OutputStreamWriter out = new   OutputStreamWriter(httpConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();
            // Read the response, if needed
            int number=httpConnection.getResponseCode();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null) {
                    Log.d("Uploading Finished", line); // for debugging purpose
                }
                reader.close();
            }
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "Unknown Host exception in uploadJSON()");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in uploadJSON()");
            ex.printStackTrace();
        }
    }

    public static void uploadSavedEventJSON(String url,HashMap item) {
        String line;
        try {
            URL urlObj = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) urlObj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.connect();
            // Send the JSON data
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("eventid",item.get("Eventid"));
            jsonParam.put("username", item.get("username"));


            OutputStreamWriter out = new   OutputStreamWriter(httpConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();
            // Read the response, if needed
            int number=httpConnection.getResponseCode();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null) {
                    Log.d("Uploading Finished", line); // for debugging purpose
                }
                reader.close();
            }
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "Unknown Host exception in uploadJSON()");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in uploadJSON()");
            ex.printStackTrace();
        }
    }







    // Upload Json data to server
    public static void uploadJSON(String url,HashMap item) {
        String line;
        try {
            URL urlObj = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) urlObj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.connect();
            // Send the JSON data
            JSONObject jsonParam = new JSONObject();

            jsonParam.put("username", item.get("username"));
            jsonParam.put("EventId",  item.get("EventId"));

            OutputStreamWriter out = new   OutputStreamWriter(httpConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();
            // Read the response, if needed
            int number=httpConnection.getResponseCode();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null) {
                    Log.d("Uploading Finished", line); // for debugging purpose
                }
                reader.close();
            }
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "Unknown Host exception in uploadJSON()");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in uploadJSON()");
            ex.printStackTrace();
        }
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null, line;
        try {
            InputStream stream = context.getAssets().open(fileName);
            if (stream != null) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder out = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                json = out.toString();
            }
        } catch (IOException ex) {
            Log.d("MyDebugMsg", "IOException in loadJSONFromAsset()");
            ex.printStackTrace();
        }
        return json;
    }

    // Makes HttpURLConnection and returns InputStream
    public static InputStream getHttpConnection(String urlString) {
        InputStream stream = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "UnknownHostexception in getHttpConnection()");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in getHttpConnection()");
            ex.printStackTrace();
        }
        return stream;
    }
}