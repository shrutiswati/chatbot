package com.shrutiswati.banasthalibot.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shrutiswati.banasthalibot.R;
import com.shrutiswati.banasthalibot.helpers.BanasthaliBotUtils;

import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout mFlContainer;
    private ChatFragment mChatFragment;
    private SettingsFragment mSettingsFragment;
    private FeedbackFragment mFeedbackFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialize variables
        initVars();

        //set listeners
        setListeners();

        //load default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, mChatFragment).commit();
    }

    private void initVars() {
        mChatFragment = new ChatFragment();
        mSettingsFragment = new SettingsFragment();
        mFeedbackFragment = new FeedbackFragment();

        mFlContainer = (FrameLayout)findViewById(R.id.fl_container);
    }

    private void setListeners(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        FragmentTransaction ftChat = getSupportFragmentManager().beginTransaction();
                        ftChat.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ftChat.replace(R.id.fl_container, mChatFragment).commit();
                        return true;
                    case R.id.navigation_feedback:
                        FragmentTransaction ftFeedback = getSupportFragmentManager().beginTransaction();
                        ftFeedback.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ftFeedback.replace(R.id.fl_container, mFeedbackFragment).commit();
                        return true;
                    case R.id.navigation_settings:
                        FragmentTransaction ftSettings = getSupportFragmentManager().beginTransaction();
                        ftSettings.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ftSettings.replace(R.id.fl_container, mSettingsFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }



}
