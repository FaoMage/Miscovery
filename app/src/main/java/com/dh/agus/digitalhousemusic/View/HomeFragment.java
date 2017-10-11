package com.dh.agus.digitalhousemusic.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dh.agus.digitalhousemusic.R;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView textViewTopBar = view.findViewById(R.id.textViewTopBar);
        textViewTopBar.setText(R.string.topbar_home);
        ImageView imageViewBack = view.findViewById(R.id.imageViewBack);
        imageViewBack.setVisibility(View.GONE);

        return view;
    }

}
