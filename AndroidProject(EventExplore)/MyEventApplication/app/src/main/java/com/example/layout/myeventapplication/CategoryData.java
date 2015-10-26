package com.example.layout.myeventapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryData {
    List<Map<String,?>> categorylist;

    public List<Map<String, ?>> getCategoryList() {
        return categorylist;
    }
    public int getSize(){
        return categorylist.size();
    }
    public HashMap getItem(int i){
        if (i >=0 && i < categorylist.size()){
            return (HashMap) categorylist.get(i);
        } else
            return null;
    }

    public CategoryData() {
        categorylist = new ArrayList<Map<String,?>>();
    }

    public void loadLocalCategoryDataJson(Context context) {
        categorylist.clear(); // clear the list

        // movie.json contains an array of movies
        String categoriesArray = loadFileFromAsset(context, "categoriesjson.json");
        if (categoriesArray == null){
            Log.d("MyDebugMsg", "Having trouble load movie.json");
            return;
        }

        try {
            // Parse the string to construct JSON array
            JSONArray categoriesJsonArray = new JSONArray(categoriesArray);
            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject categoryJsonObj = (JSONObject) categoriesJsonArray.get(i);
                if (categoryJsonObj != null) {
                   // int  categoryid = (int) categoryJsonObj.get("categoryid");
                    int categoryid=categoryJsonObj.getInt("Categoryid");

                    String image = (String) categoryJsonObj.get("CategoryImage");
                    int resID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                    String categoryname=(String) categoryJsonObj.get("CategoryName");


                    categorylist.add(createCategory(categoryid,resID,categoryname));
                }
            }
        } catch (JSONException ex){
            Log.d("MyDebugMsg", "JSONException in loadLocalMovieDataJson()");
            ex.printStackTrace();
        }
    }

    public String loadFileFromAsset(Context context, String fileName) {
        String contents = null, line;
        try {
            InputStream stream = context.getAssets().open(fileName);
            if (stream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder out = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                contents = out.toString();
            }
        } catch (IOException ex) {
            Log.d("MyDebugMsg", "IOException in loadFileFromAsset()");
            ex.printStackTrace();
        }
        return contents;
    }

    private static HashMap createCategory( int categoryid, int image, String categoryname) {
        HashMap category= new HashMap();
        category.put("categoryid", categoryid);
        category.put("image",image);
        category.put("categoryname", categoryname);
        category.put("selection",false);
        return category;
    }



}

