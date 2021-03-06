package com.dh.agus.digitalhousemusic.Model.POJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface serviceDeezer {

    @GET("track/{id}")
    Call<Track> getTrack(@Path("id") String id);

    @GET("album/{id}")
    Call<Album> getAlbum(@Path("id") String id);

    @GET("genre")
    Call<Genres> getGenres();

    @GET("genre/{id}")
    Call<Genre> getGenre(@Path("id") String id);

    @GET("genre/{id}/artists/?limit=20")
    Call<Artists> getGenreArtists(@Path("id") String id);

    @GET("artist/{id}/albums/?limit=2")
    Call<Albums> getArtistsAlbums(@Path("id") String id);

    @GET("album/{id}/tracks/?limit=10")
    Call<DataTracksList> getDataTracksList(@Path("id") String id);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.deezer.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
