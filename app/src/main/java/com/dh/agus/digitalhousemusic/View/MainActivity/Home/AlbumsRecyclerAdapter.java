package com.dh.agus.digitalhousemusic.View.MainActivity.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.R;

import java.util.ArrayList;
import java.util.List;


public class AlbumsRecyclerAdapter extends RecyclerView.Adapter<AlbumsRecyclerAdapter.MyItemHolder> {
    private Context context;
    private List<Album> data = new ArrayList<>();
    private AlbumsRecyclerAdapter.RecyclerViewInterface listener;

    public AlbumsRecyclerAdapter(Context context, List<Album> data, RecyclerViewInterface listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public MyItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlbumsRecyclerAdapter.MyItemHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyItemHolder holder, final int position) {
        final Album album = data.get(position);

        // SETEO EL DATO EN EL VIEWHOLDER
        holder.loadAlbum(album);

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.albumOnClick(position, album);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateList(List<Album> albumList) {
        this.data = albumList;
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        TextView mTitle;


        public MyItemHolder(View itemView) {
            super(itemView);

            mImg = (ImageView) itemView.findViewById(R.id.item_img);
            mTitle = (TextView) itemView.findViewById(R.id.item_title);
        }

        public void loadAlbum(Album album){
            Glide.with(mImg.getContext()).load(album.getCoverMedium()).into(mImg);
            mTitle.setText(album.getTitle());
        }
    }

    public interface RecyclerViewInterface {
        void albumOnClick(Integer albumPosition, Album album);
    }
}