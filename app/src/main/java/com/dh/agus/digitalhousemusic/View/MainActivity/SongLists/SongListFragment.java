package com.dh.agus.digitalhousemusic.View.MainActivity.SongLists;


import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongListFragment extends Fragment {

    private static final String KEY_ALBUM = "KEY_ALBUM";
    private static final String KEY_LIST_TYPE = "KEY_LIST_TYPE";

    public static final String TYPE_FAVORITE = "TYPE_FAVORITE";
    public static final String TYPE_PLAYLIST = "TYPE_PLAYLIST";
    public static final String TYPE_COMMON = "TYPE_COMMON";
    //todo esta bien usado?
    @StringDef({TYPE_COMMON,TYPE_FAVORITE,TYPE_PLAYLIST})
    private @interface SongListType {}

    public static SongListFragment SongListFragmentFactory(Album album, @SongListType String listType) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ALBUM,album);
        bundle.putString(KEY_LIST_TYPE,listType);
        SongListFragment songListFragment = new SongListFragment();
        songListFragment.setArguments(bundle);
        return songListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Se extrae del bundle el album
        Bundle bundle = getArguments();
        Album album = bundle.getParcelable(KEY_ALBUM);
        String listType = bundle.getString(KEY_LIST_TYPE);

        // Se extrae del album los tracks
        DataTracksList dataTracksList = album.getTracks();
        List<Track> songsList = dataTracksList.getData();

        // Se infla la view
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);

        // Se crea el RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_songListFragment);

        // SongListRecyclerViewAdapter | getActivity pasa la información directamente a la actividad
        SongListRecyclerViewAdapter songListRecyclerViewAdapter = new SongListRecyclerViewAdapter(getContext(),
                songsList, (SongListRecyclerViewAdapter.RecyclerViewInterface) getActivity(), listType);

        // Se crea el layoutManager
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        // Se setean el adapter y el layoutManager
        recyclerView.setAdapter(songListRecyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //Se agrega un background difuminado
        ImageView imageViewSongListBackground = view.findViewById(R.id.imageViewSongListBackground);
        RequestOptions requestOptions = new RequestOptions().bitmapTransform(new BlurTransformation(15));
        Glide.with(getContext()).load(album.getCoverMedium()).apply(requestOptions).into(imageViewSongListBackground);

        return view;
    }
}