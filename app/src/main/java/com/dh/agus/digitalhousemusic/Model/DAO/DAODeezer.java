package com.dh.agus.digitalhousemusic.Model.DAO;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Albums;
import com.dh.agus.digitalhousemusic.Model.POJO.Artists;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Genre;
import com.dh.agus.digitalhousemusic.Model.POJO.Genres;
import com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer;

import retrofit2.Call;
import retrofit2.Callback;

import static com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer.retrofit;


/**
 * Created by zetaxmage on 26/09/17.
 */

public class DAODeezer {

    private serviceDeezer service;

    public DAODeezer() {
        service = retrofit.create(serviceDeezer.class);
    }

    public void getArtistsAlbums (String artistId, Callback<Albums> controllerCallback) {
        Call<Albums> response = service.getArtistsAlbums(artistId);
        response.enqueue(controllerCallback);
    }

    public void getGenreArtists (String genreId, Callback<Artists> controllerCallback) {
        Call<Artists> response = service.getGenreArtists(genreId);
        response.enqueue(controllerCallback);
    }

    public void getGenre (String genreId, Callback<Genre> controllerCallback) {
        Call<Genre> response = service.getGenre(genreId);
        response.enqueue(controllerCallback);
    }

    public void getAlbum (String albumId, Callback<Album> controllerCallback) {
        Call<Album> response = service.getAlbum(albumId);
        response.enqueue(controllerCallback);
    }

    public void getDataTracksList (String albumId, Callback<DataTracksList> controllerCallback) {
        Call<DataTracksList> response = service.getDataTracksList(albumId);
        response.enqueue(controllerCallback);
    }

    public void getGenres(Callback<Genres> controllerCallback) {
        Call<Genres> response = service.getGenres();
        response.enqueue(controllerCallback);
    }
}
