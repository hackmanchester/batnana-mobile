package com.poblcreative.batnana.overlay;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.poblcreative.batnana.R;
import com.poblcreative.batnana.overlay.BatnanaOverlayItem;

public class BatnanaBalloonOverlayView<Item extends BatnanaOverlayItem> extends BalloonOverlayView<Item>
{

    private ImageView image;

    public BatnanaBalloonOverlayView(Context context, int balloonBottomOffset)
    {
        super(context, balloonBottomOffset);
//        Log.i(this.toString(), "Children: " + getChildCount());
        LinearLayout layout = (LinearLayout) getChildAt(0);
        image = (ImageView) layout.findViewById(R.id.balloon_image);
        int heightDip = 65;
        int widthDip = 95;
        int heightPx = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDip, context.getResources().getDisplayMetrics()) + 0.5f);
        int widthPx = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthDip, context.getResources().getDisplayMetrics()) + 0.5f);

//        Log.i(this.toString(), widthDip + "dips wide is " + widthPx + "px, " + heightDip + "dips high is " + heightPx + "px");

        image.setAdjustViewBounds(true);
        image.setMaxHeight(heightPx);
        image.setMinimumHeight(heightPx);
        image.setMaxWidth(widthPx);
        image.setMinimumWidth(widthPx);
    }

    @Override
    public void setData(Item item)
    {
        Log.i(this.toString(), "Image is :" + item.getImage());
        if (item.getImage() != null) {
            image.setVisibility(VISIBLE);
//            String url = item.getImage();
//            ImageLoader imageLoader = new ImageLoader(getContext(), "thumbs");
//            imageLoader.DisplayImage(url, image);
        } else {
            image.setVisibility(GONE);
        }
        super.setData(item);
    }

}