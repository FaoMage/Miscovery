package com.dh.agus.digitalhousemusic.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Song;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer;
import com.dh.agus.digitalhousemusic.R;
import com.squareup.picasso.Picasso;

import at.favre.lib.dali.Dali;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer.retrofit;


public class SongActivity extends AppCompatActivity  implements Callback<Track> {
    static final String SONG_ID = "song_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            // Data
            Integer songID = bundle.getInt(SONG_ID);

            serviceDeezer service = retrofit.create(serviceDeezer.class);

            Call<Track> response = service.getTrack(songID.toString());

            response.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<Track> call, Response<Track> response) {
        int code = response.code();
        if (code == 200) {
            System.out.println(response);

            setContentView(R.layout.song_activity);

            Track track = response.body();
            TextView textViewSongName = (TextView) findViewById(R.id.textViewSongName);
            TextView textViewAlbumName = (TextView) findViewById(R.id.textViewSongAlbumName);

            textViewSongName.setText(track.getTitle());

            Album album = track.getAlbum();
            textViewAlbumName.setText(album.getTitle());

            ImageView backgroundImageView = (ImageView) findViewById(R.id.imageViewBackground);
            Picasso.with(this).load(album.getCoverXl()).into(backgroundImageView);
            Dali.create(this).load(R.drawable.coldplay).blurRadius(25).into(backgroundImageView);

            ImageView imageAlbum = (ImageView) findViewById(R.id.imageViewAlbumImage);
            Picasso.with(this).load(album.getCoverMedium()).into(imageAlbum);

        } else {
            System.out.println("Error");
        }
    }

    @Override
    public void onFailure(Call<Track> call, Throwable throwable) {
        System.out.println("Error Fatal");
    }
}
