package com.poblcreative.batnana.geolocation;

import com.google.android.maps.GeoPoint;

/**
 * 
 * @author Shaun Rowe <shaun@poblcreative.com>
 */
public final class LatLonPoint extends GeoPoint
{

    public LatLonPoint(double latitude, double longitude)
    {
        super((int) (latitude * 1E6), (int) (longitude * 1E6));
    }

    public double getLatitude()
    {
        double latitude = super.getLatitudeE6();
        return (latitude / 1E6);
    }

    public double getLongitude()
    {
        double longitude = super.getLongitudeE6();
        return (longitude / 1E6);
    }

}