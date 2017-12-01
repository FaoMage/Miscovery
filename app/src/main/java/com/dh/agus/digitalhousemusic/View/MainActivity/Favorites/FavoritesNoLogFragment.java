package com.dh.agus.digitalhousemusic.View.MainActivity.Favorites;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.MainActivity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesNoLogFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites_no_log, container, false);

        Button button = view.findViewById(R.id.button_loginForFavorites);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.login(null);
            }
        });

        return view;
    }


}
