package com.dh.agus.digitalhousemusic;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        fragmentTransaction.add(R.id.frame_MainActivity,mainActivityFragment);
        fragmentTransaction.commit();
    }
}
