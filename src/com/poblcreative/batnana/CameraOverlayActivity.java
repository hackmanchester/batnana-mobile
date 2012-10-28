/**
 * 
 */
package com.poblcreative.batnana;

import java.io.IOException;
import java.util.List;

import com.poblcreative.batnana.geolocation.LatLonPoint;

import android.R.bool;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * @author Shaun Rowe <shaun@poblcreative.com>
 * 
 */
public class CameraOverlayActivity extends Activity implements SensorEventListener
{
    private static final String TAG = "CameraOverlayActivity";
    private SensorManager sensorManager;
    private Sensor compass;
    LocationManager locationManager;
    LocationListener locationListener;
    Location location;
    LatLonPoint batnanaPoint;
    MediaPlayer player;
    
    Bitmap bitmap;
    DrawOnTop draw;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Preview preview = new Preview(this);
        setContentView(preview);
        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.batnana_camera_overlay);
        draw = new DrawOnTop(this);
        draw.setVisibility(View.GONE);
        addContentView(draw, new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
//        RelativeLayout controls = (RelativeLayout) findViewById(R.layout.camera_controls);
//        addContentView(controls, new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT));
//        controls.bringToFront();
        
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        batnanaPoint = new LatLonPoint(extras.getDouble("latitude"), extras.getDouble("longitude"));
        
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        compass = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL);
        
        AssetFileDescriptor afd;
        try {
            afd = getAssets().openFd("batman-intro.mp3");
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor());
            player.prepare();
            player.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        myLocation();
    }
    
    private void myLocation()
    {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location newLocation)
            {
                location = newLocation;
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

    class DrawOnTop extends View
    {
        public DrawOnTop(Context context)
        {
            super(context);

        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            // Paint paint = new Paint();
            // paint.setStyle(Paint.Style.STROKE);
            // paint.setColor(Color.BLACK);
            // canvas.drawText("Test Text", 10, 10, paint);
            canvas.drawBitmap(bitmap, 150, 200, null);
            
            super.onDraw(canvas);
        }
    }

    class Preview extends SurfaceView implements SurfaceHolder.Callback
    {
        SurfaceHolder mHolder;
        Camera camera;
        boolean isPreviewRunning;

        Preview(Context context)
        {
            super(context);
            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void surfaceCreated(SurfaceHolder holder)
        {
            // The Surface has been created, acquire the camera and tell it
            // where
            // to draw.
            camera = Camera.open();
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder)
        {
            // Surface will be destroyed when we return, so stop the preview.
            // Because the CameraDevice object is not a shared resource, it's
            // very
            // important to release it when the activity is paused.
            camera.stopPreview();
            camera = null;
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w,
                int h)
        {
            if (isPreviewRunning) {
                camera.stopPreview();
            }

            Parameters parameters = camera.getParameters();
            Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
                    .getDefaultDisplay();

            List<Camera.Size> previewSizes = parameters
                    .getSupportedPreviewSizes();
            for (Camera.Size size : previewSizes) {
                Log.i(TAG, "Size found, width: " + size.width + ", height: "
                        + size.height);
            }

            Log.i(TAG, "Width sent: " + w + ", height sent: " + h);

            Camera.Size size = previewSizes.get(2);
            int height = size.height;
            int width = size.width;

            if (display.getRotation() == Surface.ROTATION_0) {
                parameters.setPreviewSize(height, width);
                camera.setDisplayOrientation(90);
            }

            if (display.getRotation() == Surface.ROTATION_90) {
                parameters.setPreviewSize(width, height);
            }

            if (display.getRotation() == Surface.ROTATION_180) {
                parameters.setPreviewSize(height, width);
            }

            if (display.getRotation() == Surface.ROTATION_270) {
                parameters.setPreviewSize(width, height);
                camera.setDisplayOrientation(180);
            }

            parameters.setPreviewSize(width, height);
            camera.setDisplayOrientation(90);
            camera.setParameters(parameters);
            previewCamera();
        }

        public void previewCamera()
        {
            try {
                camera.setPreviewDisplay(mHolder);
                camera.startPreview();
                isPreviewRunning = true;
            } catch (Exception e) {
                Log.d(TAG, "Cannot start preview", e);
            }
        }
        
        Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            
            @Override
            public void onPictureTaken(byte[] data, Camera camera)
            {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Canvas canvas = new Canvas(BitmapFactory.decodeByteArray(data, 0, data.length, options));
                Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.batnana_camera_overlay);
                canvas.drawBitmap(overlay, 150, 200, new Paint());
                canvas.save();
            }
        };
    }

    /* (non-Javadoc)
     * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    /* (non-Javadoc)
     * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
     */
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float azimuth = Math.round(event.values[0]); // get azimuth from the orientation sensor (it's quite simple)
        
        if(location != null) {
            Location currentLoc = location;// get location from GPS or network
            // convert radians to degrees
//            azimuth = (azimuth * 180) / (float) Math.PI;
            GeomagneticField geoField = new GeomagneticField(
                         Double.valueOf(currentLoc.getLatitude()).floatValue(),
                         Double.valueOf(currentLoc.getLongitude()).floatValue(),
                         Double.valueOf(currentLoc.getAltitude()).floatValue(),
                         System.currentTimeMillis());
            azimuth += geoField.getDeclination(); // converts magnetic north into true north
            Location target = new Location(currentLoc);
            target.setLatitude(batnanaPoint.getLatitude());
            target.setLongitude(batnanaPoint.getLongitude());
            float bearing = currentLoc.bearingTo(target); // (it's already in degrees)
//            float bearing = target.bearingTo(currentLoc);
            float direction = azimuth - bearing;
            
            Log.i(TAG, "Azimuth: " + Float.toString(azimuth));
            Log.i(TAG, "Bearing: " + Float.toString(bearing));
            Log.i(TAG, "Direction: " + Float.toString(direction));
            
            int azimuthRounded = Math.round(azimuth);
            int directionRounded = Math.round(direction);
            int bearingRounded = Math.round(bearing);
            
            // The other values provided are: 
            //  float pitch = event.values[1];
            //  float roll = event.values[2];
            if(Math.abs(bearing-azimuth) <= 10) {
                draw.setVisibility(View.VISIBLE);
            } else {
                draw.setVisibility(View.GONE);
            }
        }
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed()
    {
        sensorManager.unregisterListener(this);
        player.stop();
        super.onBackPressed();
    }
}