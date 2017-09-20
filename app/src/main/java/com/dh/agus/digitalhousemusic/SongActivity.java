package com.dh.agus.digitalhousemusic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import at.favre.lib.dali.Dali;


public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_activity);

        Song song = new Song(1, "dfsfs", "s", R.drawable.coldplay, "s", "sda");

        ImageView imageAlbum = (ImageView) findViewById(R.id.imageViewAlbumImage);
        imageAlbum.setImageResource(song.getAlbumImage());

        ImageView backgroundImageView = (ImageView) findViewById(R.id.imageViewBackground);

        Dali.create(this).load(R.drawable.coldplay).blurRadius(25).into(backgroundImageView);
    }
}
