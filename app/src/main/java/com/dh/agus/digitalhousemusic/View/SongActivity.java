package com.dh.agus.digitalhousemusic.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SongActivity extends AppCompatActivity implements TrackFragment.TrackFragmentInterface {
    static final String SONG_POSITION = "song_position";
    static final String SONG_TRACKLIST= "song_tracklist";
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            ArrayList<Track> trackList = bundle.getParcelableArrayList(this.SONG_TRACKLIST);
            Integer trackPosition = bundle.getInt(this.SONG_POSITION);

            setContentView(R.layout.song_activity);

            viewPager = (ViewPager) findViewById(R.id.trackAlbumImageViewPager);
            TrackViewPagerAdapter adapter = new TrackViewPagerAdapter(getSupportFragmentManager(),
                    trackList);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(trackPosition);

            ImageView imageViewPrevious = (ImageView) findViewById(R.id.imageViewPrevious);
            imageViewPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(getItem(-1), true);
                }
            });

            ImageView imageViewNext = (ImageView) findViewById(R.id.imageViewNext);
            imageViewNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(getItem(+1), true);
                }
            });

            ImageView imageViewPause = (ImageView) findViewById(R.id.imageViewPause);
            imageViewPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SongActivity.this,
                            "Pausa la cancion", Toast.LENGTH_SHORT).show();
                }
            });

            ImageView imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
            imageViewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SongActivity.this,
                            "Vuelve a la lista", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void implementFragment(String title, String albumTitle, String albumCover) {
        TextView textViewSongName = (TextView) findViewById(R.id.textViewSongName);
        textViewSongName.setText(title);

        TextView textViewAlbumName = (TextView) findViewById(R.id.textViewSongAlbumName);
        textViewAlbumName.setText(albumTitle);

        TextView textViewTopBarSongName = (TextView) findViewById(R.id.textViewTopBar);
        textViewTopBarSongName.setText(title);

        // Carga la imagen blureada al background
        RequestOptions requestOptions = new RequestOptions().bitmapTransform(new BlurTransformation(15));
        ImageView backgroundImageView = (ImageView) findViewById(R.id.imageViewBackground);
        Glide.with(this).load(albumCover).apply(requestOptions).into(backgroundImageView);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

}
