package com.dh.agus.digitalhousemusic.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;

import java.util.ArrayList;
import java.util.List;

import at.favre.lib.dali.Dali;


public class MainActivityFragment extends Fragment {

    private List<Track> songsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        Album album = bundle.getParcelable("ALBUM");

        DataTracksList dataTracksList = album.getTracks();
        ArrayList<Track> tracks = dataTracksList.getData();

        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        // ClickListener del boton <.
        ImageView backButton = view.findViewById(R.id.imageViewBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Funcion: Volver a lista de albumes", Toast.LENGTH_SHORT).show();
            }
        });

        loadSongs(tracks);

        // Creacion del RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_MainActivity);
        // ClickListeners de cada view del RecyclerView.

        // RecyclerViewAdapter | getActivity pasa la información directamente a la actividad
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),
                    songsList, (RecyclerViewAdapter.RecyclerViewInterface) getActivity());

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //ItemDecoration: Agrega una linea separadora
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(divider);

        //Test songslist background
        ImageView imageViewSongListBackground = view.findViewById(R.id.imageViewSongListBackground);
        Dali.create(getContext()).load(R.drawable.coldplay).blurRadius(25).into(imageViewSongListBackground);

        return view;
    }

    // Metodo que carga una base de datos para probar la lista
    private void loadSongs (ArrayList<Track> tracks) {
        for (Track track : tracks)
        {
            songsList.add(track);
        }
    }

}
