package com.dh.agus.digitalhousemusic.Model.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Album implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("cover_small")
    @Expose
    private String coverSmall;
    @SerializedName("cover_medium")
    @Expose
    private String coverMedium;
    @SerializedName("cover_big")
    @Expose
    private String coverBig;
    @SerializedName("cover_xl")
    @Expose
    private String coverXl;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("tracklist")
    @Expose
    private String tracklist;
    @SerializedName("tracks")
    @Expose
    private DataTracksList tracks;
    @SerializedName("type")
    @Expose
    private String type;

    protected Album(Parcel in) {
        id = in.readString();
        title = in.readString();
        link = in.readString();
        cover = in.readString();
        coverSmall = in.readString();
        coverMedium = in.readString();
        coverBig = in.readString();
        coverXl = in.readString();
        releaseDate = in.readString();
        tracklist = in.readString();
        tracks = in.readParcelable(DataTracksList.class.getClassLoader());
        type = in.readString();
    }

    // Constructor agregado para crear Favoritos
    public Album () {}

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    public String getCoverXl() {
        return coverXl;
    }

    public void setCoverXl(String coverXl) {
        this.coverXl = coverXl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public DataTracksList getTracks() {
        return tracks;
    }

    public void setDataTracksList(DataTracksList tracklist) {
        this.tracks = tracklist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeString(cover);
        parcel.writeString(coverSmall);
        parcel.writeString(coverMedium);
        parcel.writeString(coverBig);
        parcel.writeString(coverXl);
        parcel.writeString(releaseDate);
        parcel.writeString(tracklist);
        parcel.writeParcelable(tracks, i);
        parcel.writeString(type);
    }
}
