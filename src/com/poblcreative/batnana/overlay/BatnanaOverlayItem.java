package com.poblcreative.batnana.overlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 * 
 * @author Shaun Rowe <shaun@poblcreative.com>
 */
public final class BatnanaOverlayItem extends OverlayItem
{

    private long batnanaId;
    private String image;

    public BatnanaOverlayItem(GeoPoint point, String title, String snippet,
            long id)
    {
        super(point, title, snippet);
        setBatnanaId(id);
    }

    /**
     * @return the propertyId
     */
    public long getBatnanaId()
    {
        return batnanaId;
    }

    /**
     * @param batnanaId
     *            the propertyId to set
     */
    public void setBatnanaId(long batnanaId)
    {
        this.batnanaId = batnanaId;
    }

    /**
     * @return the image
     */
    public String getImage()
    {
        return image;
    }

    /**
     * @param image
     *            the image to set
     */
    public void setImage(String image)
    {
        this.image = image;
    }

}
