/**
 * 
 */
package com.poblcreative.batnana.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poblcreative.batnana.R;
import com.poblcreative.batnana.models.BatLocation;

/**
 * @author Shaun Rowe <shaun@poblcreative.com>
 *
 */
public class BatLocationListAdapter extends ArrayAdapter<BatLocation>
{

    public LayoutInflater inflater;
    public Activity activity;

    public BatLocationListAdapter(Activity context, int textViewResourceId, ArrayList<BatLocation> objects)
    {
        super(context, textViewResourceId, objects);
        activity = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        RelativeLayout vi = (RelativeLayout) convertView;
        if (convertView == null) {
            vi = (RelativeLayout) inflater.inflate(R.layout.search_result_item, null);
        }
        BatLocation batLocation = (BatLocation) getItem(position);

        ((TextView) vi.findViewById(R.id.location_name)).setText(batLocation.getName());
        return vi;
    }

}