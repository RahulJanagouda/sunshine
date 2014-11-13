package com.example.rahul.sunshine;

/**
 * Created by rahul on 10/11/14.
 */

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rahul.sunshine.Util.Util;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        ArrayList<String> tempData = new ArrayList<String>();
        tempData.add("Today - Sunny - 88/63");
        tempData.add("Tomorrow - Foggy - 70/46");
        tempData.add("Weds - Cloudy - 72/63");
        tempData.add("Thurs - Rainy - 64/51");
        tempData.add("Fri - Foggy - 70/46");
        tempData.add("Sat - Sunny - 76/68");

        ArrayAdapter<String> forecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                tempData
        );

        ListView forecastListView = (ListView) rootView.findViewById(R.id.listview_forecast);
        forecastListView.setAdapter(forecastAdapter);

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
                new FetchWeather().execute(7);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class FetchWeather extends AsyncTask<Integer, Void, String[]> {

        @Override
        protected String[] doInBackground(Integer... params) {

            String[] results = new String[params[0]];

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            String forecastJsonString = null;

            try {

                Uri uri = Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily")
                        .buildUpon()
                        .appendQueryParameter("lon", "74.5")
                        .appendQueryParameter("lat","15.87")
                        .appendQueryParameter("cnt","7")
                        .appendQueryParameter("mode","json")
                        .appendQueryParameter("units","metrics").build();

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

                try {
                    results = Util.getWeatherDataFromJson(forecastJsonString,params[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                Log.v("DATA:",forecastJsonString);
                Log.v("DATA:",results.toString());

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
            return results;
        }
    }

}
