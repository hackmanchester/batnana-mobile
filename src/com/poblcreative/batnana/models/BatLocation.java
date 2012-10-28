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
    private int id;
    
    @JsonProperty("FsqName")
    private String name;

    @JsonProperty("Lat")
    private double latitude;

    @JsonProperty("Lng")
    private double longitude;

    private int getId()
    {
        return id;
    }

    private void setId(int id)
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