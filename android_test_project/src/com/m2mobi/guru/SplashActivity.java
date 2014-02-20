package com.m2mobi.guru;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import com.google.gson.Gson;
import com.m2mobi.guru.models.GeoList;
import com.m2mobi.guru.utils.AssetUtils;
import com.m2mobi.guru.utils.Constants;
import com.m2mobi.guru.utils.MyLog;

import java.util.Collections;

/**
 * Created by m2mobi on 2/10/14.
 */

public class SplashActivity extends ActionBarActivity implements Handler.Callback {

    private static final String LOG_TAG = "SplashActivity";

    /**
     * Splash screen timer
     */
    private long mSplashTime;

    /**
     * Minimal time the splash is visible
     */
    private static final int SPLASH_TIME_OUT = 2000;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyLog.i(LOG_TAG, "----- onCreate() -----");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Get the shared preferences
        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES._PREFERENCES_FILENAME, Context.MODE_PRIVATE);

        // Start the splash time
        mSplashTime = System.currentTimeMillis();

        boolean jsonStored = mSharedPreferences.getBoolean(Constants.SHARED_PREFERENCES.JSON_ORDERED, false);

        // Save the json file, but in properly formatted order (accented characters and such)
        if (!jsonStored) {
            // Fill the list of countries
            AssetManager assetManager = getAssets();
            String rawJson = AssetUtils.assetFileContents(assetManager, "countries_list.json");
            MyLog.d(LOG_TAG, "Raw country code data:");
            // MyLog.v(LOG_TAG, "" + rawJson);

            // Generate object list
            Gson gson = new Gson();
            GeoList geoList = gson.fromJson(rawJson, GeoList.class);

            // Order the objects
            Collections.sort(geoList.geonames);

            // Save the ordered json
            String orderedJson = gson.toJson(geoList, GeoList.class);

            // Save the contents to the file
            AssetUtils.saveFile(this, "countries_list.json", orderedJson);
        }

        // If the splash has been visible for a longer time then needed
        long timePassed = System.currentTimeMillis() - mSplashTime;

        long additionalDuration = 0;
        if (timePassed < SPLASH_TIME_OUT) {
            additionalDuration = SPLASH_TIME_OUT - timePassed;
        }

        MyLog.v(LOG_TAG, "additionalDuration: " + additionalDuration);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mSharedPreferences.edit().putBoolean(Constants.SHARED_PREFERENCES.JSON_ORDERED, true).commit();

                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }, additionalDuration);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Start the splash time
        mSplashTime = System.currentTimeMillis();
        MyLog.v(LOG_TAG, "mSplashTime: " + mSplashTime);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}

