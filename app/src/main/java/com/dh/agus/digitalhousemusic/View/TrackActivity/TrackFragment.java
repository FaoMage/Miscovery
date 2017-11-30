package com.dh.agus.digitalhousemusic.View.TrackActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;

public class TrackFragment extends Fragment {
    public static final String COVER = "COVER";

    public static TrackFragment trackFragmentFabric(String cover) {
        TrackFragment trackFragment = new TrackFragment();
        Bundle bundle = new Bundle();
        bundle.putString(COVER, cover);
        trackFragment.setArguments(bundle);

        return trackFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track, container, false);

        Bundle bundle = getArguments();
        String cover = bundle.getString(COVER);

        // Carga la imagen del album principal
        ImageView imageAlbum = (ImageView) view.findViewById(R.id.imageViewAlbumImage);
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.placeholder);
        Glide.with(this).load(cover).apply(requestOptions).into(imageAlbum);
        return view;
    }

}
