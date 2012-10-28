/**
 * 
 */
package com.poblcreative.batnana;

import java.io.IOException;
import java.util.List;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
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

/**
 * @author Shaun Rowe <shaun@poblcreative.com>
 * 
 */
public class CameraOverlayActivity extends Activity
{
    private static final String TAG = "CameraOverlayActivity";
    Bitmap bitmap;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Preview mPreview = new Preview(this);
        DrawOnTop mDraw = new DrawOnTop(this);
        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.batnana_camera_overlay);
        setContentView(mPreview);
        addContentView(mDraw, new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
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
        Camera mCamera;
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
            mCamera = Camera.open();
            try {
                mCamera.setPreviewDisplay(holder);
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
            mCamera.stopPreview();
            mCamera = null;
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w,
                int h)
        {
            if (isPreviewRunning) {
                mCamera.stopPreview();
            }

            Parameters parameters = mCamera.getParameters();
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
                mCamera.setDisplayOrientation(90);
            }

            if (display.getRotation() == Surface.ROTATION_90) {
                parameters.setPreviewSize(width, height);
            }

            if (display.getRotation() == Surface.ROTATION_180) {
                parameters.setPreviewSize(height, width);
            }

            if (display.getRotation() == Surface.ROTATION_270) {
                parameters.setPreviewSize(width, height);
                mCamera.setDisplayOrientation(180);
            }

            parameters.setPreviewSize(width, height);
            mCamera.setDisplayOrientation(90);
            mCamera.setParameters(parameters);
            previewCamera();
        }

        public void previewCamera()
        {
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
                isPreviewRunning = true;
            } catch (Exception e) {
                Log.d(TAG, "Cannot start preview", e);
            }
        }
    }
}