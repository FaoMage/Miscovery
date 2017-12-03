package com.dh.agus.digitalhousemusic.View.MainActivity.Favorites;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dh.agus.digitalhousemusic.Controller.Controller;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.FavoriteAlbum;
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

    private ProgressBar progressBar;
    private FragmentManager fragmentManager;
    private TextView textViewNone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        fragmentManager = getChildFragmentManager();
        progressBar = view.findViewById(R.id.progessBar_favorites);
        textViewNone = view.findViewById(R.id.textView_favorites_none);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // user logueado
            setFavoriteList();
        } else {
            // user no logueado
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FavoritesNoLogFragment favoritesNoLogFragment = new FavoritesNoLogFragment();
            fragmentTransaction.replace(R.id.frame_favorites,favoritesNoLogFragment);
            fragmentTransaction.commit();
        }
    }

    private void setFavoriteList () {
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        progressBar.setVisibility(View.VISIBLE);
        Controller controller = new Controller();
        controller.getFavorites(new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> result) {
                if (result.size() > 0) {
                    DataTracksList dataTracksList = new DataTracksList();
                    ArrayList<Track> favoriteTracks = new ArrayList<>(result);
                    dataTracksList.setData(favoriteTracks);
                    FavoriteAlbum favoriteAlbum = new FavoriteAlbum();
                    favoriteAlbum.setDataTracksList(dataTracksList);

                    SongListFragment songListFragment = SongListFragment.SongListFragmentFactory
                            (favoriteAlbum, SongListFragment.TYPE_FAVORITE);

                    progressBar.setVisibility(View.GONE);
                    fragmentTransaction.replace(R.id.frame_favorites, songListFragment);
                    fragmentTransaction.commit();
                } else {
                    progressBar.setVisibility(View.GONE);
                    textViewNone.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
