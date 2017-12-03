package com.dh.agus.digitalhousemusic.View.MainActivity.Home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dh.agus.digitalhousemusic.Controller.Controller;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Albums;
import com.dh.agus.digitalhousemusic.Model.POJO.Artist;
import com.dh.agus.digitalhousemusic.Model.POJO.Artists;
import com.dh.agus.digitalhousemusic.Model.POJO.Genre;
import com.dh.agus.digitalhousemusic.Model.POJO.Genres;
import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.MainActivity.MainActivity;
import com.dh.agus.digitalhousemusic.View.MainActivity.SongLists.SongListFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<Album> albumList;
    private List<Genre> genreList;
    private List<Artist> artistsList;
    private GenresRecyclerAdapter genresRecyclerAdapter;
    private AlbumsRecyclerAdapter albumsRecyclerAdapter;
    private Controller controller = new Controller();
    private ProgressBar progressBarAlbums;
    private ProgressBar progressBarGenres;
    private TextView textViewRecents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBarAlbums = view.findViewById(R.id.progressBar_home_albums);
        progressBarGenres = view.findViewById(R.id.progressBar_home_genres);
        textViewRecents = view.findViewById(R.id.textView_noRecents);
        albumList = new ArrayList<>();
        genreList = new ArrayList<>();
        artistsList = new ArrayList<>();
        final TextView textViewGenre = view.findViewById(R.id.textView_genre);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_home);
        GenresRecyclerAdapter.RecyclerViewInterface listener =
                new GenresRecyclerAdapter.RecyclerViewInterface() {
                    @Override
                    public void genreOnClick(Integer genrePosition, Genre genre) {
                        textViewGenre.setText(genre.getName());
                        getAlbums(genre);
                    }
                };


        genresRecyclerAdapter =
                new GenresRecyclerAdapter(getContext(), genreList, listener);
        LinearLayoutManager gridLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setAdapter(genresRecyclerAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        getGenres();

        AlbumsRecyclerAdapter.RecyclerViewInterface albumListener =
                new AlbumsRecyclerAdapter.RecyclerViewInterface() {
                    @Override
                    public void albumOnClick(Integer albumPosition, final Album album) {
                        MainActivity m = (MainActivity) getContext();
                        SongListFragment songListFragment =
                                SongListFragment.SongListFragmentFactory(album, SongListFragment.TYPE_COMMON);
                        m.changeFragment(songListFragment, MainActivity.NOT_HOME, album.getTitle());
                    }
                };

        RecyclerView albumsRecyclerView = view.findViewById(R.id.recyclerViewAlbums);
        albumsRecyclerAdapter =
                new AlbumsRecyclerAdapter(getContext(), albumList, albumListener);
        LinearLayoutManager albumsGridLayoutManager =
                new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL,false);
        albumsRecyclerView.setAdapter(albumsRecyclerAdapter);
        albumsRecyclerView.setLayoutManager(albumsGridLayoutManager);

        Genre fakeGenre = new Genre();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            fakeGenre.setId(0);
            fakeGenre.setName("Todos");
        } else {
            fakeGenre.setId(-1);
            fakeGenre.setName("Ultimos Escuchados");
        }
        getAlbums(fakeGenre);
        textViewGenre.setText(fakeGenre.getName());

        // No internet handle
        Context context = getContext();
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            progressBarGenres.setVisibility(View.GONE);
            progressBarAlbums.setVisibility(View.GONE);
            textViewGenre.setText("Albums disponibles sin internet");
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                LinearLayout linearLayoutNoInternet = view.findViewById(R.id.linearLayout_home_noInternet);
                ImageView imageViewDivider = view.findViewById(R.id.imageView_home_divider);
                ImageView refresh = view.findViewById(R.id.imageView_home_noInternet_refresh);
                imageViewDivider.setVisibility(View.GONE);
                linearLayoutNoInternet.setVisibility(View.VISIBLE);
                textViewGenre.setVisibility(View.GONE);
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.refreshFragment();
                    }
                });
            }
        }

        return view;
    }

    private void getGenres () {
        progressBarGenres.setVisibility(View.VISIBLE);
        Controller controller = new Controller();
        controller.getAllGender(new ResultListener<Genres>() {
            @Override
            public void finish(Genres result) {
                genreList.addAll(result.getData());
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    genreList.get(0).setName("Ultimos Escuchados");
                    genreList.get(0).setId(-1);
                }
                progressBarGenres.setVisibility(View.GONE);
                genresRecyclerAdapter.updateList(genreList);
                genresRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getAlbums(Genre genre) {
        albumList = new ArrayList<>();
        artistsList = new ArrayList<>();
        textViewRecents.setVisibility(View.GONE);
        albumsRecyclerAdapter.updateList(albumList);
        albumsRecyclerAdapter.notifyDataSetChanged();
        progressBarAlbums.setVisibility(View.VISIBLE);
        if (genre.getId() != -1) {
            controller.getGenderArtists(genre.getId().toString(), new ResultListener<Artists>() {
                @Override
                public void finish(Artists result) {
                    List<Artist> artists = result.getData();
                    if (artists != null) {
                        artistsList.addAll(artists);
                        for (final Artist x : artistsList) {
                            controller.getArtistsAlbums(x.getId(), new ResultListener<Albums>() {
                                @Override
                                public void finish(Albums result) {
                                    List<Album> albums = result.getData();
                                    if (albums != null) {
                                        albumList.addAll(albums);
                                    }
                                    progressBarAlbums.setVisibility(View.GONE);
                                    albumsRecyclerAdapter.updateList(albumList);
                                    albumsRecyclerAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
            });
        } else {
            controller.getRecentAlbums(new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> result) {
                    progressBarAlbums.setVisibility(View.GONE);
                    if (result.size() == 0) {
                        textViewRecents.setVisibility(View.VISIBLE);
                    } else {
                        textViewRecents.setVisibility(View.GONE);
                        albumsRecyclerAdapter.updateList(result);
                        albumsRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
