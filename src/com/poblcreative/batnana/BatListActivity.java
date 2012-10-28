package com.poblcreative.batnana;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.poblcreative.batnana.adapter.BatLocationListAdapter;
import com.poblcreative.batnana.geolocation.LatLonPoint;
import com.poblcreative.batnana.models.BatLocation;
import com.poblcreative.batnana.rest.BatLocationSearch;
import com.poblcreative.batnana.rest.BatLocationSearchService;

/**
 * 
 * @author Shaun Rowe <shaun@poblcreative.com>
 *
 */
public class BatListActivity extends Activity
{

    public static final String NEARBY_BATS = "com.poblcreative.batnana.NEARBY_BATS";
    protected ListView results;
    private static final String TAG = "BatListActivity";
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bat_list);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        
        results = (ListView)findViewById(R.id.bat_list); 

        if (intent.getAction().equals(NEARBY_BATS)) {
             nearMeSearch();
//            BatTask task = new BatTask();
//            task.execute(new LatLonPoint(53.47620398396408, -2.2531387209892273));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_bat_list, menu);
        return true;
    }

    private void nearMeSearch()
    {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location)
            {
                NearbyBatTask searchTask = new NearbyBatTask();
                searchTask.execute(location);
            }

            public void onStatusChanged(String provider, int status,
                    Bundle extras)
            {
                // throw new
                // UnsupportedOperationException("Not supported yet.");
            }

            public void onProviderEnabled(String provider)
            {
                // throw new
                // UnsupportedOperationException("Not supported yet.");
            }

            public void onProviderDisabled(String provider)
            {
                // throw new
                // UnsupportedOperationException("Not supported yet.");
            }

        };
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        locationManager.requestLocationUpdates(
                locationManager.getBestProvider(criteria, true), 72000, 5,
                locationListener);
    }

    private class BatTask extends
            AsyncTask<LatLonPoint, Void, ArrayList<BatLocation>> implements
            OnCancelListener
    {

        private ProgressDialog dialog;
        public static final String TAG = "BatTask";

        @Override
        public void onCancel(DialogInterface dialog)
        {
            // TODO Auto-generated method stub
            cancel(true);
            finish();
        }

        @Override
        protected void onPreExecute()
        {
            dialog = ProgressDialog.show(BatListActivity.this, "Searching",
                    "Finding nearby locations...", false, true, this);
        }

        @Override
        protected ArrayList<BatLocation> doInBackground(LatLonPoint... params)
        {
            LatLonPoint latLonPoint = params[0];
            Log.i(BatTask.TAG,
                    "Looking for locations at Lat: "
                            + latLonPoint.getLatitude() + ", Lng: "
                            + latLonPoint.getLongitude());

            BatLocationSearch search = new BatLocationSearch();
            ArrayList<BatLocation> locations = search.search(
                    latLonPoint.getLatitude(), latLonPoint.getLongitude(), 2);

            return locations;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(ArrayList<BatLocation> result)
        {
            dialog.dismiss();
            
            BatLocationListAdapter adapter = new BatLocationListAdapter(BatListActivity.this, R.layout.search_result_item, result);
            results.setTextFilterEnabled(true);
            results.setAdapter(adapter);
            results.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int item, long arg3)
                {
                    BatLocation batLocation = (BatLocation) results.getAdapter().getItem(item);

                    Intent intent = new Intent(BatListActivity.this,
                            BatMapActivity.class);
                    intent.setAction(BatMapActivity.SHOW_BATS);
                    intent.putExtra("latitude", batLocation.getLatitude());
                    intent.putExtra("longitude", batLocation.getLongitude());
                    intent.putExtra("name", batLocation.getName());
                    startActivity(intent);
                }
                
            });
        }

    }

    private class NearbyBatTask extends
            AsyncTask<Location, Void, ArrayList<BatLocation>> implements
            OnCancelListener
    {

        private ProgressDialog dialog;
        private static final String TAG = "NearbyBatTask";

        @Override
        public void onCancel(DialogInterface dialog)
        {
            // TODO Auto-generated method stub
            cancel(true);
            finish();
        }

        @Override
        protected void onPreExecute()
        {
            dialog = ProgressDialog.show(BatListActivity.this, "Searching",
                    "Finding nearby locations...");
        }

        @Override
        protected ArrayList<BatLocation> doInBackground(Location... params)
        {
            Location latLonPoint = params[0];
            Log.i(BatTask.TAG,
                    "Looking for locations at Lat: "
                            + latLonPoint.getLatitude() + ", Lng: "
                            + latLonPoint.getLongitude());

            BatLocationSearch search = new BatLocationSearch();
            ArrayList<BatLocation> locations = search.search(
                    latLonPoint.getLatitude(), latLonPoint.getLongitude(), 1609);

            return locations;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(ArrayList<BatLocation> result)
        {
            dialog.dismiss();
            
            BatLocationListAdapter adapter = new BatLocationListAdapter(BatListActivity.this, R.layout.search_result_item, result);
            results.setTextFilterEnabled(true);
            results.setAdapter(adapter);
            results.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int item, long arg3)
                {
                    BatLocation batLocation = (BatLocation) results.getAdapter().getItem(item);

                    Intent intent = new Intent(BatListActivity.this,
                            BatMapActivity.class);
                    intent.setAction(BatMapActivity.SHOW_BATS);
                    intent.putExtra("latitude", batLocation.getLatitude());
                    intent.putExtra("longitude", batLocation.getLongitude());
                    intent.putExtra("name", batLocation.getName());
                    startActivity(intent);
                }
                
            });
        }

    }

}