package com.poblcreative.batnana.overlay;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.android.maps.MapView;
import com.poblcreative.batnana.CameraOverlayActivity;
import com.poblcreative.batnana.geolocation.LatLonPoint;

import java.util.ArrayList;

/**
 * 
 * @author Shaun Rowe <shaun@poblcreative.com>
 */
public class ItemizedBatnanaOverlay<Item extends BatnanaOverlayItem> extends
        BalloonItemizedOverlay<BatnanaOverlayItem>
{

    private ArrayList<BatnanaOverlayItem> overlays = new ArrayList<BatnanaOverlayItem>();
    private Context c;

    public ItemizedBatnanaOverlay(Drawable defaultMarker, MapView mapView)
    {
        super(boundCenter(defaultMarker), mapView);
        c = mapView.getContext();
    }

    public void addOverlay(BatnanaOverlayItem overlay)
    {
        overlays.add(overlay);
        populate();
    }

    @Override
    protected BatnanaOverlayItem createItem(int i)
    {
        return overlays.get(i);
    }

    @Override
    public int size()
    {
        return overlays.size();
    }

    @Override
    protected boolean onBalloonTap(int index, BatnanaOverlayItem item)
    {
        // long id = overlays.get(index).getPropertyId();

        // if(id != 0) {
        // Intent intent = new Intent(c, ViewPropertyActivity.class);
        // intent.setAction(ViewPropertyActivity.VIEW_PROPERTY);
        // intent.putExtra("id", id);
        // c.startActivity(intent);
        // }

        LatLonPoint latLonPoint = (LatLonPoint) overlays.get(index).getPoint();
        Intent intent = new Intent(c, CameraOverlayActivity.class);
        intent.putExtra("latitude", latLonPoint.getLatitude());
        intent.putExtra("longitude", latLonPoint.getLongitude());
        c.startActivity(intent);

        return true;
    }

    public void fakeTap(int index)
    {
        onTap(index);
    }

    @Override
    protected BatnanaBalloonOverlayView<BatnanaOverlayItem> createBalloonOverlayView()
    {
        return new BatnanaBalloonOverlayView<BatnanaOverlayItem>(getMapView()
                .getContext(), getBalloonBottomOffset());
    }

}