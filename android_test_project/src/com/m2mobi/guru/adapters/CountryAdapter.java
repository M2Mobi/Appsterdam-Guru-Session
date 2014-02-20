package com.m2mobi.guru.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.m2mobi.guru.R;
import com.m2mobi.guru.models.GeoList;

/**
 * Created by m2mobi on 2/6/14.
 */
public class CountryAdapter extends BaseAdapter {

    private static final String LOG_TAG = "CountryAdapter";

    private GeoList mGeoList;
    private Context mContext;

    private static class ViewHolder {
        TextView countryName;
    }

    public CountryAdapter(final Context pContext, final GeoList pGeoList) {
        mContext = pContext;
        mGeoList = pGeoList;
    }

    public void setGeoList(final GeoList pGeoList) {
        mGeoList = pGeoList;

        // Update this view
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mGeoList.geonames.size();
    }

    @Override
    public Object getItem(int position) {
        return mGeoList.geonames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // if convertView is null, the view is newly inflated. else, re-assign new values.
        ListRow row;
        if (convertView == null) {
            row = new ListRow(mContext, null); // Inflate layout
        } else {
            row = (ListRow) convertView;
        }

        // Hide the row if filter-mode is on

        // Assign values
        //row.setIcon(some_image);
        row.setIconVisibility(mGeoList.geonames.get(position).isVisited);
        //row.setTitle(some_text);
        row.setSubtitle(mGeoList.geonames.get(position).countryName);

        return row;
    }

    // Custom class for ListRow
    private class ListRow extends LinearLayout {
        private ImageView mIcon;
        private TextView mTitle;
        private TextView mSubtitle;

        public ListRow(Context context, AttributeSet attrs) {
            super(context, attrs);

            // RelativeLayout intializations happen here
            LayoutInflater.from(context).inflate(R.layout.country_row, this);

            // Store the views.
            mIcon = (ImageView) findViewById(R.id.country_row_visited);
            // mTitle = (TextView) findViewById(R.id.title);
            mSubtitle = (TextView) findViewById(R.id.country_row_name);
        }

        public void setIcon(Drawable drawable) {
            mIcon.setImageDrawable(drawable);
        }

        public void setIconVisibility(final boolean isVisited) {
            if (isVisited) {
                mIcon.setVisibility(View.VISIBLE);
            } else {
                mIcon.setVisibility(View.GONE);
            }
        }

        public void setTitle(String text) {
            mTitle.setText(text);
        }

        public void setSubtitle(String subtitle) {
            mSubtitle.setText(subtitle); // perhaps text
        }
    }

}
