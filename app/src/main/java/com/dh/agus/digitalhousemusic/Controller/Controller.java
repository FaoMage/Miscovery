package com.dh.agus.digitalhousemusic.Controller;

import android.util.Log;

import com.dh.agus.digitalhousemusic.Model.DAO.DAODeezer;
import com.dh.agus.digitalhousemusic.Model.DAO.DAOFirebase;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Albums;
import com.dh.agus.digitalhousemusic.Model.POJO.Artists;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Genres;

import java.util.List;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Queue;

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

    public void getAlbum (final String albumId, final ResultListener<Album> viewListener){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // Si esta logueado
            getRecentAlbums(new ResultListener<List<Album>>() {
                @Override
                public void finish(final List<Album> result) {
                    Integer i = isInRecents(result,albumId);
                    if (i >= 0) {
                        //si esta en recents manda ese
                        checkFavorites(result.get(i), viewListener);
                    } else {
                        //si no esta, lo pide a deezer
                        DAODeezer daoDeezer = new DAODeezer();
                        daoDeezer.getAlbum(albumId, new Callback<Album>() {
                            @Override
                            public void onResponse(Call<Album> call, Response<Album> response) {
                                addRecentAlbum(response.body());
                                checkFavorites(response.body(), viewListener);
                            }

                            @Override
                            public void onFailure(Call<Album> call, Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
                    }
                }
            });
        } else {
            // Si no
            DAODeezer daoDeezer = new DAODeezer();
            daoDeezer.getAlbum(albumId, new Callback<Album>() {
                @Override
                public void onResponse(Call<Album> call, Response<Album> response) {
                    viewListener.finish(response.body());
                }

                @Override
                public void onFailure(Call<Album> call, Throwable throwable) {

                }
            });

        }
    }

    private Integer isInRecents (List<Album> albumList, String id) {
        for (Integer i = 0; i<albumList.size(); i++){
            if (albumList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    // Se fija cuales de las canciones del album requerido son favoritos y lo agrega al POJO Track
    private void checkFavorites (final Album album, final ResultListener<Album> resultListener) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final DataTracksList dataTracksList = album.getTracks();
            DAOFirebase daoFirebase = new DAOFirebase();
            daoFirebase.getFavoriteTracks(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> result) {
                    for (Track track : dataTracksList.getData()) {
                        String id = track.getId();
                        for (Track favTrack : result) {
                            if (favTrack.getId().equals(id)) {
                                track.setFavorite(true);
                            }
                        }
                    }
                    resultListener.finish(album);
                }
            });
        } else {
            resultListener.finish(album);
        }
    }

    public void getFavorites (final ResultListener<List<Track>> resultListener) {
        DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.getFavoriteTracks(new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> result) {
                resultListener.finish(result);
            }
        });
    }

    public void addFavorite (Track track) {
        DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.addFavorite(track);
    }

    public void removeFavorite (Track track) {
        DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.removeFavorite(track);
    }

    public void getRecentAlbums (ResultListener<List<Album>> resultListener) {
        DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.getRecentAlbums(resultListener);
    }

    // todo - esta funcion no fue testeada al 100% (queues)
    // No funciona bien si se piden mas de un album a la vez, pero no deberia pasar eso.
    private void addRecentAlbum (final Album album) {
        final DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.getRecentsQueue(new ResultListener<Queue<String>>() {
            @Override
            public void finish(Queue<String> result) {
                if (result.size() >= 10) {
                    daoFirebase.removeRecentAlbum(result.poll());
                }
                daoFirebase.addRecentAlbum(album);
                result.offer(album.getId());
                daoFirebase.updateRecentsQueue(result);
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
