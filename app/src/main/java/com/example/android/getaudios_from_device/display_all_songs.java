package com.example.android.getaudios_from_device;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.Manifest;
import android.widget.Toast;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class display_all_songs extends Fragment {
    private static final int MY_PERMITION_REQUEST=1;
    private ArrayList<songs> mArrayList;
    private songsAdapter mSongAdapter;
    private ListView mlistView;
    public display_all_songs() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_general, container, false);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMITION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMITION_REQUEST);
            }
        }else{
            mlistView=(ListView) rootView.findViewById(R.id.list);
            mArrayList=new ArrayList<>();
            Scan_songs();
            mSongAdapter=new songsAdapter(getActivity(),mArrayList);
            mlistView.setAdapter(mSongAdapter);
            mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   sendDataNowPlayingActivity(position);

                }
            });

            FloatingActionButton floatingActionButton=(FloatingActionButton) rootView.findViewById(R.id.floati_ac_buton);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getString(R.string.info_display_all_songs))
                            .setPositiveButton(getString(R.string.positiveButton), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .setNegativeButton("", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
        return rootView;
    }
    //function that scan the songs from the device
public void Scan_songs(){
    ContentResolver contentResolver = getActivity().getContentResolver();
    Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

    if(songCursor != null && songCursor.moveToFirst())
    {
        int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int songPath=songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

        do {
            String currentArtist = songCursor.getString(songArtist);
            String currentTitle = songCursor.getString(songTitle);
            String currentPath=songCursor.getString(songPath);
            mArrayList.add(new songs(currentArtist, currentTitle,currentPath));
        } while(songCursor.moveToNext());
    }
}

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
       switch (requestCode){
           case MY_PERMITION_REQUEST:
               if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                       Toast.makeText(getActivity(), "Permition Granted!", Toast.LENGTH_SHORT).show();
                       mlistView=(ListView) getView().findViewById(R.id.list);
                       mArrayList=new ArrayList<>();
                       //scan songs from the device
                       Scan_songs();
                       mSongAdapter=new songsAdapter(getActivity(),mArrayList);
                       mlistView.setAdapter(mSongAdapter);

                       mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               sendDataNowPlayingActivity(position);
                           }
                       });
                   }
               }else {
                   Toast.makeText(getActivity(), "No permition Granted!", Toast.LENGTH_SHORT).show();
               }
               return;
       }
    }

     ///methode that send data to the now playing activity combinig bundle and intent.
    public void sendDataNowPlayingActivity(int position){
        Intent intent = new Intent(getActivity(), Now_playing.class);
        // bundle that hold the data of the currently playing song
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ArrayList", mArrayList);
        bundle.putInt("position",position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
