package com.dh.agus.digitalhousemusic.Controller;

import com.dh.agus.digitalhousemusic.Model.DAO.DAODeezer;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zetaxmage on 26/09/17.
 */

public class Controller {

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
