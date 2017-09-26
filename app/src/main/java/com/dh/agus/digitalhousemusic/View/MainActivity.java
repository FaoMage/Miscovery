package com.dh.agus.digitalhousemusic.View;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer;
import com.dh.agus.digitalhousemusic.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer.retrofit;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerViewInterface, Callback<Album> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serviceDeezer service = retrofit.create(serviceDeezer.class);
        Call<Album> response = service.getAlbum("302127");
        response.enqueue(this);

        setContentView(R.layout.activity_main);

    }

    @Override
    public void onResponse(Call<Album> call, Response<Album> response) {
        int code = response.code();
        if (code == 200) {
            System.out.println(response);

            Album album = response.body();

            Bundle unBundle = new Bundle();

            // GUARDO EL MENSAJE
            unBundle.putParcelable("ALBUM", album);


            // Carga el fragment.
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MainActivityFragment mainActivityFragment = new MainActivityFragment();
            mainActivityFragment.setArguments(unBundle);
            fragmentTransaction.add(R.id.frame_MainActivity, mainActivityFragment);
            fragmentTransaction.commit();
        } else {
            System.out.println("Error");
        }
    }

    @Override
    public void onFailure(Call<Album> call, Throwable throwable) {
        System.out.println("Error Fatal");
    }

    @Override
    public void favoriteOnClick() {
        Toast.makeText(this, "Funcion: Agregar a favoritos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void menuOnClick() {
        Toast.makeText(this, "Funcion: Abrir menu de cancion", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void songOnClick(String songId) {
        Intent intent = new Intent(this, SongActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(SongActivity.SONG_ID, songId);

        intent.putExtras(bundle);
        intent.putExtra(Intent.EXTRA_TEXT, songId);
        startActivity(intent);
    }
}
