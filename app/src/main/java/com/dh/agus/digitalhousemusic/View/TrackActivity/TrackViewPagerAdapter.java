package com.dh.agus.digitalhousemusic.View.TrackActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> trackFragmentList = new ArrayList<>();

    public TrackViewPagerAdapter(FragmentManager fm, Album album) {
        super(fm);
        // Busco el tracklist
        DataTracksList dataTracksList = album.getTracks();
        List<Track> trackList = dataTracksList.getData();
        // Para cada track del tracklist
        for (Track track:trackList) {
            Fragment trackFragment;
            if (track.getAlbum() == null) {
                // Si el album dentro del track es null, lo busca en el album donde vino
                trackFragment = TrackFragment.trackFragmentFabric(album.getCoverMedium());
            } else {
                // Si no es null, lo busca en el album dentro del track
                trackFragment = TrackFragment.trackFragmentFabric(track.getAlbum().getCoverMedium());
            }
            trackFragmentList.add(trackFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return trackFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return trackFragmentList.size();
    }
}


