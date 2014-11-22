package com.example.rahul.sunshine;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_view_location:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String lat= preferences.getString(getString(R.string.pref_location_latitude_key), getString(R.string.pref_location_latitude_default));
                String lon= preferences.getString(getString(R.string.pref_location_longitude_key), getString(R.string.pref_location_longitude_default));
                Uri geoURI = Uri.parse("geo:"+lat+","+lon+"?z=11");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                mapIntent.setData(geoURI);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


}
