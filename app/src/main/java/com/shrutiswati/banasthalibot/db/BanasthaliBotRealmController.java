package com.shrutiswati.banasthalibot.db;

import android.content.Context;

import com.shrutiswati.banasthalibot.db.tables.ChatTable;
import com.shrutiswati.banasthalibot.db.tables.UserTable;
import com.shrutiswati.banasthalibot.helpers.BanasthaliBotPreferences;
import com.shrutiswati.banasthalibot.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;

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

    public List<ChatMessage> getAllChatMessages(String userID){
        List<ChatTable> chats;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        chats = realm.where(ChatTable.class).equalTo("userId", userID).findAll();
        ArrayList<ChatMessage> chatList = new ArrayList<>();
        if(chats != null){
            for(ChatTable dbMessage : chats){
                chatList.add(new ChatMessage(dbMessage.getMessage(), Long.parseLong(dbMessage.getTimestamp()), dbMessage.getMessageOwner()));
            }
        }
        realm.commitTransaction();
        realm.close();
        return chatList;
    }

    public void insertChatMessageToDB(ChatMessage message, String userID){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ChatTable chatTable = new ChatTable();
        chatTable.setUserId(userID);
        chatTable.setMessageOwner(message.getSentBy());
        chatTable.setMessage(message.getMessage());
        chatTable.setTimestamp(String.valueOf(message.getTimestamp()));
        realm.insertOrUpdate(chatTable);
        realm.commitTransaction();
        realm.close();
    }
}
