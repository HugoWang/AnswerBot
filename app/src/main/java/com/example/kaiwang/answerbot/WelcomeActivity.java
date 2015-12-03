package com.example.kaiwang.answerbot;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    Button explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ActionBar myActionBar=getSupportActionBar();
        myActionBar.hide();

        Typeface tf = Typeface.createFromAsset(getAssets(),"RobotoCondensed-Regular.ttf");
        TextView tv = (TextView)findViewById(R.id.welcome_font);
        tv.setTypeface(tf);

        explore = (Button)findViewById(R.id.explore);
        Typeface explore_button = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        explore.setTypeface(explore_button);


        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEntry = new Intent(getApplicationContext(), EntryActivity.class);
                startActivity(toEntry);
            }
        });

        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(WelcomeActivity.this, EntryActivity.class);
                WelcomeActivity.this.startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }
        }, 1500);
        */
    }
}
