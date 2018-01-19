package com.shrutiswati.banasthalibot.db.models;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Rohit Gupta on 19/1/18.
 */

public class SettingsTable extends RealmObject {
    @PrimaryKey
    public String userId;
    public String colorPref;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getColorPref() {
        return colorPref;
    }

    public void setColorPref(String colorPref) {
        this.colorPref = colorPref;
    }
}
