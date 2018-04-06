package com.khtn.hang.excercise_week5;

import android.app.Application;

import io.realm.Realm;


public class MyApp extends Application {

    private static final String TAG = MyApp.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
