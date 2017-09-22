package com.dh.agus.digitalhousemusic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    private List<Song> songsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        // ClickListener del boton <.
        ImageView backButton = view.findViewById(R.id.imageViewBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Funcion: Volver a lista de albumes", Toast.LENGTH_SHORT).show();
            }
        });

        // Agrega canciones para el test.
        loadSongs();

        // Creacion del RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_MainActivity);
        // ClickListeners de cada view del RecyclerView.
        RecyclerViewAdapter.RecyclerViewInterface listener = new RecyclerViewAdapter.RecyclerViewInterface() {
            @Override
            public void favoriteOnClick() {
                Toast.makeText(getContext(), "Funcion: Agregar a favoritos", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void menuOnClick() {
                Toast.makeText(getContext(), "Funcion: Abrir menu de cancion", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void songOnClick() {
                Toast.makeText(getContext(), "Funcion: Ir a la reproduccion", Toast.LENGTH_SHORT).show();
            }
        };
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),
                songsList, listener);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //ItemDecoration: Agrega una linea separadora
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(divider);

        return view;
    }

    // Metodo que carga una base de datos para probar la lista
    private void loadSongs () {
        songsList.add(new Song(1, "Stairway to Heaven", "Led Zeppelin", R.drawable.coldplay, "Led Zeppelin", ""));
        songsList.add(new Song(2, "Come as you are", "Nirvana", R.drawable.coldplay, "Nirvana", ""));
        songsList.add(new Song(3, "Friday I'm in Love", "The Cure", R.drawable.coldplay, "The Cure", ""));
        songsList.add(new Song(4, "Black", "Pearl Jam", R.drawable.coldplay, "Pearl Jam", ""));
        songsList.add(new Song(5, "Plush", "Stone Temple Pilots", R.drawable.coldplay, "Stone Temple Pilots", ""));
        songsList.add(new Song(6, "September", "Earth, Wind & Fire", R.drawable.coldplay, "Earth, Wind & Fire", ""));
        songsList.add(new Song(7, "Sultans of Swing", "Dire Straits", R.drawable.coldplay, "Dire Straits", ""));
        songsList.add(new Song(8, "Layla", "Eric Clapton", R.drawable.coldplay, "Eric Clapton", ""));
        songsList.add(new Song(9, "Could you be loved", "Bob Marley", R.drawable.coldplay, "Bob Marley", ""));
        songsList.add(new Song(10, "Another one bites the dust", "Queen", R.drawable.coldplay, "Queen", ""));
        songsList.add(new Song(11, "Hotel California", "Eagles", R.drawable.coldplay, "Eagles", ""));
        songsList.add(new Song(12, "Losing my religion", "R.E.M", R.drawable.coldplay, "R.E.M", ""));
        songsList.add(new Song(12, "Bad Moon Rising", "Creedence", R.drawable.coldplay, "Creedence", ""));
        songsList.add(new Song(12, "Message in a bottle", "The Police", R.drawable.coldplay, "The Police", ""));
        songsList.add(new Song(12, "Love her madly", "The Doors", R.drawable.coldplay, "The Doors", ""));
    }

}
