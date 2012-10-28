/**
 * 
 */
package com.poblcreative.batnana.rest;

import java.util.ArrayList;

import com.poblcreative.batnana.models.BatLocation;

/**
 * @author Shaun Rowe <shaun@poblcreative.com>
 * 
 */
public class BatLocationSearch
{

    BatLocationSearchService service;

    public BatLocationSearch()
    {
        service = RestClient.getBatLocationSearchService();
    }

    public ArrayList<BatLocation> search(double lat, double lng, int radius)
    {
        ArrayList<BatLocation> locations = service.nearMe(lat, lng, radius);

        return locations;
    }
}