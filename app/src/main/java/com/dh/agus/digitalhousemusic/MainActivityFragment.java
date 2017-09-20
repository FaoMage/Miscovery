package com.dh.agus.digitalhousemusic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    private List<String> songsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        loadSongs();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_MainActivity);
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
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),songsList,listener);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //item decoration?

        return view;
    }

    // Metodo que carga una base de datos para probar la lista
    private void loadSongs () {
        songsList.add("Musica1");
        songsList.add("Musica2");
        songsList.add("Musica3");
        songsList.add("Musica4");
        songsList.add("Musica5");
        songsList.add("Musica6");
        songsList.add("Musica7");
        songsList.add("Musica8");
        songsList.add("Musica9");
        songsList.add("Musica10");
        songsList.add("Musica11");
        songsList.add("Musica12");
        songsList.add("Musica13");
        songsList.add("Musica14");
        songsList.add("Musica15");
        songsList.add("Musica16");
        songsList.add("Musica17");
        songsList.add("Musica18");
        songsList.add("Musica19");
        songsList.add("Musica20");
    }

}
