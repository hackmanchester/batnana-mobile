package com.poblcreative.batnana;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

/**
 * 
 * @author Shaun Rowe <shaun@poblcreative.com>
 *
 */
public class MainActivity extends Activity
{

    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void login(View buttonView)
    {
        Log.i(TAG, "Login clicked");
        // Intent intent = new Intent(this, BatListActivity.class);
        // intent.setAction(BatListActivity.NEARBY_BATS);
        Intent intent = new Intent(this, CameraOverlayActivity.class);
        startActivity(intent);
    }

}