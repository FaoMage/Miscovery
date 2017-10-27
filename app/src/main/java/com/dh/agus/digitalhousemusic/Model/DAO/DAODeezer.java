package com.dh.agus.digitalhousemusic.Model.DAO;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
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

    public void getAlbum (String albumId, Callback<Album> albumCallback) {
        Call<Album> response = service.getAlbum(albumId);
        response.enqueue(albumCallback);
    }
}
