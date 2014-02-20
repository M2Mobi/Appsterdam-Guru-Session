package com.m2mobi.guru;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.m2mobi.guru.adapters.CountryAdapter;
import com.m2mobi.guru.models.GeoList;
import com.m2mobi.guru.models.GeoName;
import com.m2mobi.guru.utils.AssetUtils;
import com.m2mobi.guru.utils.Constants;
import com.m2mobi.guru.utils.MyLog;

public class CountryListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String LOG_TAG = "ListActivity";

    private GeoList mGeoList;
    private ListView mListView;
    private Gson mGson;
    private boolean isFiltered = false;
    private CountryAdapter mCountryAdapter;

    /**
     * The distance the screen has been scrolled - used when restoring scrolled height
     */
    private int mCurrentPos = 0;
    private int mTopRow = 0;

    /**
     * Constants to fetch bundle keys when the activity gets destroyed (on lower quality devices)
     */
    private static final String RESTORED_CURRENT_POSITION = "RESTORED_CURRENT_POSITION";
    private static final String RESTORED_TOP_ROW = "RESTORED_TOP_ROW";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mGson = new Gson();

        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setOnItemClickListener(this);
        mListView.setEmptyView(findViewById(android.R.id.empty));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_list_myplaces:
                //mListView.invalidate();

                // Show all items
                if (!isFiltered) {
                    GeoList filtered = mGeoList.getVisitedOnly();

                    mCountryAdapter.setGeoList(filtered);
                    isFiltered = true;
                }

                // Show filtered items only
                else {
                    mCountryAdapter.setGeoList(mGeoList);
                    isFiltered = false;
                }

                break;
            default:
                throw new IllegalArgumentException("Element has no event specified.");
        }

        //((BaseAdapter) mListView.getAdapter()).notifyDataSetInvalidated();
        ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Fill the list of countries
        String rawJson = AssetUtils.savedFileContents(this, "countries_list.json");
        MyLog.d(LOG_TAG, "Raw country code data:");
        // MyLog.v(LOG_TAG, "" + rawJson);

        // Generate object list
        mGeoList = mGson.fromJson(rawJson, GeoList.class);

        MyLog.d(LOG_TAG, "geoList.geonames.size(): " + mGeoList.geonames.size());

        // Create the adapter
        mCountryAdapter = new CountryAdapter(this, mGeoList);
        mListView.setAdapter(mCountryAdapter);

        // Restore scroll location
        restoreScrollLocation();
    }

    /**
     * Saves the location of the listview.
     */
    private void saveScrollLocation() {
        MyLog.i(LOG_TAG, "saveScrollLocation()");

        mCurrentPos = mListView.getFirstVisiblePosition();
        View topRow = mListView.getChildAt(0);
        mTopRow = (topRow == null) ? 0 : topRow.getTop();
    }

    /**
     * Restores the location of the listview.
     */
    private void restoreScrollLocation() {
        MyLog.i(LOG_TAG, "restoreScrollLocation()");

        if (mCurrentPos != 0 && mTopRow != 0) {
            mListView.setSelectionFromTop(mCurrentPos, mTopRow);
        } else {
            MyLog.w(LOG_TAG, "Nothing to restore.");
        }
    }

    /**
     * From what the documentation says, it doesn't have to use this method, it's sort of a backup.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(RESTORED_CURRENT_POSITION, mCurrentPos);
        outState.putInt(RESTORED_TOP_ROW, mTopRow);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyLog.i(LOG_TAG, "onItemClick(" + position + ")");
        MyLog.d(LOG_TAG, "countryName: " + ((GeoName) parent.getItemAtPosition(position)).countryName);

        // Store scroll location
        saveScrollLocation();

        // Start the new activity with the bundled object
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.SERIALIZED_OBJECT.GEONAME, ((GeoName) parent.getItemAtPosition(position)));
        startActivity(intent);
    }
}
