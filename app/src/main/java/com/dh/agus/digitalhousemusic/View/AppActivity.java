package com.dh.agus.digitalhousemusic.View;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dh.agus.digitalhousemusic.R;

public class AppActivity extends AppCompatActivity {
    Activity activity;
    Context context;

    public void setAppBarContext(Context context, Activity activity){
        this.context=context;
        this.activity=activity;
    }

    public void implementAppBar(){
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
    }

    public void implementActivityDrawer(int drawerLayout) {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(drawerLayout);

        // ANIMACION DEL BOTON HAMBURGUESA
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
}
