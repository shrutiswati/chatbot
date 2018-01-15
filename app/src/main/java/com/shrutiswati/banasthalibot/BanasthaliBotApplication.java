package com.shrutiswati.banasthalibot;

import android.app.Application;

import com.shrutiswati.banasthalibot.db.models.ChatTable;
import com.shrutiswati.banasthalibot.db.models.UserTable;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Rohit Gupta on 15/1/18.
 */

public class BanasthaliBotApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().
                name("banasthalibot.realm").
                schemaVersion(1).
                build();
        Realm.setDefaultConfiguration(config);
    }
}
