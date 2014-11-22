package com.example.rahul.sunshine;

/**
 * Created by rahul on 10/11/14.
 */

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String latitude = settings.getString(getString(R.string.pref_location_latitude_key), getString(R.string.pref_location_latitude_default));
        String longitude = settings.getString(getString(R.string.pref_location_longitude_key), getString(R.string.pref_location_longitude_default));
        String units = settings.getString(getString(R.string.pref_temperature_key), getString(R.string.pref_temperature_default));
        new FetchWeather().execute(latitude,longitude,units);

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
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String latitude = settings.getString(getString(R.string.pref_location_latitude_key), getString(R.string.pref_location_latitude_default));
                String longitude = settings.getString(getString(R.string.pref_location_longitude_key), getString(R.string.pref_location_longitude_default));
                String units = settings.getString(getString(R.string.pref_temperature_key), getString(R.string.pref_temperature_default));
                new FetchWeather().execute(latitude,longitude,units);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class FetchWeather extends AsyncTask<String, Void, String[]>  {

        @Override
        protected String[] doInBackground(String... params) {

            if(params.length == 0){
                return null;
            }

            String latitude = params[0];
            String longitude = params[1];
            String unit = params[2];

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            String forecastJsonString = null;

            try {

                Uri uri = Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily")
                        .buildUpon()
                        .appendQueryParameter("lat",latitude)
                        .appendQueryParameter("lon", longitude)
                        .appendQueryParameter("cnt","7")
                        .appendQueryParameter("mode","json")
                        .appendQueryParameter("units",unit).build();

                URL forecastUrl = new URL(uri.toString());

                httpURLConnection = (HttpURLConnection) forecastUrl.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                StringBuffer stringBuffer = new StringBuffer();

                InputStream inputStream = httpURLConnection.getInputStream();

                if (inputStream == null) {
                    forecastJsonString = null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    forecastJsonString = null;
                } else {
                    forecastJsonString = stringBuffer.toString();
                }





                Log.v("DATA:",forecastJsonString);
//                Log.v("DATA:",results[0 ].toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
                if (bufferedReader != null)
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }


            try {
                return Util.getWeatherDataFromJson(forecastJsonString,7);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);

            if(strings!=null && strings.length>0){

                forecastAdapter.clear();
                forecastAdapter.addAll(strings);
            }

        }
    }

}
