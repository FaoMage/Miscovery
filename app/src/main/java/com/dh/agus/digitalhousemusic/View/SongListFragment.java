package com.dh.agus.digitalhousemusic.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongListFragment extends Fragment {

    public static final String KEY_ALBUM = "KEY_ALBUM";

    public static SongListFragment SongListFragmentFactory(Album album) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ALBUM,album);
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

        // Se extrae del album los tracks
        DataTracksList dataTracksList = album.getTracks();
        List<Track> songsList = dataTracksList.getData();

        // Se infla la view
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        // Se setea el ClickListener del boton <.
        ImageView backButton = view.findViewById(R.id.imageViewBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Funcion: Volver a lista de albumes", Toast.LENGTH_SHORT).show();
            }
        });

        // Se crea el RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_MainActivity);

        // RecyclerViewAdapter | getActivity pasa la información directamente a la actividad
        //todo explicar que es el (RecyclerViewAdapter.RecyclerViewInterface) getActivity()
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),
                songsList, (RecyclerViewAdapter.RecyclerViewInterface) getActivity());

        // Se crea el layoutManager
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        // Se setean el adapter y el layoutManager
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //Se agrega un background difuminado
        ImageView imageViewSongListBackground = view.findViewById(R.id.imageViewSongListBackground);
        Picasso.with(getContext()).load(album.getCoverMedium()).into(imageViewSongListBackground);

        // Se cambia el nombre del topbar
        TextView textViewTopBarSongName = view.findViewById(R.id.textViewAlbumName);
        textViewTopBarSongName.setText(album.getTitle());

        return view;
    }
}
