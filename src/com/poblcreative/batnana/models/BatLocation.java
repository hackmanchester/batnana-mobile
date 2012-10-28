package com.poblcreative.batnana.models;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author Shaun Rowe <shaun@poblcreative.com>
 *
 */
public class BatLocation
{

    @JsonProperty("FsqId")
    private String id;
    
    @JsonProperty("FsqName")
    private String name;

    @JsonProperty("TargetLat")
    private double latitude;

    @JsonProperty("TargetLng")
    private double longitude;

    private String getId()
    {
        return id;
    }

    private void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }

    public double getLatitude()
    {
        return latitude;
    }
    
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }
    
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

}