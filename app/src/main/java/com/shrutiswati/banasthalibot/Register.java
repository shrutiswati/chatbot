package com.shrutiswati.banasthalibot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by shruti suman on 1/14/2018.
 */

public class Register extends Activity {
    DatabaseHelper helper=new DatabaseHelper(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button but1 = (Button) findViewById(R.id.registerbutton);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    EditText Name=(EditText)findViewById(R.id.registername);
                    EditText Email=(EditText)findViewById(R.id.registeremail);
                    EditText Username=(EditText)findViewById(R.id.registerusername);
                    EditText Password=(EditText)findViewById(R.id.registerpassword);
                    EditText password=(EditText)findViewById(R.id.registerconfirmpassword);

                    String Namestr=Name.getText().toString();
                    String Emailstr=Email.getText().toString();
                    String Usernamestr=Username.getText().toString();
                    String Passwordstr=Password.getText().toString();
                    String passwordstr=password.getText().toString();
                    if(!Passwordstr.equals(passwordstr))
                    {
                        Toast pass=Toast.makeText(Register.this,"Passwords don't match.Try again!",Toast.LENGTH_LONG);
                        pass.show();
                    }
                    else{
                        Contact c=new Contact();
                        c.setName(Namestr);
                        c.setEmail(Emailstr);
                        c.setUname(Usernamestr);
                        c.setPass(Passwordstr);
                        helper.insertContact(c);
                        Toast.makeText(Register.this, "You've successfully registered!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Register.this, Login.class);
                        startActivity(i);
                    }

                }

            });
        }
    }
