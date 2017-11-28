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
import com.dh.agus.digitalhousemusic.Model.POJO.FavoriteTrack;
import com.dh.agus.digitalhousemusic.Model.POJO.Favoritos;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.AppActivity;
import com.dh.agus.digitalhousemusic.View.LoginActivity.LoginActivity;
import com.dh.agus.digitalhousemusic.View.MainActivity.Favorites.FavoritesFragment;
import com.dh.agus.digitalhousemusic.View.MainActivity.Home.HomeFragment;
import com.dh.agus.digitalhousemusic.View.MainActivity.PlayList.PlaylistFragment;
import com.dh.agus.digitalhousemusic.View.MainActivity.SongLists.SongListFragment;
import com.dh.agus.digitalhousemusic.View.MainActivity.SongLists.SongListRecyclerViewAdapter;
import com.dh.agus.digitalhousemusic.View.TrackActivity.SongActivity;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppActivity
        implements SongListRecyclerViewAdapter.RecyclerViewInterface {

    public static final String HOME = "HOME";
    public static final String NOT_HOME = "NOT_HOME";

    private FirebaseAuth mAuth;
    private Boolean logged = false;
    private String loggedUser = null;

    private NavigationView navigationView;

    private HomeFragment homeFragment;
    private FavoritesFragment favoritesFragment;
    private PlaylistFragment playlistFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAppBarContext(MainActivity.this, this);
        implementAppBar();
        implementActivityDrawer(R.id.drawer_mainActivity);

        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
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
                        changeFragment(homeFragment,HOME, "Miscovery");
                        break;

                    case R.id.menu_favorites:
                        changeFragment(favoritesFragment, NOT_HOME, "Favoritos");
                        break;

                    case R.id.menu_playlist:
                        changeFragment(playlistFragment, NOT_HOME, "Playlist");
                        break;
                }

                return false;
            }
        });
        View view = bottomNavigationView.findViewById(R.id.menu_home);
        view.performClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            logged = true;
            loggedUser = user.getEmail();

            TextView textViewHeader = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView_header_loggedEmail);
            textViewHeader.setText(loggedUser);
            changeLoginLogout();
        }
    }

    // Cambia el fragment actual por el enviado por parametro
    public void changeFragment (Fragment fragment, String tag, String title) {
        // Setear el title
        setTitle(title);

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

    // Metodo para logout
    private void logout () {
        logged = false;
        loggedUser = null;
        TextView textViewHeader = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView_header_loggedEmail);
        textViewHeader.setText(loggedUser);

        LoginManager.getInstance().logOut();
        mAuth.signOut();

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
    public void favoriteOnClick(View view, Track track, Album album) {
        if (logged) {
            // Si esta logueado, cambia el corazon a agregado
            ImageView imageView = view.findViewById(R.id.imageViewFavorite);
            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference().child(uId).child("favoritos");
            if (!track.getFavorite()) {
                imageView.setImageResource(R.drawable.ic_favorite_accent_24dp);
                FavoriteTrack favoriteTrack = new FavoriteTrack(track.getId(),
                        track.getTitle(), album.getId(), album.getTitle());
                reference.child(favoriteTrack.getTrackId()).setValue(favoriteTrack);
                track.setFavorite(true);
            } else {
                imageView.setImageResource(R.drawable.ic_favorite_border_accent_24dp);
                reference.child(track.getId()).removeValue();
                track.setFavorite(false);
            }

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
