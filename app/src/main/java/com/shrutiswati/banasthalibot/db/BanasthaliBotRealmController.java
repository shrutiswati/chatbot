package com.shrutiswati.banasthalibot.db;

import com.shrutiswati.banasthalibot.db.models.ChatTable;
import com.shrutiswati.banasthalibot.db.models.UserTable;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Rohit Gupta on 15/1/18.
 */

public class BanasthaliBotRealmController {
    private static BanasthaliBotRealmController ourInstance;

    public static BanasthaliBotRealmController getInstance() {
        if(ourInstance == null){
            ourInstance = new BanasthaliBotRealmController();
        }
        return ourInstance;
    }

    private BanasthaliBotRealmController() {
    }

    public List<UserTable> getAllUsers(){
        List<UserTable> users;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        users = realm.where(UserTable.class).findAll();
        realm.commitTransaction();
        realm.close();
        return users;
    }

    public List<ChatTable> getAllChatMessages(){
        List<ChatTable> chats;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        chats = realm.where(ChatTable.class).findAll();
        realm.commitTransaction();
        realm.close();
        return chats;
    }
}
