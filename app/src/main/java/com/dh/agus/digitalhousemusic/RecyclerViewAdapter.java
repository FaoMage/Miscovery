package com.dh.agus.digitalhousemusic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<String> songsList;
    private RecyclerViewInterface listener;

    public RecyclerViewAdapter (Context context, List<String> songsList, RecyclerViewInterface listener) {
        this.context = context;
        this.songsList = songsList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.structure_recycler_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String songName = songsList.get(position);
        holder.loadView(songName);

        // onclicks codeados de forma rara para mostrar que van a hacer
        holder.itemView.findViewById(R.id.imageViewFavorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.favoriteOnClick();
            }
        });
        holder.itemView.findViewById(R.id.imageViewMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.menuOnClick();
            }
        });
        holder.textViewNameSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.songOnClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewNameSong;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewNameSong = itemView.findViewById(R.id.textView_SongName);
        }

        public void loadView (String songName) {
            textViewNameSong.setText(songName);
        }
    }

    public interface RecyclerViewInterface {
        void favoriteOnClick ();
        void menuOnClick ();
        void songOnClick();
    }
}
