package com.example.rahul.sunshine;

/**
 * Created by rahul on 10/11/14.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rahul.sunshine.Util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    ArrayAdapter<String> forecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Map<String,String> location = Util.getPreferredLocation(getActivity());
        String latitude = location.get("latitude");
        String longitude = location.get("longitude");

        new FetchWeatherTask(getActivity(),forecastAdapter).execute(latitude, longitude);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        forecastAdapter= new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
        new ArrayList<String>()
        );

        ListView forecastListView = (ListView) rootView.findViewById(R.id.listview_forecast);
        forecastListView.setAdapter(forecastAdapter);

        forecastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),DetailActivity.class).putExtra(Intent.EXTRA_TEXT, ""+parent.getItemAtPosition(position)));
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecast_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Map<String,String> location = Util.getPreferredLocation(getActivity());
                String latitude = location.get("latitude");
                String longitude = location.get("longitude");
                new FetchWeatherTask(getActivity(),forecastAdapter).execute(latitude, longitude);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
