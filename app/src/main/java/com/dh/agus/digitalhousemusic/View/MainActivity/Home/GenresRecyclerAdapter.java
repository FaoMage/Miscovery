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
import com.dh.agus.digitalhousemusic.Model.POJO.Genre;
import com.dh.agus.digitalhousemusic.R;

import java.util.ArrayList;
import java.util.List;


public class GenresRecyclerAdapter extends RecyclerView.Adapter<GenresRecyclerAdapter.MyItemHolder> {
    private Context context;
    private List<Genre> data = new ArrayList<>();

    public GenresRecyclerAdapter(Context context, List<Genre> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public MyItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GenresRecyclerAdapter.MyItemHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyItemHolder holder, int position) {
        final Genre genre = data.get(position);

        // SETEO EL DATO EN EL VIEWHOLDER
        holder.loadGenre(genre);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateList(List<Genre> genreList) {
        this.data = genreList;
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        TextView mTitle;


        public MyItemHolder(View itemView) {
            super(itemView);

            mImg = (ImageView) itemView.findViewById(R.id.item_img);
            mTitle = (TextView) itemView.findViewById(R.id.item_title);
        }

        public void loadGenre(Genre genre){

            Glide.with(mImg.getContext()).load(genre.getPictureMedium()).into(mImg);
            mTitle.setText(genre.getName());

        }
    }
}