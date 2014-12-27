package com.example.rahul.sunshine.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.rahul.sunshine.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rahul on 14/11/14.
 */
public class Util {


    public static Map<String,String> getPreferredLocation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String,String> location = new HashMap<String, String>();
        location.put("latitude", prefs.getString(context.getString(R.string.pref_location_latitude_key),
                context.getString(R.string.pref_location_latitude_default)) );
        location.put("longitude", prefs.getString(context.getString(R.string.pref_location_longitude_key),
                context.getString(R.string.pref_location_longitude_default)) );
        return location;
    }

}
