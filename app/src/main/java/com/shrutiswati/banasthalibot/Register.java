package com.shrutiswati.banasthalibot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shrutiswati.banasthalibot.db.BanasthaliBotRealmController;
import com.shrutiswati.banasthalibot.db.models.UserTable;

import io.realm.Realm;

/**
 * Created by shruti suman on 1/14/2018.
 */

public class Register extends Activity {
    EditText mEtName;
    EditText mEtEmail;
    EditText mEtUsername;
    EditText mEtConfirmPassword;
    EditText mEtPassword;
    Button mBtnRegister;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initializeViews();
        setListeners();
    }

    private void setListeners() {
     mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mEtPassword.getText().toString().equals(mEtConfirmPassword.getText().toString())) {
                    Toast.makeText(Register.this, "Passwords don't match.Try again!", Toast.LENGTH_SHORT).show();
                } else {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    UserTable user = new UserTable();
                    user.setFullName(mEtName.getText().toString());
                    user.setEmailId(mEtEmail.getText().toString());
                    user.setPassword(mEtPassword.getText().toString());
                    user.setUserName(mEtUsername.getText().toString());
                    realm.copyToRealmOrUpdate(user);
                    realm.commitTransaction();
                    realm.close();
                    Toast.makeText(Register.this, "Sucessfully registered. Please login with your new ID", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

        });
    }

    private void initializeViews() {
        mEtName = (EditText) findViewById(R.id.registername);
        mEtEmail = (EditText) findViewById(R.id.registeremail);
        mEtUsername = (EditText) findViewById(R.id.registerusername);
        mEtPassword = (EditText) findViewById(R.id.registerpassword);
        mEtConfirmPassword = (EditText) findViewById(R.id.registerconfirmpassword);
        mBtnRegister = (Button) findViewById(R.id.registerbutton);
    }
}
