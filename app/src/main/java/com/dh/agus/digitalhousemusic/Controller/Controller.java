package com.dh.agus.digitalhousemusic.Controller;

import com.dh.agus.digitalhousemusic.Model.DAO.DAODeezer;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;

import retrofit2.Callback;

/**
 * Created by zetaxmage on 26/09/17.
 */

public class Controller {

    public void getAlbum (String albumId, Callback<Album> albumCallback){
        DAODeezer daoDeezer = new DAODeezer();
        daoDeezer.getAlbum(albumId,albumCallback);
    }
}
