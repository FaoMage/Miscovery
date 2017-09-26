package com.dh.agus.digitalhousemusic.Model.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataTracksList implements Parcelable {
    @SerializedName("data")
    @Expose
    private ArrayList<Track> data;

    protected DataTracksList(Parcel in) {
        data = in.createTypedArrayList(Track.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataTracksList> CREATOR = new Creator<DataTracksList>() {
        @Override
        public DataTracksList createFromParcel(Parcel in) {
            return new DataTracksList(in);
        }

        @Override
        public DataTracksList[] newArray(int size) {
            return new DataTracksList[size];
        }
    };

    public ArrayList<Track> getData() {
        return data;
    }

    public void setData(ArrayList<Track> tracks) {
        this.data = tracks;
    }
}
