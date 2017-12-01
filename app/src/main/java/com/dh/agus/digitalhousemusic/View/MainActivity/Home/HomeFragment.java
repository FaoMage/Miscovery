package com.dh.agus.digitalhousemusic.View.MainActivity.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dh.agus.digitalhousemusic.Controller.Controller;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Albums;
import com.dh.agus.digitalhousemusic.Model.POJO.Artist;
import com.dh.agus.digitalhousemusic.Model.POJO.Artists;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Genre;
import com.dh.agus.digitalhousemusic.Model.POJO.Genres;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.MainActivity.MainActivity;
import com.dh.agus.digitalhousemusic.View.MainActivity.SongLists.SongListFragment;
import com.dh.agus.digitalhousemusic.View.TrackActivity.SongActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private List<Album> albumList;
    private List<Genre> genreList;
    private List<Artist> artistsList;
    private GenresRecyclerAdapter genresRecyclerAdapter;
    private AlbumsRecyclerAdapter albumsRecyclerAdapter;
    Controller controller = new Controller();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        albumList = new ArrayList<>();
        genreList = new ArrayList<>();
        artistsList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_home);
        GenresRecyclerAdapter.RecyclerViewInterface listener =
                new GenresRecyclerAdapter.RecyclerViewInterface() {
                    @Override
                    public void genreOnClick(Integer genrePosition, Genre genre) {
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


        // lo que hay q sacar
        AlbumsRecyclerAdapter.RecyclerViewInterface albumListener =
                new AlbumsRecyclerAdapter.RecyclerViewInterface() {
                    @Override
                    public void albumOnClick(Integer albumPosition, final Album album) {
                        /*controller.getTrackList(album.getId().toString(), new ResultListener<DataTracksList>() {
                            @Override
                            public void finish(DataTracksList result) {
                                album.setDataTracksList(result);

                                MainActivity m = (MainActivity) getContext();
                                SongListFragment songListFragment =
                                        SongListFragment.SongListFragmentFactory(album, SongListFragment.TYPE_COMMON);
                                m.changeFragment(songListFragment, MainActivity.NOT_HOME, album.getTitle());

                            }
                        });*/

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
        fakeGenre.setId(0);
        getAlbums(fakeGenre);

        return view;
    }

    private void getGenres () {
        Controller controller = new Controller();
        controller.getAllGender(new ResultListener<Genres>() {
            @Override
            public void finish(Genres result) {
                genreList.addAll(result.getData());
                genresRecyclerAdapter.updateList(genreList);
                genresRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getAlbums(Genre genre) {
        albumList = new ArrayList<>();
        artistsList = new ArrayList<>();
        controller.getGenderArtists(genre.getId().toString(), new ResultListener<Artists>() {
            @Override
            public void finish(Artists result) {
                List<Artist> artists = result.getData();
                if (artists!=null) {
                    artistsList.addAll(artists);
                    for (final Artist x : artistsList) {
                        controller.getArtistsAlbums(x.getId(), new ResultListener<Albums>() {
                            @Override
                            public void finish(Albums result) {
                                List<Album> albums = result.getData();
                                //todo se rompe aca, no se por que

                                if (albums!=null) {
                                    albumList.addAll(albums);
                                }
                                albumsRecyclerAdapter.updateList(albumList);
                                albumsRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }
}
