package com.shrutiswati.banasthalibot.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.shrutiswati.banasthalibot.R;

/**
 * Created by shruti suman on 2/6/2018.
 */

public class AboutUs extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        final TextView to= (TextView)findViewById(R.id.editT);
    }
}
