package com.answerbot.mobile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EntryActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Button start;
    TextView start_text;
    CheckBox male_check, female_check;
    EditText input_age;
    DataBaseOperations MyDb;
    String Gender;
    String UserID;
    Boolean isNew;
    String my_age;
    int my_age_value, age_input_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.answerbot.mobile.R.layout.activity_entry);
        MyDb = new DataBaseOperations(this);
        UserID= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Cursor res = MyDb.getAllData();
        isNew=(res.getCount()==0);
        //Hide ActionBar
        ActionBar myActionBar=getSupportActionBar();
        try {
            if (myActionBar != null) {
                myActionBar.hide();
            }
        }catch (NullPointerException e){ e.printStackTrace();}

        Typeface tf = Typeface.createFromAsset(getAssets(),"RobotoCondensed-Regular.ttf");
        start_text = (TextView)findViewById(com.answerbot.mobile.R.id.start_text);
        start_text.setTypeface(tf);

        start = (Button)findViewById(com.answerbot.mobile.R.id.start_btn);
        start.setTypeface(tf);

        male_check = (CheckBox)findViewById(com.answerbot.mobile.R.id.male_check);
        female_check = (CheckBox)findViewById(com.answerbot.mobile.R.id.female_check);
        male_check.setTypeface(tf);
        female_check.setTypeface(tf);

        input_age = (EditText)findViewById(com.answerbot.mobile.R.id.editAge);
        input_age.setTypeface(tf);


        male_check.setOnCheckedChangeListener(this);
        female_check.setOnCheckedChangeListener(this);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_age = input_age.getText().toString();
                try {
                    my_age_value = Integer.parseInt(input_age.getText().toString());
                }
                catch (NumberFormatException e){
                    age_input_check = 1;
                }
                if ((male_check.isChecked() || female_check.isChecked()) && (my_age.length() != 0) && (my_age_value < 110)) {
                    if (isNew){
                        Boolean isSuccessful=MyDb.putInfo(UserID,Gender, my_age_value);
                        if (isSuccessful) {
                            Intent toEntry = new Intent(getApplicationContext(), HomeActivity.class);
                            Bundle bun = new Bundle();
                            bun.putString("user_id", UserID);
                            toEntry.putExtras(bun);
                            startActivity(toEntry);
                        } else {
                            Toast.makeText(getApplicationContext(),"Something went wrong...",Toast.LENGTH_SHORT);
                        }
                    } else {
                        MyDb.updateInfo("1",UserID,Gender,my_age_value);
                        finish();
                    }

                } else if ((age_input_check == 1) && (my_age_value > 110)) {
                    Toast.makeText(getApplicationContext(), "Your age is unreliable!", Toast.LENGTH_SHORT).show();
                } else if ((!(male_check.isChecked() || female_check.isChecked())) && (my_age.length() != 0)) {
                    Toast.makeText(getApplicationContext(), "Your gender is missing!", Toast.LENGTH_SHORT).show();
                } else if ((!(male_check.isChecked() || female_check.isChecked())) && (age_input_check!= 0)) {
                    Toast.makeText(getApplicationContext(), "Your age and gender are missing!", Toast.LENGTH_SHORT).show();
                } else if (age_input_check != 0) {
                    Toast.makeText(getApplicationContext(), "Your age is missing!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case com.answerbot.mobile.R.id.male_check:
                if (male_check.isChecked() && isChecked) {
                    female_check.setChecked(false);
                    Gender = "male";
                    Log.d("TEST","male is checked");
                    return;
                }
                break;
            case com.answerbot.mobile.R.id.female_check:
                if (female_check.isChecked() && isChecked) {
                    male_check.setChecked(false);
                    Gender = "female";
                    Log.d("TEST", "female is checked");
                    return;
                }
                break;
            default:
                break;
        }
    }
}
