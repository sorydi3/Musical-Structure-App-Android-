package com.example.android.getaudios_from_device;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by sam on 29/05/2017.
 */

public class songs implements Parcelable{
    private String mSonArtist;
    private String mSongTitle;
    private String mSongPath;

    public songs(String Artist, String title,String Path){
        this.mSonArtist = Artist;
        this.mSongTitle = title;
       this.mSongPath=Path;
    }
   public songs(Parcel input){
       mSonArtist=input.readString();
       mSongTitle=input.readString();
       mSongPath=input.readString();
   }

    public String getSongArtist(){
        return mSonArtist;
    }

    public String getSongTitle(){
        return mSongTitle;
    }
    public String getPathSong(){
        return mSongPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSonArtist);
        dest.writeString(mSongTitle);
        dest.writeString(mSongPath);
    }
    public static final Parcelable.Creator<songs> CREATOR
            = new Parcelable.Creator<songs>() {
        public songs createFromParcel(Parcel in) {
            return new songs(in);
        }

        public songs[] newArray(int size) {
            return new songs[size];
        }
    };
}
