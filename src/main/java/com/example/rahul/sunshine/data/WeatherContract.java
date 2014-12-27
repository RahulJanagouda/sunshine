package com.example.rahul.sunshine.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rahul on 1/12/14.
 */
public class WeatherContract {



        // The "Content authority" is a name for the entire content provider, similar to the
        // relationship between a domain name and its website.  A convenient string to use for the
        // content authority is the package name for the app, which is guaranteed to be unique on the
        // device.
                public static final String CONTENT_AUTHORITY = "com.example.rahul.sunshine.app";

        // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
        // the content provider.
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        // Possible paths (appended to base content URI for possible URI's)
        // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
        // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
        // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
        // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
        public static final String PATH_WEATHER = "weather";
        public static final String PATH_LOCATION = "location";

    public static final class WeatherEntry implements BaseColumns{




                public static final Uri CONTENT_URI =
                        BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();

                public static final String CONTENT_TYPE =
                        "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;
                public static final String CONTENT_ITEM_TYPE =
                        "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;



        public static final String TABLE_NAME = "weather";

        public static final String COLUMN_LOC_KEY = "loaction_id";

        public static final String COLUMN_DATETEXT = "date";

        public static final String COLUMN_WEATHER_ID = "weather_id";


        public static final String COLUMN_SHORT_DESC = "short_desc";

        public static final String COLUMN_MIN_TEMP = "min_temp";
        public static final String COLUMN_MAX_TEMP = "max_temp";

        public static final String COLUMN_HUMIDITY = "humidity";

        public static final String COLUMN_PRESSURE = "pressure";

        public static final String COLUMN_WIND_SPEED = "wind";

        public static final String COLUMN_DEGREES = "degrees";

        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildWeatherLocation(String locationSetting) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
        }

        public static Uri buildWeatherLocationWithStartDate(
                String locationSetting, String startDate) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_DATETEXT, startDate).build();
        }

        public static Uri buildWeatherLocationWithDate(String locationSetting, String date) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting).appendPath(date).build();
        }

        public static String getLocationSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getDateFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getStartDateFromUri(Uri uri) {
            return uri.getQueryParameter(COLUMN_DATETEXT);
        }

    }


    public static final class LocationEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

        public static final String TABLE_NAME = "location";


        public static final String COLUMN_LOCATION_SETTING = "loc_settings";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_COORD_LONG = "longitude";
        public static final String COLUMN_COORD_LAT = "latitude";

                public static Uri buildLocationUri(long id) {
                    return ContentUris.withAppendedId(CONTENT_URI, id);
                }
    }


    // Format used for storing dates in the database.  ALso used for converting those strings
    // back into date objects for comparison/processing.
    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Converts unix time to a string representation, used for easy comparison and database lookup.
     * @param date The input date
     * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
     */
    public static String getDbDateString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }


}
