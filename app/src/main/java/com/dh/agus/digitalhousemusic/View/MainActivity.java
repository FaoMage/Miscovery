package com.dh.agus.digitalhousemusic.View;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.R;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerViewInterface  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Carga el fragment.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        fragmentTransaction.add(R.id.frame_MainActivity,mainActivityFragment);
        fragmentTransaction.commit();
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
    public void songOnClick(Integer songId) {
        Intent intent = new Intent(this, SongActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(SongActivity.SONG_ID, songId);

        intent.putExtras(bundle);
        intent.putExtra(Intent.EXTRA_TEXT, songId);
        startActivity(intent);
    }
}
