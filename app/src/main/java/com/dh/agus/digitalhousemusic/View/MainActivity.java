package com.dh.agus.digitalhousemusic.View;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Artist;
import com.dh.agus.digitalhousemusic.Model.POJO.DataTracksList;
import com.dh.agus.digitalhousemusic.Model.POJO.Favoritos;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer;
import com.dh.agus.digitalhousemusic.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dh.agus.digitalhousemusic.Model.POJO.serviceDeezer.retrofit;

public class MainActivity extends AppCompatActivity
        implements RecyclerViewAdapter.RecyclerViewInterface, Callback<Album> {

    private static final String HOME = "HOME";
    private static final String NOT_HOME = "NOT_HOME";

    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    private Boolean logged = false;
    private String loggedEmail = null;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final serviceDeezer service = retrofit.create(serviceDeezer.class);
        //Call<Album> response = service.getAlbum("302127");
        Call<Album> response = service.getAlbum("5979050");
        response.enqueue(this);

        // Busco el DrawerLayout y NavigationView
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_mainActivity);
        navigationView = findViewById(R.id.navigationView_mainActivity);
        // Le seteo el onSelectedListener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_menuMainActivity_favoritos:
                        SongListFragment songListFragment =
                                SongListFragment.SongListFragmentFactory(loadHardcodeFavoritos());
                        changeFragment(songListFragment,NOT_HOME);
                        break;

                    case R.id.item_menuMainActivity_login:
                        login(null);
                        break;

                    case R.id.item_menuMainActivity_logout:
                        logout();
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        HomeFragment homeFragment = new HomeFragment();
                        changeFragment(homeFragment,NOT_HOME);
                        break;

                    case R.id.menu_favorites:
                        SongListFragment songListFragment =
                                SongListFragment.SongListFragmentFactory(loadHardcodeFavoritos());
                        changeFragment(songListFragment,NOT_HOME);
                        break;

                    case R.id.menu_playlist:
                        PlaylistFragment playlistFragment = new PlaylistFragment();
                        changeFragment(playlistFragment,NOT_HOME);
                        break;
                }

                return false;
            }
        });
    }

    private Favoritos loadHardcodeFavoritos () {
        Artist artist = new Artist("Fulano de tal");
        DataTracksList dataTracksList = new DataTracksList();
        Track track1 = new Track("Una Cancion de Favoritos",artist);
        Track track2 = new Track("Una Cancion de Favoritos",artist);
        Track track3 = new Track("Una Cancion de Favoritos",artist);
        Track track4 = new Track("Una Cancion de Favoritos",artist);
        Track track5 = new Track("Una Cancion de Favoritos",artist);
        Track track6 = new Track("Una Cancion de Favoritos",artist);
        Track track7 = new Track("Una Cancion de Favoritos",artist);
        Track track8 = new Track("Una Cancion de Favoritos",artist);
        Track track9 = new Track("Una Cancion de Favoritos",artist);
        Track track10 = new Track("Una Cancion de Favoritos",artist);
        Track track11 = new Track("Una Cancion de Favoritos",artist);
        Track track12 = new Track("Una Cancion de Favoritos",artist);
        ArrayList<Track> trackList = new ArrayList<>();
        trackList.add(track1);
        trackList.add(track2);
        trackList.add(track3);
        trackList.add(track4);
        trackList.add(track5);
        trackList.add(track6);
        trackList.add(track7);
        trackList.add(track8);
        trackList.add(track9);
        trackList.add(track10);
        trackList.add(track11);
        trackList.add(track12);
        dataTracksList.setData(trackList);
        Favoritos favoritos = new Favoritos();
        favoritos.setDataTracksList(dataTracksList);
        return favoritos;
    }

    @Override
    public void onResponse(Call<Album> call, Response<Album> response) {
        int code = response.code();
        if (code == 200) {
            // Saca el album del response
            Album album = response.body();

            // Crea el nuevo Fragment y lo cambia
            SongListFragment songListFragment =
                    SongListFragment.SongListFragmentFactory(album);
            changeFragment(songListFragment,HOME);
        } else {
            System.out.println("Error");
        }
    }

    // Cambia el fragment actual por el enviado por parametro
    private void changeFragment (Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Busca el fragment actual
        Fragment loadedFragment = fragmentManager.findFragmentById(R.id.frame_mainActivity);
        // Si el Fragment es null o distinto al que quiero cargar
        //todo mostrar solucion del bug a pedro
        //if (loadedFragment == null || !loadedFragment.getClass().equals(fragment.getClass())) {
        if (loadedFragment == null || !loadedFragment.equals(fragment)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_mainActivity,fragment,tag);
            // Si es el Fragment Home, lo agrega al backstack
            if (loadedFragment != null && loadedFragment.getTag().equals(HOME)) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }

    // Metodo para login y override para obetener el resultado
    private void login (@Nullable String message) {
        Intent intent = new Intent(this,LoginActivity.class);
        Bundle bundle = new Bundle();
        // Le pasa un mensaje para mostrar en el loginActivity
        bundle.putString(LoginActivity.KEY_MESSAGE,message);
        intent.putExtras(bundle);
        // Pide que se cree una actividad esperando un resultado
        startActivityForResult(intent,LoginActivity.REQUEST_LOGIN);
    }

    // Se ejecuta cuando termina el login
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LoginActivity.REQUEST_LOGIN &&
                resultCode == Activity.RESULT_OK &&
                data != null) {

            Bundle bundle = data.getExtras();
            loggedEmail = bundle.getString(KEY_EMAIL);

            // Seteo el email en el drawer
            TextView textView = findViewById(R.id.textView_header_loggedEmail);
            textView.setText(loggedEmail);

            changeLoginLogout();

            logged = true;
        }
    }

    // Metodo para logout
    private void logout () {
        logged = false;
        loggedEmail = null;
        TextView textView = findViewById(R.id.textView_header_loggedEmail);
        textView.setText(loggedEmail);
        changeLoginLogout();
    }

    // Prende o apaga los botones de login y logout
    private void changeLoginLogout (){
        Menu menu = navigationView.getMenu();
        MenuItem login = menu.findItem(R.id.item_menuMainActivity_login);
        MenuItem logout = menu.findItem(R.id.item_menuMainActivity_logout);

        if (login.isVisible() && !logout.isVisible()) {
            login.setVisible(false);
            logout.setVisible(true);
        } else {
            login.setVisible(true);
            logout.setVisible(false);
        }
    }

    // Override de Retrofit
    @Override
    public void onFailure(Call<Album> call, Throwable throwable) {
        System.out.println("Error Fatal");
    }

    // Override de onclicks
    @Override
    public void favoriteOnClick(View view) {
        if (logged) {
            // Si esta logueado, cambia el corazon a agregado
            ImageView imageView = view.findViewById(R.id.imageViewFavorite);
            imageView.setImageResource(R.drawable.ic_favorite_accent_24dp);
        } else {
            // Si no, le pide que se loguee
            login(this.getString(R.string.login_favorites_mensaje));
        }
    }

    @Override
    public void menuOnClick(View view) {
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
