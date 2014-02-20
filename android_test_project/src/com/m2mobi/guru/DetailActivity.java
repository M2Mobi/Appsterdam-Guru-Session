package com.m2mobi.guru;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.m2mobi.guru.models.GeoName;
import com.m2mobi.guru.utils.MyLog;
import com.m2mobi.guru.utils.SaveCountryTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.security.InvalidParameterException;

import static com.m2mobi.guru.utils.Constants.SERIALIZED_OBJECT;

/**
 * Created by m2mobi on 2/7/14.
 */
public class DetailActivity extends ActionBarActivity implements View.OnClickListener, Handler.Callback {

    private static final String LOG_TAG = "DetailActivty";

    private GeoName mGeoName;

    private ToggleButton mVisitedToggle;
    private ProgressBar mProgressBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        MyLog.i(LOG_TAG, "onCreate()");

        // Get the bundled object
        if (!getIntent().hasExtra(SERIALIZED_OBJECT.GEONAME)) {
            throw new InvalidParameterException("Activity requires geoname object.");
        }

        // Enable up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGeoName = (GeoName) getIntent().getSerializableExtra(SERIALIZED_OBJECT.GEONAME);

        // Download the flag image
        ImageView imageView = (ImageView) findViewById(R.id.detail_flag);
        String imageUrl = String.format(getString(R.string.detail_imageurl), mGeoName.countryCode.toLowerCase());
        MyLog.d(LOG_TAG, "imageUrl: " + imageUrl);
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options); // Incoming options will be used

        // Progressbar indicator
        mProgressBar = (ProgressBar) findViewById(R.id.detail_progress);

        // Country name and code
        String countryName = String.format(getString(R.string.detail_country_name), mGeoName.countryName, mGeoName.countryCode);
        ((TextView) findViewById(R.id.detail_countryname)).setText(countryName);

        // Capital
        ((TextView) findViewById(R.id.detail_capital)).setText(mGeoName.capital);

        // Continent and abbreviation
        String continent = String.format(getString(R.string.detail_continent), mGeoName.continentName, mGeoName.continent);
        ((TextView) findViewById(R.id.detail_continent)).setText(continent);

        // Area in km2
        ((TextView) findViewById(R.id.detail_area)).setText(mGeoName.areaInSqKm);

        // Currency
        ((TextView) findViewById(R.id.detail_currency)).setText(mGeoName.currencyCode);

        // Language
        ((TextView) findViewById(R.id.detail_language)).setText(mGeoName.languages);

        // Population
        ((TextView) findViewById(R.id.detail_population)).setText(mGeoName.population);

        // Visited toggle
        mVisitedToggle = (ToggleButton) findViewById(R.id.detail_visited);
        mVisitedToggle.setChecked(mGeoName.isVisited);

        mVisitedToggle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_visited:
                Handler handler = new Handler(DetailActivity.this);

                // Show the loading indicator
                mProgressBar.setVisibility(View.VISIBLE);
                mVisitedToggle.setVisibility(View.INVISIBLE);

                // Set the country as visited
                SaveCountryTask saveTask = new SaveCountryTask(DetailActivity.this, handler, mGeoName);
                saveTask.execute(mVisitedToggle.isChecked());
                break;
            default:
                throw new IllegalArgumentException("Element has no event specified.");
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        mProgressBar.setVisibility(View.GONE);
        mVisitedToggle.setVisibility(View.VISIBLE);

        return false;
    }
}