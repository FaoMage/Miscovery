package com.dh.agus.digitalhousemusic.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dh.agus.digitalhousemusic.Model.POJO.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> trackFragmentList = new ArrayList<>();

    public TrackViewPagerAdapter(FragmentManager fm, List<Track> trackList) {
        super(fm);
        for (Track track:trackList) {
            Fragment trackFragment = TrackFragment.trackFragmentFabric(track);
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


