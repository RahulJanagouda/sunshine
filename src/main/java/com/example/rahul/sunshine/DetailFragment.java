package com.example.rahul.sunshine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rahul on 21/11/14.
 */
public class DetailFragment extends Fragment {


    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    public static final String SHARE_TAG = "#SunshineApp";
    private String mForecastString;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mForecastString = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
        ((TextView)rootView.findViewById(R.id.forecast_text)).setText(mForecastString);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.detail_fragment, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);

        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        if (shareActionProvider != null){
            shareActionProvider.setShareIntent(createShareIntent());
        } else {
            Log.d(LOG_TAG,"shareActionProvider is null");
        }

    }


        private Intent createShareIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastString+SHARE_TAG);

        return shareIntent;
    }
}