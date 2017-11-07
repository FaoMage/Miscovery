package com.dh.agus.digitalhousemusic.View.MainActivity;

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
import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.AppActivity;
import com.dh.agus.digitalhousemusic.View.LoginActivity.LoginActivity;
import com.dh.agus.digitalhousemusic.View.MainActivity.Home.HomeFragment;
import com.dh.agus.digitalhousemusic.View.MainActivity.PlayList.PlaylistFragment;
import com.dh.agus.digitalhousemusic.View.MainActivity.SongLists.SongListFragment;
import com.dh.agus.digitalhousemusic.View.MainActivity.SongLists.SongListRecyclerViewAdapter;
import com.dh.agus.digitalhousemusic.View.TrackActivity.SongActivity;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import java.util.ArrayList;

public class MainActivity extends AppActivity
        implements SongListRecyclerViewAdapter.RecyclerViewInterface {

    public static final String HOME = "HOME";
    public static final String NOT_HOME = "NOT_HOME";

    public static final String KEY_USER = "KEY_USER";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    private Boolean logged = false;
    private String loggedUser = null;

    private NavigationView navigationView;

    private HomeFragment homeFragment;
    private SongListFragment favoriteSongListFragment;
    private PlaylistFragment playlistFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAppBarContext(MainActivity.this, this);
        implementAppBar();
        implementActivityDrawer(R.id.drawer_mainActivity);

        homeFragment = new HomeFragment();
        favoriteSongListFragment =
                SongListFragment.SongListFragmentFactory(loadHardcodeFavoritos(),SongListFragment.TYPE_FAVORITE);
        playlistFragment = new PlaylistFragment();

        // Busco el DrawerLayout y NavigationView
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_mainActivity);
        navigationView = (NavigationView) findViewById(R.id.navigationView_mainActivity);
        // Le seteo el onSelectedListener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        changeFragment(homeFragment,HOME);
                        break;

                    case R.id.menu_favorites:
                        changeFragment(favoriteSongListFragment,NOT_HOME);
                        break;

                    case R.id.menu_playlist:
                        changeFragment(playlistFragment,NOT_HOME);
                        break;
                }

                return false;
            }
        });
        View view = bottomNavigationView.findViewById(R.id.menu_home);
        view.performClick();
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

    // Cambia el fragment actual por el enviado por parametro
    public void changeFragment (Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Busca el fragment actual
        Fragment loadedFragment = fragmentManager.findFragmentById(R.id.frame_mainActivity);
        // Si el Fragment actual es null o distinto al que quiero cargar
        if (loadedFragment == null || !loadedFragment.equals(fragment)) {
            // Si el Fragment que quiero cargar es Home y ya hay un Home en backstack
            if (tag.equals(HOME) && fragmentManager.getBackStackEntryCount() > 0) {
                // Pone el que estaba en el backstack
                fragmentManager.popBackStack();
            } else {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_mainActivity, fragment, tag);
                // Si el ultimo fragment cargado es el Home, lo agrega al backstack
                if (loadedFragment != null && loadedFragment.getTag().equals(HOME)) {
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();
            }
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
            loggedUser = bundle.getString(KEY_USER);

            // Seteo el email en el drawer
            TextView textView = (TextView) findViewById(R.id.textView_header_loggedEmail);
            textView.setText(loggedUser);

            Toast.makeText(this, "Bienvenido " + loggedUser, Toast.LENGTH_SHORT).show();

            changeLoginLogout();

            logged = true;
        }
    }

    // Metodo para logout
    private void logout () {
        logged = false;
        loggedUser = null;
        TextView textView = (TextView) findViewById(R.id.textView_header_loggedEmail);
        textView.setText(loggedUser);

        LoginManager.getInstance().logOut();

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
    public void songOnClick(Integer songPosition, Album album) {
        Intent intent = new Intent(this, SongActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(SongActivity.SONG_POSITION, songPosition);
        bundle.putParcelable(SongActivity.SONG_ALBUM, album);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
