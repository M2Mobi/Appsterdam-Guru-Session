package com.m2mobi.guru.models;
import com.m2mobi.guru.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m2mobi on 2/6/14.
 */
public class GeoList {

    private static final String LOG_TAG = "GeoList";

    public List<GeoName> geonames = new ArrayList<GeoName>(5);

    public int getVisitedCount() {
        int count = 0;

        for (int i = 0; i < geonames.size(); i++) {
            if (geonames.get(i).isVisited) {
                count++;
            }
        }

        return count;
    }

    public GeoList getVisitedOnly() {
        MyLog.i(LOG_TAG, "getVisitedOnly()");

        GeoList visited = new GeoList();

        for (int i = 0; i < this.geonames.size(); i++) {
            if (this.geonames.get(i).isVisited) {
                MyLog.d(LOG_TAG, "adding: " + this.geonames.get(i).countryName + " (#" + i + ")");
                visited.geonames.add(this.geonames.get(i));
            }
        }

        return visited;
    }

}
