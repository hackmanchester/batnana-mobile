package com.poblcreative.batnana.rest;

import java.util.ArrayList;

import com.poblcreative.batnana.models.BatLocation;

import org.codegist.crest.annotate.HeaderParam;
import org.codegist.crest.annotate.Path;
import org.codegist.crest.annotate.QueryParam;

/**
 * 
 * @author Shaun Rowe <shaun@poblcreative.com>
 * 
 */
public interface BatLocationSearchService
{

    /**
     * Get all properties near the users current location
     * 
     * @param latitude
     * @param longitude
     * @return ArrayList<BatLocation>
     */
    @Path("/4sq")
    ArrayList<BatLocation> nearMe(@QueryParam("lan") double latitude,
            @QueryParam("lng") double longitude,
            @QueryParam("radius") int radius);

}