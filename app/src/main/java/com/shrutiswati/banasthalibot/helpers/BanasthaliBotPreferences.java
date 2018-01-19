package com.shrutiswati.banasthalibot.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Rohit Gupta on 19/1/18.
 */

public class BanasthaliBotPreferences {
    private static BanasthaliBotPreferences preferences;
    private SharedPreferences mPrefs;
    private Context mContext;

    private BanasthaliBotPreferences(Context context){
        this.mContext = context;
        mPrefs = context.getSharedPreferences("BanasthaliBotPreference", Context.MODE_PRIVATE);
    }

    public static BanasthaliBotPreferences getInstance(Context context){
        if(preferences == null){
            preferences = new BanasthaliBotPreferences(context);
        }
        return preferences;
    }

    public boolean getBooleanPrefrences(String key) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(key, false);
    }

    public void setBooleanPreferences(String key, boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getStringPreferences(String key) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getString(key, "");
    }

    public void setStringPreferences(String key, String value) {

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        editor.putString(key, value);
        editor.commit();
    }
}
