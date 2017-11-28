package com.dh.agus.digitalhousemusic.View.MainActivity.Favorites;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dh.agus.digitalhousemusic.Controller.Controller;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Artist;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.FavoriteTrack;
import com.dh.agus.digitalhousemusic.Model.POJO.Favoritos;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.MainActivity.SongLists.SongListFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        final ProgressBar progressBar = view.findViewById(R.id.progessBar_favorites);
        final FragmentManager fragmentManager = getChildFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // user logueado
            progressBar.setVisibility(View.VISIBLE);
            Controller controller = new Controller();
            controller.getFavorites(new ResultListener<List<FavoriteTrack>>() {
                @Override
                public void finish(List<FavoriteTrack> result) {
                    ArrayList<Track> trackList = new ArrayList<>();
                    DataTracksList dataTracksList = new DataTracksList();

                    for (FavoriteTrack favoriteTrack : result) {
                        Album album = new Album();
                        album.setTitle(favoriteTrack.getAlbumTitle());
                        album.setId(favoriteTrack.getAlbumId());
                        Artist artist = new Artist(favoriteTrack.getArtistId(),favoriteTrack.getArtistName());
                        album.setArtist(artist);
                        Track track = new Track(favoriteTrack.getTrackTitle(),
                                favoriteTrack.getTrackId(),album,artist);
                        track.setFavorite(true);
                        trackList.add(track);
                    }

                    dataTracksList.setData(trackList);
                    Favoritos favoritos = new Favoritos();
                    favoritos.setDataTracksList(dataTracksList);

                    SongListFragment songListFragment = SongListFragment.SongListFragmentFactory
                            (favoritos,SongListFragment.TYPE_FAVORITE);

                    progressBar.setVisibility(View.GONE);
                    fragmentTransaction.replace(R.id.frame_favorites,songListFragment);
                    fragmentTransaction.commit();
                }
            });
        } else {
            // user no logueado
            FavoritesNoLogFragment favoritesNoLogFragment = new FavoritesNoLogFragment();
            fragmentTransaction.replace(R.id.frame_favorites,favoritesNoLogFragment);
            fragmentTransaction.commit();
        }




        return view;
    }

}
