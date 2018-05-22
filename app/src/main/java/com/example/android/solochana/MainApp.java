package com.example.android.solochana;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by ajays on 22-05-2018.
 */

public class MainApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
