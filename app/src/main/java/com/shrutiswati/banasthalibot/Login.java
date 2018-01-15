package com.shrutiswati.banasthalibot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shrutiswati.banasthalibot.db.models.UserTable;

import io.realm.Realm;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button but1 = (Button) findViewById(R.id.button);
        Button but2 = (Button) findViewById(R.id.button2);
        but1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText a = (EditText) findViewById(R.id.username);
                String str = a.getText().toString();
                EditText b = (EditText) findViewById(R.id.password);
                String pass = b.getText().toString();
                //find user with user id
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                UserTable user = realm.where(UserTable.class).equalTo("userName", str).findFirst();
                if(user == null){
                    //user not found
                    realm.commitTransaction();
                    realm.close();
                    Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                    return;
                } else if(user.getPassword().equals(pass)) {
                    //user found and passwords match
                    realm.commitTransaction();
                    realm.close();
                    Intent intent = new Intent(Login.this, Display.class);
                    intent.putExtra("USERNAME", str);
                    startActivity(intent);
                    return;
                } else {
                    //user found but passwords do not match
                    realm.commitTransaction();
                    realm.close();
                    Toast.makeText(Login.this, "Invalid password. Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*if (pass.equals(password)) {
                    Intent toy = new Intent(Login.this, Display.class);
                    toy.putExtra("Username", str);
                    startActivity(toy);
                } else {
                    Toast temp = Toast.makeText(Login.this, "Username and Password don't match.Try again!", Toast.LENGTH_LONG);
                    temp.show();
                }*/
            }


        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });


    }


}




