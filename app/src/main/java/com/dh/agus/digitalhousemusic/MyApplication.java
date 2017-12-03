package com.dh.agus.digitalhousemusic;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ZetaxMage on 01/12/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
