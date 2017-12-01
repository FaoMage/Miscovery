package com.dh.agus.digitalhousemusic.Controller;

import android.util.Log;

import com.dh.agus.digitalhousemusic.Model.DAO.DAODeezer;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Albums;
import com.dh.agus.digitalhousemusic.Model.POJO.Artist;
import com.dh.agus.digitalhousemusic.Model.POJO.Artists;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Genre;
import com.dh.agus.digitalhousemusic.Model.POJO.Genres;

import java.util.List;

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

    public void getArtistsAlbums (String artistId, final ResultListener<Albums> viewListener) {
        DAODeezer daoDeezer = new DAODeezer();
        daoDeezer.getArtistsAlbums(artistId, new Callback<Albums>() {
            @Override
            public void onResponse(Call<Albums> call, Response<Albums> response) {
                viewListener.finish(response.body());
            }

            @Override
            public void onFailure(Call<Albums> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getGenderArtists (String genderId, final ResultListener<Artists> viewListener) {
        DAODeezer daoDeezer = new DAODeezer();
        daoDeezer.getGenreArtists(genderId, new Callback<Artists>() {
            @Override
            public void onResponse(Call<Artists> call, Response<Artists> response) {
                viewListener.finish(response.body());
            }

            @Override
            public void onFailure(Call<Artists> call, Throwable t) {
                t.printStackTrace();
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

    public void getTrackList (final String albumId, final ResultListener<DataTracksList> viewListener){
        DAODeezer daoDeezer = new DAODeezer();
        daoDeezer.getDataTracksList(albumId, new Callback<DataTracksList>() {
            @Override
            public void onResponse(Call<DataTracksList> call, Response<DataTracksList> response) {
                viewListener.finish(response.body());
            }

            @Override
            public void onFailure(Call<DataTracksList> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


}
