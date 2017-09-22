package com.dh.agus.digitalhousemusic.Model.POJO;

public class Song {
    private Integer id;
    private String name;
    private String album;
    private Integer albumImage;
    private String artist;
    private String previewUrl;


    public Song(Integer id, String name, String album, Integer albumImage, String artist,
                String previewUrl) {
        this.id = id;
        this.name = name;
        this.album = album;
        this.albumImage = albumImage;
        this.artist = artist;
        this.previewUrl = previewUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getAlbum() {
        return album;
    }


    public Integer getAlbumImage() {
        return albumImage;
    }



    public String getArtist() {
        return artist;
    }


    public String getPreviewUrl() {
        return previewUrl;
    }
}
