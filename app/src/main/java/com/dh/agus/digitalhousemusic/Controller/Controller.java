package com.dh.agus.digitalhousemusic.Controller;

import com.dh.agus.digitalhousemusic.Model.DAO.DAODeezer;
import com.dh.agus.digitalhousemusic.Model.DAO.DAOFirebase;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.FavoriteTrack;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zetaxmage on 26/09/17.
 */

public class Controller {

    public void getAlbum (final String albumId, final ResultListener<Album> viewListener){
        DAODeezer daoDeezer = new DAODeezer();
        daoDeezer.getAlbum(albumId, new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                //viewListener.finish(response.body());
                checkFavorites(response.body(),viewListener);
            }

            @Override
            public void onFailure(Call<Album> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    // Se fija cuales de las canciones del album requerido son favoritos y lo agrega al POJO Track
    private void checkFavorites (final Album album, final ResultListener<Album> resultListener) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final DataTracksList dataTracksList = album.getTracks();
            DAOFirebase daoFirebase = new DAOFirebase();
            daoFirebase.getFavoriteTracks(new ResultListener<List<FavoriteTrack>>() {
                @Override
                public void finish(List<FavoriteTrack> result) {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        for (Track track : dataTracksList.getData()) {
                            String id = track.getId();
                            for (FavoriteTrack favoriteTrack : result) {
                                if (favoriteTrack.getTrackId().equals(id)) {
                                    track.setFavorite(true);
                                }
                            }
                        }
                        resultListener.finish(album);
                    }
                }
            });
        } else {
            resultListener.finish(album);
        }
    }

    public void getFavorites (final ResultListener<List<FavoriteTrack>> resultListener) {
        DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.getFavoriteTracks(new ResultListener<List<FavoriteTrack>>() {
            @Override
            public void finish(List<FavoriteTrack> result) {
                resultListener.finish(result);
            }
        });
    }
}
