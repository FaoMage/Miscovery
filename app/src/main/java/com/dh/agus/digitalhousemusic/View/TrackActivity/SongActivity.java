package com.dh.agus.digitalhousemusic.View.TrackActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.AppActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SongActivity extends AppActivity {
    public static final String SONG_POSITION = "SONG_POSITION";
    public static final String SONG_ALBUM = "SONG_ALBUM";
    private Track actualTrack;
    private ViewPager viewPager;
    private MediaPlayer mMediaPlayer;
    private ImageView mPlayerControl;
    private SeekBar mSeekBar;
    Handler handler;
    Runnable runnable;
    TextView mProgressBarStarts;
    TextView mProgressBarEnds;


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

            mPlayerControl = (ImageView) findViewById(R.id.imageViewPlayerControl);

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    togglePlayPause();
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mPlayerControl.setImageResource(R.drawable.ic_play);
                }
            });

            handler = new Handler();
            mProgressBarStarts = (TextView) findViewById(R.id.textViewProgressBarStarts);
            mProgressBarEnds = (TextView) findViewById(R.id.textViewProgressBarEnds);
            mSeekBar = (SeekBar) findViewById(R.id.textViewSeekBar);
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                    if (input) {
                        mMediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

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
                    actualTrack = track;

                    TextView textViewSongName = (TextView) findViewById(R.id.textViewSongName);
                    textViewSongName.setText(track.getTitle());

                    TextView textViewAlbumName = (TextView) findViewById(R.id.textViewSongAlbumName);
                    textViewAlbumName.setText(track.getArtist().getName());
                    setTitle(album.getTitle());

                    mMediaPlayer.stop();
                    mMediaPlayer.reset();

                    try {
                        mMediaPlayer.setDataSource(track.getPreview());
                        mMediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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

            mPlayerControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if (isConnected) {
                        togglePlayPause();
                    } else {
                        String message = "Debes estar conectado a internet para reproducir la cancion";
                        Toast.makeText(SongActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Carga la imagen blureada al background
            RequestOptions requestOptions = new RequestOptions()
                    .bitmapTransform(new BlurTransformation(15));
            ImageView backgroundImageView = (ImageView) findViewById(R.id.imageViewBackground);
            LinearLayout linearLayout = findViewById(R.id.linealLayout_song);

            if (album.getTitle().equals("Favoritos")){
              linearLayout.setBackgroundResource(R.color.colorTransparent);
            }
            Glide.with(SongActivity.this).load(album.getCoverMedium()).apply(requestOptions).transition(withCrossFade()).into(backgroundImageView);
        }
    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    public void playCycle() {
        mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
        mProgressBarStarts.setText(getTimeString(mMediaPlayer.getCurrentPosition()));
        if (mMediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        handler.removeCallbacks(runnable);
    }

    private void togglePlayPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPlayerControl.setImageResource(R.drawable.ic_play);
        } else {
            mMediaPlayer.start();
            mSeekBar.setMax(mMediaPlayer.getDuration());
            mProgressBarEnds.setText(getTimeString(mMediaPlayer.getDuration()));
            mPlayerControl.setImageResource(R.drawable.ic_pause);
            playCycle();
        }
    }

    // create an action bar button
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_track_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_menuTrackActivity_share) {
            shareSong();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareSong() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hey! Tengo una canción para tí: " + actualTrack.getTitle() + "("
                + actualTrack.getArtist().getName() + ") - " + actualTrack.getLink();
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Compartir via"));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

}
