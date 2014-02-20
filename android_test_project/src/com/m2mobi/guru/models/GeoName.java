package com.m2mobi.guru.models;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.Normalizer;

/**
 * Created by m2mobi on 2/6/14.
 */
public class GeoName implements Serializable, Comparable<GeoName> {

    private static final long serialVersionUID = 5879749452042870625L;

    @SerializedName("areaInSqKm")
	public String areaInSqKm;

	@SerializedName("capital")
	public String capital;

	@SerializedName("continent")
	public String continent;

	@SerializedName("continentName")
	public String continentName;

	@SerializedName("countryCode")
	public String countryCode;

	@SerializedName("countryName")
	public String countryName;

	@SerializedName("currencyCode")
	public String currencyCode;

	@SerializedName("fipsCode")
	public String fipsCode;

	@SerializedName("geonameId")
	public Integer geonameId;

	@SerializedName("isoAlpha3")
	public String isoAlpha3;

	@SerializedName("isoNumeric")
	public String isoNumeric;

	@SerializedName("languages")
	public String languages;

	@SerializedName("population")
	public String population; // Should not be a String

    public boolean isVisited = false;

	@SerializedName("north")
	public Double north;

	@SerializedName("east")
	public Double east;

	@SerializedName("south")
	public Double south;

	@SerializedName("west")
	public Double west;

    @Override
    public int compareTo(final GeoName pGeoName) {
        //final int BEFORE = -1;
        final int EQUAL = 0;
        //final int AFTER = 1;

        // Normalize text
        String countryName_a = removeDiacriticalMarks(countryName);
        String countryName_b = removeDiacriticalMarks(pGeoName.countryName);

        int comparison = countryName_a.compareTo(countryName_b);
        if (comparison != EQUAL){
            return comparison;
        }

        return EQUAL;
    }

    public static final String removeDiacriticalMarks(final String pString) {
        return Normalizer.normalize(pString, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}
