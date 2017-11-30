package com.dh.agus.digitalhousemusic.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dh.agus.digitalhousemusic.View.MainActivity.MainActivity;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ZetaxMage on 26/10/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}