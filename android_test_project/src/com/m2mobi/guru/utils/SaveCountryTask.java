package com.m2mobi.guru.utils;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import com.google.gson.Gson;
import com.m2mobi.guru.models.GeoList;
import com.m2mobi.guru.models.GeoName;

import java.lang.ref.WeakReference;

/**
 * Created by m2mobi on 2/10/14.
 */
public class SaveCountryTask extends AsyncTask<Boolean, Void, Boolean> {

    private static final String LOG_TAG = "SaveCountryTask";

    private WeakReference<ActionBarActivity> mReference;
    private Handler mHandler;
    private GeoName mGeoName;

    public SaveCountryTask(final ActionBarActivity activity, final Handler pHandler, final GeoName pGeoName) {
        mReference = new WeakReference<ActionBarActivity>(activity);
        mHandler = pHandler;
        mGeoName = pGeoName;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        MyLog.i(LOG_TAG, "doInBackground(" + params[0] + ")");

        ActionBarActivity activity = mReference.get();

        if (activity != null) {
            // Deserialize the object
            String countriesRaw = AssetUtils.savedFileContents(activity, "countries_list.json");

            Gson gson = new Gson();
            GeoList list = gson.fromJson(countriesRaw, GeoList.class);

            putVisited(list, mGeoName.countryName, params[0]);

            String changedRaw = gson.toJson(list, GeoList.class);

            // Store object
            AssetUtils.saveFile(activity, "countries_list.json", changedRaw);

            // return value
            return true;
        }

        return false;
    }

    private void putVisited(final GeoList pList, final String pCountryName, final boolean isVisited) {
        MyLog.i(LOG_TAG, "putVisited(" + pCountryName + ", " + isVisited + ")");

        for (int i = 0; i < pList.geonames.size(); i++) {
            MyLog.v(LOG_TAG, "country: " + pList.geonames.get(i).countryName);

            if (pList.geonames.get(i).countryName.equals(pCountryName)) {
                MyLog.d(LOG_TAG, "Changing country: " + pList.geonames.get(i).countryName);
                pList.geonames.get(i).isVisited = isVisited;
                return;
            }
        }
    }

    @Override
    protected void onPostExecute(final Boolean pVisited) {
        super.onPostExecute(pVisited);

        // If the activity is still alive
        if (mReference.get() != null) {
            mHandler.sendEmptyMessage(0);
        }
    }
}
