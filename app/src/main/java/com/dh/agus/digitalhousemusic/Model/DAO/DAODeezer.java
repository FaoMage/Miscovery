package com.dh.agus.digitalhousemusic.Model.DAO;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
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

    public void getGenre (String genreId, Callback<Genre> controllerCallback) {
        Call<Genre> response = service.getGenre(genreId);
        response.enqueue(controllerCallback);
    }

    public void getAlbum (String albumId, Callback<Album> controllerCallback) {
        Call<Album> response = service.getAlbum(albumId);
        response.enqueue(controllerCallback);
    }

    public void getGenres(Callback<Genres> controllerCallback) {
        Call<Genres> response = service.getGenres();
        response.enqueue(controllerCallback);
    }
}
