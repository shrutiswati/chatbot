package com.shrutiswati.banasthalibot.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shrutiswati.banasthalibot.R;
import com.shrutiswati.banasthalibot.db.tables.UserTable;

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
        Uppercase();
        Lowercase();
        Digit();

    }

    private void setListeners() {
        final Pattern special=Pattern.compile("[^a-z0-9 ]",Pattern.CASE_INSENSITIVE);
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
                UserTable Users = realm.where(UserTable.class).equalTo("emailID", str1).findFirst();
                if(mEtName.getText().toString().isEmpty() || mEtUsername.getText().toString().isEmpty()|| mEtPassword.getText().toString().isEmpty() || mEtConfirmPassword.getText().toString().isEmpty()|| mEtEmail.getText().toString().isEmpty()  )
                {
                    Toast.makeText(RegisterActivity.this, "No field must be empty.Try again!", Toast.LENGTH_SHORT).show();
                }
                else if (!mEtPassword.getText().toString().equals(mEtConfirmPassword.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Passwords don't match.Try again!", Toast.LENGTH_SHORT).show();

                }
                else if(mEtPassword.getText().toString().length()<8)
                {
                    Toast.makeText(RegisterActivity.this, "Password should be of atleast 8 characters!", Toast.LENGTH_SHORT).show();
                }
                else if(!special.matcher(mEtPassword.toString()).find() || !Uppercase() || !Lowercase() || !Digit())
                {
                    Toast.makeText(RegisterActivity.this, "Password must contain atleast one special character,one uppercase letter,one lowercase letter and one digit", Toast.LENGTH_SHORT).show();
                }
                else if(User.equals(mEtUsername.getText().toString()))
                {
                    realm.commitTransaction();
                    realm.close();
                    Toast.makeText(RegisterActivity.this, "Username already exists.Choose another!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(Users.equals(mEtEmail.getText().toString()))
                {
                    realm.commitTransaction();
                    realm.close();
                    Toast.makeText(RegisterActivity.this, "This E-mail id has already been taken.Choose another!", Toast.LENGTH_SHORT).show();
                    return;
                }
                 else {

                    user.setFullName(mEtName.getText().toString());
                    user.setEmailId(mEtEmail.getText().toString());
                    user.setPassword(mEtPassword.getText().toString());
                    user.setUserName(mEtUsername.getText().toString());
                    realm.copyToRealmOrUpdate(user);
                    realm.commitTransaction();
                    realm.close();
                    Toast.makeText(RegisterActivity.this, "Sucessfully registered. Please login with your new ID", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

        });

    }
    public boolean Uppercase()
    {
        for(int i=0;i<mEtPassword.getText().toString().length();i++)
        {
            if(Character.isUpperCase(mEtPassword.toString().charAt(i)))
            {
                return true;
            }

        }
        return false;
    }
    public boolean Lowercase()
    {
        for(int i=0;i<mEtPassword.getText().toString().length();i++)
        {
            if(Character.isLowerCase(mEtPassword.toString().charAt(i)))
            {
                return true;
            }
        }
        return false;
    }
    public boolean Digit()
    {
        for(int i=0;i<mEtPassword.getText().toString().length();i++)
        {
            if(Character.isDigit(mEtPassword.getText().toString().charAt(i)))
            {
                return true;
            }

        }
        return false;
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
