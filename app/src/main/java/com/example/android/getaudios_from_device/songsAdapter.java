package com.example.android.getaudios_from_device;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sam on 29/05/2017.
 */

public class songsAdapter extends ArrayAdapter<songs> {
    public songsAdapter(Activity context, ArrayList<songs> mArrayList) {
        super(context, 0,mArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_adapter, parent, false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list
        final songs currentword  = getItem(position);
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView TITLE = (TextView) listItemView.findViewById(R.id.text1);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        TITLE.setText(currentword.getSongTitle());
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView ARTIST = (TextView) listItemView.findViewById(R.id.text2);
        // Get the version number from the current AndroidFlavor object and
        //set this text on the number TextView
        ARTIST.setText(currentword.getSongArtist());
        TextView path = (TextView) listItemView.findViewById(R.id.text3);
        // Get the version number from the current AndroidFlavor object and
        //set this text on the number TextView
        path.setText(currentword.getPathSong());

        return listItemView;
    }

}
