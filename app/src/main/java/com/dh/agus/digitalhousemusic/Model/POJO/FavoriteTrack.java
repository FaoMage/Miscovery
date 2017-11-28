package com.dh.agus.digitalhousemusic.Model.POJO;

/**
 * Created by ZetaxMage on 27/11/2017.
 */

public class FavoriteTrack {
    private String trackId;
    private String trackTitle;
    private String albumId;
    private String albumTitle;

    public FavoriteTrack(String trackId, String trackTitle, String albumId, String albumTitle) {
        this.trackId = trackId;
        this.trackTitle = trackTitle;
        this.albumId = albumId;
        this.albumTitle = albumTitle;
    }

    public FavoriteTrack() {
    }

    public String getTrackId() {
        return trackId;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }
}
