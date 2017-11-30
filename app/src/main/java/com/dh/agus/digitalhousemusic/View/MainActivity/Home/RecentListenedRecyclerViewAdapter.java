package com.dh.agus.digitalhousemusic.View.MainActivity.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.R;

import java.util.List;

/**
 * Created by ZetaxMage on 26/10/2017.
 */

public class RecentListenedRecyclerViewAdapter
        extends RecyclerView.Adapter<RecentListenedRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Album> albumList;
    private RecentListenedRecyclerViewAdapterInteface listener;

    public RecentListenedRecyclerViewAdapter(Context context, List<Album> albumList, RecentListenedRecyclerViewAdapterInteface listener) {
        this.context = context;
        this.albumList = albumList;
        this.listener = listener;
    }

    public void updateList(List<Album> albumList) {
        this.albumList = albumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.itemstructure_recycler_recentlistened,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Album album = albumList.get(i);
        viewHolder.loadView(album);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickAlbum(album);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAlbum;
        TextView textViewArtist;
        ImageView imageViewBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewAlbum = itemView.findViewById(R.id.textView_recentL_album);
            textViewArtist = itemView.findViewById(R.id.textView_recentL_artist);
            imageViewBackground = itemView.findViewById(R.id.imageView_recentL_background);
        }

        public void loadView (Album album) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.placeholder);
            Glide.with(context).load(album.getCoverMedium()).apply(requestOptions).into(imageViewBackground);
            textViewAlbum.setText(album.getTitle());
            textViewArtist.setText(album.getArtist().getName());
        }
    }

    public interface RecentListenedRecyclerViewAdapterInteface {
        void onClickAlbum(Album album);
    }
}
