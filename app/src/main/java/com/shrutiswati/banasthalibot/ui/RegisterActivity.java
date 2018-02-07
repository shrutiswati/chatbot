package com.shrutiswati.banasthalibot.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shrutiswati.banasthalibot.R;
import com.shrutiswati.banasthalibot.db.tables.UserTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;

/**
 * Created by shruti suman on 1/14/2018.
 */

public class RegisterActivity extends Activity {
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
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                UserTable user = new UserTable();
                EditText a = (EditText) findViewById(R.id.registerusername);
                String str = a.getText().toString();
                EditText b = (EditText) findViewById(R.id.registeremail);
                String str1 = a.getText().toString();
                UserTable User = realm.where(UserTable.class).equalTo("userName", str).findFirst();
                UserTable Users = realm.where(UserTable.class).equalTo("emailId", str1).findFirst();
                if (mEtName.getText().toString().isEmpty() || mEtUsername.getText().toString().isEmpty() || mEtPassword.getText().toString().isEmpty() || mEtConfirmPassword.getText().toString().isEmpty() || mEtEmail.getText().toString().isEmpty()) {
                    realm.commitTransaction();
                    realm.close();
                    Toast.makeText(RegisterActivity.this, "No field must be empty.Try again!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    if (unique(User)) {
                        if (islength(mEtPassword.getText().toString())){
                            if (isvalidatePassword(mEtPassword.getText().toString())) {
                                if (samePassword()) {
                                    if (uniques(Users)) {
                                        if (isValidEmaillId(mEtEmail.getText().toString())) {
                                            user.setFullName(mEtName.getText().toString());
                                            user.setEmailId(mEtEmail.getText().toString());
                                            user.setPassword(mEtPassword.getText().toString());
                                            user.setUserName(mEtUsername.getText().toString());
                                            realm.copyToRealmOrUpdate(user);
                                            Toast.makeText(RegisterActivity.this, "Sucessfully registered. Please login with your new ID", Toast.LENGTH_SHORT).show();
                                            realm.commitTransaction();
                                            realm.close();
                                            finish();
                                            return;
                                        }
                                        else{
                                            realm.commitTransaction();
                                            realm.close();
                                            Toast.makeText(getApplicationContext(), "InValid Email Address.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    }
                                    else
                                    {
                                        realm.commitTransaction();
                                        realm.close();
                                        Toast.makeText(RegisterActivity.this, "Email-id already exists.Choose another!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                else{
                                    realm.commitTransaction();
                                    realm.close();
                                    Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
                            else{
                                realm.commitTransaction();
                                realm.close();
                                Toast.makeText(getApplicationContext(), "Password must have an uppercase letter,a lowercase letter,a digit and a special character", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        else{
                            realm.commitTransaction();
                            realm.close();
                            Toast.makeText(getApplicationContext(), "Password must be 8 characters long.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else
                    {

                            realm.commitTransaction();
                            realm.close();
                            Toast.makeText(RegisterActivity.this, "Username already exists.Choose another!", Toast.LENGTH_SHORT).show();
                            return;


                    }




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

    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();


    }

    private boolean ValidEmaillId(String email) {
        if (isValidEmaillId(email))

        {
            return true;
        }
        else {


            return false;
        }

    }

    public boolean unique(UserTable userTable) {
        if (userTable == null)
            return true;
        else {

            return false;
        }
    }
    public boolean uniques(UserTable userTable) {
        if (userTable == null)
            return true;
        else {

            return false;
        }
    }

    public boolean isvalidatePassword(String s) {
        if (validatePassword(s)) {
            return true;
        } else {

            return false;
        }
    }

    public boolean validatePassword(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) {
                return true;
            } else if (Character.isUpperCase(s.charAt(i))) {
                return true;
            } else if (Character.isDigit(s.charAt(i))) {
                return true;
            } else if (hasSpecial(s)) {
                return true;
            }


        }
        return false;
    }


    public boolean islength(String s) {
        if (mEtPassword.getText().toString().length() < 8) {

            return false;
        }
        return true;
    }

    public boolean hasSpecial(String s) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        return m.find();
    }

    public boolean samePassword() {
        if (mEtPassword.getText().toString().equals(mEtConfirmPassword.getText().toString())) {
            return true;
        } else {

            return false;
        }
    }
}