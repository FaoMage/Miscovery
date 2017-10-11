package com.dh.agus.digitalhousemusic.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        TextView textViewTopBar = view.findViewById(R.id.textViewTopBar);
        textViewTopBar.setText(R.string.menu_playlist);
        ImageView imageViewBack = view.findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Funcion: Volver atras", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
