package com.dh.agus.digitalhousemusic.View.MainActivity.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dh.agus.digitalhousemusic.Controller.Controller;
import com.dh.agus.digitalhousemusic.Model.DAO.ResultListener;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.MainActivity.MainActivity;
import com.dh.agus.digitalhousemusic.View.MainActivity.SongLists.SongListFragment;
import com.dh.agus.digitalhousemusic.View.TrackActivity.SongActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private List<Album> albumList;
    private RecentListenedRecyclerViewAdapter recentListenedRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        albumList = new ArrayList<>();

        TextView textViewTopBar = view.findViewById(R.id.textViewTopBar);
        textViewTopBar.setText(R.string.topbar_home);
        ImageView imageViewBack = view.findViewById(R.id.imageViewBack);
        imageViewBack.setVisibility(View.GONE);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_home);
        RecentListenedRecyclerViewAdapter.RecentListenedRecyclerViewAdapterInteface listener =
                new RecentListenedRecyclerViewAdapter.RecentListenedRecyclerViewAdapterInteface() {
                    @Override
                    public void onClickAlbum(Album album) {
                        MainActivity m = (MainActivity) getContext();
                        SongListFragment songListFragment = SongListFragment.SongListFragmentFactory(album);
                        m.changeFragment(songListFragment,MainActivity.NOT_HOME);
                    }
                };
        recentListenedRecyclerViewAdapter =
                new RecentListenedRecyclerViewAdapter(getContext(),albumList,listener);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(recentListenedRecyclerViewAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        getTestList();

        return view;
    }

    private void getTestList () {
        Controller controller = new Controller();
        controller.getAlbum("5979050", new ResultListener<Album>() {
            @Override
            public void finish(Album result) {
                albumList.add(result);
                recentListenedRecyclerViewAdapter.updateList(albumList);
                recentListenedRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        controller.getAlbum("302127", new ResultListener<Album>() {
            @Override
            public void finish(Album result) {
                albumList.add(result);
                recentListenedRecyclerViewAdapter.updateList(albumList);
                recentListenedRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
