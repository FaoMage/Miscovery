package com.dh.agus.digitalhousemusic.View.MainActivity.SongLists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;

import java.util.List;

public class SongListRecyclerViewAdapter extends RecyclerView.Adapter<SongListRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private Album album;
    private List<Track> songsList;
    private RecyclerViewInterface listener;
    private String listType;

    public SongListRecyclerViewAdapter(Context context, Album album, RecyclerViewInterface listener, String listType) {
        this.context = context;
        this.album = album;
        DataTracksList dataTracksList = album.getTracks();
        songsList = dataTracksList.getData();
        this.listener = listener;
        this.listType = listType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.itemstructure_recycler_songlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Track track = songsList.get(position);
        holder.loadView(track.getTitle(), track.getArtist().getName());

        if (listType.equals(SongListFragment.TYPE_FAVORITE) ||
            listType.equals(SongListFragment.TYPE_PLAYLIST)) {
            holder.itemView.setBackgroundResource(R.color.colorTransparent);
        }

        holder.imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.favoriteOnClick(view);
            }
        });
        holder.imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.menuOnClick(view);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.songOnClick(position, album);
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
        private ImageView imageViewFavorite;
        private ImageView imageViewMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewNameSong = itemView.findViewById(R.id.textView_SongName);
            this.textViewArtistName = itemView.findViewById(R.id.textView_ArtistName);
            this.linearLayout = itemView.findViewById(R.id.linearLayoutSongTitles);
            this.imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
            this.imageViewMenu = itemView.findViewById(R.id.imageViewMenu);
        }

        public void loadView (String songName, String artistName) {
            textViewNameSong.setText(songName);
            textViewArtistName.setText(artistName);
        }
    }

    public interface RecyclerViewInterface {
        void favoriteOnClick (View view);
        void menuOnClick (View view);
        void songOnClick(Integer songPosition, Album album);
    }
}
