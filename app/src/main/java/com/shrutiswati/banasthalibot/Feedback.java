package com.shrutiswati.banasthalibot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Feedback extends AppCompatActivity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        final EditText to=(EditText)findViewById(R.id.feedback_email);
        final EditText subject=(EditText)findViewById(R.id.feedback_subject);
        final EditText message=(EditText)findViewById(R.id.feedback_message);
        Button sendE=(Button)findViewById(R.id.feedback_button);
        sendE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toS=to.getText().toString();
                if(isValidEmaillId(to.getText().toString().trim())){
                    String subS=subject.getText().toString();
                    String mesS=message.getText().toString();
                    Intent email=new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL,new String[] {toS});
                    email.putExtra(Intent.EXTRA_SUBJECT,subS);
                    email.putExtra(Intent.EXTRA_TEXT,mesS);
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email,"Choose app to send mail"));
                }else{
                    Toast.makeText(getApplicationContext(), "InValid Email Address.", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
    private boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }



}
