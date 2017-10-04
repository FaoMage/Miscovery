package com.dh.agus.digitalhousemusic.Model.POJO;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZetaxMage on 04/10/2017.
 */

public class Favoritos extends Album {
    List<Track> trackList = new ArrayList<>();

    public Favoritos () {
        setTitle("Favoritos");
    }

    public void addToFavorites (Track track) {
        trackList.add(track);
    }

    public void deleteFromFavorites (Track track) {
        if (trackList.contains(track)) {
            trackList.remove(track);
        }
    }
}
