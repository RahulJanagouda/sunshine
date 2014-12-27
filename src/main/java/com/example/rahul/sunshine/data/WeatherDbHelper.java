package com.example.rahul.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rahul.sunshine.data.WeatherContract.WeatherEntry;
import com.example.rahul.sunshine.data.WeatherContract.LocationEntry;


/**
 * Created by rahul on 1/12/14.
 */
public class WeatherDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "weather.db";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        final String SQL_CREATE_LOCATION_TABLE =
                " CREATE TABLE " + LocationEntry.TABLE_NAME
                        +" ("
                        +LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, "
                        +LocationEntry.COLUMN_LOCATION_SETTING + " TEXT NOT NULL, "
                        +LocationEntry.COLUMN_COORD_LONG + " REAL NOT NULL, "
                        +LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL, "

                        + " UNIQUE (" + LocationEntry.COLUMN_LOCATION_SETTING +") ON CONFLICT IGNORE); ";

        final String SQL_CREATE_WEATHER_TABLE =
                " CREATE TABLE " + WeatherEntry.TABLE_NAME
                        + " ( "
                        + WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + WeatherEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, "
                        + WeatherEntry.COLUMN_DATETEXT + " TEXT NOT NULL, "
                        + WeatherEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, "
                        + WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL, "

                        + WeatherEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, "
                        + WeatherEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, "

                        + WeatherEntry.COLUMN_HUMIDITY + " REAL NOT NULL, "
                        + WeatherEntry.COLUMN_PRESSURE + " REAL NOT NULL, "
                        + WeatherEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, "
                        + WeatherEntry.COLUMN_DEGREES + " REAL NOT NULL, "

                +" FOREIGN KEY ( "+WeatherEntry.COLUMN_LOC_KEY +" ) REFERENCES "
                +LocationEntry.TABLE_NAME + " ( " + LocationEntry._ID + " ), "

                +" UNIQUE ( " + WeatherEntry.COLUMN_DATETEXT + " , " +
                        WeatherEntry.COLUMN_LOC_KEY + " ) ON CONFLICT REPLACE); ";


        db.execSQL(SQL_CREATE_LOCATION_TABLE);
        db.execSQL(SQL_CREATE_WEATHER_TABLE);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+ LocationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXITS "+ WeatherEntry.TABLE_NAME);
        onCreate(db);
    }
}
