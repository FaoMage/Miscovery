package com.dh.agus.digitalhousemusic.View.TrackActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;

public class TrackFragment extends Fragment {
    public static final String TRACK = "track";

    public static TrackFragment trackFragmentFabric(Track track) {
        TrackFragment trackFragment = new TrackFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TRACK, track);
        trackFragment.setArguments(bundle);

        return trackFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track, container, false);

        Bundle bundle = getArguments();
        Track track = bundle.getParcelable(TRACK);

        // Carga la imagen del album principal
        // TODO - ver porque no levanta el album
        Album album = track.getAlbum();

        ImageView imageAlbum = (ImageView) view.findViewById(R.id.imageViewAlbumImage);
        Glide.with(this).load("https://e-cdns-images.dzcdn.net/images/cover/396027cb1a5886a50e97be30ac819e3d/250x250-000000-80-0-0.jpg").into(imageAlbum);
        return view;
    }

}
