package com.example.rahul.sunshine.data;

import android.provider.BaseColumns;

/**
 * Created by rahul on 1/12/14.
 */
public class WeatherContract {

    public static final class WeatherEntry implements BaseColumns{
        public static final String TABLE_NAME = "weather";

        public static final String COLUMN_LOC_KEY = "loaction_id";

        public static final String COLUMN_DATETEXT = "date";

        public static final String COLUMN_WEATHER_ID = "weather_id";


        public static final String COLUMN_SHORT_DESC = "short_desc";

        public static final String COLUMN_MIN_TEMP = "min_temp";
        public static final String COLUMN_MAX_TEMP = "max_temp";

        public static final String COLUMN_HUMIDITY = "humidity";

        public static final String COLUMN_PRESSURE = "pressure";

        public static final String COLUMN_WIND = "wind";

        public static final String COLUMN_DEGREES = "degrees";

    }


    public static final class LocationEntry implements BaseColumns{

        public static final String TABLE_NAME = "location";


        public static final String COLUMN_LOCATION_SETTING = "loc_settings";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_LATITUDE = "latitude";
    }


}
