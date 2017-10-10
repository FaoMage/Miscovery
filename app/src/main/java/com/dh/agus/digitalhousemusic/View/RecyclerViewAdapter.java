package com.dh.agus.digitalhousemusic.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Track> songsList;
    private RecyclerViewInterface listener;

    public RecyclerViewAdapter (Context context, List<Track> songsList, RecyclerViewInterface listener) {
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
        final Track track = songsList.get(position);
        holder.loadView(track.getTitle(), track.getArtist().getName());

        holder.itemView.findViewById(R.id.imageViewFavorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.favoriteOnClick(view);
            }
        });
        holder.itemView.findViewById(R.id.imageViewMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.menuOnClick(view);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.songOnClick(track.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewNameSong;
        private TextView textViewArtistName;
        private LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewNameSong = itemView.findViewById(R.id.textView_SongName);
            this.textViewArtistName = itemView.findViewById(R.id.textView_ArtistName);
            this.linearLayout = itemView.findViewById(R.id.linearLayoutSongTitles);
        }

        public void loadView (String songName, String artistName) {
            textViewNameSong.setText(songName);
            textViewArtistName.setText(artistName);
        }
    }

    public interface RecyclerViewInterface {
        void favoriteOnClick (View view);
        void menuOnClick (View view);
        void songOnClick(String songId);
    }
}
