package com.shrutiswati.banasthalibot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shruti suman on 1/13/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Register.db";
    private static final String TABLE_NAME="register";
    private  static final String COL_0="ID";
    private static final String COL_1="Name";
    private static final String COL_2="E-mail";
    private static final String COL_3="Username";
    private static final String COL_4="Password";
    SQLiteDatabase db;
    private static final String TABLE_CREATE="create table register(ID integer primary key not null,"+"Name text not null, E-mail text not null,Username text primary key not null,Password text not null);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db=db;
    }
    public void insertContact(Contact c){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        String query="select * from register";
        Cursor cursor=db.rawQuery(query,null);
        int count= cursor.getCount();
        values.put(COL_0,count);
        values.put(COL_1,c.getName());
        values.put(COL_2,c.getEmail());
        values.put(COL_1,c.getUname());
        values.put(COL_1,c.getPass());
        db.insert(TABLE_NAME,null,values);
        db.close();

    }
    public String searchPass(String Username){
        db=this.getReadableDatabase();
        String query="select Username,Password from"+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        String a,b;
        b="not found";
        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(0);

                if(a.equals(Username)){
                    b=cursor.getString(1);
                    break;
                }

            }
            while(cursor.moveToNext());
        }
        return  b;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String  query="DROP TABLE IF EXISTS"+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);


    }
}
