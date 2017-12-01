package com.dh.agus.digitalhousemusic.Controller;

import com.dh.agus.digitalhousemusic.Model.DAO.DAODeezer;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Genre;
import com.dh.agus.digitalhousemusic.Model.POJO.Genres;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zetaxmage on 26/09/17.
 */

public class Controller {
    public void getAllGender(final ResultListener<Genres> viewListener) {
        DAODeezer daoDeezer = new DAODeezer();
        daoDeezer.getGenres(new Callback<Genres>() {
            @Override
            public void onResponse(Call<Genres> call, Response<Genres> response) {
                viewListener.finish(response.body());
            }

            @Override
            public void onFailure(Call<Genres> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public void getGenderArtists (String genderId, final ResultListener<Genre> viewListener) {
        DAODeezer daoDeezer = new DAODeezer();
        daoDeezer.getGenre(genderId, new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                viewListener.finish(response.body());
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public void getAlbum (String albumId, final ResultListener<Album> viewListener){
        DAODeezer daoDeezer = new DAODeezer();
        daoDeezer.getAlbum(albumId, new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                viewListener.finish(response.body());
            }

            @Override
            public void onFailure(Call<Album> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
