package com.dh.agus.digitalhousemusic.View.TrackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.AppActivity;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SongActivity extends AppActivity {
    public static final String SONG_POSITION = "SONG_POSITION";
    public static final String SONG_ALBUM = "SONG_ALBUM";
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            final Album album = bundle.getParcelable(SONG_ALBUM);
            Integer trackPosition = bundle.getInt(SONG_POSITION);

            setContentView(R.layout.song_activity);

            setAppBarContext(SongActivity.this, this);
            implementAppBar();

            viewPager = (ViewPager) findViewById(R.id.trackAlbumImageViewPager);
            TrackViewPagerAdapter adapter = new TrackViewPagerAdapter(getSupportFragmentManager(),
                    album);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(trackPosition);

            ViewPager.OnPageChangeListener pagerList = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {}

                @Override
                public void onPageSelected(int i) {
                    DataTracksList dataTracksList = album.getTracks();
                    List<Track> list = dataTracksList.getData();
                    Track track = list.get(i);

                    TextView textViewSongName = (TextView) findViewById(R.id.textViewSongName);
                    textViewSongName.setText(track.getTitle());

                    TextView textViewAlbumName = (TextView) findViewById(R.id.textViewSongAlbumName);
                    //todo esto va a traer un problema despues
                    textViewAlbumName.setText(album.getTitle());

                    setTitle(album.getTitle());
                }
                @Override
                public void onPageScrollStateChanged(int i) {}
            };

            viewPager.addOnPageChangeListener(pagerList);
            pagerList.onPageSelected(trackPosition);

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

            // Carga la imagen blureada al background
            RequestOptions requestOptions = new RequestOptions().bitmapTransform(new BlurTransformation(15));
            ImageView backgroundImageView = (ImageView) findViewById(R.id.imageViewBackground);
            Glide.with(SongActivity.this).load(album.getCoverMedium()).apply(requestOptions).transition(withCrossFade()).into(backgroundImageView);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

}
