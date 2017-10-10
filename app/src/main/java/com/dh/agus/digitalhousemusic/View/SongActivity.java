package com.dh.agus.digitalhousemusic.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer;
import com.dh.agus.digitalhousemusic.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer.retrofit;


public class SongActivity extends AppCompatActivity implements Callback<Track> {
    static final String SONG_ID = "song_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            // Data
            String songID = bundle.getString(SONG_ID);

            serviceDeezer service = retrofit.create(serviceDeezer.class);

            Call<Track> response = service.getTrack(songID);

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
            TextView textViewTopBarSongName = (TextView) findViewById(R.id.textViewTopBar);

            textViewSongName.setText(track.getTitle());

            Album album = track.getAlbum();
            textViewAlbumName.setText(album.getTitle());

            textViewTopBarSongName.setText(track.getTitle());

            ImageView imageAlbum = (ImageView) findViewById(R.id.imageViewAlbumImage);
            ImageView backgroundImageView = (ImageView) findViewById(R.id.imageViewBackground);

            // Carga la imagen blureada al background
            RequestOptions requestOptions = new RequestOptions().bitmapTransform(new BlurTransformation(15));
            Glide.with(this).load(album.getCoverMedium()).apply(requestOptions).into(backgroundImageView);

            // Carga la imagen del album principal
            Glide.with(this).load(album.getCoverMedium()).into(imageAlbum);

            //agrego el back
            ImageView imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
            imageViewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SongActivity.this, "Funcion: Volver al album", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            System.out.println("Error");
        }
    }

    @Override
    public void onFailure(Call<Track> call, Throwable throwable) {
        System.out.println("Error Fatal");
    }
}
