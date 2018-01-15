package com.shrutiswati.banasthalibot;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by shruti suman on 1/14/2018.
 */

public class Display extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        String username =getIntent().getStringExtra("Username");
        TextView tv=(TextView)findViewById(R.id.TVusername);
        tv.setText(username);

    }

}
