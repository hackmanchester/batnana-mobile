package com.poblcreative.batnana;

import java.util.List;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import com.google.android.maps.MapActivity;
import com.poblcreative.batnana.geolocation.LatLonPoint;
import com.poblcreative.batnana.overlay.BatnanaOverlayItem;
import com.poblcreative.batnana.overlay.ItemizedBatnanaOverlay;

/**
 * 
 * @author Shaun Rowe <shaun@poblcreative.com>
 * 
 */
public class BatMapActivity extends com.google.android.maps.MapActivity
{

    public static String SHOW_BATS = "com.poblcreative.batnana.SHOW_BATS";
    private static final String TAG = "BatMapActivity";

    protected MapView map;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = (MapView) findViewById(R.id.batmapview);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (intent.getAction().equals(SHOW_BATS)) {
            Log.i(TAG, "We're showing the bats");
            double lat = extras.getDouble("latitude");
            double lng = extras.getDouble("longitude");
            String name = extras.getString("name");
            LatLonPoint latLonPoint = new LatLonPoint(lat, lng);
            map.setBuiltInZoomControls(true);
            map.setSatellite(false);
            map.getController().setZoom(20);
            map.getController().animateTo(latLonPoint);
            // map.getController().setCenter(latLonPoint);

            List<Overlay> overlays = map.getOverlays();
            Drawable drawable = getResources().getDrawable(R.drawable.banana);
            ItemizedBatnanaOverlay<BatnanaOverlayItem> itemizedOverlay = new ItemizedBatnanaOverlay<BatnanaOverlayItem>(
                    drawable, map);
            BatnanaOverlayItem overlayItem = new BatnanaOverlayItem(
                    latLonPoint, name, "A Batnana is hiding around here", 0);
            itemizedOverlay.addOverlay(overlayItem);
            overlays.add(itemizedOverlay);
            // itemizedOverlay.onTap(latLonPoint, map);
        }

        // getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // switch (item.getItemId()) {
        // case android.R.id.home:
        // NavUtils.navigateUpFromSameTask(this);
        // return true;
        // }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isRouteDisplayed()
    {
        // TODO Auto-generated method stub
        return false;
    }

}
